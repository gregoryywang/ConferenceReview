
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
insert into conference(topic, conference_date, submit_paper, review_paper, make_recommendation,  final_decision, revise_paper) values('Innovations in Artificial Intelligence', '2014-10-01 07:00:00', '2013-04-01 23:59:59', '2013-07-15 23:59:59', '2013-09-01 23:59:59', '2013-9-20 23:59:59', '2013-09-27 23:59:59');
--Add Categories into conference
insert into conference_category(conf_id, cat_id) values(1,1);
insert into conference_category(conf_id, cat_id) values(1,2);
insert into conference_category(conf_id, cat_id) values(1,4);

--Add Program Chair user (USER_ID = 2)
insert into user(first_name, last_name, username, password, email_address) values('PrgmChair', 'User', 'PrgmChairTest', 'PrgmChairTest','PrgmChairTest@uw.edu');
--Link ProgramChairTest user to the Conference1
insert into user_role_paper_conference_join(user_id, role_id, conf_id) values (2, 5, 1);

--Add 2nd Conference with ProgramChairTest as PrgrmChair (CONF_ID = 2)
insert into conference(topic, conference_date, submit_paper,
review_paper, make_recommendation, final_decision, revise_paper)
values('Next Steps in Embedded Systems', '2014-10-10 07:00:00', '2013-07-01 23:59:59', '2013-07-30 23:59:59', '2013-08-01 23:59:59', '2013-10-01 23:59:59', '2013-11-01 23:59:59');
insert into conference_category(conf_id, cat_id) values(2,1);
insert into conference_category(conf_id, cat_id) values(2,2);
insert into conference_category(conf_id, cat_id) values(2,3);
insert into conference_category(conf_id, cat_id) values(2,4);
insert into user_role_paper_conference_join(user_id, role_id, conf_id) values(2,5,2);

--Add Author user (USER_ID = 3)
insert into user(first_name, last_name, username, password, email_address) values('Author', 'User', 'AuthorTest', 'AuthorTest','AuthorTest@uw.edu');

--Create a Paper with AuthorTest as user (PAPER_ID = 1)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(3, 'Making Design Decisions Easy', 'design, awesomeness', 'minimalist abstract', 4, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference and AuthorTest
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,2,1,2);

--Create a Paper2 with AuthorTest (PAPER_ID = 2)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(3, 'Design and Implmementation of Stuff', 'design, awesomeness', 'minimalist abstract', 4, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference2 and AuthorTest
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,2,2,2);

--Add SubProgramChairUser(USER_ID = 4)
insert into user(first_name, last_name, username, password, email_address) values('SubProgram', 'User', 'SubPrgmTest', 'SubPrgmTest', 'SubPrgmTest@uw.edu');
--Associate SubprogramChair with Paper 2
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(4,4,2,2);

--Create a Paper3 with AuthorTest (PAPER_ID = 3)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(3, 'Nexts Steps in Algorithm Design', 'design, awesomeness', 'minimalist abstract', 2, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference2 and AuthorTest
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,2,3,2);
--Associate Paper3 with SubProgramChair
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(4,4,3,2);

--Add Reviewer user (USER_ID = 5)
insert into user(first_name, last_name, username, password, email_address) values('Reviewer', 'User', 'ReviewerTest', 'ReviewerTest','ReviewerTest@uw.edu');
--Associate ReivewerTest with Paper 3
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (5, 3, 3, 2);

