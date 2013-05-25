
-- Create Categories
INSERT INTO category(display) values ('Curriculum');
INSERT INTO category(display) values ('Software');
INSERT INTO category(display) values ('Hardware');
INSERT INTO category(display) values ('Other');

--Create Roles
insert into role_type(role_type, role_id) values ('Administrator', 1);
insert into role_type(role_type, role_id) values ('Author' , 2);
insert into role_type(role_type, role_id) values ('Reviewer', 3);
insert into role_type(role_type, role_id) values ('SubProgram Chair' ,4);
insert into role_type(role_type, role_id) values ('Program Chair', 5);

--Add Admin user
insert into user(first_name, last_name, username, password, email_address) values('AdminTest', 'AdminTest', 'AdminTest', 'AdminTest','AdminTest@uw.edu');
insert into user_role_paper_conference_join(user_id, role_id) values (1, 1);

--Add Program Chair user
insert into user(first_name, last_name, username, password, email_address) values('PrgmChairTest', 'PrgmChairTest', 'PrgmChairTest', 'PrgmChairTest','PrgmChairTest@uw.edu');
--Add Conference
insert into conference(topic, conference_date, submit_paper, review_paper, make_recommendation,  final_decision, revise_paper) values('Innovations in Artificial Intelligence', '2013-10-01 07:00:00', '2013-04-01 23:59:59', '2013-06-15 23:59:59', '2013-07-01 23:59:59', '2013-07-20 23:59:59', '2013-09-01 23:59:59');
--Add Categories into conference
insert into conference_category(conf_id, cat_id) values(1,1);
insert into conference_category(conf_id, cat_id) values(1,2);
insert into conference_category(conf_id, cat_id) values(1,4);

--Link Progarm chair user to the conference
insert into user_role_paper_conference_join(user_id, role_id, conf_id) values (2, 5, 1);

--Add Reviewer user
insert into user(first_name, last_name, username, password, email_address) values('ReviewerTest', 'ReviewerTest', 'ReviewerTest', 'ReviewerTest','ReviewerTest@uw.edu');
insert into user_role_paper_conference_join(user_id, role_id) values (3, 3);

--Add general user
insert into user(first_name, last_name, username, password, email_address) values('Default', 'User', 'UserTest', 'UserTest', 'UserTest@uw.edu');

