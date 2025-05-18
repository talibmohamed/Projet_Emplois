package org.example.projet_emplois.model;

import javafx.beans.property.*;

import java.util.List;

public class Room {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty capacity;
    private List<Equipment> equipmentList;

    public Room(int id, String name, int capacity, List<Equipment> equipmentList) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.equipmentList = equipmentList;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @Override
    public String toString() {
        return getName();
    }



}
