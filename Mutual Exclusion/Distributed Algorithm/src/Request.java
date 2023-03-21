import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Request extends Thread {
    private ArrayList<DatagramPacket> packets; 

    @Override
    public void run() {
        try (// Classe java para trabalhar com multicast ou broadcast
            MulticastSocket mcs = new MulticastSocket(6001)) {
            byte rec[] = new byte[256];
            DatagramPacket pkg = new DatagramPacket(rec, rec.length);

            while(true) {
                // Recebendo datagrama 
                mcs.receive(pkg);
                // Adicionando pacotes no ArrayList com todas as requisições
                packets.add(pkg);
            }
        
            // recebendo dados enviados via broadcast
            //String data = new String(pkg.getData(), 0, pkg.getLength());
            //System.out.println("Dados recebidos: " + data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
