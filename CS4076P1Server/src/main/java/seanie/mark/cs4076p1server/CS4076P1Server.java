/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package seanie.mark.cs4076p1server;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author Seani
 */
public class CS4076P1Server {

    private static ServerSocket servSock;
    private static final int PORT = 6558;
    private static int clientConnections = 0;
    
    public static void main(String[] args) {
        try{
            servSock = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("Unable to attrach port!");
            System.exit(1);
        }
        
        do{
            run();
        }while(true);
    }
    private static void run(){
        
        Socket link = null;
        
        try{
            link = servSock.accept();
            clientConnections++;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

