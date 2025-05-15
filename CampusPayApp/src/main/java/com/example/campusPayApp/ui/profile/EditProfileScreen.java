package com.example.campusPayApp.ui.profile;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.utils.LocalStorageManager;
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
    public TextField businessCategory;
    public Hyperlink skipSetup;
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
            updateUser();

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
                    "\"business_category\": " + "\"" + businessCategory.getText() + "\"" +
                    "}";
            try {
                user.updateUser(user_id, data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void onClickHandleSkip(ActionEvent event) throws  IOException {
            skipProfileSetup();
        }
//
        public void skipProfileSetup() throws IOException {
            System.out.println("Can't skip till you setup profile");
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

        private void setUserInfo() throws IOException {



//           System.out.println(studentStringData);
//        String studentString = studentStringData.substring(1, studentStringData.length() -1);

//            System.out.println(student);

            if (student == null) {
//                System.out.println((Object) null);
            } else {
    //        System.out.println(student);
    //            middlename.setText(student.get("middlename").getAsString());
                middlename.setText("");
    //            middlename.setDisable(true);
    //            lastname.setText(student.get("lastName").getAsString());
    //            lastname.setDisable(true);
                lastname.setText("");

    //            firstname.setText(student.get("firstName").getAsString());
    //            firstname.setDisable(true);
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
            }
        }



}


