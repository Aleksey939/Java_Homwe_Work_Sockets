import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String arg[]) throws IOException {
        Socket client =new Socket();
        client.connect(new InetSocketAddress(InetAddress.getLocalHost(), SocketServer.PORT));
        boolean connected=true;
        while(connected) {
                Scanner scanner = new Scanner(System.in);
            try {
                InputStream in = client.getInputStream();
                InputStreamReader r = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(r);
                OutputStream out = client.getOutputStream();
                PrintStream print = new PrintStream(out, true);
                System.out.print(" Enter Message");
                String request =scanner.nextLine();
                print.println(request);
                String answer = reader.readLine();
                System.out.print("Client: Server response "+answer);
                if(request.contains("Bye"))
                    connected=false;

            }

            catch (Exception ex){
                ex.printStackTrace();
            }
            }
        client.close();
        }
    }

