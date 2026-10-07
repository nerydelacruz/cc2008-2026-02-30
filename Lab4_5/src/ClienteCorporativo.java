import java.util.ArrayList;

/* Clase del cliente corporativo. Es una empresa con NIT, hasta 3 alquileres activos y siempre 10 %. */
public class ClienteCorporativo extends Cliente {

    private String nombreContacto;
    private static final int LIMITE_ACTIVOS = 3;
    private static final double PORCENTAJE_DESCUENTO = 0.10;

    /* Constructor. El nombre de la empresa se guarda como el nombre del cliente */
    public ClienteCorporativo(String nit, String nombreEmpresa, String nombreContacto, ArrayList<TipoLicencia> licencias) {
        super(nit, nombreEmpresa, licencias);

        if (nombreContacto == null || nombreContacto.trim().length() == 0) {
            throw new IllegalArgumentException("El nombre del contacto no puede quedar vacio");
        }

        this.nombreContacto = nombreContacto.trim();
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    /* Siempre 10 % del subtotal */
    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * PORCENTAJE_DESCUENTO;
    }

    @Override
    public int getLimiteAlquileresActivos() {
        return LIMITE_ACTIVOS;
    }

    @Override
    public String getTipoCliente() {
        return "Corporativo";
    }

    /* Agrega el contacto a la descripcion de Cliente */
    @Override
    public String toString() {
        return super.toString() + " | Contacto: " + nombreContacto;
    }
}
