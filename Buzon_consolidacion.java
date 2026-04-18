/*Es el encargado de revisar los eventos del buzón de entrada.
o Si el evento es sospechoso, lo envía al buzón de alertas.
o Si es válido, lo envía al buzón para clasificación.
o El bróker termina cuando ha procesado todos los eventos que el sistema
debe generar.  */
import java.util.Queue;
import java.util.LinkedList;

public class Buzon_consolidacion {
    private Queue<Evento> colaEventos;
    private int capacidad;
    
    public Buzon_consolidacion(int capacidad) {
        this.capacidad = capacidad;
        this.colaEventos = new LinkedList<>();
    }

    public synchronized void depositar(Evento evento) throws InterruptedException {
        // Los clasificadores esperan pasivamente (esta lleno)
        while (capacidad > 0 && colaEventos.size() == capacidad) {
            wait();
        }

        colaEventos.add(evento);

        // (relevante en el caso de que estaba vacio antes) 
        // -> despertamos a los Servidores para retirar 
        // (solo porque ellos tb esperan pasivamente)
        notifyAll();
    }

    public synchronized Evento retirar() throws InterruptedException {
        // Los servidores esperan pasivamente 
        while (colaEventos.isEmpty()) {
            wait();
        }

        Evento evento = colaEventos.poll();
         // (relevante en el caso de que estaba lleno antes) 
        // -> despertamos a los Clasificadores para depositar 
        // (solo porque ellos tb esperan pasivamente)
        notifyAll();
        return evento;
    }
}
