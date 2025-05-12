from zipfile import ZipFile
from pathlib import Path

# Define target path structure
base_path = Path("/mnt/data/model_classes/org/example/projet_emplois/model")
base_path.mkdir(parents=True, exist_ok=True)

# Define class content
classes = {
    "User.java": '''package org.example.projet_emplois.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;

    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
''',

    "Room.java": '''package org.example.projet_emplois.model;

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
''',

    "Course.java": '''package org.example.projet_emplois.model;

public class Course {
    private int id;
    private String name;
    private String type;
    private int teacherId;

    public Course(int id, String name, String type, int teacherId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getTeacherId() { return teacherId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
}
''',

    "TimeSlot.java": '''package org.example.projet_emplois.model;

public class TimeSlot {
    private int id;
    private String day;
    private String startTime;
    private String endTime;

    public TimeSlot(int id, String day, String startTime, String endTime) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public void setId(int id) { this.id = id; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
''',

    "Schedule.java": '''package org.example.projet_emplois.model;

public class Schedule {
    private int id;
    private int courseId;
    private int roomId;
    private int timeSlotId;

    public Schedule(int id, int courseId, int roomId, int timeSlotId) {
        this.id = id;
        this.courseId = courseId;
        this.roomId = roomId;
        this.timeSlotId = timeSlotId;
    }

    public int getId() { return id; }
    public int getCourseId() { return courseId; }
    public int getRoomId() { return roomId; }
    public int getTimeSlotId() { return timeSlotId; }

    public void setId(int id) { this.id = id; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setTimeSlotId(int timeSlotId) { this.timeSlotId = timeSlotId; }
}
''',

    "Enrollment.java": '''package org.example.projet_emplois.model;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;

    public Enrollment(int id, int studentId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }

    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
''',

    "Notification.java": '''package org.example.projet_emplois.model;

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
'''
}

# Write files
for filename, content in classes.items():
    with open(base_path / filename, "w", encoding="utf-8") as f:
        f.write(content)

# Create ZIP file
zip_path = "/mnt/data/model_classes.zip"
with ZipFile(zip_path, "w") as zipf:
    for file in base_path.glob("*.java"):
        zipf.write(file, arcname=file.relative_to(base_path.parent.parent.parent))

zip_path
