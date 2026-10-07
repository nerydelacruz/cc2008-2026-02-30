public class PokemonVeneno extends Pokemon {
    protected int toxicidad;

    public PokemonVeneno(String nombre, int nivel, int hp, int toxicidad) {
        super(nombre, nivel, hp, "Veneno");
        this.toxicidad = toxicidad;
    }

    @Override
    public String estadisticas() {
        return "Toxicidad: " + toxicidad;
    }
}
