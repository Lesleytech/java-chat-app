import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = args[0];

        try {
            DatagramSocket datagramSocket = new DatagramSocket(Config.CLIENT_UDP_PORT);
            Socket socket = new Socket(InetAddress.getByName(host), Config.TCP_PORT);
            DataSender ds = new DataSender(datagramSocket, socket.getInetAddress(), Config.SERVER_UDP_PORT);
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

            datagramSocket.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
