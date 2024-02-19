package seanie.mark.cs4076p1server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import seanie.mark.cs4076p1server.exceptions.IncorrectActionException;

/**
 *
 * @author Seanie
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
        
        Socket link;
        
        try{
            link = servSock.accept();
            clientConnections++;

            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            String message = in.readLine();

            try {

                String action = message.substring(0, 2);
                String details = message.substring(2, message.length());

                List<String> possibleActions = Arrays.asList("ac", "rc", "ds");
                if(!possibleActions.contains(action)){
                    throw new IncorrectActionException("Incorrect action");
                }

                switch (action) {
                    case "ac" -> { //add class
                        out.println("You added class");
                    }
                    case "rc" -> { //remove class
                        out.println("You removed class");
                    }
                    case "ds" -> { //display schedule
                        out.println("You displayed schedule");
                    }
                }
            }catch (IncorrectActionException e){
                out.println(e.getMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

