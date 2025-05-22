package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projet_emplois.dao.CourseDAO;
import org.example.projet_emplois.dao.EnrollmentDAO;
import org.example.projet_emplois.dao.UserDAO;
import org.example.projet_emplois.model.Course;
import org.example.projet_emplois.model.Enrollment;
import org.example.projet_emplois.model.User;

public class EnrollmentTabController {

    @FXML private ComboBox<User> studentComboBox;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private TableColumn<Enrollment, String> studentColumn;
    @FXML private TableColumn<Enrollment, String> courseColumn;
    @FXML private Label messageLabel;

    private final ObservableList<Enrollment> enrollmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadComboBoxData();
        loadEnrollmentTable();

        studentColumn.setCellValueFactory(data -> data.getValue().studentNameProperty());
        courseColumn.setCellValueFactory(data -> data.getValue().courseNameProperty());

        enrollmentTable.setItems(enrollmentList);

        addDeleteButtonToTable();
    }

    private void loadComboBoxData() {
        studentComboBox.setItems(FXCollections.observableArrayList(UserDAO.getAllStudents()));
        courseComboBox.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
    }

    private void loadEnrollmentTable() {
        enrollmentList.setAll(EnrollmentDAO.getAllEnrollments());
    }

    @FXML
    public void handleEnroll() {
        User student = studentComboBox.getValue();
        Course course = courseComboBox.getValue();

        if (student == null || course == null) {
            messageLabel.setText("Sélectionnez un étudiant et un cours.");
            return;
        }

        boolean success = EnrollmentDAO.addEnrollment(student.getId(), course.getId());

        if (success) {
            messageLabel.setText("Étudiant inscrit au cours.");
            loadEnrollmentTable();
        } else {
            messageLabel.setText("Étudiant déjà inscrit à ce cours.");
        }
    }

    private void addDeleteButtonToTable() {
        TableColumn<Enrollment, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(90);

        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Enrollment enrollment = getTableView().getItems().get(getIndex());
                    boolean removed = EnrollmentDAO.removeEnrollment(enrollment.getId());
                    if (removed) {
                        messageLabel.setText("Étudiant désinscrit.");
                        loadEnrollmentTable();
                    } else {
                        messageLabel.setText("Erreur de suppression.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        enrollmentTable.getColumns().add(actionColumn);
    }
}
