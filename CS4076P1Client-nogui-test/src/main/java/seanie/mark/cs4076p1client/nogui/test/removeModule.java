package seanie.mark.cs4076p1server;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class removeModule {
    public Stage stage;

    private Runnable returnToMain; // Callback to switch back to the main scene

    public removeModule(Stage stage,Runnable returnToMain) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10); 
        Label choiceBoxHeader = new Label("Please select the module you wish to remove ");
        Button backButton = new Button("Return");
        ChoiceBox <String> moduleOptions = new ChoiceBox<>();
        moduleOptions.getItems().addAll("A","B","Only for now "); //TODO: Connect rest of funtionallity here

        Button submitButton = new Button("Submit");
        layout.getChildren().addAll(backButton,choiceBoxHeader,moduleOptions,submitButton);
        Scene scene = new Scene(layout, 400, 400); 
        stage.setScene(scene); 
        stage.setTitle("Welcome Screen"); 

        backButton.setOnAction(e -> {
            returnToMain.run();
        });

        submitButton.setOnAction(e -> { //TODO: This code is flawed and needs to be fixed , Wrap in logic for if (sucsessfull)
            Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Submisson Sucsessfull");
            alert.setContentText("Module removed succsesfully , return to see new updated timetable");
            alert.show();
        });
        Utillity.qForTermination(scene); //Q to terminate
        Utillity.enterForSubmisson(scene, submitButton); // Submit using ENTER

    }



}
