Project Overview

The Library Management System is a desktop application developed using Java (Swing GUI) and MySQL to help automate the daily operations of a library. It provides an intuitive graphical interface for managing books, members, and book issue/return transactions, while securely handling user authentication.

Features

User Authentication: Sign Up, Login, and Forgot Password functionality.

Book Management: Add, update, delete, and view books.

Member Management: Add, update, delete, and view library members.

Issue & Return: Issue books to members, set due dates, record returns, and calculate fines.

Database Integration: Persistent storage of all records using MySQL.

Technologies Used

Java (Swing for GUI)

JDBC for database connectivity

MySQL for data storage

How to Run

Import the project into IntelliJ IDEA.

Add the MySQL Connector/J JAR to the project Libraries.

Create the lms_db database and tables using the provided SQL script.

Update the database credentials in DBConnection.java.

Run the application by executing the LoginFrame class.
