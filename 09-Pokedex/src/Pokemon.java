public abstract class Pokemon {
    protected String nombre;
    protected int nivel;
    protected int hp;
    protected String tipo;

    public Pokemon(String nombre, int nivel, int hp, String tipo) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.hp = hp;
        this.tipo = tipo;
    }

    public abstract String estadisticas();

    public void mostrarFicha() {
        System.out.printf("%-12s | Nv. %-3d | HP %-4d | %-18s | %s%n",
                nombre, nivel, hp, tipo, estadisticas());
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getHp() {
        return hp;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + " (Nv. " + nivel + ", " + tipo + ")";
    }
}
