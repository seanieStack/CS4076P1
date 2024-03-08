package seanie.mark.cs4076p1client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import java.io.IOException;



public class App extends Application {
    private Scene mainScene;
    private Stage stage;

    private static InetAddress host;
    private static final int PORT = 6558;



    @Override
    public void start(Stage stage) throws IOException {

        Socket link;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            link = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);

            BufferedReader finalIn = in;
            PrintWriter finalOut = out;

            this.stage = stage;


            VBox layout = new VBox(10); // Use a VBox layout with spacing of 10
            Label welcomelabel = new Label("Please select an option");

            Button addModule = new Button("Add Module");

            addModule.setOnAction(event -> new addModule(stage,this::returnHome, finalIn, finalOut));

            Button removeModule = new Button("Remove Module");
            removeModule.setOnAction(event -> new removeModule(stage,this::returnHome, finalIn, finalOut));

            Button showTimetable = new Button("Display timetable");
            showTimetable.setOnAction(event -> new showTimetable(stage,this::returnHome, finalIn, finalOut));
            //showTimetable.setonAction(event -> goToShowTimetable());
            Button customizeApplication = new Button("Option");
            Button quitApplication = new Button("Quit application");
            Scene scene;
            quitApplication.setOnAction((event -> Utillity.quitApp(finalIn, finalOut) ));


            layout.getChildren().addAll(welcomelabel, addModule, removeModule, showTimetable, customizeApplication, quitApplication); // Add the button to the layout

            mainScene = new Scene(layout, 400, 400);
            stage.setScene(mainScene); // Set the scene on the stage
            stage.setTitle("Welcome Screen");
            stage.show();

            stage.setOnCloseRequest((WindowEvent we) -> {
                Utillity.quitApp(finalIn, finalOut);
            });

            // Press Q to terminate
            /*
             scene.setOnKeyPressed(event -> {
             if (event.getCode()  ==  KeyCode.Q ) {
             quitApplication();
             }});
             }
             */

//            Utillity.qForTermination(mainScene);

        }
        catch (IOException e){
            System.out.println("Unable to connect to server");
            System.exit(1);
        }


    }
    public void quitApp() {
        Platform.exit();
    }

    public  void returnHome () {
        stage.setScene(mainScene);
    }

    public static void main(String [] args ) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        launch();
    }
}

