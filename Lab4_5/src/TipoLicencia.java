/* Enum de los tipos de licencia. Sabe si una licencia cubre lo que pide otra. */
public enum TipoLicencia {
    A,
    B,
    C,
    M;

    /* Dice si esta licencia autoriza la requerida. A cubre B y C, B cubre C y M va por aparte */
    public boolean autoriza(TipoLicencia requerida) {
        if (this == requerida) {
            return true;
        }

        if (this == A && (requerida == B || requerida == C)) {
            return true;
        }

        if (this == B && requerida == C) {
            return true;
        }

        return false;
    }
}
