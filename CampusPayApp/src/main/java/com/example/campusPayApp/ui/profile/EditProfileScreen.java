package com.example.campusPayApp.ui.profile;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.example.campusPayApp.utils.ThemedAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileScreen implements Initializable {

    @FXML
    public TextField middlename;
    public TextField firstname;
    public TextField lastname;
    public TextField email;
    public TextField resumeLink;
    public TextField portfolioLink;
    public TextField githubLink;
    public TextArea bio;
    public TextArea skills;
    public TextField businessName;
    public ComboBox<String> businessCategory;
    public Button saveSetup;
    public ComboBox<String> gender;


    String userData = String.valueOf(LocalStorageManager.getObject("registeredUser"));

    Gson gson = new Gson();
    Profile user = new Profile();

    String studentStringData = user.getProfileByEmail(userData);
    JsonObject student = gson.fromJson(studentStringData, JsonObject.class);


    public EditProfileScreen() throws IOException {
    }

    public void onClickHandleSave(ActionEvent event) throws IOException {
            saveProfileDetails();
        }

    public void saveProfileDetails() throws IOException {
        if (!validateFields()) {
            ThemedAlert.showAlert("Error", "Please fill all required fields.", Alert.AlertType.ERROR);
            return;
        }

        updateUser();
        ThemedAlert.showAlert("Success", "Profile saved successfully!", Alert.AlertType.INFORMATION);
        HelloApplication.changeScene(new String[]{"profile-view.fxml", "Profile"});
    }

        private void updateUser() {
            saveSetup.setDisable(true);
            Profile user = new Profile();

            String user_id = student.get("id").getAsString();
            String data =  "{" +
                    "\"firstname\": " + "\"" + firstname.getText() + "\"" + ", " +
                    "\"middlename\": " + "\"" + middlename.getText() + "\"" + ", " +
                    "\"lastname\": " + "\"" + lastname.getText() + "\"" + ", " +
                    "\"bio\": " + "\"" + bio.getText() + "\"" + ", " +
                    "\"github_link\": " + "\"" + githubLink.getText() + "\"" + ", " +
                    "\"resume_link\": " + "\"" + resumeLink.getText() + "\"" + ", " +
                    "\"portfolio_link\": " + "\"" + portfolioLink.getText() + "\"" + ", " +
                    "\"business_name\": " + "\"" + businessName.getText() + "\"" + ", " +
                    "\"business_category\": " + "\"" + businessCategory.getValue() + "\"" +
                    "}";
            try {
                user.updateUser(user_id, data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            try {
                setUserInfo();
//                System.out.println(userData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    private boolean validateFields() {
        boolean isValid = true;

        TextInputControl[] requiredFields = {
                firstname, lastname, email, bio, skills, businessName
        };

        for (TextInputControl field : requiredFields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                field.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                isValid = false;
            } else {
                field.setStyle("");
            }
        }

        // Validate ComboBoxes (e.g., businessCategory)
        if (businessCategory.getValue() == null) {
            businessCategory.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        return isValid;
    }

        private void setUserInfo() throws IOException {

            if (student == null) {
//                System.out.println((Object) null);
            } else {
                middlename.setText("");
                lastname.setText("");
                firstname.setText("");

                email.setText(student.get("email").getAsString());
                email.setDisable(true);
    //        bio.setText(name);
    //        skills.setText("Frontend, backend, UI/UX, devops");
    //        githubLink.setText("https://github.com/IFeanyI03/");
    //        portfolioLink.setText("https://portfolio-ifeanyi03s.vercel.app/");
    //        resumeLink.setText("https://docs.google.com/document/d/1QnUrCez9euBWoUzo4MCg-HZabkC-c87Yev80eFiuemw/edit?tab=t.0");

                ObservableList<String> options = FXCollections.observableArrayList(
                        "Male",
                        "Female"
                );
                gender.setItems(options);
                gender.setValue("Male");
    //        gender.setValue(student.get("gender").getAsString());
    //            gender.setDisable(true);// Set a default value (optional)

                ObservableList<String> businessCategories = FXCollections.observableArrayList(
                        "Technology",
                        "Marketing",
                        "Healthcare",
                        "Finance",
                        "Education",
                        "Retail",
                        "Agriculture",
                        "Entertainment"
                );
                businessCategory.setItems(businessCategories);
                businessCategory.setValue("Technology");  // Set default value
            }
        }



}


