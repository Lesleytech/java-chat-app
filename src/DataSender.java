import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataSender {
    public DataSender() {
        try {
            DatagramSocket ds = new DatagramSocket();
            String str = "Hello world";
            InetAddress ip = InetAddress.getByName("127.0.0.1");

            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 5000);
            ds.send(dp);
            ds.close();
            System.out.println("DataSender quit");
        } catch (Exception e) {}
    }
}
