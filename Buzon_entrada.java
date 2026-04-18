/*Es el encargado de revisar los eventos del buzón de entrada.
o Si el evento es sospechoso, lo envía al buzón de alertas.
o Si es válido, lo envía al buzón para clasificación.
o El bróker termina cuando ha procesado todos los eventos que el sistema
debe generar.  */
import java.util.Queue;
import java.util.LinkedList;

public class Buzon_entrada {
    // has to be made public so administrador can access it 
    public  Queue<Evento> colaEventos;
    private String nombre; 
    private boolean debe_terminar = false;
    
    public Buzon_entrada(String nom) {
        // Linked list to implement the queue interface 
        this.colaEventos = new LinkedList<>();
        nombre = nom;
    }

    public synchronized void depositar(Evento evento) throws InterruptedException {
        colaEventos.add(evento);
        // For Test 
        System.out.println("Deposicion, size of Buzon" + nombre + ": " + colaEventos.size());
        notifyAll();
    }

    public synchronized Evento retirar() throws InterruptedException {
        // wait for the producers to fill buzon if it's empty 
        while (colaEventos.isEmpty()) {
            // espera pasiva 
            wait();
        }
        // returns and deletes element at the front of the container
        Evento evento = colaEventos.poll();
        System.out.println("Deposicion, size of Buzon" + nombre + ": " + colaEventos.size());
        return evento;
    }
}
