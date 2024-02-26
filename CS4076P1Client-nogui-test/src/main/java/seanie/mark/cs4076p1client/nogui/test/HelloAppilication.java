package seanie.mark.cs4076p1server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;



import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        // Create TextFields for user input
        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        Label startTimeLabel = new Label("Start Time:");
        TextField userInputStartTime = new TextField();


        Label endTimeLabel = new Label("End Time:");
        TextField userInputEndTime = new TextField();

        Label dayLabel = new Label("Day:");
        TextField userInputDay = new TextField();

        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();

        // Create a Button to trigger the action
        Button submitButton = new Button("Submit");

        // Create a Text element to display the result
        Text displayText = new Text();

        // Set the action when the button is pressed
        submitButton.setOnAction(e -> {
            // Get the text from each TextField
            String userModule = userInputModule.getText();
            String userDay = userInputDay.getText();
            String startTime = userInputStartTime.getText();
            String endTime = userInputEndTime.getText();
            String userRoom = userInputRoom.getText();

            boolean validTimeFormat = VerifyInput.isValidTimeFormat(startTime,endTime);
            boolean validTime = VerifyInput.isValidTime(startTime,endTime);
            boolean differentTime = VerifyInput.isDifferentTime(startTime,endTime);

            errorLabel.setText("");

            if (!validTimeFormat) {
                errorLabel.setText("Time must be 5 in length !"); // Causes crash
            } else if (!differentTime) {
                errorLabel.setText("Start and end time can not have the same value !"); //Working
            } else if (!validTime) {
                errorLabel.setText("Start time can not be greater than end time !"); // Working
            }

            // Concatenate the inputs for display
            String resultText = "Module: " + userModule + "\nDay: " + userDay + "\nStart Time: " + startTime + "\nEnd Time: " + endTime + "\nRoom: " + userRoom;
            displayText.setText(resultText);
        });

        // Create a VBox to hold our elements, add all elements including labels
        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(moduleLabel, userInputModule, startTimeLabel, userInputStartTime, endTimeLabel, userInputEndTime, dayLabel, userInputDay, roomLabel, userInputRoom, submitButton, displayText,errorLabel);
        //root.getChildren().add(errorLabel);


        // Create a Scene with our layout
        Scene scene = new Scene(root, 400, 400); // Adjusted for better visibility

        // Set the scene to the stage and show it
        stage.setScene(scene);
        stage.setTitle("JavaFX User Input Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
