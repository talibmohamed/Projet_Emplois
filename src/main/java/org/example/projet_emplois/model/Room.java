package org.example.projet_emplois.model;

public class Room {
    private int id;
    private String name;
    private int capacity;
    private String[] equipment;

    public Room(int id, String name, int capacity, String[] equipment) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.equipment = equipment;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String[] getEquipment() { return equipment; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setEquipment(String[] equipment) { this.equipment = equipment; }
}
