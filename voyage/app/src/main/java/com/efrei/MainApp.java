package com.efrei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
      //  Parent root = FXMLLoader.load(getClass().getResource("Train.fxml"));
        
        Parent root = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
        
        // Add the ImageView to your scene or layout
        // For example, if you want to add it to the root node:

        Scene scene = new Scene(root);
        
        stage.setTitle("Administration VoyageExpress");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}