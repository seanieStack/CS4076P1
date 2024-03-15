package seanie.mark.cs4076p1server;

import java.util.List;

public class ActionHandler {
    static String addClass(String details) {
        int numberOfModules = UtilityFunctions.getNumberOfModules();
        if(numberOfModules <= 5){
            // details will look like "CS4076 09:00-10:00 monday CS4005B"
            String[] parts = details.strip().split(" ");

            String moduleCode = parts[0];
            String time = parts[1];
            String day = parts[2];
            String room = parts[3];

            boolean overlapping = UtilityFunctions.checkOverlap(time, day);
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

            //check if module is in db
            boolean moduleInDB = UtilityFunctions.moduleInDB(moduleCode);
            if(moduleInDB){
                UtilityFunctions.addTimetableEntryToDB(moduleCode, time, day, room);
            }
            else{
                UtilityFunctions.addModuleToDB(moduleCode);
                UtilityFunctions.addTimetableEntryToDB(moduleCode, time, day, room);
            }
            // send message to client to say that the class was added successfully
            return "ca";

        }
        else {
            // send message to client to say that the timetable is full
            return "ttf";
        }
    }

    static String removeClass(String details) {
        String[] parts = details.strip().split(" ");

        String moduleCode = parts[0];
        String time = parts[1];
        String day = parts[2];
        String room = parts[3];

        boolean moduleInDB = UtilityFunctions.moduleInDB(moduleCode);
        if(moduleInDB){
            return UtilityFunctions.removeTimetableEntryFromDB(moduleCode, time, day, room);
        }
        else{
            return "cnf";
        }
    }

    static String displaySchedule(String details) {
        String[] parts = details.strip().split(" ");

        String moduleCode = parts[0];

        boolean moduleInDB = UtilityFunctions.moduleInDB(moduleCode);
        if(moduleInDB){
            return UtilityFunctions.displayModuleTimetable(moduleCode);
        }
        else{
            return "cnf";
        }
    }

    static String getTimetableEntry(String details, List<Module> currentModules) {
        String currentEntrys = " ";

        String[] parts = details.strip().split(" ");
        String moduleCode = parts[0];
        List<String> currentModulesNames = new ArrayList<>();
        for (Module m : currentModules) {
            if (m.getModuleCode().equals(moduleCode)) {
                List<TimetableEntry> timetable = m.getTimetable();
                for (TimetableEntry t : timetable) {
                    for (int i = 0; i < timetable.size(); i++) {
                        System.out.println(t.getTime() + " " + t.getDay() + " " + t.getRoom());
                        return (moduleCode + " " + t.getTime() + " " + t.getDay() + " " + t.getRoom());
                    }
                }

            }

        }
        return currentEntrys;
}
