-- Author: Roshun Jones
-- Desc: Creates the required tables and populates with data

--Create User Table
create table user(user_id int not null auto_increment,
first_name varchar(25), last_name varchar(25), username varchar(10),
password varchar(15),email_address varchar(50),
Primary Key(user_id))

--Create Conference Table

--Create Role Type Table
create table role_type(role_id int not null auto_increment, title varchar(15) not null, role_type int not null);

--Create UserRolePaperConference Table
create table user_role_paper_conference_join(user_id int not null, role_id int not null, paper_id int, conf_id int not null, primary key(user_id, role_id, conf_id), foreign key(user_id) references user, foreign key(role_id) references role_type, foreign key(paper_id) references paper, foreign key(conf_id) references conference);

--Populate with data
--Add Admin user
insert into user(first_name, last_name, username, password, email_address) values('AdminTest', 'AdminTest', 'AdminTest', 'AdminTest','AdminTest@uw.edu');