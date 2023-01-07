import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DataReceiver implements Runnable {
    DatagramSocket ds;

    public DataReceiver(DatagramSocket ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                ds.receive(dp);

                String message = new String(dp.getData(), 0, dp.getLength());

                if (message.equalsIgnoreCase("disconnect")) {
                    break;
                }

                String output = String.format("[%s] %s", dp.getAddress().toString().replace("/", ""), message);
                System.out.println(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
