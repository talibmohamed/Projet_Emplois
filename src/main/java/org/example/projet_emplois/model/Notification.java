package org.example.projet_emplois.model;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private String createdAt;

    public Notification(int id, int userId, String message, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getMessage() { return message; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setMessage(String message) { this.message = message; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
