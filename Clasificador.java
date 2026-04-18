/*Leen los eventos del buzón de clasificación y dependiendo del tipo de
evento lo envían al servidor de consolidación y despliegue correspondiente.
o Un clasificador termina cuando recibe un evento de fin. */
public class Clasificador extends Thread {
    private static int activos;
    private static Object lock = new Object();

    private Buzon_clasificacion clasificacion_buz;
    private Buzon_consolidacion[] consolidacion_buz;
    private int ns;
    private int id;

    public Clasificador(Buzon_clasificacion clasificacion_buz, Buzon_consolidacion[] consolidacion_buz, int ns, int id) {
        this.clasificacion_buz = clasificacion_buz;
        this.consolidacion_buz = consolidacion_buz;
        this.ns = ns;
        this.id = id;

        synchronized (lock) {
            activos++;
        }
    }

    public void run(){
        try{
            while(true){
                Evento evento = clasificacion_buz.retirar();

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
                            consolidacion_buz[i].depositar(Evento.crearEventoFin());
                        }
                    }

                    System.out.println("Terminación de Clasificador" + id);

                    break;
                }

                consolidacion_buz[evento.tipo - 1].depositar(evento);
            }
        } catch (InterruptedException e) {}
    }

}
