

-- 1. Create Database
CREATE DATABASE IF NOT EXISTS lms_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE lms_db;

-- 2. Create Tables

-- Admin / Authentication table
CREATE TABLE IF NOT EXISTS admin (
  admin_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password_hash VARCHAR(256) NOT NULL,
  email VARCHAR(150)
);

-- Books table
CREATE TABLE IF NOT EXISTS books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(150),
  publisher VARCHAR(150),
  quantity INT DEFAULT 0
);

-- Members table
CREATE TABLE IF NOT EXISTS members (
  member_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(150) UNIQUE,
  contact VARCHAR(50),
  department VARCHAR(100)
);

-- Issue & Return table
CREATE TABLE IF NOT EXISTS issue_books (
  issue_id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT NOT NULL,
  member_id INT NOT NULL,
  issue_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE,
  fine DECIMAL(10,2) DEFAULT 0,
  FOREIGN KEY (book_id) REFERENCES books(book_id),
  FOREIGN KEY (member_id) REFERENCES members(member_id)
);



It's optional you can run the upper given queries only

  
-- 3. Insert Default Admin User
-- Password “admin123” must be hashed in application.
INSERT INTO admin (username, password_hash, email)
VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a84b', 'admin@example.com');

-- Optional: Sample Books
INSERT INTO books (title, author, publisher, quantity)
VALUES 
('Introduction to Programming', 'John Doe', 'TechPress', 5),
('Effective Java', 'Joshua Bloch', 'Addison-Wesley', 3),
('Database Systems', 'Ramez Elmasri', 'Pearson', 4);

-- Optional: Sample Members
INSERT INTO members (name, email, contact, department)
VALUES
('Alice Johnson', 'alice.j@example.com', '9876543210', 'Computer Science'),
('Bob Lee', 'bob.lee@example.com', '9123456780', 'Information Technology');

-- Optional: Sample Issue entries
INSERT INTO issue_books (book_id, member_id, issue_date, due_date)
VALUES
(1, 1, '2026-01-01', '2026-01-15'),
(2, 2, '2026-01-02', '2026-01-16');

-- End of SQL Script
