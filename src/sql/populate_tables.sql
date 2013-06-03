
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

--Add Admin user (USERID = 1)
insert into user(first_name, last_name, username, password, email_address) values('AdminTest', 'AdminTest', 'AdminTest', 'AdminTest','AdminTest@uw.edu');
insert into user_role_paper_conference_join(user_id, role_id) values (1, 1);

--Add Conference (CONF_ID = 1)
insert into conference(topic, conference_date, submit_paper, review_paper, make_recommendation,  final_decision, revise_paper) values('Innovations in Artificial Intelligence', '2013-10-01 07:00:00', '2013-04-01 23:59:59', '2013-06-15 23:59:59', '2013-07-01 23:59:59', '2013-07-20 23:59:59', '2013-09-01 23:59:59');
--Add Categories into conference
insert into conference_category(conf_id, cat_id) values(1,1);
insert into conference_category(conf_id, cat_id) values(1,2);
insert into conference_category(conf_id, cat_id) values(1,4);

--Add Program Chair user (USER_ID = 2)
insert into user(first_name, last_name, username, password, email_address) values('PrgmChairTest', 'PrgmChairTest', 'PrgmChairTest', 'PrgmChairTest','PrgmChairTest@uw.edu');
--Link Program chair user to the conference
insert into user_role_paper_conference_join(user_id, role_id, conf_id) values (2, 5, 1);

--Add 2nd Conference with same program chair (CONF_ID = 2)
insert into conference(topic, conference_date, submit_paper,
review_paper, make_recommendation, final_decision, revise_paper)
values('Next Steps in Embedded Systems', '2014-10-10 07:00:00', '2013-06-01 23:59:59', '2013-07-01 23:59:59', '2013-08-01 23:59:59', '2013-10-01 23:59:59', '2013-11-01 23:59:59');
insert into conference_category(conf_id, cat_id) values(2,1);
insert into conference_category(conf_id, cat_id) values(2,3);
insert into conference_category(conf_id, cat_id) values(2,4);
insert into user_role_paper_conference_join(user_id, role_id, conf_id) values(2,5,2);

--Add Author user (USER_ID = 3)
insert into user(first_name, last_name, username, password, email_address) values('AuthorTest', 'AuthorTest', 'AuthorTest', 'AuthorTest','AuthorTest@uw.edu');
--Create a Paper with AuthorTest as user (PAPER_ID = 1)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(3, 'Design and Implmementation of Stuff', 'design, awesomeness', 'minimalist abstract', 4, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference and author
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,2,1,2);
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,5,1,2);


--Add Reviewer user (USER_ID = 4)
insert into user(first_name, last_name, username, password, email_address) values('ReviewerTest', 'ReviewerTest', 'ReviewerTest', 'ReviewerTest','ReviewerTest@uw.edu');
insert into user_role_paper_conference_join(user_id, role_id, conf_id, paper_id) values (4, 3, 2, 1);

--Add general user (USER_ID = 5)
insert into user(first_name, last_name, username, password, email_address) values('Default', 'User', 'UserTest', 'UserTest', 'UserTest@uw.edu');


