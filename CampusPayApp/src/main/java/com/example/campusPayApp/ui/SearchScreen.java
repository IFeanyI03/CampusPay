package com.example.campusPayApp.ui;


import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Jobs;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SearchScreen {
    Gson gson = new Gson();

    @FXML
    public Button searchButton;
    public TextField searchInput;
    public GridPane searchJobContainer;

    public void onClickHandleSearch(ActionEvent event) throws IOException {
        populateGrid();
    }

    private void populateGrid() throws IOException {
        searchJobContainer.setLayoutY(125);
        getJobs();
        JsonArray responseArray = gson.fromJson(getJobs(), JsonArray.class);
        searchJobContainer.getChildren().clear();

        if (responseArray == null || responseArray.isEmpty()) {
            // Handle the case where there's no data
            Label noDataLabel = new Label("No jobs available.");
            searchJobContainer.add(noDataLabel, 0, 0, 1, 1); // Add to the first cell, spanning 1 row and 1 column
            return;
        }


        int rowIndex = 0;
        for(JsonElement element : responseArray) {
            AnchorPane jobItemPane = createJobItem(String.valueOf(element));

            searchJobContainer.add(jobItemPane, 0, rowIndex); // Add each job item to a new row in the first column
            rowIndex++;
        }
    }

    private AnchorPane createJobItem(String s) {
        JsonObject responseObject = gson.fromJson(s, JsonObject.class);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200.0); // Or your desired height
        anchorPane.setPrefWidth(200.0);  // Or your desired width

        Label titleLabel = new Label(responseObject.get("name").getAsString());
        titleLabel.setLayoutX(93.0);
        titleLabel.setLayoutY(13.0);
        titleLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        titleLabel.setFont(Font.font("System Bold", 12.0));

        Label typeLabel = new Label(responseObject.get("type").getAsString());
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
        Label deadlineLabel = new Label(responseObject.get("deadline").getAsString());
        deadlineLabel.setLayoutX(548.0);
        deadlineLabel.setLayoutY(14.0);
        deadlineLabel.setTextFill(javafx.scene.paint.Color.valueOf("#118c4f"));
        deadlineLabel.setFont(Font.font("System Bold", 12.0));

        Label locationLabel = new Label(responseObject.get("location").getAsString());
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
            LocalStorageManager.saveString("jobId", responseObject.get("id").getAsString());

            try {
                HelloApplication.changeScene(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Apply button clicked for: " + responseObject.get("id").getAsString());
            // Implement your application logic here
        });

        return anchorPane;

    }

    private String getJobs() throws IOException {
        String role = searchInput.getText();
        Jobs job = new Jobs();
        return job.getJobSByRole(role);
    }
//   public void getJob() throws IOException {
//
//   }
}
