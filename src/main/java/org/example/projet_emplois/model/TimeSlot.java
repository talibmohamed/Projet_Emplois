package org.example.projet_emplois.model;

import javafx.beans.property.*;
import java.time.LocalTime;

public class TimeSlot {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty day = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();

    public TimeSlot(int id, String day, LocalTime startTime, LocalTime endTime) {
        this.id.set(id);
        this.day.set(day);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
    }

    public int getId() { return id.get(); }
    public String getDay() { return day.get(); }
    public LocalTime getStartTime() { return startTime.get(); }
    public LocalTime getEndTime() { return endTime.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty dayProperty() { return day; }
    public ObjectProperty<LocalTime> startTimeProperty() { return startTime; }
    public ObjectProperty<LocalTime> endTimeProperty() { return endTime; }

    @Override
    public String toString() {
        return day.get() + " - " + startTime.get() + " → " + endTime.get();
    }
}
