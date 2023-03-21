import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Process1 {
    private static Socket socketProcess;
    
    private static void acessCriticalRegion(String expectedMessage) throws IOException {
        BufferedReader inFromCoordinator = new BufferedReader(new InputStreamReader(socketProcess.getInputStream()));

        String message = inFromCoordinator.readLine();

        //SendMessageOk(message, expectedMessage);
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        socketProcess = new Socket("localhost", 6000);
    }
}
