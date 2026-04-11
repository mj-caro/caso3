public class Clasificador extends Thread {
    private static int activos;
    private static Object lock = new Object();

    private Buzon clasificacion;
    private Buzon[] buzonServidores;
    private int ns;

    public Clasificador(Buzon clasificacion, Buzon[] buzonServidores, int ns) {
        this.clasificacion = clasificacion;
        this.buzonServidores = buzonServidores;
        this.ns = ns;

        synchronized (lock) {
            activos++;
        }
    }

    public void run(){
        try{
            while(true){
                Evento evento = clasificacion.retirar();

                if (evento.esFin) {
                    boolean ultimo = false;

                    synchronized (lock) {
                        activos--;
                        if (activos == 0) {
                            ultimo = true;
                        }
                    }

                    if (ultimo) {
                        for (int i = 0; i < ns; i++) {
                            buzonServidores[i].depositar(Evento.crearEventoFin());
                        }
                    }

                    break;
                }

                buzonServidores[evento.tipo - 1].depositar(evento);
            }
        } catch (InterruptedException e) {}
    }

}
