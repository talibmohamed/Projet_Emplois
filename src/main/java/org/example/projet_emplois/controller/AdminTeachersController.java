package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.Teacher;

public class AdminTeachersController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML private TableView<Teacher> teachersTable;
    @FXML private TableColumn<Teacher, Integer> idColumn;
    @FXML private TableColumn<Teacher, String> nameColumn;
    @FXML private TableColumn<Teacher, String> emailColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTable();
    }

    @FXML
    private void handleAddTeacher() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Tous les champs sont requis.");
            return;
        }

        boolean success = UserDAO.addTeacher(name, email, password);
        if (success) {
            messageLabel.setText("Enseignant ajouté !");
            nameField.clear(); emailField.clear(); passwordField.clear();
            refreshTable();
        } else {
            messageLabel.setText("Erreur : email déjà utilisé ?");
        }
    }


    @FXML
    private void handleDeleteTeacher() {
        Teacher selected = teachersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Sélectionnez un enseignant.");
            return;
        }

        boolean success = UserDAO.deleteUserById(selected.getId());
        if (success) {
            messageLabel.setText("Enseignant supprimé.");
            refreshTable();
        } else {
            messageLabel.setText("Erreur lors de la suppression.");
        }
    }

    private void refreshTable() {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList(UserDAO.getAllTeachers());
        teachersTable.setItems(teachers);
    }
}