package com.example.campusPayApp.ui.profile;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
    public TextField businessCategory;
    public Hyperlink skipSetup;
    public Button saveSetup;
    public ComboBox<String> gender;


    public void onClickHandleSave(ActionEvent event) throws IOException {
        saveProfileDetails();
    }

    public void saveProfileDetails() throws IOException {
        saveSetup.setDisable(true);
    }

    public void onClickHandleSkip(ActionEvent event) throws  IOException {
        skipProfileSetup();
    }

    public void skipProfileSetup() throws IOException {
        HelloApplication app = new HelloApplication();
        String[] data = {"home-view.fxml", "Home"};
        app.changeScene(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserInfo();
    }

    private void setUserInfo() {
        Gson gson = new Gson();
        String student = String.valueOf(LocalStorageManager.getObject("User"));
        JsonObject studentObject = gson.fromJson(student, JsonObject.class);
        System.out.println(student + "\n" + studentObject);

        if (student == null) {
            System.out.println((Object) null);
        } else {
//        System.out.println(student);
//            middlename.setText(student.get("middlename").getAsString());
//            middlename.setDisable(true);
//            lastname.setText(student.get("lastName").getAsString());
//            lastname.setDisable(true);
//            firstname.setText(student.get("firstName").getAsString());
//            firstname.setDisable(true);
//            email.setText(student.get("email").getAsString());
//            email.setDisable(true);
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
            gender.setDisable(true);// Set a default value (optional)
        }
    }



}


