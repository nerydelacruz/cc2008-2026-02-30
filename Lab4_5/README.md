# Ejercicios 4 y 5. Herencia y polimorfismo - RentaMovil

**Nombre completo:** Nery Javier de la Cruz Huinil
**Carné:** 261233

**Nombre completo:** David Maximiliano Pablo Cuy
**Carné:** 261341

## Descripción
Aplicación de consola para la empresa RentaMovil, que alquila automóviles, motocicletas,
camionetas de carga y microbuses a clientes individuales y corporativos. Desde un menú de
10 opciones el personal puede registrar vehículos y clientes, consultar la flota y los
clientes, cotizar, confirmar o cancelar alquileres, registrar devoluciones y fines de
mantenimiento, y ver los reportes de flota, ingresos, descuentos, alquileres activos e
historial de un cliente.

El programa usa dos jerarquías de herencia:

- `Vehiculo` (abstracta) guarda los datos comunes, maneja el estado y calcula el subtotal.
  `VehiculoPasajeros` (abstracta) guarda la cantidad de pasajeros que comparten `Automovil`
  y `Microbus`. `Motocicleta` y `CamionetaCarga` heredan directo de `Vehiculo`.
- `Cliente` (abstracta) guarda el identificador, el nombre, las licencias y los conteos.
  `ClienteIndividual` y `ClienteCorporativo` deciden su descuento y su límite de alquileres.

El recargo, la licencia requerida, el umbral de mantenimiento, el descuento, el límite de
alquileres activos y la descripción se resuelven con métodos sobrescritos. La flota y los
clientes se guardan en un solo `ArrayList` del tipo común, y fuera de las jerarquías no hay
`instanceof` ni comparaciones de tipo. La única excepción es el menú de registro, que
pregunta la categoría para saber qué objeto construir.

`Cotizacion` calcula los montos y las razones de rechazo sin cambiar nada; `Alquiler` guarda
un alquiler confirmado; `RentaMovil` administra las colecciones, valida las operaciones y
arma los reportes; `Main` tiene el menú. `TipoLicencia` y `EstadoVehiculo` son enums. Las
validaciones viven en los constructores, que lanzan `IllegalArgumentException`, y el menú
atrapa las excepciones para que el programa nunca termine por un dato incorrecto.

El análisis, el diagrama UML y la tabla de pruebas están en `docs/`.

## Cómo ejecutar
```bash
javac -d bin src/*.java
java -cp bin Main
```
