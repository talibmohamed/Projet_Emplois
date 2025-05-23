package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.Student;

public class AdminStudentsController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> emailColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTable();
    }

    @FXML
    private void handleAddStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Tous les champs sont requis.");
            return;
        }

        boolean success = UserDAO.addStudent(name, email, password);
        if (success) {
            messageLabel.setText("Étudiant ajouté !");
            nameField.clear(); emailField.clear(); passwordField.clear();
            refreshTable();
        } else {
            messageLabel.setText("Erreur : email déjà utilisé ?");
        }
    }


    @FXML
    private void handleDeleteStudent() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Sélectionnez un étudiant.");
            return;
        }

        boolean success = UserDAO.deleteUserById(selected.getId());
        if (success) {
            messageLabel.setText("Étudiant supprimé.");
            refreshTable();
        } else {
            messageLabel.setText("Erreur lors de la suppression.");
        }
    }

    private void refreshTable() {
        ObservableList<Student> students = FXCollections.observableArrayList(UserDAO.getAllStudents());
        studentsTable.setItems(students);
    }
}