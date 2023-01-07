import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends ServerSocket{
    public TCPServer(int port) throws IOException {
        super(port);
        waitForConnections();
    }

    private void waitForConnections() {
        System.out.println("Waiting for connection...");

        try {
            while(true) {
                Socket incoming = this.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));

                System.out.println("New connection: " + incoming.getInetAddress());

                out.println("Connection accepted! We can chat now");
                out.flush();

                while(true) {
                    String input = in.readLine();

                    if(input == null || input.equalsIgnoreCase("disconnect")) {
                        break;
                    }
                    System.out.println(incoming.getInetAddress() + "said -> " + input);
                    out.println("Echo: " + input);
                    out.flush();
                }

                incoming.close();
                out.close();
                in.close();

                System.out.println("Peer disconnected: " + incoming.getInetAddress());
            }
        } catch (Exception ex) {}
    }
}
