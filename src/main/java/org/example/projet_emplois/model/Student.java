package org.example.projet_emplois.model;

import java.util.List;

public class Student extends User {
    private String studentId;

    public Student(int id, String name, String email, String password, String studentId) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "student";
    }

    public String getStudentId() {
        return "S-" + id;
    }
    public List<Schedule> viewSchedule() { return null; }
    public void receiveNotification(String msg) {}
    public String viewRoomInfo() { return ""; }
}
