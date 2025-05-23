package com.example.campusPayApp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class ThemedAlert {
    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Custom content label
        Label contentLabel = new Label(message);
        contentLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
        alert.getDialogPane().setContent(contentLabel);

        // Style the dialog pane (green/white theme)
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #fff;" +
                        "-fx-border-color: #118C4F;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;"
        );

        // Style buttons
        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: #118C4F;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8px;"
        );

        // Resize to fit content
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }
}