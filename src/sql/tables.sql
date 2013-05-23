-- Author: Roshun Jones
-- Desc: Creates the required tables and populates with data

--Create User Table
create table user(user_id int not null auto_increment,
first_name varchar(25), last_name varchar(25), username varchar(10),
password varchar(15),email_address varchar(50),
Primary Key(user_id))

--Create Conference Table
--Edits: Danielle
create table conference(
conf_id int not null, 
topic varchar(20) not null, 
conference_date Timestamp not null, 
submit_paper Timestamp not null, 
review_paper Timestamp not null,
final_decision Timestamp not null,
make_recommendation Timestamp not null,
revise_paper Timestamp not null, 
primary key(conf_id));

--Create Role Type Table
create table role_type(
role_id int not null auto_increment, 
title varchar(16) not null, 
role_type int not null);

--Create UserRolePaperConference Table
create table user_role_paper_conference_join(
user_id int not null, 
role_id int not null, 
paper_id int, 
conf_id int, 
primary key(user_id, role_id, conf_id), 
foreign key(user_id) references user, 
foreign key(role_id) references role_type, 
foreign key(paper_id) references paper, 
foreign key(conf_id) references conference);

--Populate with data
--Add Admin user
insert into user(first_name, last_name, username, password, email_address) values('AdminTest', 'AdminTest', 'AdminTest', 'AdminTest','AdminTest@uw.edu');
--Add general user
insert into user(first_name, last_name, username, password, email_address) values('Default', 'User', 'UserTest', 'UserTest', 'UserTest@uw.edu');

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


-- Create Paper Table
CREATE TABLE PAPER(
paper_id int not null auto_increment,
user_id int not null,
title varchar(100) not null,
keywords varchar(1000) not null,
abstract varchar(1000) not null,
cat_id int not null,
document_path varchar(50) not null,
revised_document_path varchar(50),
status varchar(36),
recomm_rating int,
recomm_comments varchar(1000),
active int,
PRIMARY KEY(paper_id),
FOREIGN KEY(user_id) REFERENCES user,
FOREIGN KEY(cat_id) REFERENCES category,
);

-- Create Review Table
CREATE TABLE REVIEW(
review_id int not null auto_increment,
user_id int not null,
cmmt_subpgrmchair varchar(1000),
summary_rating int not null,
summary_cmmt varchar(1000),
active int not null,
PRIMARY KEY(review_id),
FOREIGN KEY(user_id) REFERENCES user
);

-- Create Ratings_Comment_Type Table
CREATE TABLE RATING_COMMENT_TYPE(
review_id int not null,
question_id int not null,
rating int not null,
comment varchar(1000),
PRIMARY KEY(review_id, question_id),
FOREIGN KEY(review_id)
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