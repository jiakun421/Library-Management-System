drop table Book;
drop table Book_Info;
drop table Loan_Record;
drop table Customer;
drop table Employee;


CREATE TABLE Book(
   book_id varchar(50) PRIMARY KEY,
   ISBN bigint,
   status varchar(50),
   location_id varchar(50)
);

CREATE TABLE Book_Info(
   ISBN bigint PRIMARY KEY,
   title varchar(50),
   author varchar(50),
   genre varchar(50),
   price float
);

CREATE TABLE Loan_Record(
    record_id varchar(50) PRIMARY KEY,
    book_id varchar(50),
    customer_id varchar(50),
    borrow_date DATE,
    return_date DATE
);


CREATE TABLE Customer(
   customer_id varchar(50) PRIMARY KEY,
   name varchar(50),
   email varchar(50)
);

CREATE TABLE Employee(
   employee_id varchar(50) PRIMARY KEY,
   name varchar(50),
   password varchar(50)
);







