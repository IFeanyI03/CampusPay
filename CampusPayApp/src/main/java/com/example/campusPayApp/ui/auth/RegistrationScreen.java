package com.example.campusPayApp.ui.auth;

import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.api.Profile;
import com.example.campusPayApp.api.ValidateStudents;
import com.example.campusPayApp.utils.ThemedAlert;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import com.example.campusPayApp.utils.LocalStorageManager;


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
        ToggleGroup group = new ToggleGroup();
        studentToggle.setToggleGroup(group);
        employerToggle.setToggleGroup(group);

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

            studentForm.setVisible(isStudent);
            employerForm.setVisible(!isStudent);
            toast.setText("");
        });

        // Initialize form visibility
        studentForm.setVisible(true);
        employerForm.setVisible(false);

        email.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !isValidGmail(newVal)) {
                email.setStyle("-fx-border-color: #ff4444; -fx-border-radius: 8;");
            } else {
                email.setStyle("-fx-border-color: #118C4F; -fx-border-radius: 8;");
            }
        });
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

    private boolean isValidGmail(String email) {
        String gmailRegex = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@gmail\\.com$";
        return email.toLowerCase().matches(gmailRegex);
    }

    public void registerEmployer() throws IOException {
        if(email.getText().isEmpty() || employerPassword.getText().isEmpty()){
            toast.setText("Please enter your details first");
            toast.setTextFill(Color.ORANGERED);
        } else if (!isValidGmail((email.getText()))) {
            toast.setText("Please use a valid Gmail address.");
            toast.setTextFill(Color.ORANGERED);
            email.setStyle("-fx-border-color: #ff4444; -fx-border-radius: 8");
        } else {
            try {
                registerEmployerButton.setDisable(true);
                int hashPassword = employerPassword.getText().hashCode();
                Profile profile = new Profile();

                // 1. Create employer JSON
                String employerJsonString = "{" +
                        "\"isStudent\": " + false + ", " +
                        "\"email\": " + "\"" + email.getText() + "\"" + ", " +
                        "\"password\": " + "\"" + hashPassword + "\"" +
                        "}";

                // 2. Register employer
                String response = profile.post(employerJsonString);

                if (response.equals("201")) {
                    toast.setText("Account created successfully!");
                    toast.setTextFill(Color.GREEN);

                    // 3. STORE USER SESSION IMMEDIATELY
                    String userData = "{" +
                            "\"email\": \"" + email.getText() + "\"," +
                            "\"isStudent\": false" +
                            "}";
                    LocalStorageManager.saveObject("User", userData);

                    // 4. Redirect to profile setup
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                                Platform.runLater(() -> {
                                    try {
                                        ThemedAlert.showAlert(
                                                "Success",
                                                "Please complete your profile",
                                                Alert.AlertType.INFORMATION
                                        );
                                        HelloApplication.changeScene(
                                                new String[]{"edit-profile-view.fxml", "Profile Setup"}
                                        );
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                });
                            });
                    pause.play();

                } else if (response.equals("409")) {
                    toast.setText("User already exists");
                    toast.setTextFill(Color.ORANGERED);
                    registerEmployerButton.setDisable(false);
                } else {
                    toast.setText("Registration failed");
                    toast.setTextFill(Color.ORANGERED);
                    registerEmployerButton.setDisable(false);
                }

            } catch (Exception e) {
                registerEmployerButton.setDisable(false);
                toast.setText("Error: " + e.getMessage());
                toast.setTextFill(Color.ORANGERED);
                e.printStackTrace();
            }
        }
    }

    public void goToSignInPage() throws IOException {
        String[] data = {"sign-in-view.fxml", "Sign In"};
        HelloApplication.changeScene(data);
    }
}
