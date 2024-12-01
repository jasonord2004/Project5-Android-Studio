package com.example.project4rupizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is responsible for launching the user interface.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class HelloApplication extends Application {
    /**
     * Loads up the scene and stage for the user interface.
     * @param stage - The stage of the application
     * @throws IOException - An input or output exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the user interface.
     * @param args - arguments
     */
    public static void main(String[] args) {
        launch();
    }
}