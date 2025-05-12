-- USERS TABLE
create table if not exists users (
                                     id serial primary key,
                                     name text not null,
                                     email text not null unique,
                                     password text not null,
                                     role text not null check (role in ('admin', 'teacher', 'student'))
    );

-- ROOMS TABLE
create table if not exists rooms (
                                     id serial primary key,
                                     name text not null,
                                     capacity integer not null,
                                     equipment text[]  -- e.g., ['projector', 'whiteboard']
);

-- COURSES TABLE
create table if not exists courses (
                                       id serial primary key,
                                       name text not null,
                                       type text not null check (type in ('CM', 'TD', 'TP')),
    teacher_id integer references users(id) on delete set null
    );

-- TIME SLOTS TABLE
create table if not exists timeslots (
                                         id serial primary key,
                                         day varchar(10) not null, -- e.g., Monday
    start_time time not null,
    end_time time not null
    );

-- SCHEDULE TABLE (Emploi du Temps)
create table if not exists schedule (
                                        id serial primary key,
                                        course_id integer references courses(id) on delete cascade,
    room_id integer references rooms(id) on delete set null,
    timeslot_id integer references timeslots(id) on delete cascade
    );

-- ENROLLMENT TABLE (students enrolled in courses)
create table if not exists enrollment (
                                          id serial primary key,
                                          student_id integer references users(id) on delete cascade,
    course_id integer references courses(id) on delete cascade
    );

-- NOTIFICATIONS TABLE
create table if not exists notifications (
                                             id serial primary key,
                                             user_id integer references users(id) on delete cascade,
    message text not null,
    created_at timestamp default now()
    );
