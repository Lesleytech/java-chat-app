import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = args[0];

        try {
            DatagramSocket datagramSocket = new DatagramSocket(Config.UDP_PORT);
            Socket socket = new Socket(InetAddress.getByName(host), Config.TCP_PORT);
            DataSender ds = new DataSender(datagramSocket, socket.getInetAddress());
            Scanner scanner = new Scanner(System.in);
            Thread drThread = new Thread(new DataReceiver(datagramSocket));

            drThread.start();

            String message;

            while (true) {
                message = scanner.nextLine();
                ds.send(message);

                if (message.equalsIgnoreCase("disconnect")) {
                    break;
                }
            }

            // This will stale the Server thread while waiting for messages.
            // When it receives the 'disconnect' message, execution will continue and all sockets are closed.
            drThread.join();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
