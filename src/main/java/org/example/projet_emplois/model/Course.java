package org.example.projet_emplois.model;

public class Course {
    private int id;
    private String name;
    private String type;
    private int teacherId;

    public Course(int id, String name, String type, int teacherId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getTeacherId() { return teacherId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
}
