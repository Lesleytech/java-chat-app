import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataSender {
    DatagramSocket ds;
    InetAddress ip;

    public DataSender(DatagramSocket ds, InetAddress ip) {
        this.ds = ds;
        this.ip = ip;
    }

    public void send(String message) {
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, Config.UDP_PORT);
            ds.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
