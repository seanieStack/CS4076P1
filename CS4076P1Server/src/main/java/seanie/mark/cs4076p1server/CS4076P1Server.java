package seanie.mark.cs4076p1server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seanie.mark.cs4076p1server.exceptions.IncorrectActionException;

/**
 *
 * @author Seanie
 */
public class CS4076P1Server {
    //aberrations

    //actions client can request
    //ac = add class
    //rc = remove class
    //ds = display schedule
    //st = terminate connection

    //server responses
    //ttf = timetable full
    //ca = class added
    //ol = class overlaps
    //cr = class removed will contain new free time
    //nsc = no scheduled class (at that time)
    //cnf = class not found (class not in timetable)
    //cp = class printed


    private static ServerSocket servSock;
    private static final int PORT = 6558;
    
    public static void main(String[] args) {

        try{
            servSock = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("Unable to attach port!");
            System.exit(1);
        }

        //noinspection InfiniteLoopStatement
        do{
            run();
        }while(true);
    }
    private static void run(){
        Socket link;
        
        try{
            link = servSock.accept();

            List<Module> currentModules = new ArrayList<>();

            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            boolean running = true;

            while(running) {

                String message = in.readLine();
                //(ac, rc, ds, st) (moduleCode time(00:00-00:00) day room)
                System.out.println("Message received: " + message);

                try {

                    String action = message.substring(0, 2);
                    String details = message.substring(2);

                    List<String> possibleActions = Arrays.asList("ac", "rc", "ds", "st");
                    if (!possibleActions.contains(action)) {
                        throw new IncorrectActionException("Incorrect action\n");
                    }

                    switch (action) {
                        case "ac" -> out.println(UtilityFunctions.addClass(details, currentModules));
                        case "rc" -> out.println(UtilityFunctions.removeClass(details, currentModules));
                        case "ds" -> out.println(UtilityFunctions.displaySchedule(details, currentModules));
                        case "st" -> {
                            System.out.println("TERMINATE");
                            out.println("TERMINATE");
                            link.close();
                            running = false;
                        }
                    }
                } catch (IncorrectActionException e) {
                    out.println(e.getMessage());
                }
            }
        }catch(IOException e){
            System.out.println("Error when connecting to client" + Arrays.toString(e.getStackTrace()));
        }
    }
}

