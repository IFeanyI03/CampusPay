package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SignInScreen {
    @FXML
    public Hyperlink registerLink;

    @FXML
    private Label toast;

    @FXML
    private Button signInButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void onClickHandleSignIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    @FXML
    public void onClickGoToRegister(ActionEvent event) throws IOException {
        goToRegisterPage();
    }

    HelloApplication app = new HelloApplication();

    public void checkLogin() throws IOException {

        if (!username.getText().isEmpty() && password.getText().isEmpty()) {
            toast.setText("Sign In successful");
            toast.setTextFill(Color.GREEN);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                try {
                    String[] data = {"home-view.fxml", "Home"};
                    app.changeScene(data);
                } catch (IOException ex) {
                    ex.printStackTrace(); // Handle the exception appropriately
                }
            });
            pause.play();
        } else if (username.getText().isEmpty() && password.getText().isEmpty()) {
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGE);
        } else {
            toast.setText("Incorrect username or password");
            toast.setTextFill(Color.RED);
        }
    }

    public void goToRegisterPage() throws IOException {
        String[] data = {"register-view.fxml", "Register"};
        app.changeScene(data);
    }
}