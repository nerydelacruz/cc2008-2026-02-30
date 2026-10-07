import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/* Programa principal. Carga los datos iniciales y muestra el menu de RentaMovil hasta que el usuario sale. */
public class Main {

    private static Scanner scanner;
    private static RentaMovil empresa;

    public static void main(String[] args) {
        /* Para que los montos salgan como Q1,050.00 sin importar la configuracion de la computadora */
        Locale.setDefault(Locale.US);

        scanner = new Scanner(System.in);
        empresa = new RentaMovil();

        cargarDatosIniciales();

        System.out.println("RENTAMOVIL - SISTEMA DE ALQUILER DE VEHICULOS");

        int opcion = 0;

        while (opcion != 10) {
            mostrarMenu();
            opcion = leerEntero("Opcion: ");

            if (opcion == 1) {
                registrarVehiculo();
            } else if (opcion == 2) {
                registrarCliente();
            } else if (opcion == 3) {
                consultarFlota();
            } else if (opcion == 4) {
                consultarClientes();
            } else if (opcion == 5) {
                cotizarAlquiler();
            } else if (opcion == 6) {
                alquilarVehiculo();
            } else if (opcion == 7) {
                registrarDevolucion();
            } else if (opcion == 8) {
                finalizarMantenimiento();
            } else if (opcion == 9) {
                mostrarReportes();
            } else if (opcion == 10) {
                System.out.println("Adios");
            } else {
                System.out.println("La opcion debe estar entre 1 y 10");
            }
        }

        scanner.close();
    }

    /* Registra 2 vehiculos por categoria y clientes de ambos tipos. Todo disponible y sin ingresos */
    private static void cargarDatosIniciales() {
        empresa.registrarVehiculo(new Automovil("P-101AAA", "Toyota", "Corolla", 300, 5, true));

        /* Este automovil esta cerca de su umbral de 30 dias para probar el mantenimiento */
        Automovil cercaDelUmbral = new Automovil("P-102AAB", "Nissan", "Sentra", 250, 5, false);
        cercaDelUmbral.cargarDiasAcumulados(28);
        empresa.registrarVehiculo(cercaDelUmbral);

        empresa.registrarVehiculo(new Motocicleta("M-201AAA", "Honda", "CB190", 150, 190));
        empresa.registrarVehiculo(new Motocicleta("M-202AAB", "Kawasaki", "Ninja 400", 200, 400));
        empresa.registrarVehiculo(new CamionetaCarga("C-301AAA", "Toyota", "Hilux", 200, 1.5));
        empresa.registrarVehiculo(new CamionetaCarga("C-302AAB", "Isuzu", "NPR", 350, 3));
        empresa.registrarVehiculo(new Microbus("B-401AAA", "Toyota", "Hiace", 450, 15, true));
        empresa.registrarVehiculo(new Microbus("B-402AAB", "Nissan", "Urvan", 400, 12, false));

        ArrayList<TipoLicencia> licenciasAna = new ArrayList<TipoLicencia>();
        licenciasAna.add(TipoLicencia.C);
        ClienteIndividual ana = new ClienteIndividual("1234567890101", "Ana Lopez", licenciasAna);
        /* Ana ya tiene 3 alquileres confirmados, el siguiente lleva el 5 % */
        ana.cargarAlquileresPrevios(3);
        empresa.registrarCliente(ana);

        ArrayList<TipoLicencia> licenciasLuis = new ArrayList<TipoLicencia>();
        licenciasLuis.add(TipoLicencia.M);
        empresa.registrarCliente(new ClienteIndividual("2345678901201", "Luis Perez", licenciasLuis));

        ArrayList<TipoLicencia> licenciasMaria = new ArrayList<TipoLicencia>();
        licenciasMaria.add(TipoLicencia.B);
        licenciasMaria.add(TipoLicencia.M);
        empresa.registrarCliente(new ClienteIndividual("3456789012301", "Maria Gomez", licenciasMaria));

        ArrayList<TipoLicencia> licenciasTransportes = new ArrayList<TipoLicencia>();
        licenciasTransportes.add(TipoLicencia.A);
        empresa.registrarCliente(new ClienteCorporativo("1234567-8", "Transportes del Sur", "Carlos Ruiz", licenciasTransportes));

        ArrayList<TipoLicencia> licenciasEventos = new ArrayList<TipoLicencia>();
        licenciasEventos.add(TipoLicencia.C);
        empresa.registrarCliente(new ClienteCorporativo("7654321-0", "Eventos GT", "Sofia Mendez", licenciasEventos));
    }

