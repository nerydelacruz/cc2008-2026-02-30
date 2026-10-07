/* Clase abstracta intermedia. Guarda la cantidad de pasajeros que comparten automoviles y microbuses. */
public abstract class VehiculoPasajeros extends Vehiculo {

    private int cantidadPasajeros;

    /* Constructor. Manda los datos comunes a Vehiculo y revisa que los pasajeros sean mayores que 0 */
    protected VehiculoPasajeros(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que 0");
        }

        this.cantidadPasajeros = cantidadPasajeros;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }
}
