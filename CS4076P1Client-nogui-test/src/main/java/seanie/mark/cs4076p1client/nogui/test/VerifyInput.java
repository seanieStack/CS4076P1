package seanie.mark.cs4076p1server;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VerifyInput {

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

}
