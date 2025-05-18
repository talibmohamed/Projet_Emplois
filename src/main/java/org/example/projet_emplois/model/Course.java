package org.example.projet_emplois.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Course {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty teacherId;
    private final SimpleStringProperty teacherName;

    public Course(int id, String name, String type, int teacherId, String teacherName) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.teacherId = new SimpleIntegerProperty(teacherId);
        this.teacherName = new SimpleStringProperty(teacherName);
    }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getType() { return type.get(); }
    public int getTeacherId() { return teacherId.get(); }
    public String getTeacherName() { return teacherName.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setType(String type) { this.type.set(type); }
    public void setTeacherId(int id) { this.teacherId.set(id); }
    public void setTeacherName(String name) { this.teacherName.set(name); }

    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty typeProperty() { return type; }
    public SimpleStringProperty teacherNameProperty() { return teacherName; }

    @Override
    public String toString() {
        return name.get();
    }
}
