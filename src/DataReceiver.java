import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DataReceiver implements Runnable {
    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket(5000);

            while (true) {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                ds.receive(dp);

                String str = new String(dp.getData(), 0, dp.getLength());
                if(str.equalsIgnoreCase("disconnect")) {
                    break;
                }

                System.out.println(str);
            }

            ds.close();
        } catch (Exception e) {
        }
    }
}
