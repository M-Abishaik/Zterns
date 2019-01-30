use TAXI;
show tables;
set sql_safe_updates = 0;

create table USER_DETAIL(USER_ID int not null auto_increment, USERNAME varchar(100), MAIL varchar(100), CONTACT varchar(11) unique, ROLE varchar(10), PASS varchar(50), CREATED_AT timestamp default current_timestamp, CREATED_BY int default 0, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int default 0, primary key(USER_ID));  
create table DRIVER_DETAIL(DRIVER_ID int not null auto_increment, USER_ID int, LICENCE_NUMBER varchar(100) unique not null, DRIVER_STATUS varchar(15) default 'available', CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(DRIVER_ID), foreign key(USER_ID) references USER_DETAIL(USER_ID));
create table CAB_MODEL(MODEL_ID int not null auto_increment, MODEL_NAME varchar(50), MODEL_DESCRIPTION text, NUMBER_OF_SEATS int, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(MODEL_ID));
create table CAB_DETAIL(CAB_ID int not null auto_increment, LICENCE_PLATE varchar(100), CAB_STATUS varchar(15) default 'available', LATITUDE float, LONGITUDE float, MODEL_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(CAB_ID), foreign key(MODEL_ID) references CAB_MODEL(MODEL_ID));
create table CAB_DRIVER(CAB_DRIVER_ID int not null auto_increment, DRIVER_ID int, CAB_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(CAB_DRIVER_ID));
create table ADDRESS_DETAIL(ADDRESS_ID int not null auto_increment, USER_ID int, STREET_ADDRESS text, CITY varchar(100), ZIP_CODE int(7), CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(ADDRESS_ID), foreign key(USER_ID) references USER_DETAIL(USER_ID));
create table ROUTE_DETAIL(ROUTE_ID int not null auto_increment, SOURCE_ADDRESS_ID int, DESTINATION_ADDRESS_ID int, DISTANCE float, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(ROUTE_ID));
create table CAB_STATUS(STATUS_ID int not null auto_increment, STATUS_NAME varchar(50), CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(STATUS_ID));
create table CAB_RIDE_DETAIL(CAB_RIDE_ID int not null auto_increment, USER_ID int, DRIVER_ID int, START_TIME varchar(50), END_TIME varchar(50), SOURCE_ADDRESS_ID int, DESTINATION_ADDRESS_ID int, CAB_ID int not null, PRICE float, STATUS_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(CAB_RIDE_ID), foreign key(DRIVER_ID) references DRIVER_DETAIL(DRIVER_ID), foreign key(SOURCE_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID), foreign key(DESTINATION_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID), foreign key(CAB_ID) references CAB_DETAIL(CAB_ID), foreign key(STATUS_ID) references CAB_STATUS(STATUS_ID), foreign key(USER_ID) references USER_DETAIL(USER_ID));
create table RATING(RATING_ID int not null auto_increment, CAB_RIDE_ID int, RATING float, CREATED_AT timestamp default current_timestamp, CREATED_BY int, UPDATED_AT timestamp default current_timestamp, UPDATED_BY int, primary key(RATING_ID), foreign key(CAB_RIDE_ID) references CAB_RIDE_DETAIL(CAB_RIDE_ID)); 


insert into CAB_STATUS(STATUS_NAME, CREATED_BY, UPDATED_BY) values('Ride booked', 1,1);
insert into CAB_STATUS(STATUS_NAME, CREATED_BY, UPDATED_BY) values('Ride started', 1,1);
insert into CAB_STATUS(STATUS_NAME, CREATED_BY, UPDATED_BY) values('Ride completed', 1,1);
insert into CAB_STATUS(STATUS_NAME, CREATED_BY, UPDATED_BY) values('Ride cancelled', 1,1);
insert into CAB_STATUS(STATUS_NAME, CREATED_BY, UPDATED_BY) values('Ride not booked', 1,1);

insert into CAB_DRIVER(DRIVER_ID, CAB_ID, CREATED_BY, UPDATED_BY) values(1,2,3,4);

