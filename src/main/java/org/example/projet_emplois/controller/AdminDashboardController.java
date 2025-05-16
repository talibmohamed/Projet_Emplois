package org.example.projet_emplois.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.example.projet_emplois.model.Admin;

import java.io.IOException;

public class AdminDashboardController {

    private Admin admin;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TabPane adminTabs;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        welcomeLabel.setText("Bienvenue, " + admin.getName());

        try {
            FXMLLoader studentTabLoader = new FXMLLoader(getClass().getResource(
                    "/org/example/projet_emplois/views/admin/students-tab.fxml"));
            Tab studentTab = new Tab("Étudiants");
            studentTab.setContent(studentTabLoader.load());
            adminTabs.getTabs().add(studentTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
