/* Clase del automovil. Pone sus reglas de recargo, licencia y mantenimiento. */
public class Automovil extends VehiculoPasajeros {

    private boolean transmisionAutomatica;
    private static final double RECARGO_AUTOMATICO = 50;
    private static final int UMBRAL_MANTENIMIENTO = 30;

    /* Constructor. Los pasajeros los revisa VehiculoPasajeros */
    public Automovil(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean transmisionAutomatica) {
        super(placa, marca, modelo, tarifaDiaria, cantidadPasajeros);
        this.transmisionAutomatica = transmisionAutomatica;
    }

    public boolean isTransmisionAutomatica() {
        return transmisionAutomatica;
    }

    /* Q50 por dia si es automatico, nada si es manual */
    @Override
    protected double calcularRecargo(int dias) {
        if (transmisionAutomatica == true) {
            return RECARGO_AUTOMATICO * dias;
        }

        return 0;
    }

    /* El automovil pide licencia C o superior */
    @Override
    public TipoLicencia getLicenciaRequerida() {
        return TipoLicencia.C;
    }

    @Override
    public int getUmbralMantenimiento() {
        return UMBRAL_MANTENIMIENTO;
    }

    @Override
    public String getCategoria() {
        return "Automovil";
    }

    /* Pasajeros y tipo de transmision */
    @Override
    public String describirCaracteristicas() {
        String transmision = "manual";

        if (transmisionAutomatica == true) {
            transmision = "automatica";
        }

        return "Pasajeros: " + getCantidadPasajeros() + " | Transmision: " + transmision;
    }
}
