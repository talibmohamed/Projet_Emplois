package org.example.projet_emplois.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projet_emplois.dao.CourseDAO;
import org.example.projet_emplois.dao.RoomDAO;
import org.example.projet_emplois.dao.ScheduleDAO;
import org.example.projet_emplois.dao.TimeSlotDAO;
import org.example.projet_emplois.model.Course;
import org.example.projet_emplois.model.Room;
import org.example.projet_emplois.model.Schedule;
import org.example.projet_emplois.model.TimeSlot;

public class ScheduleTabController {

    @FXML private ComboBox<Course> courseComboBox;
    @FXML private ComboBox<Room> roomComboBox;
    @FXML private ComboBox<TimeSlot> timeslotComboBox;
    @FXML private TableView<Schedule> scheduleTable;
    @FXML private TableColumn<Schedule, String> courseColumn;
    @FXML private TableColumn<Schedule, String> roomColumn;
    @FXML private TableColumn<Schedule, String> dayColumn;
    @FXML private TableColumn<Schedule, String> dateColumn;
    @FXML private TableColumn<Schedule, String> timeColumn;
    @FXML private Label messageLabel;

    private final ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadComboBoxData();
        loadScheduleTable();

        courseColumn.setCellValueFactory(data -> data.getValue().courseNameProperty());
        roomColumn.setCellValueFactory(data -> data.getValue().roomNameProperty());
        dayColumn.setCellValueFactory(data -> data.getValue().dayProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty().asString());
        timeColumn.setCellValueFactory(data -> data.getValue().timeRangeProperty());

        scheduleTable.setItems(scheduleList);

        addDeleteButtonToTable();
    }

    private void loadComboBoxData() {
        courseComboBox.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
        roomComboBox.setItems(FXCollections.observableArrayList(RoomDAO.getAllRooms()));
        timeslotComboBox.setItems(FXCollections.observableArrayList(TimeSlotDAO.getAllTimeSlots()));
    }

    private void loadScheduleTable() {
        scheduleList.setAll(ScheduleDAO.getAllSchedules());
    }

    @FXML
    public void handleAddSchedule() {
        Course course = courseComboBox.getValue();
        Room room = roomComboBox.getValue();
        TimeSlot timeslot = timeslotComboBox.getValue();

        if (course == null || timeslot == null) {
            messageLabel.setText("Cours et créneau sont obligatoires.");
            return;
        }

        boolean added = ScheduleDAO.addSchedule(course.getId(), room != null ? room.getId() : null, timeslot.getId());

        if (added) {
            messageLabel.setText("✅ Cours planifié.");
            loadScheduleTable();
        } else {
            messageLabel.setText("❌ Erreur ou doublon.");
        }
    }

    private void addDeleteButtonToTable() {
        TableColumn<Schedule, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(90);

        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.getStyleClass().add("danger-button");
                deleteButton.setOnAction(event -> {
                    Schedule selected = getTableView().getItems().get(getIndex());
                    boolean deleted = ScheduleDAO.deleteSchedule(selected.getId());

                    if (deleted) {
                        messageLabel.setText("🗑Cours déplanifié.");
                        loadScheduleTable();
                    } else {
                        messageLabel.setText("Erreur lors de la suppression.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        scheduleTable.getColumns().add(actionColumn);
    }
}
