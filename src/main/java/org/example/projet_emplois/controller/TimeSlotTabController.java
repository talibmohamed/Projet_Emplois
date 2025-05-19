package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projet_emplois.dao.TimeSlotDAO;
import org.example.projet_emplois.model.TimeSlot;

import java.time.LocalTime;
import java.util.List;

public class TimeSlotTabController {

    @FXML private TableView<TimeSlot> timeslotTable;
    @FXML private TableColumn<TimeSlot, String> dayColumn;
    @FXML private TableColumn<TimeSlot, String> startTimeColumn;
    @FXML private TableColumn<TimeSlot, String> endTimeColumn;

    @FXML private ComboBox<String> dayComboBox;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private Label messageLabel;
    private ObservableList<TimeSlot> timeSlotList;

    @FXML
    public void initialize() {
        dayComboBox.setItems(FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"));

        dayColumn.setCellValueFactory(data -> data.getValue().dayProperty());
        startTimeColumn.setCellValueFactory(data -> data.getValue().startTimeProperty().asString());
        endTimeColumn.setCellValueFactory(data -> data.getValue().endTimeProperty().asString());

        timeSlotList = FXCollections.observableArrayList(TimeSlotDAO.getAllTimeSlots());
        timeslotTable.setItems(timeSlotList);
    }

    @FXML
    public void handleAddTimeSlot() {
        String day = dayComboBox.getValue();
        String start = startTimeField.getText();
        String end = endTimeField.getText();

        if (day == null || start.isBlank() || end.isBlank()) {
            messageLabel.setText("Tous les champs sont obligatoires.");
            return;
        }

        try {
            LocalTime startTime = LocalTime.parse(start);
            LocalTime endTime = LocalTime.parse(end);

            if (endTime.isBefore(startTime)) {
                messageLabel.setText("Heure de fin invalide.");
                return;
            }

            boolean added = TimeSlotDAO.addTimeSlot(day, startTime, endTime);
            if (added) {
                messageLabel.setText("Créneau ajouté.");
                refreshTimeSlots();
            } else {
                messageLabel.setText("Erreur lors de l’ajout.");
            }

        } catch (Exception e) {
            messageLabel.setText("Format d'heure invalide (utilisez HH:MM).");
        }
    }

    @FXML
    public void handleDeleteTimeSlot() {
        TimeSlot selected = timeslotTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Sélectionnez un créneau à supprimer.");
            return;
        }

        if (TimeSlotDAO.deleteTimeSlot(selected.getId())) {
            messageLabel.setText("Créneau supprimé.");
            refreshTimeSlots();
        } else {
            messageLabel.setText("Erreur lors de la suppression.");
        }
    }

    private void refreshTimeSlots() {
        timeSlotList.setAll(TimeSlotDAO.getAllTimeSlots());
        timeslotTable.getSelectionModel().clearSelection();
        dayComboBox.setValue(null);
        startTimeField.clear();
        endTimeField.clear();
    }
}
