use TAXI;
set sql_safe_updates = 0;

create table USER_DETAIL(USER_ID int not null auto_increment, USERNAME varchar(100), MAIL varchar(100) unique, CONTACT int(11), ROLE varchar(10), PASS varchar(50), CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(USER_ID));  
create table DRIVER_DETAIL(DRIVER_ID int not null auto_increment, USER_ID int, LICENCE_NUMBER varchar(100) unique not null, DRIVER_STATUS varchar(15) default 'available', CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(DRIVER_ID), foreign key(USER_ID) references USER_DETAIL(USER_ID));
create table CAB_MODEL(MODEL_ID int not null auto_increment, MODEL_NAME varchar(50), MODEL_DESCRIPTION text, NUMBER_OF_SEATS int, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(MODEL_ID));
create table CAB_DETAIL(CAB_ID int not null auto_increment, LICENCE_PLATE varchar(100), CAB_STATUS varchar(15) default 'available', LATITUDE float, LONGITUDE float, MODEL_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(CAB_ID), foreign key(MODEL_ID) references CAB_MODEL(MODEL_ID));
create table CAB_DRIVER(CAB_DRIVER_ID int not null auto_increment, DRIVER_ID int, CAB_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(CAB_DRIVER_ID), foreign key(DRIVER_ID) references DRIVER_DETAIL(DRIVER_ID), foreign key(CAB_ID) references CAB_DETAIL(CAB_ID));
create table ADDRESS_DETAIL(ADDRESS_ID int not null auto_increment, STREET_ADDRESS text, CITY varchar(100), ZIP_CODE int(7), CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(ADDRESS_ID));
create table ROUTE_DETAIL(ROUTE_ID int not null auto_increment, SOURCE_ADDRESS_ID int, DESTINATION_ADDRESS_ID int, DISTANCE float, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(ROUTE_ID), foreign key(SOURCE_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID), foreign key(DESTINATION_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID));
create table CAB_STATUS(STATUS_ID int not null auto_increment, STATUS_NAME varchar(50), CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(STATUS_ID));
create table CAB_RIDE_DETAIL(CAB_RIDE_ID int not null auto_increment, USER_ID int, DRIVER_ID int, START_TIME varchar(50), END_TIME varchar(50), SOURCE_ADDRESS_ID int, DESTINATION_ADDRESS_ID int, CAB_ID int not null, PRICE float, STATUS_ID int, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(CAB_RIDE_ID), foreign key(DRIVER_ID) references DRIVER_DETAIL(DRIVER_ID), foreign key(SOURCE_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID), foreign key(DESTINATION_ADDRESS_ID) references ADDRESS_DETAIL(ADDRESS_ID), foreign key(CAB_ID) references CAB_DETAIL(CAB_ID), foreign key(STATUS_ID) references CAB_STATUS(STATUS_ID), foreign key(USER_ID) references USER_DETAIL(USER_ID));
create table RATING(RATING_ID int not null auto_increment, CAB_RIDE_ID int, RATING float, CREATED_AT timestamp default current_timestamp, CREATED_BY varchar(100) default 'Abishaik', UPDATED_AT timestamp default current_timestamp, UPDATED_BY varchar(100) default 'Abishaik', primary key(RATING_ID), foreign key(CAB_RIDE_ID) references CAB_RIDE_DETAIL(CAB_RIDE_ID)); 


drop table CAB_DRIVER;
drop table ROUTE_DETAIL;
drop table RATING;
drop table CAB_STATUS;
drop table CAB_RIDE_DETAIL;
drop table DRIVER_DETAIL;
drop table USER_DETAIL;
drop table CAB_DETAIL;
drop table CAB_MODEL;
drop table ADDRESS_DETAIL;


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





















insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Kerela', 123456, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Assam', 654321, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Jaipur', 789098, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Chennai', 234567, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'West Bengal', 789043, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Mumbai', 567890, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Delhi', 765432, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Lucknow', 098765, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Ahmedabad', 432123, 'Statue of Liberty');
insert into ADDRESS_DETAIL(STREET_ADDRESS, CITY, ZIP_CODE, LANDMARK) values('3/H, Houston Park, Canadian Street', 'Orrisa', 454532, 'Statue of Liberty');


insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(1, 2, 130); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(2, 3, 100); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(3, 4, 150); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(4, 5, 90); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(5, 6, 180); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(6, 7, 200); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(7, 8, 400); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(8, 9, 250); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(9, 10, 190); 
insert into ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) values(10, 1, 300); 


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


insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Benz', 'SUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('WagonR', 'MUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Audi', 'HUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Ferrari', 'SUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Volkswagen', 'MUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Swift', 'HUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Ritz', 'LUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Nano', 'LUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('BMW', 'HUV');
insert into CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION) values('Hummer', 'SUV');


insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('TN-AD19-1018', 'Available', 1);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('PY-SO11-8542', 'Available', 2);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('KA-MN15-4939', 'Available', 3);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('DL-PO77-0912', 'Available', 4);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('JK-QM56-4365', 'Available', 5);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('AS-WE45-1234', 'Available', 6);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('TY-HJ89-2345', 'Available', 7);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('WE-DF42-3456', 'Available', 8);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('FG-PO21-4567', 'Available', 9);
insert into CAB_DETAIL(LICENCE_PLATE, CAB_STATUS, MODEL_ID) values('IO-CB76-5678', 'Available', 10);


insert into CUSTOMER_DETAIL(FIRST_NAME, LAST_NAME, MAIL) values('yo', 'mo', 'bo@gmail.com');
delete from CAB_RIDE_DETAIL where CUSTOMER_ID=1;








