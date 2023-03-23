import java.io.IOException;
import java.io.RandomAccessFile;

public class Process2 {
    public static void main(String[] args) throws IOException {
        Process process1 = new Process(1, 0);

        int time = process1.getCurrentTime();

        // Enviando mensagem de requisição da requesição de uso da região crítica
        process1.sendMassegeBroadcast("criticalRegion.txt," + process1.getId() + "," + time);

        // Enviando recebendo requisição
        process1.receiveRequests();

        while (process1.requests.numberOfPacketsLessThanMessageOk()) {

        }

        if (time < process1.requests.time[0]) {
            // Escreve na região crítica
            RandomAccessFile file = new RandomAccessFile("criticalRegion.txt", "rw");
            byte[] bytes = (Integer.toString(process1.getId()) + " - " + Integer.toString(time)).getBytes();
            file.seek(file.length());
            file.write(bytes);
            file.close();
        } else {
            process1.sendMassegeBroadcast("Ok");
        }

        // Ao sair da região crítica o processo deve enviar uma mensagem de "Ok"
        process1.sendMassegeBroadcast("Ok");
    }
}
