# CS348-Group-Project

# Library Management System

This project is a SQL-based Library Management System that manages books, customers, employees, and loan records. It provides essential functionalities to manage library operations, including borrowing and returning books, and tracking book availability using unique IDs. 

## Features

- **Database Schema**: Designed and implemented relational tables for:
  - `Book`: Tracks book IDs, ISBN, status, and location.
  - `Book_Info`: Stores book details like title, author, genre, and price.
  - `Loan_Record`: Tracks loan transactions including borrow and return dates.
  - `Customer`: Manages customer information.
  - `Employee`: Manages employee accounts with credentials.
- **Data Management**:
  - Populated tables with sample data using SQL `INSERT` queries.
  - Enforced primary key constraints to ensure data integrity.
- **Core Functionalities**:
  - Users can log in to borrow and return books.
  - Book availability is tracked in real time using book IDs.
  - Employees and customers are stored in a secure database.

## Technologies Used

- **SQL**: For creating, managing, and querying the database.
- **Google Geocoding API**: (if applicable in the expanded version) Used for additional address or geolocation-related features.
- **Figma**: For designing potential front-end interfaces (optional).

## How to Use

1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/YourUsername/library-management-system.git
