public class PokemonFuego extends Pokemon {
    protected int temperatura;

    public PokemonFuego(String nombre, int nivel, int hp, int temperatura) {
        super(nombre, nivel, hp, "Fuego");
        this.temperatura = temperatura;
    }

    @Override
    public String estadisticas() {
        return "Temperatura: " + temperatura + " grados";
    }
}
