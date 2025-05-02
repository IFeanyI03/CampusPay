package com.example.campusPayApp.ui.job;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Applications;
import com.example.campusPayApp.api.Jobs;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class JobDetailScreen implements Initializable {
    public Label toast;
//    public Hyperlink applicantItem;
    public GridPane applicantItems;
    Gson gson = new Gson();
    Applications applications = new Applications();

    @FXML
    public AnchorPane applicantAnchor;
    public ScrollPane applicantScrollView;
    public Hyperlink createdBy;
    public Label name;
    public Label applicantLabel;
    public Label description;
    public Label role;
    public Label type;
    public Label jobLocation;
    public Label pay;
    public Label deadline;
    public Label createdAt;
    public Button applyButton;

    Jobs job = new Jobs();
    Profile profile = new Profile();
    String  jobId = LocalStorageManager.getString("jobId");

    Object userObject = LocalStorageManager.getObject("User");
    JsonObject user = gson.fromJson(String.valueOf(userObject), JsonObject.class);


    public void onCLickHandleApply(ActionEvent event) throws IOException {
        applyToJob();
    }

    private void applyToJob() throws IOException {
        LocalStorageManager.getString("Job");

        String body = "{" +
                "\"applicant\": " + "\"" + user.get("id").getAsString() + "\"" + ", " +
                "\"job_id\": " + "\"" + jobId + "\"" + "}";
        String response = applications.post(body);

        String thisJob = applications.getJobByJobId(jobId);
        JsonObject thisJobObject = gson.fromJson(thisJob, JsonObject.class);
        String createdBy =  thisJobObject.get("applicant").getAsString();
        boolean hasApplied = Objects.equals(createdBy, user.get("id").getAsString());
        if (Objects.equals(response, "201")  ) {
            applyButton.setDisable(true);
            applyButton.setText("Applied");
            toast.setText("Applied Successfully");
        }
        else {
            toast.setText("Application failed try again");
            toast.setTextFill(Color.ORANGERED);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Profile profile = new Profile();
        String data = null;
        try {
            data = job.getJobById(jobId);
            setApplicantList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {

            String dataString = data.substring(1, data.length() - 1);

            JsonObject jobData = gson.fromJson(dataString, JsonObject.class);
            name.setText(jobData.get("name").getAsString());
            description.setText(jobData.get("description").getAsString());
            role.setText(jobData.get("role").getAsString());
            type.setText(jobData.get("type").getAsString());
            jobLocation.setText(jobData.get("location").getAsString());
            pay.setText(jobData.get("pay").getAsString());
            deadline.setText(jobData.get("deadline").getAsString());
            createdAt.setText(jobData.get("created_at").getAsString());
            applicantScrollView.setVisible(false);
            applicantLabel.setVisible(false);

//            System.out.println("user_id" + user.get("id").getAsString() + "\n" + "job_user_id" + jobData.get("created_by").getAsString());
            if (Objects.equals(user.get("id").getAsString(), jobData.get("created_by").getAsString())) {
                createdBy.setText("Me");
                applicantScrollView.setVisible(true);
                applicantLabel.setVisible(true);
                applyButton.setDisable(true);
            }
            else {
                String name = profile.getProfileById(jobData.get("created_by").getAsString());
                String nameString = name.substring(1, name.length() -1 );
                JsonObject nameObject = gson.fromJson(nameString, JsonObject.class);

                String thisJob = applications.getJobByJobId(jobId);
                System.out.println(thisJob);
                String thisJobString = thisJob.substring(1, thisJob.length() -1 );

                JsonObject thisJobObject = gson.fromJson(thisJobString, JsonObject.class);
                String created_by =  thisJobObject.get("applicant").getAsString();
                boolean hasApplied = Objects.equals(created_by, user.get("id").getAsString());

                if (!nameObject.get("business_name").isJsonNull()){
                    createdBy.setText(nameObject.get("business_name").getAsString());
                    applyButton.setDisable(!nameObject.get("resume_link").isJsonNull());


                    if ( hasApplied ) {
                        applyButton.setDisable(true);
                        applyButton.setText("Applied");
                    }

                } else {
                    String fullName = nameObject.get("firstname").getAsString() + " " + nameObject.get("lastname").getAsString();
                    createdBy.setText(fullName);

                    if ( hasApplied ) {
                        applyButton.setDisable(true);
                        applyButton.setText("Applied");
                    }

                }


            }

//            System.out.println(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setApplicantList() throws IOException {
        String applicants = applications.getJobByJobId(jobId);
        JsonArray applicantsArray = gson.fromJson(applicants, JsonArray.class);

        if (applicantsArray == null || applicantsArray.isEmpty()) {
            Label noDataLabel = new Label("No jobs available.");
            applicantItems.add(noDataLabel, 0, 0,1,1);
            return;
        }

        System.out.println("ApplicantArray  " + applicantsArray);

        int rowIndex = 0;
        for ( JsonElement applicant : applicantsArray) {
            Profile profile = new Profile();
            JsonObject applicantObject = gson.fromJson(String.valueOf(applicant), JsonObject.class);

//            System.out.println("ApplicantObject: " + applicantObject);
            String applicantData = profile.getProfileById(applicantObject.get("applicant").getAsString());
            System.out.println(rowIndex + " ApplicantData: " + applicantData);
            String data = applicantData.substring(1, applicantData.length() - 1);
            AnchorPane jobItemPane = createApplicant(data);

            applicantItems.add(jobItemPane, 0, rowIndex); // Add each job item to a new row in the first column
            rowIndex++;
        }
    }

    @NotNull
    private AnchorPane createApplicant(String s) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(20.0); // Or your desired height
//        anchorPane.setPrefWidth(200.0);

        JsonObject applicantData = gson.fromJson(s, JsonObject.class);
        Hyperlink link = new Hyperlink();
        link.setText(applicantData.get("firstname").getAsString() + " " +  applicantData.get("lastname").getAsString());
        link.setLayoutX(14.0);
        link.setFont(Font.font("System Italic", 12.0));

        anchorPane.getChildren().addAll(link);

        link.setOnAction(event -> {
            String[] data = {"profile-view.fxml", "Profile"};
            try {
                String profileData = profile.getProfileById(applicantData.get("id").getAsString());
                String profileString = profileData.substring(1, profileData.length() -1);
                System.out.println(profileString);
                LocalStorageManager.saveObject("ProfileId", profileString);
                HelloApplication.changeScene(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return  anchorPane;
    }
}
