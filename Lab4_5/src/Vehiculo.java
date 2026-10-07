import java.util.ArrayList;

/* Clase abstracta del vehiculo. Guarda los datos comunes, maneja el estado y calcula el subtotal. */
public abstract class Vehiculo {

    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private EstadoVehiculo estado;
    private int diasDesdeMantenimiento;

    /* Constructor. Revisa la placa y la tarifa. Todo vehiculo nuevo empieza disponible y con 0 dias */
    protected Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        if (placa == null || placa.trim().length() == 0) {
            throw new IllegalArgumentException("La placa no puede quedar vacia");
        }

        if (marca == null || marca.trim().length() == 0) {
            throw new IllegalArgumentException("La marca no puede quedar vacia");
        }

        if (modelo == null || modelo.trim().length() == 0) {
            throw new IllegalArgumentException("El modelo no puede quedar vacio");
        }

        if (tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que 0");
        }

        this.placa = placa.trim().toUpperCase();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.estado = EstadoVehiculo.DISPONIBLE;
        this.diasDesdeMantenimiento = 0;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public int getDiasDesdeMantenimiento() {
        return diasDesdeMantenimiento;
    }

    /* Dice si el vehiculo se puede alquilar en este momento */
    public boolean estaDisponible() {
        if (estado == EstadoVehiculo.DISPONIBLE) {
            return true;
        }

        return false;
    }

    /* Tarifa por los dias mas el recargo de la categoria. Es final para que la formula sea igual en todos */
    public final double calcularSubtotal(int dias) {
        return tarifaDiaria * dias + calcularRecargo(dias);
    }

    /* Recargo propio de la categoria. Cada subclase lo calcula con sus datos */
    protected abstract double calcularRecargo(int dias);

    /* Licencia minima que pide la categoria */
    public abstract TipoLicencia getLicenciaRequerida();

    /* Revisa si alguna de las licencias del cliente autoriza la licencia que pide el vehiculo */
    public boolean aceptaLicencias(ArrayList<TipoLicencia> licencias) {
        for (int i = 0; i < licencias.size(); i++) {
            if (licencias.get(i).autoriza(getLicenciaRequerida()) == true) {
                return true;
            }
        }

        return false;
    }

    /* Dias acumulados que mandan al vehiculo a mantenimiento */
    public abstract int getUmbralMantenimiento();

    /* Nombre de la categoria para mostrar y para agrupar los reportes */
    public abstract String getCategoria();

    /* Texto con los datos que solo tiene la categoria */
    public abstract String describirCaracteristicas();

    /* Deja el vehiculo alquilado. Solo se puede si estaba disponible */
    public void marcarAlquilado() {
        if (estaDisponible() == false) {
            throw new IllegalStateException("El vehiculo " + placa + " no esta disponible");
        }

        estado = EstadoVehiculo.ALQUILADO;
    }

    /* Suma los dias del alquiler. Si llega al umbral pasa a mantenimiento y devuelve true */
    public boolean registrarDevolucion(int dias) {
        if (estado != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException("El vehiculo " + placa + " no esta alquilado");
        }

        diasDesdeMantenimiento = diasDesdeMantenimiento + dias;

        if (diasDesdeMantenimiento >= getUmbralMantenimiento()) {
            estado = EstadoVehiculo.EN_MANTENIMIENTO;
            return true;
        }

        estado = EstadoVehiculo.DISPONIBLE;
        return false;
    }

    /* Regresa el vehiculo a disponible y deja el acumulado en 0 */
    public void finalizarMantenimiento() {
        if (estado != EstadoVehiculo.EN_MANTENIMIENTO) {
            throw new IllegalStateException("El vehiculo " + placa + " no esta en mantenimiento");
        }

        estado = EstadoVehiculo.DISPONIBLE;
        diasDesdeMantenimiento = 0;
    }

    /* Carga dias que ya traia el vehiculo, solo para los datos iniciales. No cambia el estado */
    public void cargarDiasAcumulados(int dias) {
        if (dias < 0) {
            throw new IllegalArgumentException("Los dias acumulados no pueden ser negativos");
        }

        diasDesdeMantenimiento = dias;
    }

    /* Junta los datos comunes, el estado y las caracteristicas de la categoria */
    @Override
    public String toString() {
        return getCategoria() + " " + placa + " - " + marca + " " + modelo
                + " | " + describirCaracteristicas()
                + " | Tarifa diaria: Q" + String.format("%,.2f", tarifaDiaria)
                + " | Estado: " + estado
                + " | Dias desde mantenimiento: " + diasDesdeMantenimiento + " de " + getUmbralMantenimiento();
    }
}
