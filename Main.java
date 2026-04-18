import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    // Attributes to get from the text file

    // Número de sensores 
    private static int ni;
    // Número base de eventos
    private static int ne;
    //Número de clasificadores
    private static int nc;
    // numero de servidores de despliegue y consolidación
    private static int ns; 
    // Capacidad del buzón del buzón para clasificación
    private static int tam1;
    // Capacidad de cada buzón para consolidación
    private static int tam2;


    // Read text file (assuming it's in the right format, is in the same folder + is called data.txt)
    public static void readArchivo(){
        // 
        try(BufferedReader reader = new BufferedReader(new FileReader("data.txt"))){
            // Number of sensors
            String ni_line = reader.readLine();
            String ni_value = ni_line.split("=")[1];
            ni = Integer.parseInt(ni_value);

            // Numero base de eventos 
            String ne_line = reader.readLine();
            String ne_value = ne_line.split("=")[1];
            ne = Integer.parseInt(ne_value);

            String nc_line = reader.readLine();
            String nc_value = nc_line.split("=")[1];
            nc = Integer.parseInt(nc_value);

            String ns_line = reader.readLine();
            String ns_value = ns_line.split("=")[1];
            ns = Integer.parseInt(ns_value);

            String tam1_line = reader.readLine();
            String tam1_value = tam1_line.split("=")[1];
            tam1 = Integer.parseInt(tam1_value);

            String tam2_line = reader.readLine();
            String tam2_value = tam2_line.split("=")[1];
            tam2 = Integer.parseInt(tam2_value);

        }
        catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }
        catch(IOException e){
            System.out.println("Error");
        }

        /* Test: see if the file can be read correctly  
        System.out.println("ni: " + ni);
        System.out.println("ne: " + ne); 
        System.out.println("nc: " + nc);
        System.out.println("ns: " + ns);
        System.out.println("tam1: " + tam1);
        System.out.println("tam2: " + tam2);
        */
        
    }


    public static void main(String[] args){
        readArchivo();

        // crea los buzones
        Buzon_entrada entrada_buz = new Buzon_entrada("entrada");
        Buzon_alertas alertas_buz = new Buzon_alertas("alertas");
        Buzon_clasificacion clasificacion_buz = new Buzon_clasificacion(tam1);
        Buzon_consolidacion consolidacion_buz[] = new Buzon_consolidacion[ns];

         // create objects
         Administrador add = new Administrador(alertas_buz, clasificacion_buz, nc);
         add.start();

        // Create servidor arrays 
        Servidor[] serv = new Servidor[ns];
        Broker broker = new Broker(entrada_buz, alertas_buz, clasificacion_buz, ni, ne);
        broker.start();

        // Fill the arrays for consolidacion  
        for(int i = 0; i<ns; i++){
            consolidacion_buz[i] = new Buzon_consolidacion(tam2);
            serv[i] = new Servidor(consolidacion_buz[i]);
            serv[i].start();
        }

        // Create ns threads for classifiers 
        for(int k = 0; k<nc; k++){
            new Clasificador(clasificacion_buz,consolidacion_buz, ns, k).start();
        }
        

        // Create ni sensor threads (numero base de eventos????)
        for (int i=0;i<ni; i++){
            new Sensor(i,ne * i,entrada_buz,ns).start(); // int id, int numEventos, Buzon buzonEntrada , int ns
        }

    }
     

}
