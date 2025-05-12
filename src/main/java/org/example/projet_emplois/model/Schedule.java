package org.example.projet_emplois.model;

public class Schedule {
    private int id;
    private int courseId;
    private int roomId;
    private int timeSlotId;

    public Schedule(int id, int courseId, int roomId, int timeSlotId) {
        this.id = id;
        this.courseId = courseId;
        this.roomId = roomId;
        this.timeSlotId = timeSlotId;
    }

    public int getId() { return id; }
    public int getCourseId() { return courseId; }
    public int getRoomId() { return roomId; }
    public int getTimeSlotId() { return timeSlotId; }

    public void setId(int id) { this.id = id; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setTimeSlotId(int timeSlotId) { this.timeSlotId = timeSlotId; }
}
