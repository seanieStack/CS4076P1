package seanie.mark.cs4076p1server;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VerifyInput {

    //TODO: Implement a toLocalTime method and incorparate in methods

    public static boolean isValidTimeFormat(String startTime, String endTime) {
        if (startTime.length() != 5 || endTime.length() != 5) {
            return false; // Error message
        } else {
            return true;
        }
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

    //TODO: Implement method that ensures start and endtimes end with :00
    public static boolean endsHourly (String startTime , String endTime) {
        for (int i = 3 ; i <6 ; i ++) {
            if (startTime.charAt(i) != 0 || endTime.charAt(i) != 0) {
                return false;
            }
        }
        return true;
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

}

