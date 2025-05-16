package org.example.projet_emplois.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.example.projet_emplois.model.User;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainPane;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void loadDashboard(String role) throws IOException {
        String fxml;
        switch (role) {
            case "login" -> fxml = "/org/example/projet_emplois/views/login.fxml";
            case "admin" -> fxml = "/org/example/projet_emplois/views/admin-dashboard.fxml";
            case "teacher" -> fxml = "/org/example/projet_emplois/views/teacher-dashboard.fxml";
            case "student" -> fxml = "/org/example/projet_emplois/views/student-dashboard.fxml";
            default -> throw new IllegalArgumentException("Unknown view: " + role);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Node view = loader.load();

        if ("login".equals(role)) {
            LoginController loginController = loader.getController();
            loginController.setMainController(this);
        }

        mainPane.setCenter(view);
    }
}
