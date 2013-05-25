-- Author: Roshun Jones and Danielle Tucker
-- Desc: Creates the required tables and populates with data

--Create User Table
-- Author:Roshun
create table user(user_id int not null auto_increment,
first_name varchar(25), last_name varchar(25), username varchar(25) unique,
password varchar(15),email_address varchar(50),
Primary Key(user_id));

--Create Conference Table
--Author: Roshun Edits: Danielle
create table conference(
conf_id int not null auto_increment, 
topic varchar(128) not null, 
conference_date Timestamp not null, 
submit_paper Timestamp not null, 
review_paper Timestamp not null,
final_decision Timestamp not null,
make_recommendation Timestamp not null,
revise_paper Timestamp not null, 
primary key(conf_id));

--Create Role Type Table
--Author: Roshun  Edits:Danielle
--might not need...
create table role_type(
role_id int not null unique,
role_type varchar(16) not null, 
primary key(role_id)
);

-- Create Category Table
-- Author: Danielle
CREATE TABLE CATEGORY( 
cat_id int not null auto_increment,
display varchar(20) not null,
PRIMARY KEY(cat_id));

-- Create Paper Table
-- Author:Danielle
CREATE TABLE PAPER(
paper_id int not null auto_increment,
author_id int not null,
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
FOREIGN KEY(author_id) REFERENCES user(user_id),
FOREIGN KEY(cat_id) REFERENCES category(cat_id),
);

--Create UserRolePaperConference Table
--Author: Roshun   Edits: Danielle
create table user_role_paper_conference_join(
user_id int not null, 
role_id int not null, 
paper_id int, 
conf_id int, 
unique(user_id, role_id, conf_id),
foreign key(user_id) REFERENCES user(user_id),
foreign key(role_id) REFERENCES role_type(role_id),
foreign key(paper_id) REFERENCES paper(paper_id),
foreign key(conf_id) REFERENCES conference(conf_id)
);

-- Create ConferenceCategory Table 
--(links avaliable categories with conferences)
-- Author: Danielle
CREATE TABLE CONFERENCE_CATEGORY(
conf_id int not null,
cat_id int not null,
PRIMARY KEY(conf_id, cat_id),
FOREIGN KEY(conf_id) REFERENCES conference(conf_id),
FOREIGN KEY(cat_id) REFERENCES category(cat_id)
);


-- Create Review Table
CREATE TABLE REVIEW(
review_id int not null auto_increment,
paper_id int not null,
reviewer_id int not null,
cmmt_subpgrmchair varchar(1000),
summary_rating int not null,
summary_cmmt varchar(1000),
active int not null,
PRIMARY KEY(review_id),
FOREIGN KEY(paper_id) REFERENCES paper(paper_id),
FOREIGN KEY(reviewer_id) REFERENCES user(user_id)
);

-- Create Ratings_Comment Table
CREATE TABLE RATING_COMMENT(
review_id int not null,
question_id int not null,
rating int not null,
comment_text varchar(1000),
PRIMARY KEY(review_id, question_id),
FOREIGN KEY(review_id) REFERENCES review(review_id)
);
