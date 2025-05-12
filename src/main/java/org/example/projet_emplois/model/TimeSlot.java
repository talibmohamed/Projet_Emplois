package org.example.projet_emplois.model;

public class TimeSlot {
    private int id;
    private String day;
    private String startTime;
    private String endTime;

    public TimeSlot(int id, String day, String startTime, String endTime) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public void setId(int id) { this.id = id; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
