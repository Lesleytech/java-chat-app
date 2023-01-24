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

            while (true) {
                System.out.println("Waiting for connection...");

                Socket incoming = serverSocket.accept();
                datagramSocket = new DatagramSocket(Config.SERVER_UDP_PORT);
                InetAddress ip = incoming.getInetAddress();
                DataSender ds = new DataSender(datagramSocket, ip, Config.CLIENT_UDP_PORT);
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

                incoming.close();
                datagramSocket.close();

                System.out.println("Peer disconnected: " + incoming.getInetAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
