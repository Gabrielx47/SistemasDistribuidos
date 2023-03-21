import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Request extends Thread{
    private ServerSocket serverCoordinator;
    private ArrayList<Socket> waitingProcessList;

    Request(ServerSocket serverCoordinator, ArrayList<Socket> waitingProcessList){
        this.serverCoordinator = serverCoordinator;
        this.waitingProcessList = waitingProcessList;
    }

    @Override
    public void run() {
        while(true){
            try {
                waitingProcessList.add(serverCoordinator.accept());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
