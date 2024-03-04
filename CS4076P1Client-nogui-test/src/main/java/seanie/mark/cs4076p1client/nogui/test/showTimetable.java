package seanie.mark.cs4076p1server;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class showTimetable {
    public Stage stage;
    private Runnable returnToMain;
    
    public showTimetable(Stage stage,  Runnable returnToMain) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10); // Use a VBox layout with spacing of 10
        Label welcomeScreen = new Label("Timetable in progress");
       
        
        Scene scene = new Scene(layout, 600, 600); // Create the scene with the layout
        stage.setScene(scene); // Set the scene on the stage
        stage.setTitle("Welcome Screen"); // Optional: set a title for the window

         backButton.setOnAction(e -> {
            returnToMain.run();
        });

        Utillity.qForTermination(scene); //Q to terminat


     //GridPane used to display the timetable
        GridPane timetable = new GridPane();

        String[] times = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};


        timetable.setPadding(new javafx.geometry.Insets(100, 100, 100, 100));
        timetable.setVgap(100);
        timetable.setHgap(100);

        // TODO: replace this and setContraints block with for loop ( more effective)
        Label mondayLabel = new Label("Monday");
        Label tuesdayLabel = new Label("Tuesday");
        Label wednesdayLabel = new Label("Wednesday");
        Label thursdayLabel = new Label("Thursday");
        Label fridayLabel = new Label("Friday");


        // Placing components in the grid
        GridPane.setConstraints(mondayLabel, 1, 0);
        GridPane.setConstraints(tuesdayLabel, 2, 0);
        GridPane.setConstraints(wednesdayLabel, 3, 0);
        GridPane.setConstraints(thursdayLabel, 4, 0);
        GridPane.setConstraints(fridayLabel, 5, 0); // (1-5,0) filled with days of the week

        for (int row = 0; row < times.length; row++) {
            Label timeLabel = new Label(times[row]);
            GridPane.setConstraints(timeLabel, 0, row + 1); // I Offset it by 1 to account for the header row
            timetable.getChildren().add(timeLabel);
        }
            // Adding components to the GridPane
            timetable.getChildren().addAll(mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel);
            layout.getChildren().addAll(welcomeScreen, backButton, timetable);


        }


    }

