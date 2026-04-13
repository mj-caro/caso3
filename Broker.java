public class Broker extends Thread {
    private Buzon_ilimitado entrada;
    private Buzon_ilimitado alertas;
    private Buzon_limitado clasificados;
    private int totalEventos;

    // tiene que conocer todo los eventos procesados 
    public Broker(int num_sensores, int num_eventos){
       totalEventos = num_eventos*num_sensores; 
    }

    public void run(){
        int procesados = 0;
        System.out.println("omg");
        while (procesados < totalEventos) {
            try {
                Evento evento = entrada.retirar();

                int aleatorio = (int) (Math.random() * 200);

                if (aleatorio % 8 == 0) { //si es multiplo de 8 -> anómalo
                    alertas.depositar(evento);

                } else {
                    clasificados.depositar(evento);
                }
                procesados++;
                System.out.println("processados Broker:" + procesados);
                
            } catch (InterruptedException e) {}
        }

       
        // para enviarle el FIN al admin usamos buzon de alertas
        try {
            alertas.depositar(Evento.crearEventoFin());
            System.out.println("Broker terminando, total eventos:" + totalEventos + "procesados:" + procesados + "enviando evento de terminacion a Administrador");

        } catch (InterruptedException e) {}
    }
}
