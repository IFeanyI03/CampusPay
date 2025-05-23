package com.example.campusPayApp.ui.job;

import com.example.campusPayApp.api.Jobs;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PostJobScreen {

    Gson gson = new Gson();
    @FXML
    public TextField title;
    public TextArea description;
    public DatePicker deadline;
    public TextField type;
    public TextField role;
    public TextField jobLocation;
    public TextField pay;
    public Button createJobButton;

    public void onClickHandleCreate(ActionEvent event) throws IOException {
        System.out.println("Create Job clicked!");
        addNewJob();
    }

    private void addNewJob() throws IOException {
        Jobs job = new Jobs();
        Object userObject = LocalStorageManager.getObject("User");
        JsonObject user = gson.fromJson(String.valueOf(userObject), JsonObject.class);

        if (
                title.getText().isEmpty() ||
                        description.getText().isEmpty() ||
                        deadline.getValue().toString().isEmpty() ||
                        type.getText().isEmpty() ||
                        jobLocation.getText().isEmpty() || pay.getText().isEmpty()
        ) {
            System.out.println("Fill in all fields");
        } else {
            String body = "{" +
                    "\"name\": " + "\"" + title.getText() + "\"" + ", " +
                    "\"description\": " + "\"" + description.getText() + "\"" + ", " +
                    "\"deadline\": " + "\"" + deadline.getValue().toString() + "\"" + ", " +
                    "\"type\": " + "\"" + type.getText() + "\"" + ", " +
                    "\"role\": " + "\"" + role.getText() + "\"" + ", " +
                    "\"location\": " + "\"" + jobLocation.getText() + "\"" + ", " +
                    "\"pay\": " + "\"" + pay.getText() + "\"" + ", " +
                    "\"created_by\": " + "\"" + user.get("id").getAsString() + "\"" + "}";
            System.out.println("POST BODY: " + body);
            job.post(body);

            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(
                    getClass().getResource("/com/example/campusPayApp/home-view.fxml")
            );
            javafx.stage.Stage stage = (javafx.stage.Stage) createJobButton.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        }
    }
}
