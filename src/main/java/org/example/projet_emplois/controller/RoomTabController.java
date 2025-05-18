package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projet_emplois.dao.EquipmentDAO;
import org.example.projet_emplois.dao.RoomDAO;
import org.example.projet_emplois.model.Equipment;
import org.example.projet_emplois.model.Room;

public class RoomTabController {

    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> roomNameColumn;
    @FXML private TableColumn<Room, Integer> roomCapacityColumn;

    @FXML private TableView<Equipment> roomEquipmentTable;
    @FXML private TableColumn<Equipment, String> equipmentNameColumn;
    @FXML private TableColumn<Equipment, Integer> equipmentQuantityColumn;

    @FXML private TextField roomNameField;
    @FXML private Spinner<Integer> capacitySpinner;
    @FXML private ComboBox<Equipment> equipmentComboBox;
    @FXML private TextField equipmentQtyField;
    @FXML private Label messageLabel;

    @FXML private TextField newEquipmentField;

    @FXML private TableView<Equipment> equipmentTypeTable;
    @FXML private TableColumn<Equipment, String> equipmentTypeNameColumn;

    private ObservableList<Room> roomList;
    private ObservableList<Equipment> allEquipments;

    @FXML
    public void initialize() {
        capacitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));

        // Room table columns
        roomNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        roomCapacityColumn.setCellValueFactory(data -> data.getValue().capacityProperty().asObject());

        // Equipment assigned to room columns
        equipmentNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        equipmentQuantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());

        // Load initial data
        roomList = FXCollections.observableArrayList(RoomDAO.getAllRooms());
        roomTable.setItems(roomList);

        allEquipments = FXCollections.observableArrayList(EquipmentDAO.getAllEquipments());
        equipmentComboBox.setItems(allEquipments);
        equipmentTypeTable.setItems(allEquipments); // Show all equipment types

        // ComboBox format
        equipmentComboBox.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Equipment e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? null : e.getName());
            }
        });
        equipmentComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Equipment e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? null : e.getName());
            }
        });

        // Equipment type table column setup
        equipmentTypeNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        // Room selection listener
        roomTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedRoom) -> {
            if (selectedRoom != null) {
                roomEquipmentTable.setItems(FXCollections.observableArrayList(selectedRoom.getEquipmentList()));
                roomNameField.setText(selectedRoom.getName());
                capacitySpinner.getValueFactory().setValue(selectedRoom.getCapacity());
            }
        });
    }


    @FXML
    public void handleAddRoom() {
        String name = roomNameField.getText().trim();
        int capacity = capacitySpinner.getValue();

        if (name.isEmpty()) {
            messageLabel.setText("Le nom de la salle est requis.");
            return;
        }

        boolean success = RoomDAO.addRoom(name, capacity);
        if (success) {
            messageLabel.setText("Salle ajoutée avec succès.");
            refreshRooms();
        } else {
            messageLabel.setText("Erreur lors de l'ajout.");
        }
    }

    @FXML
    public void handleDeleteRoom() {
        Room selected = roomTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Veuillez sélectionner une salle.");
            return;
        }

        boolean success = RoomDAO.deleteRoom(selected.getId());
        if (success) {
            messageLabel.setText("Salle supprimée.");
            refreshRooms();
        } else {
            messageLabel.setText("Erreur lors de la suppression.");
        }
    }

    @FXML
    public void handleAssignEquipment() {
        Room room = roomTable.getSelectionModel().getSelectedItem();
        Equipment equipment = equipmentComboBox.getValue();
        String qtyText = equipmentQtyField.getText();

        if (room == null || equipment == null || qtyText.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantité invalide.");
            return;
        }

        boolean success = RoomDAO.assignEquipment(room.getId(), equipment.getId(), quantity);
        if (success) {
            messageLabel.setText("Équipement assigné.");
            refreshRooms();
        } else {
            messageLabel.setText("Équipement déjà assigné.");
        }
    }

    @FXML
    public void handleRemoveEquipment() {
        Room room = roomTable.getSelectionModel().getSelectedItem();
        Equipment selectedEquipment = roomEquipmentTable.getSelectionModel().getSelectedItem();

        if (room == null || selectedEquipment == null) {
            messageLabel.setText("Sélectionnez une salle et un équipement à retirer.");
            return;
        }

        boolean success = RoomDAO.removeEquipment(room.getId(), selectedEquipment.getId());
        if (success) {
            messageLabel.setText("Équipement retiré.");
            refreshRooms();
        } else {
            messageLabel.setText("Erreur lors du retrait.");
        }
    }

    private void refreshRooms() {
        roomList.setAll(RoomDAO.getAllRooms());
        roomTable.getSelectionModel().clearSelection();
        roomEquipmentTable.setItems(FXCollections.emptyObservableList());
        roomNameField.clear();
        equipmentQtyField.clear();
        capacitySpinner.getValueFactory().setValue(1);
    }

    @FXML
    public void handleAddEquipmentType() {
        String name = newEquipmentField.getText().trim();
        if (name.isEmpty()) {
            messageLabel.setText("Nom de l’équipement requis.");
            return;
        }

        if (EquipmentDAO.addEquipment(name)) {
            messageLabel.setText("Équipement ajouté.");
            refreshEquipments();
        } else {
            messageLabel.setText("Erreur ou équipement déjà existant.");
        }
    }

    @FXML
    public void handleDeleteEquipmentType() {
        Equipment selected = equipmentComboBox.getValue();
        if (selected == null) {
            messageLabel.setText("Sélectionnez un équipement à supprimer.");
            return;
        }

        if (EquipmentDAO.deleteEquipment(selected.getId())) {
            messageLabel.setText("Équipement supprimé.");
            refreshEquipments();
        } else {
            messageLabel.setText("Erreur lors de la suppression.");
        }
    }

    private void refreshEquipments() {
        allEquipments.setAll(EquipmentDAO.getAllEquipments());
        equipmentComboBox.setItems(allEquipments);
        equipmentComboBox.setValue(null);
        newEquipmentField.clear();
    }

}
