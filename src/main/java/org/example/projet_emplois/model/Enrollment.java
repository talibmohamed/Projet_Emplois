package org.example.projet_emplois.model;

import javafx.beans.property.*;

public class Enrollment {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty studentName = new SimpleStringProperty();
    private final StringProperty courseName = new SimpleStringProperty();

    public Enrollment(int id, String studentName, String courseName) {
        this.id.set(id);
        this.studentName.set(studentName);
        this.courseName.set(courseName);
    }

    // ID
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Student name
    public String getStudentName() {
        return studentName.get();
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    // Course name
    public String getCourseName() {
        return courseName.get();
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    // Optional: toString() for ComboBox or debug
    @Override
    public String toString() {
        return studentName.get() + " → " + courseName.get();
    }
}
