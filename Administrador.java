public class Administrador extends Thread { 
    private Buzon alertas;
    private Buzon clasificacion;
    private int numeroClasificadores;

    public void run() {
       try {
        while (true){
            Evento evento = alertas.retirar();

            if (evento.esFin) {
                // Si recibimos un evento de fin, lo reenviamos a los clasificadores y terminamos
                for (int i = 0; i < numeroClasificadores; i++) {
                    clasificacion.depositar(Evento.crearEventoFin());
                }
                break;

            } 

            int aleatorio = (int)(Math.random() * 20);
            
            if (aleatorio % 4 == 0) { // Si el evento es un múltiplo de 4, lo consideramos normal y se manda al buzon de clasificacion
                clasificacion.depositar(evento);
            }
        }
         } catch (InterruptedException e) {}
    }
}
