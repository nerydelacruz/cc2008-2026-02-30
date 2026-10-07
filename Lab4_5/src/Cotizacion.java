import java.util.ArrayList;

/* Clase de la cotizacion. Calcula cuanto saldria el alquiler y por que no se podria hacer, sin cambiar nada. */
public class Cotizacion {

    private Vehiculo vehiculo;
    private Cliente cliente;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private ArrayList<String> razonesRechazo;

    /* Constructor. Calcula los montos con los metodos de cada objeto y junta las razones de rechazo */
    public Cotizacion(Vehiculo vehiculo, Cliente cliente, int dias) {
        if (vehiculo == null || cliente == null) {
            throw new IllegalArgumentException("La cotizacion necesita un vehiculo y un cliente");
        }

        if (dias <= 0) {
            throw new IllegalArgumentException("Los dias de alquiler deben ser un entero positivo");
        }

        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.dias = dias;
        this.subtotal = vehiculo.calcularSubtotal(dias);
        this.descuento = cliente.calcularDescuento(subtotal);
        this.total = subtotal - descuento;
        this.razonesRechazo = new ArrayList<String>();

        evaluarRazones();
    }

    /* Revisa las tres condiciones y guarda todas las que no se cumplen */
    private void evaluarRazones() {
        if (vehiculo.estaDisponible() == false) {
            razonesRechazo.add("Vehiculo no disponible (estado: " + vehiculo.getEstado() + ")");
        }

        if (cliente.puedeConducir(vehiculo) == false) {
            razonesRechazo.add("Licencia inadecuada: el vehiculo requiere licencia " + vehiculo.getLicenciaRequerida()
                    + " o una que la cubra, y el cliente presenta " + cliente.getLicencias());
        }

        if (cliente.alcanzoLimiteActivos() == true) {
            razonesRechazo.add("Limite de alquileres activos alcanzado (" + cliente.getAlquileresActivos()
                    + " de " + cliente.getLimiteAlquileresActivos() + ")");
        }
    }

    /* Se puede alquilar solo si no hay ninguna razon de rechazo */
    public boolean esAlquilable() {
        if (razonesRechazo.size() == 0) {
            return true;
        }

        return false;
    }

    /* Devuelve una copia de las razones */
    public ArrayList<String> getRazonesRechazo() {
        return new ArrayList<String>(razonesRechazo);
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getDias() {
        return dias;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    /* Detalle con el vehiculo, su estado, los montos y si se puede alquilar */
    @Override
    public String toString() {
        String detalle = "";

        detalle = detalle + "COTIZACION\n";
        detalle = detalle + "Vehiculo: " + vehiculo.getCategoria() + " " + vehiculo.getPlaca()
                + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo() + "\n";
        detalle = detalle + "Caracteristicas: " + vehiculo.describirCaracteristicas() + "\n";
        detalle = detalle + "Estado: " + vehiculo.getEstado() + "\n";
        detalle = detalle + "Cliente: " + cliente.getTipoCliente() + " " + cliente.getIdentificador()
                + " - " + cliente.getNombre() + "\n";
        detalle = detalle + "Dias: " + dias + "\n";
        detalle = detalle + "Subtotal:  Q" + String.format("%,.2f", subtotal) + "\n";
        detalle = detalle + "Descuento: Q" + String.format("%,.2f", descuento) + "\n";
        detalle = detalle + "Total:     Q" + String.format("%,.2f", total) + "\n";

        if (esAlquilable() == true) {
            detalle = detalle + "El cliente SI puede alquilar este vehiculo en este momento";
        } else {
            detalle = detalle + "El cliente NO puede alquilar este vehiculo en este momento:";

            for (int i = 0; i < razonesRechazo.size(); i++) {
                detalle = detalle + "\n  - " + razonesRechazo.get(i);
            }
        }

        return detalle;
    }
}
