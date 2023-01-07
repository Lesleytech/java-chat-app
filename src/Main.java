public class Main {
    public static void main(String[] args)  {
        try {
            new Thread(new DataReceiver()).start();
//            new DataSender();
        } catch (Exception ex) {}
    }
}