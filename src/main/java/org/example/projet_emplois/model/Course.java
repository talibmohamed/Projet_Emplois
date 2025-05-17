package org.example.projet_emplois.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Course {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;

    private final SimpleStringProperty teacherName;

    public Course(int id, String name, String type, String teacherName) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.teacherName = new SimpleStringProperty(teacherName);
    }


    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getTeacherName() {
        return teacherName.get();
    }

    public void setTeacherName(String teacherName) {
        this.teacherName.set(teacherName);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleStringProperty teacherNameProperty() {
        return teacherName;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }


    public void setName(String newValue) {
        this.name.set(newValue);
    }

    public void setType(String newValue) {
        this.type.set(newValue);
    }
}