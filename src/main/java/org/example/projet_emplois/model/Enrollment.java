package org.example.projet_emplois.model;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;

    public Enrollment(int id, int studentId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }

    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
