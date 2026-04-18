/*Lee eventos de su propio buzón de consolidación.
o Consolida y despliega los mensajes para los clientes.
o Un servidor termina cuando recibe un mensaje de fin.  */
public class Servidor extends Thread {
    private Buzon_consolidacion consolidacion_buz;

    public Servidor(Buzon_consolidacion buzon) {
        this.consolidacion_buz = buzon;
    }

    public void run() {
        try {
            while (true) {

                System.out.println("Servidor esperando evento");

                Evento evento = consolidacion_buz.retirar();
                
                //Termina su ejecución si el evento es de fin
                if (evento.esFin) {
                    System.out.println("Servidor recibe evento FIN y termina");
                    break;
                }

                System.out.println("Servidor procesando evento");
                
                //Simulamos el tiempo de lectura y procesamiento
                Thread.sleep((int)(Math.random() * 900) + 100);
            }
            
        } catch (InterruptedException e) {}
    }
}
