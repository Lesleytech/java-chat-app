import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataSender {
    DatagramSocket ds;
    InetAddress ip;
    int port;

    public DataSender(DatagramSocket ds, InetAddress ip, int port) {
        this.ds = ds;
        this.ip = ip;
        this.port = port;
    }

    public void send(String message) {
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, port);
            ds.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
