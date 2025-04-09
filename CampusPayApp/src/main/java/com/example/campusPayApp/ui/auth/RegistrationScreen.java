package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

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

    public void onClickHandleStudentRegister(ActionEvent event) throws IOException {
        registerStudent();
    }

    @FXML
    public void onclickGoToSignIn(ActionEvent event) throws IOException {
        goToSignInPage();
    }

    HelloApplication app = new HelloApplication();

    public void registerStudent() throws IOException {
        if(matricNo.getText().isEmpty() && studentPassword.getText().isEmpty()){
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        } else {
            try{
                toast.setText("User Account Created");
                toast.setTextFill(Color.GREEN);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    try {
                        String[] data = {"edit-profile-view.fxml", "Profile SetUp"};
                        app.changeScene(data);
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }
                });
            }
        }
    }

    public void registerEmployer() throws IOException {
        if(email.getText().isEmpty() && employerPassword.getText().isEmpty()){
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        } else {
            try{
                toast.setText("User Account Created");
                toast.setTextFill(Color.GREEN);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    try {
                        String[] data = {"edit-profile-view.fxml", "Profile SetUp"};
                        app.changeScene(data);
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }
                });
            }
        }
    }

    public void goToSignInPage() throws IOException {
        String[] data = {"sign-in-view.fxml", "Sign In"};
        app.changeScene(data);
    }
}
