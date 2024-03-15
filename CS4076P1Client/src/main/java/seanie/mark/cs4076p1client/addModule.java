package seanie.mark.cs4076p1client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.DialogPane; //Unused for now , releated to font issue 

import java.io.BufferedReader;
import java.io.PrintWriter;

public class addModule {

    private final Stage stage;
    private final Runnable returnToMain;

    private final BufferedReader in;
    private final PrintWriter out;

    public addModule(Stage stage, Runnable returnToMain, BufferedReader in, PrintWriter out){
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initScreen();
    }

    public void initScreen() {

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
                DialogPane dialogPaneDT = alert.getDialogPane();
                dialogPaneDT.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                dialogPaneDT.getStyleClass().add("my-dialog");
                alert.show();
            } else if (!validLength) {

                Alert alert = new Alert(Alert.AlertType.ERROR); //Create method condensing popup window logic
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Session length exceeds 3 hours");
                alert.setContentText("Module sessions can not exceed 3 hours !");
                DialogPane dialogPaneVl = alert.getDialogPane();
                dialogPaneVl.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                dialogPaneVl.getStyleClass().add("my-dialog");
                alert.show();
            }

            String resultText = "ac " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
            displayText.setText(resultText);

            String response;

            try{
                out.println(resultText);

                response = in.readLine();
                switch (response) {
                    case "ca":
                        // change to error pop ups
                        displayText.setText("Module Added"); // Made redundant , left in for now bc of font issue  
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Module Added !");
                        alert.setHeaderText(null);    
                        alert.setContentText("Timetable is now updated to reflect " + userModule +" being added ");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        dialogPane.getStyleClass().add("my-dialog");
                        alert.show();
                        break;
                    case "ol":
                        displayText.setText("Module Overlaps");
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setTitle("Error adding Module : " + userModule);
                        overlapAlert.setHeaderText(null);
                        overlapAlert.setContentText("Module : " + userModule +" already exists and was not added  ");
                        DialogPane dialogPaneOL = overlapAlert.getDialogPane();
                        dialogPaneOL.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        dialogPaneOL.getStyleClass().add("my-dialog");
                        overlapAlert.show();

                        break;
                    case "ttf":
                        displayText.setText("Timetable Full");
                        Alert ttFullAlert = new Alert(Alert.AlertType.ERROR);
                        ttFullAlert.setTitle("Error adding Module : " + userModule);
                        ttFullAlert.setHeaderText(null);
                        ttFullAlert.setContentText("Module : " + userModule +" could not be added , A student can not exceed 5 modules   ");
                        DialogPane dialogPanettf = ttFullAlert.getDialogPane();
                        dialogPanettf.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        dialogPanettf.getStyleClass().add("my-dialog");
                        ttFullAlert.show();

                        break;
                    case "im":
                        displayText.setText("Invalid Module");
                        Alert wrongFormatAlert = new Alert(Alert.AlertType.ERROR);
                        wrongFormatAlert.setTitle("Error adding Module : " + userModule);
                        wrongFormatAlert.setHeaderText(null);
                        wrongFormatAlert.setContentText("Module : " + userModule + " does not follow the proper module formatting e.g. CS4076");
                        DialogPane dialogPaneWf = wrongFormatAlert.getDialogPane();
                        dialogPaneWf.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        dialogPaneWf.getStyleClass().add("my-dialog");
                        wrongFormatAlert.show();

                        break;
                    default:
                        displayText.setText("Error Adding Module");
                        Alert unknownErrorAlert = new Alert(Alert.AlertType.ERROR);
                        unknownErrorAlert.setTitle("Error adding Module : " + userModule);
                        unknownErrorAlert.setHeaderText(null);
                        unknownErrorAlert.setContentText("An unknown error  ");
                        unknownErrorAlert.show();

                }
            } catch (Exception ex){
                displayText.setText("Error Adding Module");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));                                                                // dayLabel, userInputDay removed from below
        root.getChildren().addAll(root2, moduleLabel, userInputModule, startTimeLabel, selectStartTime, endTimeLabel, selectEndTime, dayLabel, dayMenu, roomLabel, userInputRoom, submitButton, displayText);

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        Scene scene;
        scene = new Scene(root, 400, 475);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        Utillity.enterForSubmisson(scene,submitButton);

        stage.setScene(scene);
        stage.setTitle("Add Module");
        stage.show();
    }
}


