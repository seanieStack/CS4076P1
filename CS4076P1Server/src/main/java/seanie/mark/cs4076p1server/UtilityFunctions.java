package seanie.mark.cs4076p1server;

import java.util.ArrayList;
import java.util.List;

public class UtilityFunctions {
    private static int stringTimeToIntTime(String time){
        String temp = time.substring(0, 2);
        temp += time.substring(3);

        return Integer.parseInt(temp);
    }

    private static boolean checkOverlap(String time, String day, List<Module> currentModules){
        //get all the timetable entries for the current modules
        List<TimetableEntry> allEntries = new ArrayList<>();
        for(Module m : currentModules){
            allEntries.addAll(m.getTimetable());
        }

        //check if the new entry overlaps with any of the existing entries
        for(TimetableEntry m : allEntries){
            if(m.getDay().equals(day)){
                String[] timeSplit = time.split("-");
                int startTime1 = stringTimeToIntTime(timeSplit[0]);
                int endTime1 = stringTimeToIntTime(timeSplit[1]);

                int startTime2 = stringTimeToIntTime(m.getStartTime());
                int endTime2 = stringTimeToIntTime(m.getEndTime());

                return endTime1 > startTime2 && startTime1 < endTime2;

            }
        }
        return false;
    }

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
