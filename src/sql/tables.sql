-- Author: Roshun Jones
-- Desc: Creates the required tables and populates with data

--Create User Table
create table user(user_id int not null auto_increment,
first_name varchar(25), last_name varchar(25), username varchar(10),
password varchar(15),email_address varchar(50),
Primary Key(user_id))

--Create Conference Table
create table conference(conf_id int not null auto_increment, topic varchar(20) not null, conference_date Timestamp not null, author_sub_deadline Timestamp not null, reviewer_sub_deadline Timestamp not null, author_notification_deadline Timestamp not null, primary key(conf_id));

--Create Role Type Table
create table role_type(role_id int not null auto_increment, title varchar(15) not null, role_type int not null);

--Create UserRolePaperConference Table
create table user_role_paper_conference_join(user_id int not null, role_id int not null, paper_id int, conf_id int, primary key(user_id, role_id, conf_id), foreign key(user_id) references user, foreign key(role_id) references role_type, foreign key(paper_id) references paper, foreign key(conf_id) references conference);

--Populate with data
--Add Admin user
insert into user(first_name, last_name, username, password, email_address) values('AdminTest', 'AdminTest', 'AdminTest', 'AdminTest','AdminTest@uw.edu');

--Author: Danielle
--Create Roles
insert into role_type(title, role_type) values ('User', 0);
insert into role_type(title, role_type) values ('Administrator', 1);
insert into role_type(title, role_type) values ('Author' , 2);
insert into role_type(title, role_type) values ('Reviewer', 3);
insert into role_type(title, role_type) values ('SubProgram Chair' ,4);
insert into role_type(title, role_type) values ('Program Chair', 5);


--Link User to Roles

-- Create Categories
INSERT INTO category(display) values ('Curriculum');
INSERT INTO category(display) values ('Software');
INSERT INTO category(display) values ('Hardware');
INSERT INTO category(display) values ('Other');

--
-- Author: Danielle Tucker
--
-- Create Category Table
CREATE TABLE CATEGORY( 
cat_id int not null auto_increment,
display varchar(20) not null,
PRIMARY KEY(cat_id));

-- Create ConferenceCategory Table 
--(links avaliable categories with conferences)
CREATE TABLE CONFERENCE_CATEGORY(
conf_id int not null,
cat_id int not null,
PRIMARY KEY(conf_id, cat_id),
FOREIGN KEY(conf_id) REFERENCES conference,
FOREIGN KEY(cat_id) REFERENCES category
);

-- Create Recommendation Table
CREATE TABLE RECOMMENDATION(
recomm_id int not null auto_increment,
user_id int not null,
rating int not null,
comments varchar(1000),
active int,
PRIMARY KEY(recomm_id),
FOREIGN KEY(user_id) REFERENCES user,
);

-- Create Paper Table
CREATE TABLE PAPER(
paper_id int not null auto_increment,
user_id int not null,
title varchar(50) not null,
keywords varchar(1000) not null,
cat_id int not null,
document_path varchar(50) not null,
revised_document_path varchar(50),
recomm_id int,
status varchar(36),
active int,
PRIMARY KEY(paper_id),
FOREIGN KEY(user_id) REFERENCES user,
FOREIGN KEY(cat_id) REFERENCES category,
FOREIGN KEY(recomm_id) REFERENCES recommendation,
FOREIGN KEY(cat_id) REFERENCES category,
);

-- Create Review Table
CREATE TABLE REVIEW(
review_id int not null auto_increment,
user_id int not null,
cmmt_subpgrmchair varchar(1000),
q1_rating int not null,
q1_cmmt varchar(1000),
q2_rating int not null,
q2_cmmt varchar(1000),
q3_rating int not null,
q3_cmmt varchar(1000),
q4_rating int not null,
q4_cmmt varchar(1000),
q5_rating int not null,
q5_cmmt varchar(1000),
q6_rating int not null,
q6_cmmt varchar(1000),
q7_rating int not null,
q7_cmmt varchar(1000),
q8_rating int not null,
q8_cmmt varchar(1000),
q9_rating int not null,
q9_cmmt varchar(1000),
summary_rating int not null,
summary_cmmt varchar(1000),
active int not null,
PRIMARY KEY(review_id),
FOREIGN KEY(user_id) REFERENCES user
);

-- Create PaperReview Table
-- (links together the multiple reviews with the papers)
CREATE TABLE PAPER_REVIEW(
paper_id int not null,
review_id int not null,
PRIMARY KEY(paper_id, review_id),
FOREIGN KEY(paper_id) REFERENCES paper,
FOREIGN KEY(review_id) REFERENCES review
);