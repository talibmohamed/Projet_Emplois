package org.example.projet_emplois.controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import org.example.projet_emplois.dao.StudentDAO;
import org.example.projet_emplois.model.Schedule;
import org.example.projet_emplois.model.Student;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentDashboardController {

    private Student student;

    @FXML private Label welcomeLabel;
    @FXML private GridPane calendarGrid;
    @FXML private ComboBox<LocalDate> weekComboBox;

    private final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
    private final int startHour = 8;
    private final int endHour = 20;
    private final int intervalMinutes = 5;

    public void setStudent(Student student) {
        this.student = student;
    }

    @FXML
    public void initialize() {
        if (student != null) {
            setupDashboard();
        }
    }

    public void setupDashboard() {
        welcomeLabel.setText("Bienvenue, " + student.getName());
        drawGridSkeleton();
        populateWeeks();
        loadScheduleForSelectedWeek();
    }

    private void drawGridSkeleton() {
        calendarGrid.getChildren().clear();
        calendarGrid.getRowConstraints().clear();
        calendarGrid.getColumnConstraints().clear();

        int rowsPerHour = 60 / intervalMinutes;
        int totalRows = (endHour - startHour) * rowsPerHour;

        double targetHeight = 700;  // Fixed space to fit on screen
        double rowHeight = targetHeight / totalRows;

        calendarGrid.setPrefHeight(targetHeight);

        for (int i = 0; i <= totalRows; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(rowHeight);
            rc.setMinHeight(rowHeight);
            rc.setMaxHeight(rowHeight);
            calendarGrid.getRowConstraints().add(rc);
        }

        for (int i = 0; i <= days.length; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / (days.length + 1));
            calendarGrid.getColumnConstraints().add(cc);
        }

        for (int col = 0; col < days.length; col++) {
            Label dayLabel = new Label(days[col]);
            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            calendarGrid.add(dayLabel, col + 1, 0);
        }

        for (int i = 0; i <= totalRows; i++) {
            int totalMinutes = i * intervalMinutes;
            int hour = startHour + totalMinutes / 60;
            int minute = totalMinutes % 60;

            if (minute == 0) {
                Label timeLabel = new Label(String.format("%02d:00", hour));
                timeLabel.setStyle("-fx-font-weight: bold; -fx-padding: 2;");
                calendarGrid.add(timeLabel, 0, i + 1);

                for (int col = 1; col <= days.length; col++) {
                    Pane line = new Pane();
                    line.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");
                    calendarGrid.add(line, col, i + 1);
                }
            }
        }

        drawCurrentTimeLine();
    }

    private void drawCurrentTimeLine() {
        LocalDate today = LocalDate.now();
        LocalDate selectedWeek = weekComboBox.getValue();
        if (selectedWeek == null || today.isBefore(selectedWeek) || today.isAfter(selectedWeek.plusDays(6))) return;

        int col = getColumnFromDay(today.getDayOfWeek().toString().toLowerCase());
        int row = getRowIndex(LocalTime.now());

        if (col >= 1 && row >= 1 && row <= (endHour - startHour) * (60 / intervalMinutes) + 1) {
            Pane line = new Pane();
            line.setStyle("-fx-background-color: red;");
            line.setPrefHeight(2);
            GridPane.setColumnSpan(line, 1);
            calendarGrid.add(line, col, row);
        }
    }

    private void populateWeeks() {
        LocalDate start = LocalDate.of(2024, 8, 26);
        for (int i = 0; i < 52; i++) {
            weekComboBox.getItems().add(start.plusWeeks(i));
        }

        weekComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(LocalDate date) {
                return "Semaine du " + date.format(DateTimeFormatter.ofPattern("dd MMM"));
            }

            @Override public LocalDate fromString(String string) {
                return null;
            }
        });

        weekComboBox.getSelectionModel().select(LocalDate.now().with(DayOfWeek.MONDAY));
    }

    @FXML
    private void onWeekChanged() {
        drawGridSkeleton();
        loadScheduleForSelectedWeek();
    }

    @FXML
    private void onTodayClicked() {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        weekComboBox.getSelectionModel().select(monday);
    }

    private void loadScheduleForSelectedWeek() {
        if (student == null || weekComboBox.getValue() == null) return;

        LocalDate weekStart = weekComboBox.getValue();
        List<Schedule> entries = StudentDAO.getScheduleForWeek(student.getId(), weekStart);

        for (Schedule entry : entries) {
            int col = getColumnFromDay(entry.getDay());
            int rowStart = getRowIndex(entry.getStartTime());
            int rowEnd = getRowIndex(entry.getEndTime());
            int span = Math.max(rowEnd - rowStart, 1);

            VBox block = createCourseBlock(entry);
            calendarGrid.add(block, col, rowStart);
            GridPane.setRowSpan(block, span);
        }
    }

    private VBox createCourseBlock(Schedule entry) {
        VBox box = new VBox(2);
        box.setStyle("-fx-background-color: #cce5ff; -fx-background-radius: 5; -fx-padding: 4;");

        if (isCourseNow(entry)) {
            box.setStyle("-fx-background-color: #ffd8b1; -fx-border-color: #ff6600; -fx-border-width: 2; -fx-background-radius: 6;");
        }

        String teacher = entry.getTeacher() != null ? entry.getTeacher() : "—";

        box.getChildren().addAll(
                new Label(entry.getCourseName()),
                new Label("Heure: " + entry.timeRangeProperty().get()),
                new Label("Salle: " + entry.getRoomName()),
                new Label("Prof: " + entry.getTeacherName())
        );
        return box;
    }

    private boolean isCourseNow(Schedule entry) {
        LocalDate today = LocalDate.now();
        if (!entry.getDate().equals(today)) return false;

        LocalTime now = LocalTime.now();
        return !now.isBefore(entry.getStartTime()) && now.isBefore(entry.getEndTime());
    }

    private int getColumnFromDay(String day) {
        return switch (day.toLowerCase()) {
            case "lundi" -> 1;
            case "mardi" -> 2;
            case "mercredi" -> 3;
            case "jeudi" -> 4;
            case "vendredi" -> 5;
            case "samedi" -> 6;
            default -> 0;
        };
    }

    private int getRowIndex(LocalTime time) {
        int minutes = (time.getHour() - startHour) * 60 + time.getMinute();
        return (minutes / intervalMinutes) + 1;
    }
}
