import java.util.ArrayList;

public class Pokedex {
    private ArrayList<Pokemon> pokemones = new ArrayList<Pokemon>();

    public void agregar(Pokemon pokemon) {
        pokemones.add(pokemon);
    }

    public ArrayList<Pokemon> obtenerTodos() {
        return pokemones;
    }

    public int cantidad() {
        return pokemones.size();
    }
}
