package org.example.projet_emplois.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.projet_emplois.model.Admin;

public class AdminDashboardController {

    private Admin admin;

    @FXML
    private Label welcomeLabel;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        welcomeLabel.setText("Bienvenue, " + admin.getName());
    }
}
