package com.example.campusPayApp.ui;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Jobs;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class HomeScreen implements Initializable {

    @FXML
    private GridPane jobContainerGrid;


    Jobs job = new Jobs();
    Gson gson = new Gson();

    public void getJob() throws IOException {
        String data = job.get();
        JsonArray jobArrayData = gson.fromJson(data, JsonArray.class);
        LocalStorageManager.saveArray("Jobs", jobArrayData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getJob();
            setJobList();
            System.out.println(LocalStorageManager.getObject("User"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setJobList() {
        populateGrid();
    }

    private void populateGrid() {
        jobContainerGrid.setLayoutY(725);
        Gson gson = new Gson();
        JsonArray jobArray = gson.fromJson(LocalStorageManager.getArray("Jobs"), JsonArray.class);

        if (jobArray == null || jobArray.isEmpty()) {
            // Handle the case where there's no data
            Label noDataLabel = new Label("No jobs available.");
            jobContainerGrid.add(noDataLabel, 0, 0, 1, 1); // Add to the first cell, spanning 1 row and 1 column
            return;
        }

        int rowIndex = 0;
        for ( JsonElement job : jobArray) {
            AnchorPane jobItemPane = createJobItem(String.valueOf(job));

            jobContainerGrid.add(jobItemPane, 0, rowIndex); // Add each job item to a new row in the first column
            rowIndex++;
        }
    }

    private AnchorPane createJobItem(String jobData) {

        JsonObject job = gson.fromJson(jobData, JsonObject.class);
//        System.out.println(job);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200.0); // Or your desired height
        anchorPane.setPrefWidth(200.0);  // Or your desired width

        Label titleLabel = new Label(job.get("name").getAsString());
        titleLabel.setLayoutX(93.0);
        titleLabel.setLayoutY(13.0);
        titleLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        titleLabel.setFont(Font.font("System Bold", 12.0));

        Label typeLabel = new Label(job.get("type").getAsString());
        typeLabel.setLayoutX(279.0);
        typeLabel.setLayoutY(13.0);
        typeLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        typeLabel.setFont(Font.font("System Bold", 12.0));

        Label jobTitlePrefixLabel = new Label("Job Title:");
        jobTitlePrefixLabel.setLayoutX(36.0);
        jobTitlePrefixLabel.setLayoutY(13.0);
        jobTitlePrefixLabel.setFont(Font.font("System Italic", 12.0));

        Label typePrefixLabel = new Label("Type:");
        typePrefixLabel.setLayoutX(242.0);
        typePrefixLabel.setLayoutY(13.0);
        typePrefixLabel.setFont(Font.font("System Italic", 12.0));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Label deadlineLabel = new Label(job.get("deadline").getAsString());
        deadlineLabel.setLayoutX(548.0);
        deadlineLabel.setLayoutY(14.0);
        deadlineLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        deadlineLabel.setFont(Font.font("System Bold", 12.0));

        Label locationLabel = new Label(job.get("location").getAsString());
        locationLabel.setLayoutX(400.0);
        locationLabel.setLayoutY(13.0);
        locationLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        locationLabel.setFont(Font.font("System Bold", 12.0));

        Label locationPrefixLabel = new Label("Location:");
        locationPrefixLabel.setLayoutX(343.0);
        locationPrefixLabel.setLayoutY(14.0);
        locationPrefixLabel.setFont(Font.font("System Italic", 12.0));

        Label deadlinePrefixLabel = new Label("Deadline:"); // added this label
        deadlinePrefixLabel.setLayoutX(492.0);
        deadlinePrefixLabel.setLayoutY(15.0);
        deadlinePrefixLabel.setFont(Font.font("System Italic", 12.0));

        Button applyButton = new Button("See More");
        applyButton.setLayoutX(1130.0);
        applyButton.setLayoutY(3.0);
        applyButton.setMnemonicParsing(false);
        applyButton.setPrefHeight(40.0);
        applyButton.setStyle("-fx-background-color: #118c4f; -fx-background-radius: 8;");
        applyButton.setTextFill(javafx.scene.paint.Color.WHITE);
        applyButton.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        anchorPane.getChildren().addAll(
                titleLabel, typeLabel,
                jobTitlePrefixLabel, typePrefixLabel, deadlinePrefixLabel,
                deadlineLabel, locationLabel,
                locationPrefixLabel, applyButton);

        // You might want to add event handlers to the Apply Now button here
        applyButton.setOnAction(event -> {
            String[] data = {"job-view.fxml", "Job Details"};
            LocalStorageManager.saveString("jobId", job.get("id").getAsString());
            try {
                HelloApplication.changeScene(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//            System.out.println("Apply button clicked for: " + job.get("id").getAsString());
            // Implement your application logic here
        });

        return anchorPane;
    }
}