select * from CAB_DRIVER;
select * from ROUTE_DETAIL;
select * from RATING;
select * from CAB_STATUS;
select * from CAB_RIDE_DETAIL;	
select * from DRIVER_DETAIL;
select * from USER_DETAIL;
select * from CAB_DETAIL;
select * from CAB_MODEL;
select * from ADDRESS_DETAIL;

delete from CAB_DETAIL;

drop table CAB_DRIVER;
drop table ROUTE_DETAIL;
drop table RATING;
drop table CAB_RIDE_DETAIL;
drop table DRIVER_DETAIL;
drop table CAB_DETAIL;
drop table CAB_MODEL;
drop table ADDRESS_DETAIL;
drop table CAB_STATUS;
drop table USER_DETAIL;


DESC CAB_DRIVER;
DESC ROUTE_DETAIL;
DESC RATING;
DESC CAB_STATUS;
DESC CAB_RIDE_DETAIL;
DESC DRIVER_DETAIL;
DESC USER_DETAIL;
DESC CAB_DETAIL;
DESC CAB_MODEL;
DESC ADDRESS_DETAIL;


INSERT INTO USER_DETAIL(USERNAME, MAIL, CONTACT, ROLE, PASS) VALUES('Abishaik','m.abi1@gmail.com','1234567890', 'customer', 'Abishaik123');

insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(1, 2, 130.25); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(2, 3, 100.50); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(3, 4, 150); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(4, 5, 90.10); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(5, 1, 180); 


insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Benz', 'SUV', 4);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('WagonR', 'MUV', 5);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Audi', 'HUV', 4);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Ferrari', 'SUV', 5);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Volkswagen', 'MUV', 6);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Swift', 'HUV', 3);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Ritz', 'LUV', 4);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Nano', 'LUV', 5);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('BMW', 'HUV', 6);
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS) values('Hummer', 'SUV', 3);



insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('TN-AD19-1018', 100.25, 200.12, 1);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('PY-SO11-8542', 89.76, 300.26, 2);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('KA-MN15-4939', 150.1, 900.50, 3);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('DL-PO77-0912', 50.12, 76.23, 4);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('JK-QM56-4365', 350.25, 189.28, 5);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('AS-WE45-1234', 500.30, 677.12, 6);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('TY-HJ89-2345', 950, 370, 7);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('WE-DF42-3456', 691.23, 567.89, 8);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('FG-PO21-4567', 789.12, 899.12, 9);
insert into CAB_DETAIL(LICENCE_PLATE, LATITUDE, LONGITUDE, MODEL_ID) values('IO-CB76-5678', 322, 900, 10);






















insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, lions club, t. nagar', 'chennai', 123456);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('4/H, railway station, tambaram', 'chennai', 654321);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('5/H, toll gate, perungudi', 'chennai', 789098);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('6/H, anna university, guindy', 'chennai', 234567);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('7/H, houston park, canadian street', 'west bengal', 789043);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('8/H, gateway of india, ram nagar', 'mumbai', 567890);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('9/H, temple, naive street', 'vellore', 765432);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('10/H, periyampet, central railway station', 'chennai', 098765);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('11/H, tidal park, new nagar', 'bangalore', 432123);
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('12/H, cmda, choolaimedu', 'chennai', 454532);


insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Tom', 'Jerry', 'QWERTY2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Mike', 'Thomas', 'YUIOPT2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Steve', 'Smith', 'ASDFGH2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Mitchell', 'Johnson', 'LKJHGF2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Michael', 'Starc', 'ZXCVBN2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Ishanth', 'Sharma', 'KAJSUE2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Shane', 'Watson', 'LPOMNB2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Tim', 'Paine', 'ALSKDJ2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Morgan', 'Smith', 'QPWOEI2019', 'Available');
insert into DRIVER_DETAIL(FIRST_NAME, LAST_NAME, LICENCE_NUMBER, DRIVER_STATUS) values('Kevin', 'Pollard', 'ZMXNCB2019', 'Available');









insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(6, 7, 200); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(7, 8, 400); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(8, 9, 250); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(9, 10, 190); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(10, 1, 300); 

insert into CUSTOMER_DETAIL(FIRST_NAME, LAST_NAME, MAIL) values('yo', 'mo', 'bo@gmail.com');
delete from CAB_RIDE_DETAIL where CUSTOMER_ID=1;








