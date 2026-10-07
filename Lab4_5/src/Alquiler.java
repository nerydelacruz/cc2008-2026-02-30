/* Clase del alquiler. Guarda un alquiler ya confirmado con los montos que se cobraron. */
public class Alquiler {

    private int numero;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private boolean activo;

    /* Constructor. Copia los datos de una cotizacion valida y queda activo */
    public Alquiler(int numero, Cotizacion cotizacion) {
        if (cotizacion == null || cotizacion.esAlquilable() == false) {
            throw new IllegalArgumentException("Solo se puede crear un alquiler a partir de una cotizacion valida");
        }

        this.numero = numero;
        this.cliente = cotizacion.getCliente();
        this.vehiculo = cotizacion.getVehiculo();
        this.dias = cotizacion.getDias();
        this.subtotal = cotizacion.getSubtotal();
        this.descuento = cotizacion.getDescuento();
        this.total = cotizacion.getTotal();
        this.activo = true;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
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

    public boolean isActivo() {
        return activo;
    }

    /* Marca el alquiler como terminado. No toca los montos porque ya se cobraron */
    public void finalizar() {
        activo = false;
    }

    /* Junta los datos del alquiler con los montos a dos decimales */
    @Override
    public String toString() {
        String estadoAlquiler = "Finalizado";

        if (activo == true) {
            estadoAlquiler = "Activo";
        }

        return "Alquiler #" + numero
                + " | Cliente: " + cliente.getNombre() + " (" + cliente.getIdentificador() + ")"
                + " | Vehiculo: " + vehiculo.getCategoria() + " " + vehiculo.getPlaca()
                + " | Dias: " + dias
                + " | Subtotal: Q" + String.format("%,.2f", subtotal)
                + " | Descuento: Q" + String.format("%,.2f", descuento)
                + " | Total: Q" + String.format("%,.2f", total)
                + " | " + estadoAlquiler;
    }
}
