package org.example.projet_emplois.model;

public class Teacher extends User {


    public Teacher(int id, String name, String email, String password) {
        super(id, name, email, password);

    }

    public String getTeacherId() {
        return "T" + getId();
    }

    @Override
    public String getRole() {
        return "teacher";
    }

    @Override
    public String toString() {
        return getName(); // or getName() + " (" + getEmail() + ")"
    }
}
