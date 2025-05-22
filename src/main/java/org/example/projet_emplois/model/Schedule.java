package org.example.projet_emplois.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty courseName = new SimpleStringProperty();
    private final StringProperty roomName = new SimpleStringProperty();
    private final StringProperty day = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty();
    private final StringProperty teacher = new SimpleStringProperty();  // ✅ Added

    private final StringProperty teacherName = new SimpleStringProperty();


    public Schedule(int id, String courseName, String roomName, String day,
                    LocalDate date, LocalTime startTime, LocalTime endTime,
                    String teacherName) {
        this.id.set(id);
        this.courseName.set(courseName);
        this.roomName.set(roomName != null ? roomName : "—");
        this.day.set(day);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.teacherName.set(teacherName != null ? teacherName : "—");
    }

    public String getTeacherName() {
        return teacherName.get();
    }

    public StringProperty teacherNameProperty() {
        return teacherName;
    }



    public String getCourseName() {
        return courseName.get();
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public String getRoomName() {
        return roomName.get();
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public String getDay() {
        return day.get();
    }

    public StringProperty dayProperty() {
        return day;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime.get();
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }

    public String getTeacher() {
        return teacher.get();
    }

    public StringProperty teacherProperty() {
        return teacher;
    }

    public StringProperty timeRangeProperty() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        return new SimpleStringProperty(startTime.get().format(fmt) + " → " + endTime.get().format(fmt));
    }

    public int getId() {
        return id.get();
    }

    public String getClassName() {
        return roomName.get();
    }
}