--Create Paper4 with AuthorTest (PAPER_ID = 4)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(3, 'Software Design and the Art of Motorcycle Maintenance', 'humor, design', 'minimalist abstract', 2, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference2 and AuthorTest
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(3,2,4,2);
--Associate Paper with SubProgramChair
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(4,4,4,2);
--Associate ReivewerTest with Paper 4
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (5, 3, 4, 2);


--Add Reviewer2 user (USER_ID = 6)
insert into user(first_name, last_name, username, password, email_address) values('Reviewer', 'User2', 'ReviewerTest2', 'ReviewerTest2','ReviewerTest@uw.edu');
--Associate ReivewerTest2 with Paper 4
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (6, 3, 4, 2);

--Add Reviewer3 user (USER_ID = 7)
insert into user(first_name, last_name, username, password, email_address) values('Reviewer', 'User3', 'ReviewerTest3', 'ReviewerTest3','ReviewerTest@uw.edu');
--Associate ReivewerTest3 with Paper 4
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (7, 3, 4, 2);

--Add Reviewer2 and Reviewer3 to Paper 3
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (6, 3, 3, 2);
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (7, 3, 3, 2);


--Create Paper5 with ProgramChair as author (PAPER_ID = 5)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(2, 'A Review of ISA Impacts within Business Environment', 'business, isa', 'minimalist abstract', 4, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference1 and AuthorTest
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(2,2,5,1);
--Associate Paper with SubProgramChair
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(4,4,5,1);
--Associate ReivewerTest with Paper 5
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (5,3,5,1);
--Associate ReivewerTest2 with Paper 5
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (6,3,5,1);
--Associate ReivewerTest3 with Paper 5
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (7,3,5,1);

--Create A Review for Paper 5 written by ReviewerTest(REVIEW_ID = 1)
insert into review(paper_id, reviewer_id, cmmt_subpgrmchair, summary_rating, summary_cmmt, active) values (5, 4, 'I would recommend that you read this and approve it!!!', 4, 'Nice work on this.', 1);
--Create answers to all questions in review
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,1,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,2,2,'Needs improvement in this area');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,3,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,4,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,5,3,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,6,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,7,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,8,4,'What Can I Say???');
insert into rating_comment(review_id, question_id, rating, comment_text) values (1,9,5,'Looks good');


--Add Author2 user (USER_ID = 8)
insert into user(first_name, last_name, username, password, email_address) values('Author', 'User2', 'AuthorTest2', 'AuthorTest2', 'AuthorTest2@uw.edu');

--Create Paper6 with Author2 as author (PAPER_ID = 6)
insert into paper(author_id, title, keywords, abstract, cat_id, content, active) values(8, 'Isometric Challenges in Implementaion of Clusters', 'cluser, data centers', 'abstracts are great', 4, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 1);
--Associate Paper with conference1 and Reviewer2
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(8,2,6,1);
--Associate Paper with SubProgramChair
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values(4,4,6,1);
--Associate ReivewerTest with Paper 6
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (5,3,6,1);
--Associate ReivewerTest2 with Paper 6
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (6,3,6,1);
--Associate ReivewerTest3 with Paper 6
insert into user_role_paper_conference_join(user_id, role_id, paper_id, conf_id) values (7,3,6,1);

--Create A Review for Paper 6 written by ReviewerTest(REVIEW_ID = 2)
insert into review(paper_id, reviewer_id, cmmt_subpgrmchair, summary_rating, summary_cmmt, active) values (6, 5, 'I would recommend that you read this and approve it!!!', 4, 'Nice work on this.', 1);
--Create answers to all questions in review
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,1,4,'All items addressed');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,2,5,'Nice work here');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,3,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,4,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,5,3,'Yep');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,6,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,7,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,8,4,'What Can I Say???');
insert into rating_comment(review_id, question_id, rating, comment_text) values (2,9,5,'Meets the standard');
--Create A Review for Paper 6 written by ReviewerTest2(REVIEW_ID = 3)
insert into review(paper_id, reviewer_id, cmmt_subpgrmchair, summary_rating, summary_cmmt, active) values (6, 6, 'I would think...', 4, 'Nice work on this.', 1);
--Create answers to all questions in review
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,1,4,'Some minor details');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,2,5,'Nice work here');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,3,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,4,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,5,3,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,6,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,7,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,8,4,'What Can I Say???');
insert into rating_comment(review_id, question_id, rating, comment_text) values (3,9,5,'Meets the standard');
--Create A Review for Paper 6 written by ReviewerTest2(REVIEW_ID = 4)
insert into review(paper_id, reviewer_id, cmmt_subpgrmchair, summary_rating, summary_cmmt, active) values (6, 7, 'I would recommend that...!!!', 5, 'Nice work on this.', 1);
--Create answers to all questions in review
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,1,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,2,5,'Nice work here');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,3,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,4,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,5,3,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,6,4,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,7,5,'Looks good');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,8,4,'What Can I Say???');
insert into rating_comment(review_id, question_id, rating, comment_text) values (4,9,5,'Meets the standard');

--Add general user (USER_ID = 9)
insert into user(first_name, last_name, username, password, email_address) values('Default', 'User', 'UserTest', 'UserTest', 'UserTest@uw.edu');

