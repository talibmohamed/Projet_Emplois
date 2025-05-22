package org.example.projet_emplois.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.User;
import org.example.projet_emplois.model.Student;
import java.net.URL;
import org.example.projet_emplois.model.Teacher;

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
            FXMLLoader loader = null;
            Parent root = null;

            switch (user.getRole()) {
                case "admin":
                    loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/admin-dashboard.fxml"));
                    root = loader.load();
                    break;

                case "teacher":
                    loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/teacher-dashboard.fxml"));
                    root = loader.load();

                    TeacherDashboardController teacherController = loader.getController();
                    if (teacherController == null) {
                        errorLabel.setText("Controller is null. Check fx:controller or FXML structure.");
                        return;
                    }

                    teacherController.setTeacher((Teacher) user);
                    teacherController.setupDashboard();
                    break;


                case "student":
                    URL fxmlUrl = getClass().getResource("/org/example/projet_emplois/views/student-dashboard.fxml");
                    if (fxmlUrl == null) {
                        errorLabel.setText("FXML not found!");
                        return;
                    }

                    loader = new FXMLLoader(fxmlUrl);
                    root = loader.load();

                    StudentDashboardController controller = loader.getController();
                    if (controller == null) {
                        errorLabel.setText("Controller is null. Check fx:controller or FXML structure.");
                        return;
                    }

                    controller.setStudent((Student) user);
                    controller.setupDashboard();
                    break;


                default:
                    errorLabel.setText("Unknown role.");
                    return;
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Error loading dashboard.");
            e.printStackTrace();
        }
    }

        private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
