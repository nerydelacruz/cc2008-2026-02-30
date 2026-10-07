import java.util.ArrayList;

/* Clase del microbus. El piloto es fijo de cada microbus y cambia el recargo y la licencia. */
public class Microbus extends VehiculoPasajeros {

    private boolean incluyePiloto;
    private static final double RECARGO_PILOTO = 250;
    private static final int UMBRAL_MANTENIMIENTO = 25;

    /* Constructor. Los pasajeros los revisa VehiculoPasajeros */
    public Microbus(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean incluyePiloto) {
        super(placa, marca, modelo, tarifaDiaria, cantidadPasajeros);
        this.incluyePiloto = incluyePiloto;
    }

    public boolean isIncluyePiloto() {
        return incluyePiloto;
    }

    /* Q250 por dia si incluye piloto, nada si no */
    @Override
    protected double calcularRecargo(int dias) {
        if (incluyePiloto == true) {
            return RECARGO_PILOTO * dias;
        }

        return 0;
    }

    /* Pide licencia B o superior. Solo aplica cuando no trae piloto */
    @Override
    public TipoLicencia getLicenciaRequerida() {
        return TipoLicencia.B;
    }

    /* Si trae piloto no se pide licencia porque maneja el piloto de la empresa */
    @Override
    public boolean aceptaLicencias(ArrayList<TipoLicencia> licencias) {
        if (incluyePiloto == true) {
            return true;
        }

        return super.aceptaLicencias(licencias);
    }

    @Override
    public int getUmbralMantenimiento() {
        return UMBRAL_MANTENIMIENTO;
    }

    @Override
    public String getCategoria() {
        return "Microbus";
    }

    /* Pasajeros y si incluye piloto */
    @Override
    public String describirCaracteristicas() {
        String piloto = "sin piloto";

        if (incluyePiloto == true) {
            piloto = "con piloto de la empresa";
        }

        return "Pasajeros: " + getCantidadPasajeros() + " | " + piloto;
    }
}
