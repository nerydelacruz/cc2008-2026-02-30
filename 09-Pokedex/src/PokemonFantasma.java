public class PokemonFantasma extends Pokemon {
    protected int energiaEspectral;

    public PokemonFantasma(String nombre, int nivel, int hp, int energiaEspectral) {
        super(nombre, nivel, hp, "Fantasma");
        this.energiaEspectral = energiaEspectral;
    }

    @Override
    public String estadisticas() {
        return "Energia espectral: " + energiaEspectral;
    }
}
