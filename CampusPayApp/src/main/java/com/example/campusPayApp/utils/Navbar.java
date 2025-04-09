package com.example.campusPayApp.utils;

import com.example.campusPayApp.HelloApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Navbar {

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button profileButton;

    @FXML
    public void onClickAddButton(ActionEvent event) throws IOException {
        goToAddJob();
    }

    @FXML
    public void onClickSearchButton(ActionEvent event) throws IOException {
        goToSearch();
    }

    @FXML
    public void onClickProfileButton(ActionEvent event) throws IOException {
        goToProfile();
    }

    HelloApplication app = new HelloApplication();

    public void goToAddJob() throws IOException {
        String[] data = {"post-job-view.fxml", "Post Job"};
        app.changeScene(data);
        addButton.setStyle("-fx-background-color: #F4F !important;");
        searchButton.setStyle("-fx-background-color: #FFF !important;");
        profileButton.setStyle("-fx-background-color: #FFF !important;");
    }

    public void goToSearch() throws IOException {
        String[] data = {"search-view.fxml", "Search Job"};
        app.changeScene(data);
        Platform.runLater(() -> {
            addButton.setStyle("-fx-background-color: red !important;");
            searchButton.setStyle("-fx-background-color: red !important;");
            profileButton.setStyle("-fx-background-color: red !important;");
        });
    }

    public void goToProfile() throws IOException {
        String[] data = {"profile-view.fxml", "Profile"};
        app.changeScene(data);
        Platform.runLater(() -> {
            addButton.setStyle("-fx-background-color: red !important;");
            searchButton.setStyle("-fx-background-color: red !important;");
            profileButton.setStyle("-fx-background-color: red !important;");
        });
    }
}
