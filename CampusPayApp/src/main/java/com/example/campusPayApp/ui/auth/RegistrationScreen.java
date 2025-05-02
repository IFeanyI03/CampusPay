package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.api.ValidateStudents;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class RegistrationScreen {
    @FXML
    public Button registerStudentButton;

    @FXML
    public TextField matricNo;

    @FXML
    public PasswordField studentPassword;

    @FXML
    public Button registerEmployerButton;

    @FXML
    public TextField email;

    @FXML
    public PasswordField employerPassword;

    @FXML
    public Label toast;

    @FXML
    public Hyperlink signInLink;

    @FXML
    public void onClickHandleEmployerRegister(ActionEvent event) throws IOException {
        registerEmployer();
    }

    @FXML
    public void onClickHandleStudentRegister(ActionEvent event) throws IOException {
        registerStudent();
    }

    @FXML
    public void onclickGoToSignIn(ActionEvent event) throws IOException {
        goToSignInPage();
    }

    HelloApplication app = new HelloApplication();
    ValidateStudents student = new ValidateStudents();

    public void registerStudent() throws IOException {
        if(matricNo.getText().isEmpty() || studentPassword.getText().isEmpty()){
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        } else {
            try{
                registerStudentButton.setDisable(true);
                String responseCode = student.checkStudentDetails(matricNo.getText(), studentPassword.getText());
                if (Objects.equals(responseCode, "201")) {
                    toast.setText("Authentication Successful");
                    toast.setTextFill(Color.GREEN);
                    // Proceed to the next page after successful authentication
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        try {
                            String[] data = {"edit-profile-view.fxml", "Profile SetUp"};
                            HelloApplication.changeScene(data);
                        } catch (IOException ex) {
                            ex.printStackTrace(); // Handle the exception appropriately
                        }
                    });
                    pause.play();
                } else {
                    registerStudentButton.setDisable(false);
                    if (Objects.equals(responseCode, "409")){
                        toast.setText("User Already Exists");
                        toast.setTextFill(Color.ORANGERED);
                    } else {
                        toast.setText("Authentication Failed");
                        toast.setTextFill(Color.ORANGERED);
                    }
                }
            } catch (RuntimeException e) {
                toast.setText("Authentication Failed: " + e.getMessage());
                toast.setTextFill(Color.ORANGERED);
                e.printStackTrace(); // Log the full error for debugging
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerEmployer() throws IOException {
        if(email.getText().isEmpty() && employerPassword.getText().isEmpty()){
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        } else {
            try{
                int hashPassword = employerPassword.getText().hashCode();
                Profile profile = new Profile();
                String employerJsonString = "{" +
                        "\"isStudent\": " + false + ", " +
                        "\"email\": " + "\"" + email.getText() + "\"" + ", " +
                        "\"password\": " + "\"" + hashPassword + "\"" +
                        "}";
//
                String response = profile.post(employerJsonString);

                System.out.println(profile.post(employerJsonString));

             if (response.equals("201")){
                  toast.setText("User Account Created");
                  toast.setTextFill(Color.GREEN);
                 PauseTransition pause = new PauseTransition(Duration.seconds(2));
                 pause.setOnFinished(e -> {
                     try {
                         String[] data = {"edit-profile-view.fxml", "Profile SetUp"};
                         HelloApplication.changeScene(data);
                     } catch (IOException ex) {
                         ex.printStackTrace(); // Handle the exception appropriately
                     }
                 });
                 pause.play();
              } else if (response.equals("409")) {
                 toast.setText("User Already Exists");
                 toast.setTextFill(Color.ORANGERED);

             } else {
                 toast.setText("Authentication Failed");
                 toast.setTextFill(Color.ORANGERED);
             }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    try {
                        String[] data = {"edit-profile-view.fxml", "Profile SetUp"};
                        HelloApplication.changeScene(data);
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }
                });
            }
        }
    }

    public void goToSignInPage() throws IOException {
        String[] data = {"sign-in-view.fxml", "Sign In"};
        HelloApplication.changeScene(data);
    }
}
