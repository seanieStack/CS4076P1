package seanie.mark.cs4076p1client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;



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
        stage.setTitle("Show timetable"); 


        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        HBox root2 = new HBox();
        root2.setSpacing(10);
        
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);
        backButton.setOnAction(e -> returnToMain.run());

        GridPane timetable = new GridPane();
         
        // Creating the timetable 
        
        String [] times = Utillity.getTimes();
        String [] days = Utillity.getDays();

        timetable.setPadding(new javafx.geometry.Insets(15,15,15,15)); //Padding , Vgap and Hgap all need resizing
        timetable.setVgap(15);
        timetable.setHgap(15);


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
        

        for (int j = 0 ; j < times.length; j++){
            Label timeLabel = new Label(times[j]);
            GridPane.setConstraints(timeLabel,0,j+1);
            timetable.getChildren().add(timeLabel);
        }
       
        timetable.getChildren().addAll(mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel);

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
                                DialogPane dialogPane = notFoundAlert.getDialogPane();
                                dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                                dialogPane.getStyleClass().add("my-dialog");
                                notFoundAlert.show();
                                break;
                        }

                    } catch (Exception ex) {
                        displayText.setText("Error Sending Display Schedule Request");
                    }

                     try {
                        out.println("te " + userModule);
                        String response = in.readLine();
                        System.out.println(response);
                        if (!response.contains(userModule)) {
                            displayText.setText("Error occured : Module not found");
                        } else if (!VerifyInput.isValidModule(userInputModule.getText())) {
                            displayText.setText(userModule + ": is not a valid module");
                            Alert nonValidAlert = new Alert(Alert.AlertType.ERROR);
                            nonValidAlert.setTitle("Submission error : Invalid Module");
                            nonValidAlert.setContentText("Module : " + userModule + " does not follow the required module code formatting e.g CS4076");
                            nonValidAlert.show();
                        }else {
                            int nodes[] =  Utillity.moduleNodes(response);
                            String parts []= Utillity.splitPayload(response);
                            Label responseLabel = new Label(parts[0]+ " " +parts[3]);
                           // GridPane.setConstraints(responseLabel,nodes[0],nodes[1] );
                            GridPane.setConstraints(responseLabel,nodes[0],nodes[1] );
                            timetable.getChildren().add(responseLabel);
                        }
                    } catch (Exception ex) {
                        out.println("Error displaying time table");
                    }
                });


        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        // Adding components to the GridPane
        layout.getChildren().addAll(moduleLabel, userInputModule, root2, displayText,timetable);

       

    }
}
