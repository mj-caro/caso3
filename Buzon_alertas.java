/*Es el encargado de revisar los eventos del buzón de entrada.
o Si el evento es sospechoso, lo envía al buzón de alertas.
o Si es válido, lo envía al buzón para clasificación.
o El bróker termina cuando ha procesado todos los eventos que el sistema
debe generar.  */
import java.util.Queue;
import java.util.LinkedList;

public class Buzon_alertas{
    // has to be made public so administrador can access it 
    public  Queue<Evento> colaEventos;
    private String nombre; 
    private boolean debe_terminar = false;
    
    public Buzon_alertas(String nom) {
        // Linked list to implement the queue interface 
        this.colaEventos = new LinkedList<>();
        nombre = nom;
    }


    public synchronized void depositar(Evento evento) throws InterruptedException {
        colaEventos.add(evento);
        System.out.println("Deposicion, size of Buzon" + nombre + ": " + colaEventos.size());
    }


    public synchronized boolean is_Empty(){
        if (colaEventos.isEmpty()) {
            return true;
        }
        else return false; 
    }
    public synchronized Evento retirar() throws InterruptedException {
        while (colaEventos.isEmpty()) {
            wait();
        }
        Evento evento = colaEventos.poll();
        System.out.println("Deposicion, size of Buzon" + nombre + ": " + colaEventos.size());
        notifyAll();
        return evento;
    }
}
