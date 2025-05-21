-- USERS TABLE
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name TEXT NOT NULL,
                                     email TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     role TEXT NOT NULL CHECK (role IN ('admin', 'teacher', 'student'))
    );

-- ROOMS TABLE
CREATE TABLE IF NOT EXISTS rooms (
                                     id SERIAL PRIMARY KEY,
                                     name TEXT NOT NULL,
                                     capacity INTEGER NOT NULL
);

-- EQUIPMENT TABLE (types of equipment)
CREATE TABLE IF NOT EXISTS equipment (
                                         id SERIAL PRIMARY KEY,
                                         name TEXT NOT NULL UNIQUE
);

-- ROOM_EQUIPMENT TABLE (many-to-many with quantity)
CREATE TABLE IF NOT EXISTS room_equipment (
                                              room_id INTEGER REFERENCES rooms(id) ON DELETE CASCADE,
    equipment_id INTEGER REFERENCES equipment(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (room_id, equipment_id)
    );

-- COURSES TABLE
CREATE TABLE IF NOT EXISTS courses (
                                       id SERIAL PRIMARY KEY,
                                       name TEXT NOT NULL,
                                       type TEXT NOT NULL CHECK (type IN ('CM', 'TD', 'TP')),
    teacher_id INTEGER REFERENCES users(id) ON DELETE SET NULL
    );

-- TIME SLOTS TABLE
CREATE TABLE IF NOT EXISTS timeslots (
                                         id SERIAL PRIMARY KEY,
                                         day VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    CONSTRAINT unique_timeslot UNIQUE (date, start_time, end_time),
    CONSTRAINT valid_time_range CHECK (end_time > start_time)
    );

-- SCHEDULE TABLE
CREATE TABLE IF NOT EXISTS schedule (
                                        id SERIAL PRIMARY KEY,
                                        course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE,
    room_id INTEGER REFERENCES rooms(id) ON DELETE SET NULL,
    timeslot_id INTEGER REFERENCES timeslots(id) ON DELETE CASCADE,
    CONSTRAINT unique_room_timeslot UNIQUE (room_id, timeslot_id),
    CONSTRAINT unique_course_timeslot UNIQUE (course_id, timeslot_id)
    );

-- ENROLLMENT TABLE (students enrolled in courses)
CREATE TABLE IF NOT EXISTS enrollment (
                                          id SERIAL PRIMARY KEY,
                                          student_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT unique_enrollment UNIQUE (student_id, course_id)
    );

-- NOTIFICATIONS TABLE
CREATE TABLE IF NOT EXISTS notifications (
                                             id SERIAL PRIMARY KEY,
                                             user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
    );
