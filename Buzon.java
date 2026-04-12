/*Es el encargado de revisar los eventos del buzón de entrada.
o Si el evento es sospechoso, lo envía al buzón de alertas.
o Si es válido, lo envía al buzón para clasificación.
o El bróker termina cuando ha procesado todos los eventos que el sistema
debe generar.  */
import java.util.Queue;
import java.util.LinkedList;

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
