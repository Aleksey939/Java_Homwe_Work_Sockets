import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer  extends Thread {
    static final int PORT = 10000;
    static final String HOST = "localhost";
    private  boolean connected;

    Socket remoteClient = null;
       public SocketServer(Socket rc,boolean connected ){
        this.remoteClient=rc;
        this.connected=connected;
    }
    public void run()  {
        System.out.print("Connected to" + remoteClient.getRemoteSocketAddress());
        while (connected) {
            try {
                InputStream in = remoteClient.getInputStream();
                InputStreamReader r = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(r);
                OutputStream out = remoteClient.getOutputStream();
                PrintStream print = new PrintStream(out, true);

                String msg = reader.readLine();
                System.out.print("Server: Client send msg" + msg);
                print.println(msg);
                if (msg.contains("Bye"))
                    connected = false;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            remoteClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String arg[]) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.print("Wait for Connection...");
     boolean connected = true;
       Socket remoteClient = null;
        while (true) {
            remoteClient = server.accept();
            new SocketServer(remoteClient,connected).start();

        }

    }
}



