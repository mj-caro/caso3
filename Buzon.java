public class Buzon {
    private Queue<Evento> colaEventos;
    private int capacidad;
    
    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.colaEventos = new LinkedList<>();
    }

    public synchronized void depositar(Evento evento) throws InterruptedException {
        while (capacidad > 0 && colaEventos.size() == capacidad) {
            wait();
        }

        colaEventos.add(evento);
        notifyAll();
    }

    public synchronized Evento retirar() throws InterruptedException {
        while (colaEventos.isEmpty()) {
            wait();
        }

        Evento evento = colaEventos.poll();
        notifyAll();
        return evento;
    }
}
