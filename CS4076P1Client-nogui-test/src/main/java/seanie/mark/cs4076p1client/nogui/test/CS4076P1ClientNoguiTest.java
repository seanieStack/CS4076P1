package seanie.mark.cs4076p1client.nogui.test;

import java.io.IOException;
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
        Socket link = null;
        try{
            link = new Socket(host, PORT);
        }catch(IOException e){
            System.out.println("Unable to connect to host");
        }
    }
}
