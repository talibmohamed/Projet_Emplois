package org.example.projet_emplois.model;

public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "admin";
    }

    public String getAdminId() {
        return "A-" + id;
    }

    public void createSchedule() {

    }
    public void modifySchedule() {}
    public void assignTeacher(Course course, Teacher teacher) {}
    public void manageRoom(Room room) {}
    public void resolveConflicts() {}
}
