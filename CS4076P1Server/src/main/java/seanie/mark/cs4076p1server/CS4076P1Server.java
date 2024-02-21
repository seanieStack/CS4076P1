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

    private static ServerSocket servSock;
    private static final int PORT = 6558;
    
    public static void main(String[] args) {

        try{
            servSock = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("Unable to attach port!");
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

            List<Module> currentModules = new ArrayList<>();

            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            String message = in.readLine();
            //(ac, rc, ds, st) (moduleCode time(00:00-00:00) day room)

            try {

                String action = message.substring(0, 2);
                String details = message.substring(2);

                List<String> possibleActions = Arrays.asList("ac", "rc", "ds", "st");
                if(!possibleActions.contains(action)){
                    throw new IncorrectActionException("Incorrect action");
                }

                switch (action) {
                    case "ac" -> addClass(details, currentModules, out);
                    case "rc" -> removeClass(details, currentModules);
                    case "ds" -> displaySchedule(details, currentModules);
                    case "st" -> {
                        out.println("TERMINATE");
                        link.close();
                    }
                }
            }catch (IncorrectActionException e){
                out.println(e.getMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void addClass(String details, List<Module> currentModules, PrintWriter out) {
        if(currentModules.size() < 5){
            // details will look like "CS4076 09:00-10:00 monday CS4005B"
            String[] parts = details.split(" ");

            String moduleCode = parts[0];
            String time = parts[1];
            String day = parts[2];
            String room = parts[3];

            //todo: check if class overlaps with another class

            //Gets the module codes of the current modules
            List<String> currentModulesNames = new ArrayList<>();
            for(Module m : currentModules){
                currentModulesNames.add(m.getModuleCode());
            }

            //If the module is not already in the list, add it
            if (!currentModulesNames.contains(moduleCode)){
                Module module = new Module(moduleCode);
                module.addTimetableEntry(time, day, room);
                currentModules.add(module);
            }
            else{ //If the module is already in the list, add the timetable entry to it
                for(Module m : currentModules){
                    if(m.getModuleCode().equals(moduleCode)){
                        m.addTimetableEntry(time, day, room);
                    }
                }
            }
            // send message to client to say that the class was added successfully
            out.println("ca");

        }
        else {
            // send message to client to say that the timetable is full
            out.println("ttf");
        }
    }

    private static void removeClass(String details, List<Module> currentModules) {
        //todo: implement removeClass
    }

    private static void displaySchedule(String details, List<Module> currentModules) {
        //todo: implement displaySchedule
    }

}

