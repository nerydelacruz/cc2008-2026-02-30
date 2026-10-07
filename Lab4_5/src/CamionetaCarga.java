/* Clase de la camioneta de carga. Pone sus reglas de recargo, licencia y mantenimiento. */
public class CamionetaCarga extends Vehiculo {

    private double capacidadToneladas;
    private static final double RECARGO_POR_TONELADA = 100;
    private static final int UMBRAL_MANTENIMIENTO = 15;

    /* Constructor. Revisa que la capacidad sea mayor que 0 (puede llevar decimales) */
    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria, double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);

        if (capacidadToneladas <= 0) {
            throw new IllegalArgumentException("La capacidad de carga debe ser mayor que 0");
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    /* Q100 por tonelada de capacidad maxima por dia, aunque lleve menos carga */
    @Override
    protected double calcularRecargo(int dias) {
        return RECARGO_POR_TONELADA * capacidadToneladas * dias;
    }

    /* La camioneta pide licencia B o superior */
    @Override
    public TipoLicencia getLicenciaRequerida() {
        return TipoLicencia.B;
    }

    @Override
    public int getUmbralMantenimiento() {
        return UMBRAL_MANTENIMIENTO;
    }

    @Override
    public String getCategoria() {
        return "Camioneta de carga";
    }

    @Override
    public String describirCaracteristicas() {
        return "Capacidad maxima: " + capacidadToneladas + " toneladas";
    }
}
