package seanie.mark.cs4076p1server;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.time.Duration;

public class VerifyInput {

   

   public static LocalTime toLocalTime(String time) {
        LocalTime newTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        return newTime;
    }

    public static boolean isValidTime(String startTime, String endTime) {
        try {
            LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
            if (start.isAfter(end)) {
                return false;
            }
        } catch (DateTimeException e) {
            return false;
        }
        return true ;
    }



    public static boolean isDifferentTime ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        if (start.equals(end) ) {
            return  false ;
        }
        else  return  true;
    }

     public static boolean isValidSessionLength ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        Duration duration = Duration.between(start,end);
        long longHours = duration.toHours();
        int hours = (int) longHours;

        if (hours > 3) {
            return false;
        }
        return  true ;
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

    //TODO: Improve this my capping digit length after prefix
    public static boolean isValidRoom(String room) {

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

