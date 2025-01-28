
INSERT INTO Book VALUES('B00000001',9781603095020, 'available', 'L-0-10-002');
INSERT INTO Book VALUES('B00000002',9781603094542,'unavailable', 'L-0-10-003');
INSERT INTO Book VALUES('B00000003',9781603094924,'available', 'L-1-10-001');
INSERT INTO Book VALUES('B00000004',9781603095136,'unavailable', 'L-0-20-003');
INSERT INTO Book VALUES('B00000005',9781668001226,'available', 'L-0-30-008');


INSERT INTO Book_Info VALUES(9781603095020,'Animal Stories','Maria Hoey&Peter Hoey','Children',19.99);
INSERT INTO Book_Info VALUES(9781603094542,'Cosmoknights','Hannah Templer','Graphic Novels & Manga',19.99);
INSERT INTO Book_Info VALUES(9781603094924,'The Delicacy','James Albon','Graphic Novels & Manga',24.99);
INSERT INTO Book_Info VALUES(9781668001226,'It Starts With Us','Colleen Hoover','Romance',16.99);
INSERT INTO Book_Info VALUES(9781603095136,'Doughnuts and Doom','Balazs Lorinczi','Teen & Young Adult Fiction',14.99);

INSERT INTO Loan_Record VALUES('R135GH89D', 'B00000002', 'C00000001', 20221027, null);
INSERT INTO Loan_Record VALUES('R167GM89S','B00000004','C00000002', 20221028, null);

INSERT INTO Customer VALUES('C00000001','Kristy He','1234567@gmail.com');
INSERT INTO Customer VALUES('C00000002','Sophy Dong','7654321@gmail.com');


INSERT INTO Employee VALUES('E001', 'Luka Narisawa','somepassword');
INSERT INTO Employee VALUES('E002','Jiakun Yang','someotherpassword');



