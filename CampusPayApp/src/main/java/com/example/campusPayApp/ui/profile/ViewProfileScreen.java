package com.example.campusPayApp.ui.profile;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Applications;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

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


    Object user = Objects.equals(LocalStorageManager.getObject("ProfileId"), LocalStorageManager.getObject("User")) ? LocalStorageManager.getObject("User") : LocalStorageManager.getObject("ProfileId") ;
    JsonObject userObject = gson.fromJson(String.valueOf(user), JsonObject.class);


    String firstname = userObject.get("firstname").getAsString();
    String lastname = userObject.get("lastname").getAsString();
    String email = userObject.get("email").getAsString();
    String businessName = userObject.get("business_name").isJsonNull() ? "" : userObject.get("business_name").getAsString();
    boolean student = userObject.get("isStudent").getAsBoolean();
    String resume_link = userObject.get("resume_link").isJsonNull() ? "" : userObject.get("resume_link").getAsString();
    String bio_txt = userObject.get("bio").isJsonNull() ? "" : userObject.get("bio").getAsString();
    String github_link = userObject.get("github_link").isJsonNull() ? "" : userObject.get("github_link").getAsString();
    String portfolio_link = userObject.get("portfolio_link").isJsonNull() ? "" : userObject.get("portfolio_link").getAsString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("Boolean: " + Objects.equals( LocalStorageManager.getObject("User"), LocalStorageManager.getObject("ProfileId")));
            setDetails();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDetails() throws IOException {


        logoutButton.setVisible(Objects.equals( LocalStorageManager.getObject("User"), LocalStorageManager.getObject("ProfileId")));
        isStudent.setVisible(Objects.equals(student, true));
        name.setText(Objects.equals(businessName, "") ? firstname + " " + lastname : businessName );
        secondaryInfo.setText(Objects.equals(businessName, "") ? email : firstname + " " + lastname );
        resumeLink.setText(resume_link);
        resumeLink.setVisible(!Objects.equals(resume_link, ""));
        GithubLink.setText(github_link);
        GithubLink.setVisible(!Objects.equals(github_link, ""));
        portfolioLink.setText(portfolio_link);
        portfolioLink.setVisible(!Objects.equals(portfolio_link, ""));
        bio.setText(bio_txt);

    }

    public void onClickHandleLogout(ActionEvent event) throws IOException{
        logoutUser();
    }

    private void logoutUser() throws IOException {
        LocalStorageManager.clear();
        String[] data = {"sign-in-view.fxml", "Sign in"};
        HelloApplication.changeScene(data);
    }
}
