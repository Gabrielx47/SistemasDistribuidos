import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Process{
    private int id;
    private Clock clock;
    private Request requests;


    Process(int id, int hour){
        this.id = id;
        clock = new Clock(hour);
        requests = new Request();
    }

    public void sendMassegeBroadcast(String message) throws IOException{
        // byte[] b = "Testando Broadcast".getBytes();
        byte[] b = message.getBytes();
        // Definindo o endere�o de envio do pacote neste caso o endere�o de broadcast
        InetAddress addr = InetAddress.getByName("255.255.255.255");
        DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 6001);
        DatagramSocket ds = new DatagramSocket();
        ds.send(pkg);// enviando pacote broadcast
    }

    public void receiveRequests(){
        requests.start();
    }

    public int getId() {
        return id;
    }

    public int getCurrentTime() {
        return clock.currentTime();
    }
    
}