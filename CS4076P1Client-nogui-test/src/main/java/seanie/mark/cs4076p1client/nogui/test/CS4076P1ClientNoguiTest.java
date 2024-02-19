package seanie.mark.cs4076p1client.nogui.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 *
 * @author Seani
 */
public class CS4076P1ClientNoguiTest {
    
    private static InetAddress host;
    private static final int PORT = 6558;
    
    public static void main(String[] args) {
        try{
            host = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        run();
    }
    
    private static void run(){
        Socket link;
        try{
            link = new Socket(host, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message, response;

            System.out.println("Enter message: ");
            message = userEntry.readLine();
            out.println(message);
            response = in.readLine();
            System.out.println("\nSERVER> " + response);

        }catch(IOException e){
            System.out.println("Unable to connect to host");
        }
    }
}
