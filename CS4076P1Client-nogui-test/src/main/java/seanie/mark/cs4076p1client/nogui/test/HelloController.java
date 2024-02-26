package seanie.mark.cs4076p1server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public static class VerifyInput {

        public static boolean isValidTimeFormat ( String startTime , String endTime ) {
            if ( startTime.length() != 5 || endTime.length() != 5) {
                return  false ; // Error message
            } else {
                return true ;
            }
        }
        public static boolean isValidTime ( String startTime , String endTime) {
            LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
            if (start.isAfter(end) || start.equals(end) ) {
                return  false ;
            }
            else  return  true;
        }
    }
}
