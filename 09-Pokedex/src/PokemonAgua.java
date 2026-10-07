public class PokemonAgua extends Pokemon {
    protected int presion;

    public PokemonAgua(String nombre, int nivel, int hp, int presion) {
        super(nombre, nivel, hp, "Agua");
        this.presion = presion;
    }

    @Override
    public String estadisticas() {
        return "Presion: " + presion + " psi";
    }
}