    /* Imprime las opciones del menu */
    private static void mostrarMenu() {
        System.out.println("");
        System.out.println("MENU");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Consultar flota");
        System.out.println("4. Consultar clientes");
        System.out.println("5. Cotizar alquiler");
        System.out.println("6. Alquilar vehiculo (confirmar o cancelar)");
        System.out.println("7. Registrar devolucion");
        System.out.println("8. Registrar fin de mantenimiento");
        System.out.println("9. Reportes");
        System.out.println("10. Salir");
    }

    /* 1. Pregunta la categoria para saber que objeto construir. Es la unica parte que pregunta el tipo */
    private static void registrarVehiculo() {
        System.out.println("Categorias: 1. Automovil  2. Motocicleta  3. Camioneta de carga  4. Microbus");
        int categoria = leerEntero("Categoria: ");

        if (categoria < 1 || categoria > 4) {
            System.out.println("La categoria debe estar entre 1 y 4");
            return;
        }

        try {
            String placa = leerTexto("Placa: ");

            if (empresa.buscarVehiculo(placa) != null) {
                System.out.println("Error: ya existe un vehiculo con la placa " + placa);
                return;
            }

            String marca = leerTexto("Marca: ");
            String modelo = leerTexto("Modelo: ");
            double tarifaDiaria = leerDouble("Tarifa diaria (Q): ");

            Vehiculo vehiculo = null;

            if (categoria == 1) {
                int pasajeros = leerEntero("Cantidad de pasajeros: ");
                boolean automatica = leerSiNo("La transmision es automatica? (s/n): ");
                vehiculo = new Automovil(placa, marca, modelo, tarifaDiaria, pasajeros, automatica);
            } else if (categoria == 2) {
                int cilindraje = leerEntero("Cilindraje (cc): ");
                vehiculo = new Motocicleta(placa, marca, modelo, tarifaDiaria, cilindraje);
            } else if (categoria == 3) {
                double capacidad = leerDouble("Capacidad maxima (toneladas): ");
                vehiculo = new CamionetaCarga(placa, marca, modelo, tarifaDiaria, capacidad);
            } else {
                int pasajeros = leerEntero("Cantidad de pasajeros: ");
                boolean piloto = leerSiNo("Se entrega con piloto de la empresa? (s/n): ");
                vehiculo = new Microbus(placa, marca, modelo, tarifaDiaria, pasajeros, piloto);
            }

            empresa.registrarVehiculo(vehiculo);

            System.out.println("Vehiculo registrado: " + vehiculo.toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage() + ". No se registro el vehiculo");
        }
    }

    /* 2. Pregunta el tipo de cliente para saber que objeto construir */
    private static void registrarCliente() {
        System.out.println("Tipos: 1. Individual  2. Corporativo");
        int tipo = leerEntero("Tipo de cliente: ");

        if (tipo < 1 || tipo > 2) {
            System.out.println("El tipo debe ser 1 o 2");
            return;
        }

        try {
            Cliente cliente = null;

            if (tipo == 1) {
                String dpi = leerTexto("DPI (13 digitos): ");

                if (ClienteIndividual.esDpiValido(dpi) == false) {
                    System.out.println("Error: el DPI debe tener exactamente 13 digitos. No se registro el cliente");
                    return;
                }

                if (empresa.buscarCliente(dpi) != null) {
                    System.out.println("Error: ya existe un cliente con el identificador " + dpi);
                    return;
                }

                String nombre = leerTexto("Nombre: ");
                ArrayList<TipoLicencia> licencias = leerLicencias();
                cliente = new ClienteIndividual(dpi, nombre, licencias);
            } else {
                String nit = leerTexto("NIT: ");

                if (empresa.buscarCliente(nit) != null) {
                    System.out.println("Error: ya existe un cliente con el identificador " + nit);
                    return;
                }

                String nombreEmpresa = leerTexto("Nombre de la empresa: ");
                String nombreContacto = leerTexto("Nombre del contacto: ");
                System.out.println("Licencias de los pilotos autorizados de la empresa");
                ArrayList<TipoLicencia> licencias = leerLicencias();
                cliente = new ClienteCorporativo(nit, nombreEmpresa, nombreContacto, licencias);
            }

            empresa.registrarCliente(cliente);

            System.out.println("Cliente registrado: " + cliente.toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage() + ". No se registro el cliente");
        }
    }

