/*Lee eventos del buzón de alertas.
o Inspecciona los eventos en profundidad para determinar si son
efectivamente maliciosos. Si confirma que un evento es malicioso lo
descarta. Los eventos que pasan la inspección se llevan al buzón de
clasificación.
o El administrador termina cuando recibe un evento de fin. */
public class Administrador extends Thread {
    private Buzon_alertas alertas;
    private Buzon_clasificacion clasificacion;
    private int numeroClasificadores;

    public Administrador(Buzon_alertas alertas, Buzon_clasificacion clasificacion, int numeroClasificadores){
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.numeroClasificadores = numeroClasificadores;
    }
    
    public void run() {
       try {
            while (true){

            // espera semiactivamente que hay algo que retirar
            while(alertas.is_Empty()){
                Thread.yield();
            }

            Evento evento = alertas.retirar();

            if (evento.esFin) {
                // Si recibimos un evento de fin, lo reenviamos a los clasificadores y terminamos
                for (int i = 0; i < numeroClasificadores; i++) {
                    // Espera semiactivamente hasta el buzon no esta lleno 
                    while(clasificacion.is_Full()){
                        Thread.yield();
                    }
                    clasificacion.depositar(Evento.crearEventoFin());
                }

                System.out.println("Terminación de Administrador, size of buzon de alertas:" + alertas.colaEventos.size());
                break;
            } 

            int aleatorio = (int)(Math.random() * 20);
            
            if (aleatorio % 4 == 0) { // Si el evento es un múltiplo de 4, lo consideramos normal y se manda al buzon de clasificacion
                clasificacion.depositar(evento);
            } 
       }
        
    }catch (InterruptedException e) {}
    }
}