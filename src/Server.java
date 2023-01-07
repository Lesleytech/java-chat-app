import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Config.TCP_PORT);
            DatagramSocket datagramSocket;
            System.out.println("Waiting for connection...");

            while (true) {
                Socket incoming = serverSocket.accept();
                datagramSocket = new DatagramSocket(Config.UDP_PORT);
                InetAddress ip = incoming.getInetAddress();
                DataSender ds = new DataSender(datagramSocket, ip);
                Scanner scanner = new Scanner(System.in);
                Thread drThread = new Thread(new DataReceiver(datagramSocket));

                System.out.println("New connection: " + ip + ". Send 'disconnect' to exit chat");

                drThread.start();
                ds.send("Connection accepted! Send 'disconnect' to exit chat");

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

                incoming.close();
                datagramSocket.close();

                System.out.println("Peer disconnected: " + incoming.getInetAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
