import java.util.ArrayList;

/* Clase del cliente individual. Se identifica con DPI, tiene 1 alquiler activo y 5 % desde el cuarto. */
public class ClienteIndividual extends Cliente {

    private static final int LIMITE_ACTIVOS = 1;
    private static final int ALQUILERES_SIN_DESCUENTO = 3;
    private static final double PORCENTAJE_DESCUENTO = 0.05;

    /* Constructor. Ademas de lo que revisa Cliente, el DPI debe tener exactamente 13 digitos */
    public ClienteIndividual(String dpi, String nombre, ArrayList<TipoLicencia> licencias) {
        super(dpi, nombre, licencias);

        if (esDpiValido(dpi) == false) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 digitos");
        }
    }

    /* Revisa caracter por caracter que el texto sean 13 numeros */
    public static boolean esDpiValido(String dpi) {
        if (dpi == null) {
            return false;
        }

        String texto = dpi.trim();

        if (texto.length() != 13) {
            return false;
        }

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            if (caracter < '0' || caracter > '9') {
                return false;
            }
        }

        return true;
    }

    /* Sin descuento en los primeros 3 alquileres. Si ya tiene 3 confirmados, este es el cuarto y lleva 5 % */
    @Override
    public double calcularDescuento(double subtotal) {
        if (getAlquileresConfirmados() >= ALQUILERES_SIN_DESCUENTO) {
            return subtotal * PORCENTAJE_DESCUENTO;
        }

        return 0;
    }

    @Override
    public int getLimiteAlquileresActivos() {
        return LIMITE_ACTIVOS;
    }

    @Override
    public String getTipoCliente() {
        return "Individual";
    }
}
