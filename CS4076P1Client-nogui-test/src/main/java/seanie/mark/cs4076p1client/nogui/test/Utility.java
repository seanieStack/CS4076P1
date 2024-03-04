package seanie.mark.cs4076p1server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;

public class Utility {
    //TODO: Implement method for background screen and add to all scenes
    //TODO: Merge 


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


}