    /* 3. Muestra todos los vehiculos. Cada uno se describe con su propio toString */
    private static void consultarFlota() {
        ArrayList<Vehiculo> flota = empresa.getFlota();

        if (flota.size() == 0) {
            System.out.println("Todavia no hay vehiculos registrados");
            return;
        }

        for (int i = 0; i < flota.size(); i++) {
            System.out.println(flota.get(i).toString());
        }
    }

    /* 4. Muestra todos los clientes. Cada uno se describe con su propio toString */
    private static void consultarClientes() {
        ArrayList<Cliente> clientes = empresa.getClientes();

        if (clientes.size() == 0) {
            System.out.println("Todavia no hay clientes registrados");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(clientes.get(i).toString());
        }
    }

    /* 5. Pide placa, cliente y dias, y muestra la cotizacion sin cambiar nada */
    private static void cotizarAlquiler() {
        try {
            String placa = leerTexto("Placa del vehiculo: ");
            String identificador = leerTexto("DPI o NIT del cliente: ");
            int dias = leerEntero("Cantidad de dias: ");

            Cotizacion cotizacion = empresa.cotizar(placa, identificador, dias);

            System.out.println(cotizacion.toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
        }
    }

    /* 6. Muestra el detalle del cobro y pregunta si confirma. Si cancela no cambia nada */
    private static void alquilarVehiculo() {
        try {
            String placa = leerTexto("Placa del vehiculo: ");
            String identificador = leerTexto("DPI o NIT del cliente: ");
            int dias = leerEntero("Cantidad de dias: ");

            Cotizacion cotizacion = empresa.cotizar(placa, identificador, dias);

            System.out.println(cotizacion.toString());

            if (cotizacion.esAlquilable() == false) {
                System.out.println("Alquiler rechazado. No se modifico ningun dato");
                return;
            }

            boolean confirma = leerSiNo("Confirma el alquiler? (s/n): ");

            if (confirma == false) {
                System.out.println("Alquiler cancelado. No se modifico ningun dato");
                return;
            }

            Alquiler alquiler = empresa.confirmarAlquiler(cotizacion);

            System.out.println("Alquiler confirmado: " + alquiler.toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
        } catch (IllegalStateException error) {
            System.out.println("Error: " + error.getMessage());
        }
    }

    /* 7. Pide la placa y registra la devolucion */
    private static void registrarDevolucion() {
        try {
            String placa = leerTexto("Placa del vehiculo devuelto: ");
            boolean pasoAMantenimiento = empresa.registrarDevolucion(placa);

            if (pasoAMantenimiento == true) {
                System.out.println("Devolucion registrada. El vehiculo alcanzo su umbral y paso a mantenimiento");
            } else {
                System.out.println("Devolucion registrada. El vehiculo quedo disponible");
            }

            System.out.println(empresa.buscarVehiculo(placa).toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
        } catch (IllegalStateException error) {
            System.out.println("Error: " + error.getMessage());
        }
    }

    /* 8. Pide la placa y registra que el taller termino */
    private static void finalizarMantenimiento() {
        try {
            String placa = leerTexto("Placa del vehiculo: ");
            empresa.finalizarMantenimiento(placa);

            System.out.println("Mantenimiento finalizado: " + empresa.buscarVehiculo(placa).toString());

        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
        } catch (IllegalStateException error) {
            System.out.println("Error: " + error.getMessage());
        }
    }

    /* 9. Muestra el reporte que escoja el usuario */
    private static void mostrarReportes() {
        System.out.println("REPORTES");
        System.out.println("1. Flota por categoria y estado");
        System.out.println("2. Ingresos y descuentos");
        System.out.println("3. Alquileres activos");
        System.out.println("4. Historial de un cliente");
        int reporte = leerEntero("Reporte: ");

        if (reporte == 1) {
            System.out.println(empresa.reporteFlota());
        } else if (reporte == 2) {
            System.out.println(empresa.reporteIngresos());
        } else if (reporte == 3) {
            ArrayList<Alquiler> activos = empresa.getAlquileresActivos();

            if (activos.size() == 0) {
                System.out.println("No hay alquileres activos");
                return;
            }

            for (int i = 0; i < activos.size(); i++) {
                System.out.println(activos.get(i).toString());
            }
        } else if (reporte == 4) {
            try {
                String identificador = leerTexto("DPI o NIT del cliente: ");
                ArrayList<Alquiler> historial = empresa.getHistorialCliente(identificador);

                System.out.println(empresa.buscarCliente(identificador).toString());

                if (historial.size() == 0) {
                    System.out.println("El cliente no tiene alquileres registrados en el sistema");
                }

                for (int i = 0; i < historial.size(); i++) {
                    System.out.println(historial.get(i).toString());
                }

                System.out.println("Total pagado: Q" + String.format("%,.2f", empresa.calcularTotalPagado(identificador)));

            } catch (IllegalArgumentException error) {
                System.out.println("Error: " + error.getMessage());
            }
        } else {
            System.out.println("El reporte debe estar entre 1 y 4");
        }
    }

    /* Lee un numero entero. Si el formato es incorrecto lo vuelve a pedir */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException error) {
                System.out.println("Eso no es un numero entero, intente de nuevo");
            }
        }
    }

