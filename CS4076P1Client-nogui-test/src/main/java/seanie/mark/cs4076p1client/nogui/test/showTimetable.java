package seanie.mark.cs4076p1server;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class showTimetable {
    public Stage stage;

    public showTimetable(Stage stage) {
        this.stage = stage;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10); // Use a VBox layout with spacing of 10
        Label welcomeScreen = new Label(" Empty for now (DISPLAY TT)");
        Button submitButton = new Button("Submit");
        layout.getChildren().addAll(welcomeScreen,submitButton);
        Scene scene = new Scene(layout, 400, 400); // Create the scene with the layout
        stage.setScene(scene); // Set the scene on the stage
        stage.setTitle("Welcome Screen"); // Optional: set a title for the window


        Utlity.qForTermination(scene); //Q to terminate
        Utlity.enterForSubmisson(scene, submitButton); // Submit using ENTER , Prob not required for this scene

    }



}
