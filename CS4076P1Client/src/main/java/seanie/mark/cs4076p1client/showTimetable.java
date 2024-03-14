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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



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
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); //Added to fix Mac font issue 
        stage.setScene(scene); // Set the scene on the stage
        stage.setTitle("Welcome Screen"); // Optional: set a title for the window


        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        HBox root2 = new HBox();
        root2.setSpacing(10);
        utton fullTimetable = new Button("Display full timetable "); //TODO: Fix UI so its better , review with seanie
        Button moduleTimetable = new Button("Display module timetable");
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton,fullTimetable,moduleTimetable);
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
                                Alert notFoundAlert = new Alert(AlertType.ERROR); // Displays in Chinese 
                                notFoundAlert.setTitle("Error : Module not found");
                                notFoundAlert.setHeaderText(null);
                                notFoundAlert.setContentText("This module does not exist in our record , try adding it by selecting 'return' ,'add module'");
                                notFoundAlert.show();
                                break;
                        }

                    } catch (Exception ex) {
                        displayText.setText("Error Sending Display Schedule Request");
                    }
                });


//        Utillity.qForTermination(scene); //Q to terminate

        GridPane timetable = new GridPane();

        String [] times = Utillity.getTimes();
        String [] days = Utillity.getDays();

        timetable.setPadding(new javafx.geometry.Insets(100,100,100,100)); //Padding , Vgap and Hgap all need resizing
        timetable.setVgap(100);
        timetable.setHgap(100);


        Label mondayLabel = new Label("Monday");
        Label tuesdayLabel = new Label("Tuesday");
        Label wednesdayLabel = new Label("Wednesday");
        Label thursdayLabel = new Label("Thursday");
        Label fridayLabel   = new Label("Friday");

        GridPane.setConstraints(mondayLabel,1,0);
        GridPane.setConstraints(tuesdayLabel,2,0);
        GridPane.setConstraints(wednesdayLabel,3,0);
        GridPane.setConstraints(thursdayLabel,4,0);
        GridPane.setConstraints(fridayLabel,5,0);


        /**
        for (int i =1 ; i < days.length+1; i++) {  // Populate days
            Label dayLabel = new Label(days[i]);
            GridPane.setConstraints(dayLabel,i,0);
            timetable.getChildren().add(dayLabel);
        }
        **/ //This could work but doesn't right now

        for (int j = 0 ; j < times.length; j++){
            Label timeLabel = new Label(times[j]);
            GridPane.setConstraints(timeLabel,0,j+1);
            timetable.getChildren().add(timeLabel);
        }

        //Example usage
        String payload = "<CS4096><09:00-10:00><Monday><KGB-12>";
        int nodes[] =  Utillity.moduleNodes(payload);
        String parts []= Utillity.splitPayload(payload);
        Label exampleLabel = new Label(parts[0]+ " " +parts[1]);
        GridPane.setConstraints(exampleLabel,nodes[0],nodes[1] );
        timetable.getChildren().addAll(mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel,exampleLabel);


        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        // Adding components to the GridPane
        layout.getChildren().addAll(moduleLabel, userInputModule, root2, displayText,timetable);

        /**
         * Some comments on commit:
         * Added the axis's with days and time , needs resizing its far too big at present
         * Added example usage , This will need to be changed to be more efficient and include getting data from DB
         * I am aware that the payload string in example usage takes the wrong format (<>) but that's an easy fix 
         * Added 'Full timetable' and 'Module timetable' buttons without function 
         * I think that clicking either of these buttons should display a module specific timetable or a full timetable and not take a user to a new screen
         */

    }
}
