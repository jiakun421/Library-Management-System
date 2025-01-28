DELIMITER //
CREATE PROCEDURE searchLoan (IN startDate DATE, endDate DATE)
    BEGIN 
        DECLARE count INT;
        DECLARE temp  INT DEFAULT 0;
        
		DROP TABLE IF EXISTS result;
        CREATE TABLE result (
		  record_id varchar(50) PRIMARY KEY,
          book_id varchar(50),
          customer_id varchar(50),
          borrow_date DATE,
          return_date DATE
        );
        
        SELECT DATEDIFF(endDate, startDate) INTO count;
        WHILE temp <= count DO
		   INSERT INTO result
           (SELECT * FROM Loan_Record WHERE borrow_date = (SELECT DATE_SUB(startDate, INTERVAL -temp DAY)));
           SET temp = temp + 1;
		END WHILE;
	 END //
DELIMITER ;
