package seanie.mark.cs4076p1client;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VerifyInput {

    public static boolean isDifferentTime ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        return !start.equals(end);
    }

     public static boolean isValidSessionLength ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        Duration duration = Duration.between(start,end);
        long longHours = duration.toHours();
        int hours = (int) longHours;

         return hours <= 3;
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
        return true ;
    }
}

