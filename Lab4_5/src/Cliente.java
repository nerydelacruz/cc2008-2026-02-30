import java.util.ArrayList;

/* Clase abstracta del cliente. Guarda los datos comunes, las licencias y los conteos de alquileres. */
public abstract class Cliente {

    private String identificador;
    private String nombre;
    private ArrayList<TipoLicencia> licencias;
    private int alquileresConfirmados;
    private int alquileresActivos;

    /* Constructor. Revisa identificador, nombre y que tenga al menos una licencia. Conteos en 0 */
    protected Cliente(String identificador, String nombre, ArrayList<TipoLicencia> licencias) {
        if (identificador == null || identificador.trim().length() == 0) {
            throw new IllegalArgumentException("El identificador no puede quedar vacio");
        }

        if (nombre == null || nombre.trim().length() == 0) {
            throw new IllegalArgumentException("El nombre no puede quedar vacio");
        }

        if (licencias == null || licencias.size() == 0) {
            throw new IllegalArgumentException("El cliente debe presentar al menos una licencia");
        }

        this.identificador = identificador.trim();
        this.nombre = nombre.trim();
        this.licencias = new ArrayList<TipoLicencia>(licencias);
        this.alquileresConfirmados = 0;
        this.alquileresActivos = 0;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    /* Devuelve una copia para que nadie cambie la lista interna */
    public ArrayList<TipoLicencia> getLicencias() {
        return new ArrayList<TipoLicencia>(licencias);
    }

    public int getAlquileresConfirmados() {
        return alquileresConfirmados;
    }

    public int getAlquileresActivos() {
        return alquileresActivos;
    }

    /* Le pregunta al vehiculo si acepta las licencias de este cliente */
    public boolean puedeConducir(Vehiculo vehiculo) {
        return vehiculo.aceptaLicencias(licencias);
    }

    /* Dice si el cliente ya llego a su limite de alquileres activos */
    public boolean alcanzoLimiteActivos() {
        if (alquileresActivos >= getLimiteAlquileresActivos()) {
            return true;
        }

        return false;
    }

    /* Descuento que le toca segun su tipo */
    public abstract double calcularDescuento(double subtotal);

    /* Maximo de alquileres activos al mismo tiempo */
    public abstract int getLimiteAlquileresActivos();

    /* Nombre del tipo de cliente para mostrar */
    public abstract String getTipoCliente();

    /* Suma uno a confirmados y a activos. Solo se llama al confirmar un alquiler */
    public void registrarAlquilerConfirmado() {
        alquileresConfirmados = alquileresConfirmados + 1;
        alquileresActivos = alquileresActivos + 1;
    }

    /* Resta uno a los activos cuando devuelve el vehiculo */
    public void registrarAlquilerFinalizado() {
        if (alquileresActivos == 0) {
            throw new IllegalStateException("El cliente " + nombre + " no tiene alquileres activos");
        }

        alquileresActivos = alquileresActivos - 1;
    }

    /* Carga alquileres que el cliente ya tenia, solo para los datos iniciales */
    public void cargarAlquileresPrevios(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad de alquileres previos no puede ser negativa");
        }

        alquileresConfirmados = alquileresConfirmados + cantidad;
    }

    /* Junta el tipo, los datos y los conteos del cliente */
    @Override
    public String toString() {
        return getTipoCliente() + " " + identificador + " - " + nombre
                + " | Licencias: " + licencias
                + " | Alquileres confirmados: " + alquileresConfirmados
                + " | Activos: " + alquileresActivos + " de " + getLimiteAlquileresActivos();
    }
}
