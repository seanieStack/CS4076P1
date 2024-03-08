package seanie.mark.cs4076p1client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.PrintWriter;


public class showTimetable {
    public Stage stage;
    private final Runnable returnToMain;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public showTimetable(Stage stage,  Runnable returnToMain, BufferedReader in, PrintWriter out) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10); // Use a VBox layout with spacing of 10

        
        Scene scene = new Scene(layout, 600, 600); // Create the scene with the layout
        stage.setScene(scene); // Set the scene on the stage
        stage.setTitle("Welcome Screen"); // Optional: set a title for the window


        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        HBox root2 = new HBox();
        root2.setSpacing(10);
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);
        backButton.setOnAction(e -> returnToMain.run());

        Text displayText = new Text();

        submitButton.setOnAction(e -> {
                    String userModule = userInputModule.getText();

                    try {
                        out.println("ds " + userModule);
                        String response = in.readLine();

                        switch (response){
                            case "cp":
                                displayText.setText("Schedule Displayed in server Console");
                                break;
                            case "cnf":
                                displayText.setText("Module not found");
                                break;
                        }

                    } catch (Exception ex) {
                        displayText.setText("Error Sending Display Schedule Request");
                    }
                });


//        Utillity.qForTermination(scene); //Q to terminate

        stage.setOnCloseRequest((WindowEvent we) -> {
            Utillity.quitApp(in, out);
        });

        // Adding components to the GridPane
        layout.getChildren().addAll(moduleLabel, userInputModule, root2, displayText);


    }
}