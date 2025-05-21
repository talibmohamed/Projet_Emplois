package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projet_emplois.dao.TimeSlotDAO;
import org.example.projet_emplois.model.TimeSlot;
import org.example.projet_emplois.dao.TimeSlotResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeSlotTabController {

    @FXML private TableView<TimeSlot> timeslotTable;
    @FXML private TableColumn<TimeSlot, String> dayColumn;
    @FXML private TableColumn<TimeSlot, String> startTimeColumn;
    @FXML private TableColumn<TimeSlot, String> endTimeColumn;
    @FXML private TableColumn<TimeSlot, LocalDate> dateColumn;

    @FXML private ComboBox<String> dayComboBox;
    @FXML private DatePicker datePicker;

    @FXML private ComboBox<Integer> startHourComboBox;
    @FXML private ComboBox<Integer> startMinuteComboBox;
    @FXML private ComboBox<Integer> endHourComboBox;
    @FXML private ComboBox<Integer> endMinuteComboBox;

    @FXML private Label messageLabel;

    private ObservableList<TimeSlot> timeSlotList;

    @FXML
    public void initialize() {
        dayComboBox.setItems(FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"));

        // Auto-select day based on datePicker
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                DayOfWeek dayOfWeek = newDate.getDayOfWeek();
                String frenchDay = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH);
                dayComboBox.setValue(capitalize(frenchDay));
            }
        });

        // Populate hour and minute dropdowns
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++) hours.add(i);
        for (int i = 0; i < 60; i += 5) minutes.add(i); // step of 5

        startHourComboBox.setItems(hours);
        startMinuteComboBox.setItems(minutes);
        endHourComboBox.setItems(hours);
        endMinuteComboBox.setItems(minutes);

        startHourComboBox.setValue(8);
        startMinuteComboBox.setValue(0);
        endHourComboBox.setValue(9);
        endMinuteComboBox.setValue(0);

        dayColumn.setCellValueFactory(data -> data.getValue().dayProperty());
        startTimeColumn.setCellValueFactory(data -> data.getValue().startTimeProperty().asString());
        endTimeColumn.setCellValueFactory(data -> data.getValue().endTimeProperty().asString());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        timeSlotList = FXCollections.observableArrayList(TimeSlotDAO.getAllTimeSlots());
        timeslotTable.setItems(timeSlotList);
    }

    @FXML
    public void handleAddTimeSlot() {
        String day = dayComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (day == null || date == null) {
            messageLabel.setText("Jour et date sont obligatoires.");
            return;
        }

        LocalTime startTime = LocalTime.of(startHourComboBox.getValue(), startMinuteComboBox.getValue());
        LocalTime endTime = LocalTime.of(endHourComboBox.getValue(), endMinuteComboBox.getValue());

        if (endTime.isBefore(startTime)) {
            messageLabel.setText("Heure de fin invalide.");
            return;
        }

        TimeSlotResult result = TimeSlotDAO.addTimeSlot(day, startTime, endTime, date);

        switch (result) {
            case SUCCESS -> {
                messageLabel.setText("✅ Créneau ajouté.");
                refreshTimeSlots();
            }
            case DUPLICATE -> messageLabel.setText("❗ Ce créneau existe déjà pour cette date.");
            case ERROR -> messageLabel.setText("❌ Une erreur s’est produite lors de l’ajout.");
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
        datePicker.setValue(null);
        startHourComboBox.setValue(8);
        startMinuteComboBox.setValue(0);
        endHourComboBox.setValue(9);
        endMinuteComboBox.setValue(0);
    }

    private String capitalize(String text) {
        return text == null || text.isBlank()
                ? text
                : text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
