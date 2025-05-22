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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }


    @Override
    public String toString() {
        return studentName.get() + " → " + courseName.get();
    }
}
