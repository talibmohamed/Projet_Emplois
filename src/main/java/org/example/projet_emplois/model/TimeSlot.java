package org.example.projet_emplois.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty day = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    public TimeSlot(int id, String day, LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.id.set(id);
        this.day.set(day);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.date.set(date);
    }

    public int getId() { return id.get(); }
    public String getDay() { return day.get(); }
    public LocalTime getStartTime() { return startTime.get(); }
    public LocalTime getEndTime() { return endTime.get(); }
    public LocalDate getDate() { return date.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty dayProperty() { return day; }
    public ObjectProperty<LocalTime> startTimeProperty() { return startTime; }
    public ObjectProperty<LocalTime> endTimeProperty() { return endTime; }
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    @Override
    public String toString() {
        return date.get() + " (" + day.get() + ") - " + startTime.get() + " → " + endTime.get();
    }
}
