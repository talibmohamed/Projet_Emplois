package org.example.projet_emplois.model;

import javafx.beans.property.*;

public class Equipment {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;

    public Equipment(int id, String name) {
        this(id, name, 0);
    }

    public Equipment(int id, String name, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getQuantity() { return quantity.get(); }

    public StringProperty nameProperty() { return name; }
    public IntegerProperty quantityProperty() { return quantity; }

    @Override
    public String toString() {
        return name.get() + (quantity.get() > 0 ? " (" + quantity.get() + ")" : "");
    }
}
