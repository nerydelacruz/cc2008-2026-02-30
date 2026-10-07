/* Clase de la motocicleta. Pone sus reglas de recargo, licencia y mantenimiento. */
public class Motocicleta extends Vehiculo {

    private int cilindraje;
    private static final int LIMITE_CILINDRAJE = 250;
    private static final double RECARGO_ALTO_CILINDRAJE = 75;
    private static final int UMBRAL_MANTENIMIENTO = 20;

    /* Constructor. Revisa que el cilindraje sea mayor que 0 */
    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser mayor que 0");
        }

        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    /* Q75 una sola vez si pasa de 250 cc, sin importar los dias */
    @Override
    protected double calcularRecargo(int dias) {
        if (cilindraje > LIMITE_CILINDRAJE) {
            return RECARGO_ALTO_CILINDRAJE;
        }

        return 0;
    }

    /* La motocicleta pide licencia M */
    @Override
    public TipoLicencia getLicenciaRequerida() {
        return TipoLicencia.M;
    }

    @Override
    public int getUmbralMantenimiento() {
        return UMBRAL_MANTENIMIENTO;
    }

    @Override
    public String getCategoria() {
        return "Motocicleta";
    }

    @Override
    public String describirCaracteristicas() {
        return "Cilindraje: " + cilindraje + " cc";
    }
}
