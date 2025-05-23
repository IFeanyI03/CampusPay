package com.example.campusPayApp.ui.profile;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Applications;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.example.campusPayApp.utils.ThemedAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewProfileScreen implements Initializable {
    public Label bio;
    public Label name;
    public Label secondaryInfo;
    public Hyperlink resumeLink;
    public Hyperlink GithubLink;
    public Hyperlink portfolioLink;
    public Label isStudent;
    public Button logoutButton;

    Gson gson = new Gson();

    Object profile_id = LocalStorageManager.getObject("ProfileId");
    Object user_id = LocalStorageManager.getObject("User");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (profile_id == null) {
                JsonObject userObject = gson.fromJson(String.valueOf(user_id), JsonObject.class);
                setDetails(userObject);
            } else {
                Object user = Objects.equals(user_id, profile_id) ? user_id : profile_id;
                JsonObject userObject = gson.fromJson(String.valueOf(user), JsonObject.class);
                setDetails(userObject);
            }
        } catch (Exception e) {
            ThemedAlert.showAlert("Error", "Failed to initialize profile: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void setDetails(JsonObject userObject) throws IOException {
        try {
            // Safely get all fields with null checks
            String firstname = userObject.has("firstname") && !userObject.get("firstname").isJsonNull()
                    ? userObject.get("firstname").getAsString() : "";

            String lastname = userObject.has("lastname") && !userObject.get("lastname").isJsonNull()
                    ? userObject.get("lastname").getAsString() : "";

            String email = userObject.has("email") && !userObject.get("email").isJsonNull()
                    ? userObject.get("email").getAsString() : "";

            String businessName = userObject.has("business_name") && !userObject.get("business_name").isJsonNull()
                    ? userObject.get("business_name").getAsString() : "";

            boolean student = userObject.has("isStudent") && !userObject.get("isStudent").isJsonNull()
                    ? userObject.get("isStudent").getAsBoolean() : false;

            String resume_link = userObject.has("resume_link") && !userObject.get("resume_link").isJsonNull()
                    ? userObject.get("resume_link").getAsString() : "";

            String bio_txt = userObject.has("bio") && !userObject.get("bio").isJsonNull()
                    ? userObject.get("bio").getAsString() : "";

            String github_link = userObject.has("github_link") && !userObject.get("github_link").isJsonNull()
                    ? userObject.get("github_link").getAsString() : "";

            String portfolio_link = userObject.has("portfolio_link") && !userObject.get("portfolio_link").isJsonNull()
                    ? userObject.get("portfolio_link").getAsString() : "";

            // Update UI elements
            if (LocalStorageManager.getObject("ProfileId") == null) {
                logoutButton.setVisible(true);
            } else {
                logoutButton.setVisible(Objects.equals(
                        LocalStorageManager.getObject("User"),
                        LocalStorageManager.getObject("ProfileId")
                ));
            }

            isStudent.setVisible(student);
            name.setText(businessName.isEmpty() ? firstname + " " + lastname : businessName);
            secondaryInfo.setText(businessName.isEmpty() ? email : firstname + " " + lastname);

            resumeLink.setText(resume_link);
            resumeLink.setVisible(!resume_link.isEmpty());

            GithubLink.setText(github_link);
            GithubLink.setVisible(!github_link.isEmpty());

            portfolioLink.setText(portfolio_link);
            portfolioLink.setVisible(!portfolio_link.isEmpty());

            bio.setText(bio_txt);

        } catch (Exception e) {
            ThemedAlert.showAlert("Error", "Failed to load profile data: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    public void onClickHandleLogout(ActionEvent event) throws IOException{
        logoutUser();
    }

    private void logoutUser() throws IOException {
        LocalStorageManager.clear();
        String[] data = {"sign-in-view.fxml", "Sign in"};
        HelloApplication.changeScene(data);
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/campusPayApp/home-view.fxml"))); // adjust path as needed
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
