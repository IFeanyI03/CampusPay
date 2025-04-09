package com.example.campusPayApp.ui;

import com.example.campusPayApp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeScreen {
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
    }

    public void goToSearch() throws IOException {
        String[] data = {"search-view.fxml", "Search Job"};
        app.changeScene(data);
    }

    public void goToProfile() throws IOException {
        String[] data = {"profile-view.fxml", "Profile"};
        app.changeScene(data);
    }
}
