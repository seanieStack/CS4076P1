
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static seanie.mark.cs4076p1server.Utillity.quitApp;


public class HelloApplication extends Application {
    private Scene mainScene;
    private Stage stage;



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;


        VBox layout = new VBox(10); // Use a VBox layout with spacing of 10
        Label welcomelabel = new Label("Please select an option");

        Button addModule = new Button("Add Module");
        addModule.setOnAction(event -> new AddModule(stage,this::returnHome));

        Button removeModule = new Button("Remove Module");
        removeModule.setOnAction(event -> new removeModule(stage,this::returnHome));

        Button showTimetable = new Button("Display timetable");
        showTimetable.setOnAction(event -> new showTimetable(stage,this::returnHome));
        //showTimetable.setonAction(event -> goToShowTimetable());
        Button customizeApplication = new Button("Option");
        Button quitApplication = new Button("Quit application");
        Scene scene;
        quitApplication.setOnAction((event -> quitApp() ));


        layout.getChildren().addAll(welcomelabel, addModule, removeModule, showTimetable, customizeApplication, quitApplication); // Add the button to the layout

        mainScene = new Scene(layout, 400, 400);
        stage.setScene(mainScene); // Set the scene on the stage
        stage.setTitle("Welcome Screen"); 
        stage.show();
        // Press Q to terminate
        /**
         scene.setOnKeyPressed(event -> {
         if (event.getCode()  ==  KeyCode.Q ) {
         quitApplication();
         }});
         }
         **/

        Utillity.qForTermination(mainScene);

    }
        public void quitapp () {
         Platform.exit();
         }

    public  void returnHome () {
        stage.setScene(mainScene);
    }

    public static void main(String [] args ) {

        launch();
    } 
}

