/*Lee eventos de su propio buzón de consolidación.
o Consolida y despliega los mensajes para los clientes.
o Un servidor termina cuando recibe un mensaje de fin.  */
public class Servidor {
    private Buzon buzon;

    public Servidor(Buzon buzon) {
        this.buzon = buzon;
    }

    public void run() {
        try {
            while (true) {
                Evento evento = buzon.retirar();
                
                //Termina su ejecución si el evento es de fin
                if (evento.esFin) {
                    break;
                }
                //Simulamos el tiempo de lectura y procesamiento
                Thread.sleep((int)(Math.random() * 900) + 100);
            }
            
        } catch (InterruptedException e) {}
    }
}
