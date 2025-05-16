package org.example.projet_emplois.model;

import java.util.List;

public class Teacher extends User {

    public Teacher(int id, String name, String email, String password, String teacherId) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "teacher";
    }

    public String getTeacherId() {
        return "T-" + id;
    }

    public List<Schedule> viewSchedule() { return null; }
    public List<Course> viewCourseDetails() { return null; }
    public void reportConflict(String message) {}
}
