import java.util.ArrayList;
import java.util.HashMap;

/* Clase de la empresa. Maneja la flota, los clientes y los alquileres, valida las operaciones y arma los reportes. */
public class RentaMovil {

    private ArrayList<Vehiculo> flota;
    private ArrayList<Cliente> clientes;
    private ArrayList<Alquiler> alquileres;
    private int siguienteNumeroAlquiler;
    private double ingresosTotales;
    private double descuentosTotales;
    private HashMap<String, Double> ingresosPorCategoria;

    /* Constructor. Empieza sin vehiculos, sin clientes, sin ingresos y con el correlativo en 1 */
    public RentaMovil() {
        flota = new ArrayList<Vehiculo>();
        clientes = new ArrayList<Cliente>();
        alquileres = new ArrayList<Alquiler>();
        siguienteNumeroAlquiler = 1;
        ingresosTotales = 0;
        descuentosTotales = 0;
        ingresosPorCategoria = new HashMap<String, Double>();
    }

    /* Agrega un vehiculo de cualquier categoria. No acepta placas repetidas */
    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("No hay vehiculo que registrar");
        }

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Ya existe un vehiculo con la placa " + vehiculo.getPlaca());
        }

        flota.add(vehiculo);
    }

    /* Agrega un cliente de cualquier tipo. No acepta identificadores repetidos */
    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("No hay cliente que registrar");
        }

        if (buscarCliente(cliente.getIdentificador()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el identificador " + cliente.getIdentificador());
        }

        clientes.add(cliente);
    }

    /* Recorre la flota comparando placas. Devuelve null si ninguna coincide */
    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }

        for (int i = 0; i < flota.size(); i++) {
            if (flota.get(i).getPlaca().equalsIgnoreCase(placa.trim()) == true) {
                return flota.get(i);
            }
        }

        return null;
    }

    /* Recorre los clientes comparando identificadores. Devuelve null si ninguno coincide */
    public Cliente buscarCliente(String identificador) {
        if (identificador == null) {
            return null;
        }

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificador().equalsIgnoreCase(identificador.trim()) == true) {
                return clientes.get(i);
            }
        }

        return null;
    }

    /* Copia de la flota para consultarla sin poder cambiarla */
    public ArrayList<Vehiculo> getFlota() {
        return new ArrayList<Vehiculo>(flota);
    }

    /* Copia de los clientes para consultarlos sin poder cambiarlos */
    public ArrayList<Cliente> getClientes() {
        return new ArrayList<Cliente>(clientes);
    }

    /* Arma la cotizacion. Solo calcula, no cambia ningun dato de la empresa */
    public Cotizacion cotizar(String placa, String identificador, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehiculo con la placa " + placa);
        }

        Cliente cliente = buscarCliente(identificador);

        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con el identificador " + identificador);
        }

        if (dias <= 0) {
            throw new IllegalArgumentException("Los dias de alquiler deben ser un entero positivo");
        }

        return new Cotizacion(vehiculo, cliente, dias);
    }

    /* Vuelve a revisar todo y, si se puede, registra el alquiler, el estado, los conteos y el ingreso */
    public Alquiler confirmarAlquiler(Cotizacion cotizacion) {
        if (cotizacion == null) {
            throw new IllegalArgumentException("No hay cotizacion que confirmar");
        }

        /* Se cotiza otra vez con el estado actual, por si algo cambio desde la primera cotizacion */
        Cotizacion actual = new Cotizacion(cotizacion.getVehiculo(), cotizacion.getCliente(), cotizacion.getDias());

        if (actual.esAlquilable() == false) {
            throw new IllegalStateException("No se puede confirmar el alquiler: " + actual.getRazonesRechazo());
        }

        /* Desde aqui ya todo es valido, entonces se hacen todos los cambios juntos */
        Alquiler alquiler = new Alquiler(siguienteNumeroAlquiler, actual);
        siguienteNumeroAlquiler = siguienteNumeroAlquiler + 1;

        actual.getVehiculo().marcarAlquilado();
        actual.getCliente().registrarAlquilerConfirmado();
        alquileres.add(alquiler);

        ingresosTotales = ingresosTotales + alquiler.getTotal();
        descuentosTotales = descuentosTotales + alquiler.getDescuento();

        String categoria = actual.getVehiculo().getCategoria();
        double acumulado = 0;

        if (ingresosPorCategoria.get(categoria) != null) {
            acumulado = ingresosPorCategoria.get(categoria);
        }

        ingresosPorCategoria.put(categoria, acumulado + alquiler.getTotal());

        return alquiler;
    }

    /* Finaliza el alquiler, libera al cliente y actualiza el vehiculo. Devuelve true si paso a mantenimiento */
    public boolean registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehiculo con la placa " + placa);
        }

        if (vehiculo.getEstado() != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException("El vehiculo " + vehiculo.getPlaca() + " no esta alquilado (estado: " + vehiculo.getEstado() + ")");
        }

        Alquiler alquiler = buscarAlquilerActivo(vehiculo);

        if (alquiler == null) {
            throw new IllegalStateException("No se encontro el alquiler activo del vehiculo " + vehiculo.getPlaca());
        }

        alquiler.finalizar();
        alquiler.getCliente().registrarAlquilerFinalizado();

        return vehiculo.registrarDevolucion(alquiler.getDias());
    }

    /* Regresa a disponible un vehiculo que estaba en el taller */
    public void finalizarMantenimiento(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehiculo con la placa " + placa);
        }

        vehiculo.finalizarMantenimiento();
    }

    /* Busca el alquiler que sigue activo para ese vehiculo */
    private Alquiler buscarAlquilerActivo(Vehiculo vehiculo) {
        for (int i = 0; i < alquileres.size(); i++) {
            if (alquileres.get(i).isActivo() == true && alquileres.get(i).getVehiculo() == vehiculo) {
                return alquileres.get(i);
            }
        }

        return null;
    }

    /* Saca la lista de categorias que hay en la flota, sin repetir, en el orden en que aparecen */
    private ArrayList<String> obtenerCategorias() {
        ArrayList<String> categorias = new ArrayList<String>();

        for (int i = 0; i < flota.size(); i++) {
            String categoria = flota.get(i).getCategoria();

            if (categorias.contains(categoria) == false) {
                categorias.add(categoria);
            }
        }

        return categorias;
    }

    /* Cuenta los vehiculos por categoria y por estado */
    public String reporteFlota() {
        if (flota.size() == 0) {
            return "Todavia no hay vehiculos registrados";
        }

        String reporte = "REPORTE DE LA FLOTA\n";
        ArrayList<String> categorias = obtenerCategorias();

        for (int i = 0; i < categorias.size(); i++) {
            int registrados = 0;
            int disponibles = 0;
            int alquilados = 0;
            int enMantenimiento = 0;

            for (int j = 0; j < flota.size(); j++) {
                Vehiculo vehiculo = flota.get(j);

                if (vehiculo.getCategoria().equals(categorias.get(i)) == true) {
                    registrados = registrados + 1;

                    if (vehiculo.getEstado() == EstadoVehiculo.DISPONIBLE) {
                        disponibles = disponibles + 1;
                    } else if (vehiculo.getEstado() == EstadoVehiculo.ALQUILADO) {
                        alquilados = alquilados + 1;
                    } else {
                        enMantenimiento = enMantenimiento + 1;
                    }
                }
            }

            reporte = reporte + categorias.get(i) + ": " + registrados + " registrados"
                    + " | Disponibles: " + disponibles
                    + " | Alquilados: " + alquilados
                    + " | En mantenimiento: " + enMantenimiento + "\n";
        }

        reporte = reporte + "Total de vehiculos: " + flota.size();

        return reporte;
    }

    /* Ingresos totales, ingresos por categoria y descuentos otorgados */
    public String reporteIngresos() {
        String reporte = "REPORTE DE INGRESOS\n";
        reporte = reporte + "Ingresos totales: Q" + String.format("%,.2f", ingresosTotales) + "\n";
        reporte = reporte + "Ingresos por categoria:\n";

        ArrayList<String> categorias = obtenerCategorias();

        for (int i = 0; i < categorias.size(); i++) {
            double ingreso = 0;

            if (ingresosPorCategoria.get(categorias.get(i)) != null) {
                ingreso = ingresosPorCategoria.get(categorias.get(i));
            }

            reporte = reporte + "  " + categorias.get(i) + ": Q" + String.format("%,.2f", ingreso) + "\n";
        }

        reporte = reporte + "Descuentos otorgados: Q" + String.format("%,.2f", descuentosTotales);

        return reporte;
    }

    /* Lista de los alquileres que siguen en curso */
    public ArrayList<Alquiler> getAlquileresActivos() {
        ArrayList<Alquiler> activos = new ArrayList<Alquiler>();

        for (int i = 0; i < alquileres.size(); i++) {
            if (alquileres.get(i).isActivo() == true) {
                activos.add(alquileres.get(i));
            }
        }

        return activos;
    }

    /* Todos los alquileres confirmados de un cliente */
    public ArrayList<Alquiler> getHistorialCliente(String identificador) {
        Cliente cliente = buscarCliente(identificador);

        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con el identificador " + identificador);
        }

        ArrayList<Alquiler> historial = new ArrayList<Alquiler>();

        for (int i = 0; i < alquileres.size(); i++) {
            if (alquileres.get(i).getCliente() == cliente) {
                historial.add(alquileres.get(i));
            }
        }

        return historial;
    }

    /* Suma lo que ha pagado el cliente en todos sus alquileres */
    public double calcularTotalPagado(String identificador) {
        ArrayList<Alquiler> historial = getHistorialCliente(identificador);
        double totalPagado = 0;

        for (int i = 0; i < historial.size(); i++) {
            totalPagado = totalPagado + historial.get(i).getTotal();
        }

        return totalPagado;
    }
}
