package seanie.mark.cs4076p1client;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.PrintWriter;


public class removeModule {
    public Stage stage;

    private final Runnable returnToMain; // Callback to switch back to the main scene

    private final BufferedReader in;
    private final PrintWriter out;

    public removeModule(Stage stage, Runnable returnToMain, BufferedReader in, PrintWriter out) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initializeScreen();
    }

    private void  initializeScreen () {

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        Label startTimeLabel = new Label("Start Time:");
        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll(Utillity.getTimes());
        selectStartTime.setValue("09:00"); // Default start time


        Label endTimeLabel = new Label("End Time:");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll(Utillity.getTimes());
        selectEndTime.setValue("10:00"); //Default end time


        Label dayLabel = new Label("Day : ");
        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll(Utillity.getDays());
        dayMenu.setValue("Monday"); //Default value


        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();
        //TODO: Fix this , think its fine now check later
        HBox root2 = new HBox();
        root2.setSpacing(10);
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);

        // Create a Text element to display the result
        Text displayText = new Text();
        backButton.setOnAction(e -> returnToMain.run());

        // Set the action when the button is pressed
        submitButton.setOnAction(e -> {
            // Get the text from each TextField
            String userModule = userInputModule.getText();
            String userDay = dayMenu.getSelectionModel().getSelectedItem();
            String startTime = selectStartTime.getSelectionModel().getSelectedItem();
            String endTime = selectEndTime.getSelectionModel().getSelectedItem();
            String userRoom = userInputRoom.getText();

            // Boolean logic for throwing errors
            //TODO: check if this can be done better
            boolean differentTime = VerifyInput.isDifferentTime(startTime, endTime);
            boolean validLength = VerifyInput.isValidSessionLength(startTime, endTime);


            if (!differentTime) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Duplicate time values detected ");
                alert.setContentText("Start and end time can not have the same value !");
                alert.show();
            } else if (!validLength) {

                Alert alert = new Alert(Alert.AlertType.ERROR); //Create method condensing popup window logic
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Session length exceeds 3 hours");
                alert.setContentText("Module sessions can not exceed 3 hours !");
                alert.show();
            }

            String resultText = "rc " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;

            String response;

            try {
                out.println(resultText);
                response = in.readLine();
                if (!VerifyInput.isValidModule(userModule)) {
                    displayText.setText(userModule + ": is not a valid module");
                    Alert nonValidAlert = new Alert(Alert.AlertType.ERROR);
                    nonValidAlert.setTitle("Submission error : Invalid Module");
                    nonValidAlert.setContentText("Module : " + userModule + " does not follow the required module code formatting e.g CS4076");
                    nonValidAlert.show();
                } else {
                    switch (response) {
                        case "nsc":
                            displayText.setText("Error Removing Module");
                            Alert nscAlert = new Alert(Alert.AlertType.ERROR);
                            nscAlert.setTitle("Module removal error : ");
                            nscAlert.setContentText("Module : " + userModule + "could not be removed as this module has not scheduled class's");
                            nscAlert.show();
                            break;
                        case "cnf":
                            displayText.setText("Error Removing Module");
                            Alert cnfAlert = new Alert(Alert.AlertType.ERROR);
                            cnfAlert.setTitle("Module removal error : ");
                            cnfAlert.setContentText("Module : " + userModule + " could not be removed as this module does not exist in the database ");
                            cnfAlert.show();
                            break;
                        case "cr":  //README says 'cr + details' , what details ? //TODO:Fix error ,seanie
                            displayText.setText("Module removed successfully ");
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Module removed successfully");
                            alert.setContentText("Module : " + userModule + "removed successfully , return to see new updated timetable");
                            alert.show();
                            break;

                        default:
                            displayText.setText("Error Adding Module");
                            Alert unknownErrorAlert = new Alert(Alert.AlertType.ERROR);
                            unknownErrorAlert.setTitle("Error adding Module : " + userModule);
                            unknownErrorAlert.setContentText("An unknown error prevented " + userModule + "from being removed");
                            unknownErrorAlert.show();
                    }
                }
            } catch ( Exception ex ) {
                displayText.setText("Error Removing Module");
            }
        });


        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));                                                                // dayLabel, userInputDay removed from below
        root.getChildren().addAll(root2, moduleLabel, userInputModule, startTimeLabel, selectStartTime, endTimeLabel, selectEndTime, dayLabel, dayMenu, roomLabel, userInputRoom, submitButton, displayText);

        Scene scene;
        scene = new Scene(root, 400, 400);

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));


        // Keyboard shortcuts
//        Utillity.qForTermination(scene);
        Utillity.enterForSubmisson(scene,submitButton);

        // Set the scene to the stage and show it
        stage.setScene(scene);
        stage.setTitle("Remove Module");
        stage.show();

    }
}
