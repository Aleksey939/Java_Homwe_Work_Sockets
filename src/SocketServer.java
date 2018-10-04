import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer  {

    final static int PORT = 10000;
    final String HOST = "localhost";
        public static void main(String arg[]) throws IOException {

        ServerSocket server = new ServerSocket(PORT);
        System.out.print("Wait for Connection...");
        final boolean[] connected = {true};
        Socket remoteClient = null;
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            remoteClient = server.accept();
            System.out.print("Connected to" + remoteClient.getRemoteSocketAddress());
            Socket finalRemoteClient = remoteClient;
            service.submit(new Runnable() {
                public void run() {
                    while (connected[0]) {
                        try {
                            InputStream in = finalRemoteClient.getInputStream();
                            InputStreamReader r = new InputStreamReader(in);
                            BufferedReader reader = new BufferedReader(r);
                            OutputStream out = finalRemoteClient.getOutputStream();
                            PrintStream print = new PrintStream(out, true);

                            String msg = reader.readLine();
                            System.out.print("Server: Client send msg" + msg);
                            print.println(msg);
                            if (msg.contains("Bye"))
                                connected[0] = false;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        finalRemoteClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            });

        }
    }
}



