package com.example.campusPayApp;

import com.example.campusPayApp.utils.LocalStorageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static Stage mainStage; // Make mainStage static and accessible

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage; // Initialize the static mainStage

        Image icon = new Image("CampusPay.png");
        primaryStage.getIcons().add(icon);
        showSplashScreen(); // Show splash screen first
    }

    private void showSplashScreen() throws IOException {
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("splash-view.fxml"));
        Parent splashRoot = splashLoader.load();
        Scene splashScene = new Scene(splashRoot, 640, 360);
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(splashScene);
        splashStage.centerOnScreen();
        splashStage.show();

        // Simulate loading and then switch to the main scene
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate loading

                // Load and show the main application UI
                javafx.application.Platform.runLater(() -> {
                    try {
                        loadMainApplication();
                        splashStage.close(); // Close the splash screen
                        mainStage.show(); // Show the main application window
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle error loading main app
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void loadMainApplication() throws IOException {
//        Gson gson = new Gson();
        Object userObject = LocalStorageManager.getObject("User");

        FXMLLoader mainLoader;
        if (userObject != null) {
//            JsonObject user = gson.fromJson(String.valueOf(userObject), JsonObject.class);
            mainLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent mainRoot = mainLoader.load();
            Scene mainScene = new Scene(mainRoot, 1280, 720);
            mainStage.setTitle("Welcome");
            mainStage.setScene(mainScene);
        } else {
            mainLoader = new FXMLLoader(getClass().getResource("sign-in-view.fxml"));
            Parent mainRoot = mainLoader.load();
            Scene mainScene = new Scene(mainRoot, 1280, 720);
            mainStage.setTitle("Sign In");
            mainStage.setScene(mainScene);
        }
        mainStage.setResizable(false);
    }

    // Make this static to be easily accessible from other controllers
    public static void changeScene(String[] args) throws IOException {
        String fxmlFileName = args[0];
        String windowTitle = args[1];
        Parent pane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxmlFileName)));
        mainStage.setTitle(windowTitle);
        mainStage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}