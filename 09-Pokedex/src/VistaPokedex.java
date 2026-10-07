import java.util.ArrayList;

public class VistaPokedex {

    public void mostrarPokedex(ArrayList<Pokemon> pokemones) {
        System.out.println("===== POKEDEX =====");
        System.out.printf("%-12s | %-7s | %-7s | %-18s | %s%n",
                "Nombre", "Nivel", "HP", "Tipo", "Estadisticas");
        System.out.println("-".repeat(80));

        for (Pokemon pokemon : pokemones) {
            pokemon.mostrarFicha();
        }

        System.out.println("-".repeat(80));
        System.out.println("Total: " + pokemones.size() + " Pokemon");
    }
}
