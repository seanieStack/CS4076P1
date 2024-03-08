package seanie.mark.cs4076p1client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Utillity {
    //TODO: Implement method for background screen and add to all scenes
    //TODO: Merge Utillity and VerifyInput


    public static void quitApp(BufferedReader in, PrintWriter out) {
        try{
            out.println("st");
            String response = in.readLine();
            System.out.println(response);
            in.close();
            out.close();
            Platform.exit();
        } catch (Exception e) {
            System.out.println("Error in closing connection");
        }
    }

    public static void enterForSubmisson(Scene scene, Button button) { //@param scene is used for setOnKeyPressed
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                button.fire(); // Trigger setOnAction function
            }
        });
    }

     public static String[] getTimes() {
        return new String[]{"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};

    }

    public static String[] getDays() {
        return new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"} ;
    }

     //Payload will take the format <CS4096><14:00-15:00><Thursday><KGB-10>
    public static String[] splitPayload ( String payload) { 
        //Split spring into parts : Module code [0] , Time[1] , Day [2] and Room [3]
        String trimmedInput = payload.substring(1, payload.length() - 1);

        // Split the string based on "><" pattern
        return trimmedInput.split("><");
    }

    public static String[] splitTime(String timePart) {
        return timePart.split("-"); // Splits time into start time [0] and end time [1]
    }
    public static int [] moduleNodes (String payload) {
        String[] days = getDays();
        String[] times = getTimes();
        
        String day = splitPayload(payload)[2];
      

        // Isolate start and end Times
        String [] bothTimes =splitTime(splitPayload(payload)[1]);

        //Find matching time
        int nodeY = 0;

        for (int i = 0; i < times.length; i++) {
            if (bothTimes[0].equals(times[i])) { // Uses start time for comparison
                nodeY = i + 1; // + 1 to skip header
                break;
            }
        }

        int nodeX = 0;
        for (int j = 0; j < days.length; j++) {
            if (day.equals(days[j])) {
                nodeX = j + 1; //+1 to skip header
                break;
            }
        }

        return new int[]{nodeX,nodeY}; // Returns day (x) and time (y)
    }

}
