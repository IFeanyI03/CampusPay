package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.example.campusPayApp.utils.ThemedAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Objects;

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
    Gson gson = new Gson();

    public void checkLogin() throws IOException {
        String email = username.getText();
        if (!email.isEmpty() && !password.getText().isEmpty()) {

            Profile profile = new Profile();
            String response = profile.getProfileByEmail(email);
//            System.out.println(response);
            if (response.equals("404")) {
                toast.setText("User with this email does not exist");
                toast.setTextFill(Color.ORANGERED);
            }
            else{
                JsonObject userObject = gson.fromJson(response, JsonObject.class);
                String hashPassword = "\"" + password.getText().hashCode() + "\"";
//
                if (userObject.get("password").getAsInt() == password.getText().hashCode()) {

                    LocalStorageManager.saveObject("User", response);
                    ThemedAlert.showAlert(
                            "Success!",
                            "Your profile has been saved successfully.",
                            Alert.AlertType.INFORMATION
                    );

                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        try {
                            String[] data = {"home-view.fxml", "Home"};
                            HelloApplication.changeScene(data);
                        } catch (IOException ex) {
                            ex.printStackTrace(); // Handle the exception appropriately
                        }
                    });
                    pause.play();
                } else {
                    ThemedAlert.showAlert(
                            "Error",
                            "Incorrect details. Try again",
                            Alert.AlertType.ERROR
                    );
                }
            }
//        } else (username.getText().isEmpty() && password.getText().isEmpty()) {
//            toast.setText("Please enter your details first");
//            toast.setTextFill(Color.ORANGERED);
        } else {
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        }
    }

    public void goToRegisterPage() throws IOException {
        String[] data = {"register-view.fxml", "Register"};
        app.changeScene(data);
    }
}