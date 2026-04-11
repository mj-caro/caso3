public class Broker extends Thread {
    private Buzon entrada;
    private Buzon alertas;
    private Buzon clasificados;
    private int totalEventos;

    public void run(){
        int procesados = 0;

        while (procesados < totalEventos) {
            try {
                Evento evento = entrada.retirar();

                int aleatorio = (int) (Math.random() * 200);

                if (aleatorio % 8 == 0) { //si es multiplo de 8
                    alertas.depositar(evento);

                } else {
                    clasificados.depositar(evento);
                }
                procesados++;
                
            } catch (InterruptedException e) {}
        }

        // para enviarle el FIN al admin usamos buzon de alertas
        try {
            alertas.depositar(Evento.crearEventoFin());
        } catch (InterruptedException e) {}
    }
}
