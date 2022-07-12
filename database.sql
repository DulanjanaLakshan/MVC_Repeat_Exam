DROP DATABASE IF EXISTS ijse;
CREATE DATABASE ijse;
use ijse;

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher
(
    teacher_id varchar(45) NOT NULL,
    name       varchar(45),
    nic        varchar(45),
    contact    varchar(45),
    address    TEXT,
    CONSTRAINT PRIMARY KEY (teacher_id)
    );
SHOW TABLES;
DESCRIBE Teacher;


DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject
(
    subject_id   varchar(45),
    subject_name varchar(45),
    credit       DOUBLE,
    teacher_id   varchar(45),
    CONSTRAINT PRIMARY KEY (subject_id),
    CONSTRAINT FOREIGN KEY (teacher_id) REFERENCES Teacher (teacher_id) on DELETE CASCADE ON UPDATE CASCADE
    );

DROP TABLE IF EXISTS Course;
Create TABLE IF NOT EXISTS Course
(
    course_id  varchar(45),
    course_nae varchar(45),
    cost       DOUBLE,
    duration   varchar(45),
    subject_id varchar(45),
    CONSTRAINT PRIMARY KEY (course_id),
    CONSTRAINT FOREIGN KEY (subject_id) REFERENCES Subject (subject_id) ON DELETE CASCADE ON UPDATE CASCADE
    );


DROP TABLE IF EXISTS intake;
CREATE TABLE IF NOT EXISTS intake
(
    intake_id   varchar(45),
    start_date  DATE,
    intakecol   varchar(45),
    description varchar(45),
    course_id   varchar(45),
    CONSTRAINT PRIMARY KEY (intake_id),
    CONSTRAINT FOREIGN KEY (course_id) REFERENCES Course (course_id) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE intake;

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student
(
    student_id   varchar(45) NOT NULL,
    student_name varchar(45),
    email        TEXT,
    contact      varchar(20),
    address      TEXT,
    nic          varchar(45),
    CONSTRAINT PRIMARY KEY (student_id)
    );
SHOW TABLES;
DESCRIBE Student;

DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration
(
    registration_id varchar(45) NOT NULL,
    reg_date        DATE,
    student_id      varchar(45) NOT NULL,
    intake_id       varchar(45) NOT NULL,
    CONSTRAINT PRIMARY KEY (registration_id),
    CONSTRAINT FOREIGN KEY (student_id) REFERENCES Student (student_id) on DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (intake_id) REFERENCES intake (intake_id) on DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Registration;

DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment
(
    payment_id      varchar(45),
    date            DATE,
    cost            DOUBLE,
    registration_id varchar(45),
    CONSTRAINT PRIMARY KEY (payment_id),
    CONSTRAINT FOREIGN KEY (registration_id) REFERENCES Registration (registration_id) ON DELETE CASCADE ON UPDATE CASCADE
    );

