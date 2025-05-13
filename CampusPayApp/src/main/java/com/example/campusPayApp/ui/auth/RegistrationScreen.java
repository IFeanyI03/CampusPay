package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.api.ValidateStudents;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    private ToggleButton studentToggle;
    @FXML
    private ToggleButton employerToggle;
    @FXML
    private VBox studentForm;
    @FXML
    private VBox employerForm;

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

    @FXML
    public void initialize() {
        // Initialize toggle buttons
        ToggleGroup group = new ToggleGroup();
        studentToggle.setToggleGroup(group);
        employerToggle.setToggleGroup(group);

        // Style strings
        String selectedStyle = "-fx-background-color: #118C4F; -fx-text-fill: white;";
        String unselectedStyle = "-fx-background-color: #e0e0e0; -fx-text-fill: #555;";

        // Set initial styles
        studentToggle.setStyle(selectedStyle);
        employerToggle.setStyle(unselectedStyle);

        // Add toggle listener
        group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                // This prevents both toggles from being deselected
                studentToggle.setSelected(true);
                return;
            }

            boolean isStudent = newVal == studentToggle;

            // Update button styles
            studentToggle.setStyle(isStudent ? selectedStyle : unselectedStyle);
            employerToggle.setStyle(isStudent ? unselectedStyle : selectedStyle);

            // Show/hide forms
            studentForm.setVisible(isStudent);
            employerForm.setVisible(!isStudent);

            // Clear toast messages when switching
            toast.setText("");
        });

        // Initialize form visibility
        studentForm.setVisible(true);
        employerForm.setVisible(false);

        // ... (add any other initialization code you need) ...
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
                registerStudentButton.setDisable(false);
                toast.setText("Authentication Failed: " + e.getMessage());
                toast.setTextFill(Color.ORANGERED);
                e.printStackTrace(); // Log the full error for debugging
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerEmployer() throws IOException {
        if(email.getText().isEmpty() || employerPassword.getText().isEmpty()){
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

//                System.out.println(profile.post(employerJsonString));

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
