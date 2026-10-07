public class PokemonElectrico extends Pokemon {
    protected int voltaje;

    public PokemonElectrico(String nombre, int nivel, int hp, int voltaje) {
        super(nombre, nivel, hp, "Electrico");
        this.voltaje = voltaje;
    }

    @Override
    public String estadisticas() {
        return "Voltaje: " + voltaje + " V";
    }
}
