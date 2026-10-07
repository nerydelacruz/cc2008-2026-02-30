public class ControladorPokedex {
    private Pokedex pokedex = new Pokedex();
    private VistaPokedex vista = new VistaPokedex();

    public void iniciar() {
        registrarPokemones();
        vista.mostrarPokedex(pokedex.obtenerTodos());
    }

    private void registrarPokemones() {
        pokedex.agregar(new PokemonElectrico("Pikachu", 12, 35, 90));
        pokedex.agregar(new PokemonElectrico("Jolteon", 30, 65, 400));
        pokedex.agregar(new PokemonAgua("Squirtle", 10, 44, 60));
        pokedex.agregar(new PokemonFuego("Charmander", 11, 39, 800));
        pokedex.agregar(new PokemonVeneno("Koffing", 18, 40, 70));
        pokedex.agregar(new PokemonFantasma("Haunter", 25, 45, 115));
        pokedex.agregar(new PokemonFantasmaVeneno("Gengar", 34, 60, 130, 95));
    }
}
