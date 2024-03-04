package seanie.mark.cs4076p1server;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AddModule {


    private final Stage stage;
   ;
    private Runnable returnToMain; // Callback to switch back to the main scene

    public AddModule(Stage stage, Runnable returnToMain) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        initScreen();
    }

    public void initScreen() {
        //Application logic class
        

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        Label startTimeLabel = new Label("Start Time:");
        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectStartTime.setValue("09:00"); // Default start time


        Label endTimeLabel = new Label("End Time:");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectEndTime.setValue("10:00"); //Default end time


        Label dayLabel = new Label("Day : ");
        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
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
        backButton.setOnAction(e -> {
        returnToMain.run();
        });

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
            boolean validModule = VerifyInput.isValidModule(userModule);
            boolean validRoom = VerifyInput.isValidRoom(userRoom);



            //TODO: Refactor to switch / case
            if (!validModule) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Module : An error has occurred ");
                alert.setHeaderText("Incorrect Module Name");
                alert.setContentText("Must be a valid module ! e.g CS4076");
                alert.show();
            } /** else if (moduleExists) { //TODO: Link this with seanies code
             Needs ImperrorLalenmenation
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Module : An error has occurred ");
             alert.setHeaderText("If you wish to remove" + userModule + "please select 'return' and then 'remove module' ");
             alert.setContentText("lorem impsum filler ");
             alert.show();
             }
             **/

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
            } else if (!validRoom) {
                Alert alert = new Alert(Alert.AlertType.ERROR); //Create method condensing popup window logic
                alert.setTitle("Room : An error has occurred");
                alert.setHeaderText("The room you entered is invalid. Please enter a valid room e.g KBG-01");
                alert.setContentText("See the Undergrad teaching map for a list of valid rooms (https://www.ul.ie/media/24142/download?inline). ");
                alert.show();
            }
            // Here for testing purposes , To be removed before submisson

            String resultText = "Module: " + userModule + "\nDay: " + userDay + "\nStart Time: " + startTime + "\nEnd Time: " + endTime + "\nRoom: " + userRoom;
            displayText.setText(resultText);
            String payloadContent = ("<" + userModule + ">" + "<" + startTime + "-" + endTime + ">" + "<" + userDay + ">" + "<" + userRoom + ">");
            displayText.setText(payloadContent);
        });


        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));                                                                // dayLabel, userInputDay removed from below
        root.getChildren().addAll(root2, moduleLabel, userInputModule, startTimeLabel, selectStartTime, endTimeLabel, selectEndTime, dayLabel, dayMenu, roomLabel, userInputRoom, submitButton, displayText);



        Scene scene;
        scene = new Scene(root, 400, 400);


        // Keyboard shortcuts
        Utillity.qForTermination(scene);
        Utillity.enterForSubmisson(scene,submitButton);

        // Set the scene to the stage and show it
        stage.setScene(scene);
        stage.setTitle("Add Module");
        stage.show();
    }

    public void quitApp() {
        Platform.exit();
    }


