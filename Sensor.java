public class Sensor extends Thread{
    private int id;
    private int numEventos;
    private Buzon buzonEntrada;
    private int ns;

    public Sensor(int id, int numEventos,Buzon buzonEntrada , int ns) {
        this.id = id;
        this.numEventos = numEventos;
        this.buzonEntrada = buzonEntrada;
        this.ns = ns;
    }

    public void run() {
        for (int i = 1; i <= numEventos; i++) {
            int tipo = (int) (Math.random() * ns) + 1; // Para generar un tipo de evento aleatorio entre 1 y ns

            Evento evento = new Evento(id, i, tipo, false);

            try {
                buzonEntrada.depositar(evento);

            } catch (InterruptedException e) {}
        }
        }
    }

