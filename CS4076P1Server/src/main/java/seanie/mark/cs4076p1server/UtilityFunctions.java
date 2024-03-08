package seanie.mark.cs4076p1server;

import java.util.*;

public class UtilityFunctions {
    static int stringTimeToIntTime(String time){
        String temp = time.substring(0, 2);
        temp += time.substring(3);

        return Integer.parseInt(temp);
    }

    static boolean checkOverlap(String time, String day, List<Module> currentModules){
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

    public static boolean isValidModule(String module) {
        // Check if the module string length is exactly 6
        if (module.length() != 6) {
            return false;
        }

        // Check the first two characters for letters
        for (int i = 0; i < 2; i++) {
            char ch = module.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }

        // Check the last four characters for digits
        for (int i = 2; i < 6; i++) {
            char ch = module.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        // If all checks pass, return true
        return true;
    }

    static boolean isValidRoom(String room) {

        Set<String> campusBuildings = new HashSet<>(Arrays.asList(
                "SG", "S", "KGB", "KB", "CSG", "CS", "GLG", "GL", "FB", "FG", "F", "ERB", "ER",
                "LCB", "LC", "LB", "LG", "L", "SR", "PG", "PM", "P", "HSG", "HS", "A", "AM", "B",
                "BM", "CG", "C", "CM", "DG", "DM", "D", "EG", "E", "EM", "AD", "IWG", "IW",
                "GEM", "GEMS"
        ));

        if (!room.contains("-")) {
            return false;
        }

        String prefix = room.toUpperCase().split("-")[0];
        return campusBuildings.contains(prefix);
    }

}
