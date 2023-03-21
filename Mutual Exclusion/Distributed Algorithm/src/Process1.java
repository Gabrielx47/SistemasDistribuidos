import java.io.IOException;

public class Process1 {
    public static void main(String[] args) throws IOException {
        Process process1 = new Process(1, 0);
        int time = process1.getCurrentTime();

        process1.receiveRequests();
        process1.sendMassegeBroadcast("criticalRegion.txt," + process1.getId() + "," + time);

        
    }
}
