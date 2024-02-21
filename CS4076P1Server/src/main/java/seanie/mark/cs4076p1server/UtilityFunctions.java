package seanie.mark.cs4076p1server;

import java.util.ArrayList;
import java.util.List;

public class UtilityFunctions {
    private static int stringTimeToIntTime(String time){
        String temp = time.substring(0, 2);
        temp += time.substring(3);

        return Integer.parseInt(temp);
    }

    public static boolean checkOverlap(String time, String day, List<Module> currentModules){
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
}
