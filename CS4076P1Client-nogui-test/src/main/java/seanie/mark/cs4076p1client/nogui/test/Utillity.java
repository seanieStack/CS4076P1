package seanie.mark.cs4076p1server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;

public class Utillity {
    //TODO: Implement method for background screen and add to all scenes
    //TODO: Merge Utillity and VerifyInput


    public static void qForTermination(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Q)  { // Reminder : KeyCode is case agnostic
                quitApp();  // Terminate app
            }
        });
    }

    public static void quitApp() {
        Platform.exit();
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


}
