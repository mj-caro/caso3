public class Evento {
    int id;
    int consecutivo;
    int tipo;
    boolean esFin;

    public Evento(int id, int consecutivo, int tipo, boolean esFin) {
        this.id = id;
        this.consecutivo = consecutivo;
        this.tipo = tipo;
        this.esFin = esFin;
    }

    public String getId() {
        return id + "-" + consecutivo;
    }

    public static Evento crearEventoFin() {
        return new Evento(-1, -1, -1, true);
    }

}
