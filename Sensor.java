/*Sensores IoT
o Son los responsables de generar los eventos.
o Cada sensor produce un número asignado de eventos.
o Los sensores depositan sus eventos en un buzón de entrada de eventos.
o Cada sensor termina cuando ha generado todos los eventos asignados.  */
public class Sensor extends Thread{
    private int id;
    private int numEventos;
    private Buzon_entrada buzonEntrada;
    private int ns;

    public Sensor(int id, int numEventos, Buzon_entrada buzonEntrada , int ns) {
        this.id = id;
        // numeventos = (valor base en el archivo)*(num identificaión del sensor)
        this.numEventos = numEventos;
        this.buzonEntrada = buzonEntrada;
        this.ns = ns;
    }

    // dispositando en buzon de entrada usando espera semiactiva
    public void run() {
        /*Cada evento debe incluir, adicionalmente, un número seudoaleatorio entre 1 y ns,
        donde ns es el número de servidores de consolidación y despliegue. Este valor
        indica dos cosas: el tipo del evento y el servidor al que el evento debe llegar.  */

        // crean numEventos eventos y luego terminan
        for (int i = 1; i <= numEventos; i++) {
            int tipo = (int) (Math.random() * ns) + 1; // Para generar un tipo de evento aleatorio entre 1 y ns

            Evento evento = new Evento(id, i, tipo, false);

            try {
                buzonEntrada.depositar(evento);

            } catch (InterruptedException e) {}
        }
        System.out.println("Sensor" + id + "terminando");
        }
    }

