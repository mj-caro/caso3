public class Servidor {
    private Buzon buzon;

    public Servidor(Buzon buzon) {
        this.buzon = buzon;
    }

    public void run() {
        try {
            while (true) {
                Evento evento = buzon.retirar();

                if (evento.esFin) {
                    break;
                }

                Thread.sleep((int)(Math.random() * 900) + 100);
            }
            
        } catch (InterruptedException e) {}
    }
}
