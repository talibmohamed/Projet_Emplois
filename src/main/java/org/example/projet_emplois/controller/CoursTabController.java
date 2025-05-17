package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import org.example.projet_emplois.dao.CourseDAO;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.Course;
import org.example.projet_emplois.model.Teacher;

public class CoursTabController {

    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> courseTypeColumn;
    @FXML private TableColumn<Course, String> teacherColumn;

    @FXML private ComboBox<Teacher> teacherComboBox;
    @FXML private ComboBox<String> courseTypeComboBox;
    @FXML private TextField courseNameField;

    @FXML private Label messageLabel;
    @FXML private Button affecterButton;
    @FXML private Button addButton;

    private ObservableList<Course> courseList;
    private ObservableList<Teacher> teacherList;

    @FXML
    public void initialize() {
        coursesTable.setEditable(true);

        // Course Name – Editable
        courseNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        courseNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        courseNameColumn.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setName(event.getNewValue());
            CourseDAO.updateCourse(course.getId(), course.getName(), course.getType());
            messageLabel.setText("Nom du cours mis à jour.");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        });

        // Course Type – Editable
        courseTypeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        courseTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn("CM", "TD", "TP"));
        courseTypeColumn.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setType(event.getNewValue());
            CourseDAO.updateCourse(course.getId(), course.getName(), course.getType());
            messageLabel.setText("Type de cours mis à jour.");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        });

        // Teacher – READ-ONLY
        teacherColumn.setCellValueFactory(data -> data.getValue().teacherNameProperty());
        teacherColumn.setEditable(false);

        // Load courses and teachers
        courseList = FXCollections.observableArrayList(CourseDAO.getAllCourses());
        coursesTable.setItems(courseList);

        teacherList = FXCollections.observableArrayList(UserDAO.getAllTeachers());
        teacherComboBox.setItems(teacherList);

        teacherComboBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Teacher teacher, boolean empty) {
                super.updateItem(teacher, empty);
                setText((teacher == null || empty) ? null : teacher.getName());
            }
        });
        teacherComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Teacher teacher, boolean empty) {
                super.updateItem(teacher, empty);
                setText((teacher == null || empty) ? null : teacher.getName());
            }
        });
    }


    private void validateForm() {
        boolean disable = coursesTable.getSelectionModel().getSelectedItem() == null || teacherComboBox.getValue() == null;
        affecterButton.setDisable(disable);
    }

    @FXML
    public void handleAffecter() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        Teacher selectedTeacher = teacherComboBox.getValue();

        if (selectedCourse == null || selectedTeacher == null) {
            showError("Veuillez sélectionner un cours et un enseignant.");
            return;
        }

        boolean success = CourseDAO.assignTeacherToCourse(selectedCourse.getId(), selectedTeacher.getId());
        if (success) {
            selectedCourse.setTeacherName(selectedTeacher.getName());
            coursesTable.refresh();
            showSuccess("Enseignant affecté avec succès.");
        } else {
            showError("Erreur lors de l'affectation.");
        }
    }

    @FXML
    public void handleAddCourse() {
        String name = courseNameField.getText().trim();
        String type = courseTypeComboBox.getValue();

        if (name.isEmpty() || type == null) {
            showError("Veuillez remplir tous les champs.");
            return;
        }

        boolean success = CourseDAO.addCourse(name, type);
        if (success) {
            showSuccess("Cours ajouté avec succès.");
            refreshCourseTable();
            clearForm();
        } else {
            showError("Erreur lors de l'ajout.");
        }
    }

    @FXML
    public void handleEditCourse() {
        Course selected = coursesTable.getSelectionModel().getSelectedItem();
        String name = courseNameField.getText().trim();
        String type = courseTypeComboBox.getValue();

        if (selected == null || name.isEmpty() || type == null) {
            showError("Sélectionnez un cours et remplissez les champs.");
            return;
        }

        boolean success = CourseDAO.updateCourse(selected.getId(), name, type);
        if (success) {
            showSuccess("Cours modifié avec succès.");
            refreshCourseTable();
            clearForm();
        } else {
            showError("Erreur lors de la modification.");
        }
    }

    @FXML
    public void handleDeleteCourse() {
        Course selected = coursesTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Sélectionnez un cours à supprimer.");
            return;
        }

        boolean success = CourseDAO.deleteCourse(selected.getId());
        if (success) {
            showSuccess("Cours supprimé.");
            refreshCourseTable();
            clearForm();
        } else {
            showError("Erreur lors de la suppression.");
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setTextFill(javafx.scene.paint.Color.RED);
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
    }

    private void clearForm() {
        courseNameField.clear();
        courseTypeComboBox.getSelectionModel().clearSelection();
        teacherComboBox.getSelectionModel().clearSelection();
        coursesTable.getSelectionModel().clearSelection();
        validateForm();
    }

    private void refreshCourseTable() {
        courseList = FXCollections.observableArrayList(CourseDAO.getAllCourses());
        coursesTable.setItems(courseList);
    }
}
