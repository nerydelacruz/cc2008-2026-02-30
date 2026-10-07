public class PokemonFantasmaVeneno extends Pokemon {
    protected int energiaEspectral;
    protected int toxicidad;

    public PokemonFantasmaVeneno(String nombre, int nivel, int hp, int energiaEspectral, int toxicidad) {
        super(nombre, nivel, hp, "Fantasma / Veneno");
        this.energiaEspectral = energiaEspectral;
        this.toxicidad = toxicidad;
    }

    @Override
    public String estadisticas() {
        return "Energia espectral: " + energiaEspectral + " | Toxicidad: " + toxicidad;
    }
}
