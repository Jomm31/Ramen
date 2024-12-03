package com.example.cce104_ramen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField Username_Txt;

    @FXML
    private TextField Password_Txt;

    @FXML
    private Button Login_Button;

    @FXML
    private Button Exit_Button;

    @FXML
    private void handleLoginButtonAction() {

        String user = Username_Txt.getText();
        String pwd = Password_Txt.getText();

        if (pwd.equals("123") && user.equals("admin")) {
            // Create a new Alert for successful login
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Login successful!");

            alert.showAndWait();

            // Load the Home window
            loadHomeWindow();

        } else {
            // Create a new Alert for login failure
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Login failed!");

            alert.showAndWait();
        }
    }

    private void loadHomeWindow() {
        try {
            // Close the current login window
            Stage loginStage = (Stage) Login_Button.getScene().getWindow();
            loginStage.close();

            // Open the Home window
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Home.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            scene.getStylesheets().add(getClass().getResource("transitioncolor.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) Exit_Button.getScene().getWindow();
        stage.close();
    }
}