    /* Lee un numero con decimales. Si el formato es incorrecto lo vuelve a pedir */
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException error) {
                System.out.println("Eso no es un numero valido, intente de nuevo");
            }
        }
    }

    /* Lee un texto y no lo acepta vacio */
    private static String leerTexto(String mensaje) {
        String texto = "";

        while (texto.length() == 0) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.length() == 0) {
                System.out.println("Este dato no puede quedar vacio, intente de nuevo");
            }
        }

        return texto;
    }

    /* Lee una respuesta de si o no. Solo acepta s o n */
    private static boolean leerSiNo(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje);

            if (respuesta.equalsIgnoreCase("s") == true || respuesta.equalsIgnoreCase("si") == true) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("n") == true || respuesta.equalsIgnoreCase("no") == true) {
                return false;
            }

            System.out.println("Responda s o n");
        }
    }

    /* Lee una o mas licencias separadas por coma. Solo acepta A, B, C y M, y no las repite */
    private static ArrayList<TipoLicencia> leerLicencias() {
        while (true) {
            String texto = leerTexto("Licencias separadas por coma (A, B, C, M): ");
            String[] partes = texto.split(",");
            ArrayList<TipoLicencia> licencias = new ArrayList<TipoLicencia>();
            boolean validas = true;

            for (int i = 0; i < partes.length; i++) {
                String parte = partes[i].trim().toUpperCase();

                if (parte.length() == 0) {
                    continue;
                }

                try {
                    TipoLicencia licencia = TipoLicencia.valueOf(parte);

                    if (licencias.contains(licencia) == false) {
                        licencias.add(licencia);
                    }
                } catch (IllegalArgumentException error) {
                    System.out.println("La licencia " + parte + " no es valida");
                    validas = false;
                }
            }

            if (validas == true && licencias.size() > 0) {
                return licencias;
            }

            System.out.println("Debe ingresar al menos una licencia valida (A, B, C o M), intente de nuevo");
        }
    }
}
