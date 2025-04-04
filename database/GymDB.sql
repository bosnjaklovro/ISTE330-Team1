CREATE DATABASE IF NOT EXISTS gymDB;
USE gymDB;


-- Drop existing tables if they exist (in reverse dependency order)
DROP TABLE IF EXISTS Feedback;
DROP TABLE IF EXISTS HealthMetric;
DROP TABLE IF EXISTS WorkoutPlan;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Attendance;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS Class;
DROP TABLE IF EXISTS Membership;
DROP TABLE IF EXISTS MembershipPlan;
DROP TABLE IF EXISTS Manager;
DROP TABLE IF EXISTS Trainer;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Equipment;
DROP TABLE IF EXISTS User;

-- Create new tables

-- 1. User Table
CREATE TABLE User (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Manager', 'Trainer', 'Member') NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Member Table
CREATE TABLE Member (
    MemberID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DOB DATE,
    Phone VARCHAR(20),
    Address TEXT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- 3. Trainer Table
CREATE TABLE Trainer (
    TrainerID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Phone VARCHAR(20),
    HireDate DATE,
    Certification TEXT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- 4. Manager Table
CREATE TABLE Manager (
    ManagerID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Phone VARCHAR(20),
    HireDate DATE,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- 5. MembershipPlan Table
CREATE TABLE MembershipPlan (
    PlanID INT PRIMARY KEY AUTO_INCREMENT,
    PlanName VARCHAR(100),
    Description TEXT,
    DurationMonths INT,
    Price DECIMAL(10, 2),
    MaxClasses INT
);

-- 6. Membership Table
CREATE TABLE Membership (
    MembershipID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    PlanID INT,
    StartDate DATE,
    EndDate DATE,
    IsActive BOOLEAN,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (PlanID) REFERENCES MembershipPlan(PlanID)
);

-- 7. Class Table -- mine
CREATE TABLE Class (
    ClassID INT PRIMARY KEY AUTO_INCREMENT,
    ClassName VARCHAR(100),
    Description TEXT,
    MaxParticipants INT,
    Duration INT
);

-- 8. Schedule Table -- mine
CREATE TABLE Schedule (
    ScheduleID INT PRIMARY KEY AUTO_INCREMENT,
    ClassID INT,
    TrainerID INT,
    StartDateTime DATETIME,
    EndDateTime DATETIME,
    Location VARCHAR(100),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);

-- 9. Booking Table -- mine
CREATE TABLE Booking (
    BookingID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    ScheduleID INT,
    BookingDate DATETIME,
    Status ENUM('Booked', 'Canceled'),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (ScheduleID) REFERENCES Schedule(ScheduleID)
);

-- 10. Attendance Table
CREATE TABLE Attendance (
    AttendanceID INT PRIMARY KEY AUTO_INCREMENT,
    BookingID INT,
    Status ENUM('Present', 'Absent'),
    FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);

-- 11. Equipment Table
CREATE TABLE Equipment (
    EquipmentID INT PRIMARY KEY AUTO_INCREMENT,
    EquipmentName VARCHAR(100),
    Description TEXT,
    PurchaseDate DATE,
    LastMaintenance DATE
);

-- 12. Payment Table
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    MembershipID INT,
    Amount DECIMAL(10,2),
    PaymentDate DATETIME,
    Method ENUM('Credit Card', 'Cash'),
    FOREIGN KEY (MembershipID) REFERENCES Membership(MembershipID)
);

-- 13. WorkoutPlan Table
CREATE TABLE WorkoutPlan (
    WorkoutPlanID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    TrainerID INT,
    PlanName VARCHAR(100),
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);

-- 14. HealthMetric Table
CREATE TABLE HealthMetric (
    HealthMetricID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    RecordDate DATE,
    Weight DECIMAL(5,2),
    Height DECIMAL(5,2),
    BodyFat DECIMAL(5,2),
    BloodPressure VARCHAR(20),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);

-- 15. Feedback Table
CREATE TABLE Feedback (
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    TrainerID INT,
    ClassID INT,
    Rating INT,
    Comment TEXT,
    Date DATETIME,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID)
);

-- inserting data --

INSERT INTO User (Username, Password, Role, Email) VALUES ('Luka', 'pass123', 'Member', 'luka@gym.com');
INSERT INTO User (Username, Password, Role, Email) VALUES ('Rob', 'pass123', 'Trainer', 'rob@gym.com');
INSERT INTO User (Username, Password, Role, Email) VALUES ('Tena', 'pass123', 'Manager', 'tena@gym.com');
INSERT INTO User (Username, Password, Role, Email) VALUES ('Kiril', 'pass123', 'Member', 'kiril@gym.com');
INSERT INTO User (Username, Password, Role, Email) VALUES ('Lovro', 'pass123', 'Trainer', 'lovro@gym.com');
INSERT INTO Member (UserID, FirstName, LastName, DOB, Phone, Address) VALUES (1, 'Luka', 'K', '1992-09-20', '+1-805-837-6064', '0075 Kelsey Mountains, Josephland, OK 94774');
INSERT INTO Member (UserID, FirstName, LastName, DOB, Phone, Address) VALUES (4, 'Kiril', 'K', '1995-04-02', '+1-314-355-3751', '76015 Cain Courts, West Gary, SD 67659');
INSERT INTO Trainer (UserID, FirstName, LastName, Phone, HireDate, Certification) VALUES (2, 'Rob', 'H', '194.940.9398x622', '2024-07-08', 'Certified Whatever Trainer');
INSERT INTO Trainer (UserID, FirstName, LastName, Phone, HireDate, Certification) VALUES (5, 'Lovro', 'B', '001-784-402-1173', '2025-01-01', 'Certified Above Trainer');
INSERT INTO Manager (UserID, FirstName, LastName, Phone, HireDate) VALUES (3, 'Tena', 'R', '001-515-516-8161x180', '2024-05-18');
INSERT INTO MembershipPlan (PlanName, Description, DurationMonths, Price, MaxClasses) VALUES ('Basic', 'Gym only', 3, 100.0, 0);
INSERT INTO MembershipPlan (PlanName, Description, DurationMonths, Price, MaxClasses) VALUES ('Standard', 'Gym + classes', 6, 200.0, 5);
INSERT INTO MembershipPlan (PlanName, Description, DurationMonths, Price, MaxClasses) VALUES ('Premium', 'All access', 12, 350.0, 999);
INSERT INTO Membership (MemberID, PlanID, StartDate, EndDate, IsActive) VALUES (1, 1, '2025-01-10', '2025-04-10', TRUE);
INSERT INTO Membership (MemberID, PlanID, StartDate, EndDate, IsActive) VALUES (2, 2, '2025-02-10', '2025-08-09', TRUE);
INSERT INTO Class (ClassName, Description, MaxParticipants, Duration) VALUES ('Yoga', 'Relaxing yoga', 15, 60);
INSERT INTO Class (ClassName, Description, MaxParticipants, Duration) VALUES ('HIIT', 'Intense training', 20, 45);
INSERT INTO Class (ClassName, Description, MaxParticipants, Duration) VALUES ('Pilates', 'Core workout', 10, 50);
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (1, 1, '2025-04-02 11:00:00', '2025-04-02 12:00:00', 'Room A');
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (1, 2, '2025-04-01 07:00:00', '2025-04-01 08:00:00', 'Room B');
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (2, 1, '2025-04-11 13:00:00', '2025-04-11 13:45:00', 'Room B');
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (2, 2, '2025-04-10 07:00:00', '2025-04-10 07:45:00', 'Room B');
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (3, 1, '2025-04-02 17:00:00', '2025-04-02 17:50:00', 'Room A');
INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES (3, 2, '2025-04-06 08:00:00', '2025-04-06 08:50:00', 'Room C');
INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES (1, 1, '2025-03-02 15:00:00', 'Booked');
INSERT INTO Attendance (BookingID, Status) VALUES (1, 'Present');
INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES (1, 3, '2025-03-15 19:00:00', 'Booked');
INSERT INTO Attendance (BookingID, Status) VALUES (2, 'Present');
INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES (2, 1, '2025-03-10 16:00:00', 'Booked');
INSERT INTO Attendance (BookingID, Status) VALUES (3, 'Absent');
INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES (2, 3, '2025-03-17 18:00:00', 'Booked');
INSERT INTO Attendance (BookingID, Status) VALUES (4, 'Absent');
INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES (2, 6, '2025-03-28 17:00:00', 'Canceled');
INSERT INTO Equipment (EquipmentName, Description, PurchaseDate, LastMaintenance) VALUES ('Treadmill', 'Almost indicate draw late.', '2023-07-26', '2025-04-03');
INSERT INTO Equipment (EquipmentName, Description, PurchaseDate, LastMaintenance) VALUES ('Bike', 'Cell course join kid.', '2024-02-20', '2025-04-03');
INSERT INTO Equipment (EquipmentName, Description, PurchaseDate, LastMaintenance) VALUES ('Dumbbells', 'Sound trip art everything.', '2024-03-08', '2025-04-03');
INSERT INTO Equipment (EquipmentName, Description, PurchaseDate, LastMaintenance) VALUES ('Rowing Machine', 'Impact president feel eight.', '2023-08-07', '2025-04-03');
INSERT INTO Payment (MembershipID, Amount, PaymentDate, Method) VALUES (1, 200.0, '2025-01-16 10:59:05', 'Cash');
INSERT INTO Payment (MembershipID, Amount, PaymentDate, Method) VALUES (2, 350.0, '2025-03-24 23:08:55', 'Cash');
INSERT INTO WorkoutPlan (MemberID, TrainerID, PlanName, StartDate, EndDate) VALUES (1, 1, 'Plan_1_1', '2025-04-01', '2025-05-31');
INSERT INTO WorkoutPlan (MemberID, TrainerID, PlanName, StartDate, EndDate) VALUES (2, 1, 'Plan_2_1', '2025-04-01', '2025-05-31');
INSERT INTO HealthMetric (MemberID, RecordDate, Weight, Height, BodyFat, BloodPressure) VALUES (1, '2025-04-03', 88.27, 183.35, 18.2, '110/90');
INSERT INTO HealthMetric (MemberID, RecordDate, Weight, Height, BodyFat, BloodPressure) VALUES (1, '2025-04-03', 75.25, 184.69, 10.73, '125/88');
INSERT INTO HealthMetric (MemberID, RecordDate, Weight, Height, BodyFat, BloodPressure) VALUES (2, '2025-04-03', 76.66, 175.67, 13.54, '125/71');
INSERT INTO HealthMetric (MemberID, RecordDate, Weight, Height, BodyFat, BloodPressure) VALUES (2, '2025-04-03', 87.52, 182.98, 14.37, '116/87');
INSERT INTO Feedback (MemberID, TrainerID, ClassID, Rating, Comment, Date) VALUES (1, 1, 3, 3, 'Training understand upon happen practice.', '2025-01-31 16:29:07');
INSERT INTO Feedback (MemberID, TrainerID, ClassID, Rating, Comment, Date) VALUES (2, 1, 3, 3, 'Over force tonight leg water matter decade.', '2025-01-10 01:22:23');

