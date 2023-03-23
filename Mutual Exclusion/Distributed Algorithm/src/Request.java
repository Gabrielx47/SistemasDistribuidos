import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Request extends Thread {
    private ArrayList<String> criticalRegionName;
    private int[] pid;
    public int[] time; 
    private int messageOk;
    private int numberOfPackets;

    Request(){
        messageOk = 0;
        numberOfPackets = 0;
        criticalRegionName = new ArrayList<>();
        pid = new int[10];
        time = new int[10];
    }

    @Override
    public void run() {
        try (// Classe java para trabalhar com multicast ou broadcast
            MulticastSocket mcs = new MulticastSocket(6001)) {
            byte rec[] = new byte[256];
            DatagramPacket pkg = new DatagramPacket(rec, rec.length);

            String message;
            String[] messageParts;

            while(true) {
                //mcs.setSoTimeout(10000);
                // Recebendo datagrama 
                mcs.receive(pkg);
                message = new String(pkg.getData(), 0, pkg.getLength());
                messageParts = message.split(",");

                System.out.println("message = " + message);
                if(messageParts[0].compareTo("Ok") == 0) {
                    ++messageOk;
                    System.out.println("messageOk = " + messageOk);
                    System.out.println("numberOfPackets = " + numberOfPackets);
                }else if(message != null){
                    
                    System.out.println("Lenght messageParts: " + messageParts.length);
                    System.out.println("messageParts[0]: " + messageParts[0]);
                    
                    /*if(criticalRegionName[numberOfPackets] != null) 
                        System.out.println("criticalRegionName[numberOfPackets] = null");*/
                    
                    criticalRegionName.add(messageParts[0]);
                    System.out.println("messageParts[1]: " + messageParts[1]); 
                    pid[numberOfPackets] = Integer.parseInt(messageParts[1]);
                    time[numberOfPackets] = Integer.parseInt(messageParts[2]);
                    ++numberOfPackets;
                    System.out.println("numberOfPackets = " + numberOfPackets);

                    //Ordena as requisições por horário de envio da requisição
                    bubbleSort();
                }

                message = null;
            }
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void bubbleSort(){
        for(int i = 0; i < numberOfPackets; i++) {
            for(int x = i + 1; x < numberOfPackets; x++) {
                if(time[i] > time[x]){
                    swap(i, x, time);
                    swap(i, x, pid);
                    swap(i, x, criticalRegionName);
                }
            }
        }
    }

    private void swap(int i, int x,int[] numbers) {
        int aux;
        aux = numbers[i];
        numbers[i] = numbers[x];
        numbers[x] = aux;
    }

    private void swap(int i, int x, ArrayList<String> numbers) {
        String aux = numbers.get(i);
        numbers.set(i, numbers.get(x)); 
        numbers.set(x, aux);
    }

    public int getMessageOk() {
        return messageOk;
    }

    public int getNumberOfPackets() {
        return numberOfPackets;
    }

    public boolean numberOfPacketsLessThanMessageOk(){
        return (messageOk < numberOfPackets) ? true : false;
    }
}
