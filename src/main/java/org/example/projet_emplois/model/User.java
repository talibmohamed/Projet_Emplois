package org.example.projet_emplois.model;

public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public abstract String getRole();

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
