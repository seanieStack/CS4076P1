package seanie.mark.cs4076p1server;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler {
    static String addClass(String details, List<Module> currentModules) {
        if(currentModules.size() < 5){
            // details will look like "CS4076 09:00-10:00 monday CS4005B"
            String[] parts = details.strip().split(" ");

            String moduleCode = parts[0];
            String time = parts[1];
            String day = parts[2];
            String room = parts[3];

            boolean overlapping = UtilityFunctions.checkOverlap(time, day, currentModules);
            if(overlapping){
                // send message to client to say that the class overlaps
                return "ol";
            }

            boolean validModule = UtilityFunctions.isValidModule(moduleCode);
            if (!validModule) {
                // send message to client to say that the module is invalid
                return "im";
            }

            boolean validRoom = UtilityFunctions.isValidRoom(room);
            if (!validRoom) {
                // send message to client to say that the room is invalid
                return "ir";
            }

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
            return "ca";

        }
        else {
            // send message to client to say that the timetable is full
            return "ttf";
        }
    }

    static String removeClass(String details, List<Module> currentModules) {
        String[] parts = details.strip().split(" ");

        String moduleCode = parts[0];
        String time = parts[1];
        String day = parts[2];
        String room = parts[3];

        boolean removed = false;

        for(Module m : currentModules){
            if(m.getModuleCode().equals(moduleCode) ) {
                if (m.removeTimetableEntry(time, day, room).equals("cr")) {
                    removed = true;
                } else {
                    return "nsc";
                }
            }
        }
        if (removed){
            return "cr" + " " + time + " " + day + " " + room;
        }
        else{
            return "cnf";
        }
    }

    static String displaySchedule(String details, List<Module> currentModules) {
        String[] parts = details.strip().split(" ");

        String moduleCode = parts[0];

        for(Module m : currentModules){
            if(m.getModuleCode().equals(moduleCode)){
                List<TimetableEntry> timetable = m.getTimetable();
                for(TimetableEntry t : timetable){
                    System.out.println(t.getTime() + " " + t.getDay() + " " + t.getRoom());
                }
                return "cp";
            }
        }
        return "cnf";
    }
}
