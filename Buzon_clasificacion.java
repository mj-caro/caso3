/*Es el encargado de revisar los eventos del buzón de entrada.
o Si el evento es sospechoso, lo envía al buzón de alertas.
o Si es válido, lo envía al buzón para clasificación.
o El bróker termina cuando ha procesado todos los eventos que el sistema
debe generar.  */
import java.util.Queue;
import java.util.LinkedList;

public class Buzon_clasificacion {
    private Queue<Evento> colaEventos;
    private int capacidad;
    
    public Buzon_clasificacion(int capacidad) {
        this.capacidad = capacidad;
        this.colaEventos = new LinkedList<>();
    }

    // Admin y Broker esperan semiactivamente que esta funcion devuelve falso 
    public synchronized boolean is_Full(){
        if(colaEventos.size() == capacidad){
            return true;
        }
        else return false; 
    }

    // Admin y Broker invocan esa funcion cuando is_Full devuelve falso
    public synchronized void depositar(Evento evento) throws InterruptedException {
        colaEventos.add(evento);
        // despierta a los Clasificadores para poder retirar
        notifyAll();

    }

    // los Clasificadores esperan pasivamente que la cola no esta vacia
    public synchronized Evento retirar() throws InterruptedException {
        while (colaEventos.isEmpty()) {
            wait();
        }

        Evento evento = colaEventos.poll();
        return evento;
    }
}
