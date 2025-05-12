package org.example.projet_emplois.model;

import java.util.List;

public class Teacher extends User {
    private String teacherId;

    public Teacher(int id, String name, String email, String password, String teacherId) {
        super(id, name, email, password);
        this.teacherId = teacherId;
    }

    @Override
    public String getRole() {
        return "teacher";
    }

    public List<Schedule> viewSchedule() { return null; }
    public List<Course> viewCourseDetails() { return null; }
    public void reportConflict(String message) {}
}
