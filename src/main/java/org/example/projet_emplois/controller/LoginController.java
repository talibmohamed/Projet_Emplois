package org.example.projet_emplois.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.User;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = UserDAO.findByEmailAndPassword(email, password);
        if (user == null) {
            errorLabel.setText("Invalid credentials.");
            return;
        }

        try {
            FXMLLoader loader;
            switch (user.getRole()) {
                case "admin":
                    loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/admin-dashboard.fxml"));
                    break;
                case "teacher":
                    loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/teacher-dashboard.fxml"));
                    break;
                case "student":
                    loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/student-dashboard.fxml"));
                    break;
                default:
                    errorLabel.setText("Unknown role.");
                    return;
            }

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Error loading dashboard.");
            e.printStackTrace();
        }
    }
}
