import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Coordinator {
    private static ServerSocket serverCoordinator;
    private static ArrayList<Socket> waitingList;
    private static Socket socketProcess;
    
    /**
     * A a classe permiti que um processo use (grant) e libere (release) a região crítica, onde o mesmo poderá escrever
     * em um arquivo o seu id e a hora da requisição de uso da região crítica.
     * 
     * @throws IOException Ocorre quando uma ou mais exceções da classe IOException
     * for acionada.
     */
    private static void acessCriticalRegion(String expectedMessage) throws IOException{
            BufferedReader inFromProcess = new BufferedReader(new InputStreamReader(socketProcess.getInputStream()));

            String messageRequest = inFromProcess.readLine();

            SendMessageOk(messageRequest, expectedMessage);
    }


    /**
     * Verifica se a mensagem enviada é igual a esparada, se sim, é enviado uma
     * mensagem de
     * retorno ao processo com a palavra "Ok".
     * 
     * @param message         Mensagem enviada pelo processo
     * @param expectedMessage Mensagem esperada pelo coordenador
     * @throws IOException Ocorre quando uma ou mais exceções da classe IOException
     *                     for acionada.
     */
    private static void SendMessageOk(String message, String expectedMessage) throws IOException {
        if (message.equals(expectedMessage)) {
            PrintWriter sendMessageToProcess = new PrintWriter(socketProcess.getOutputStream(), true);
            sendMessageToProcess.println("Ok");
        }
    }
    
    public static void main(String[] args) throws IOException {
        serverCoordinator = new ServerSocket(6000);
        waitingList = new ArrayList<Socket>();

        Request requestMessages = new Request(serverCoordinator, waitingList);
        
        requestMessages.start();

        socketProcess = waitingList.get(0);
        while(socketProcess != null){
            acessCriticalRegion("Usar");
            acessCriticalRegion("Liberar");

            waitingList.remove(0);
            socketProcess = waitingList.get(0);
        }
    }
}
