#ConferenceReview

ConferenceReview is a Java swing-based application which manages reviews and recommendations for papers submitted to conferences.

__Author:__ Danielle T, Roshun J, Levon K, Yong YW<br />
__Status:__ Stable<br />
__Version:__ 1.0

Google Code website: https://code.google.com/p/da-deliverables/

##Installation

An archive file containing the compiled .JAR binaries for this project is hosted in the Google Code website.

Currently, user names and passwords are hard-coded and can be found in the Users section below.

##Project Report

Conference Review System
Spring 2013

DaDeliverables (Roshun J, Yong Yu W, Levon K, Danielle T) 

###Instructions For Running File

The runnable file is packaged in a .zip file which you will need to extract the contents into a local directory where the file ConferenceReviewSystem.jar can be launched. The contents should consist of three files: The application jar file and two database files. Note that these files must reside in the same directory for the application to work correctly.

###Users

The database is pre-populated with the following users with the login and password being identical:

UserTest (Login:UserTest Psswd: UserTest) - This is what a default user will see which is pretty much not very interesting because they are not an author yet and can only submit a paper.

AuthorTest (Login/Passwd: AuthorTest) - This user has several papers submitted to several conferences

ReviewerTest (Login/Passwd: ReviewerTest) - This user has several papers which they are reviewers for and either have already submitted a review or have yet to submit a review

SubPrgrmTest (Login/Passwd: SubPrgmTest) - This user is a SubProgram Chair who has some papers who they need to assign reviewers and others which already have reviewers assigned to them.  Some reviewers who are assigned have submitted reviews and others have not.

PrgmChairTest (Login/Passwd: PrgmChairTest) - This user is a Program Chair who has several papers which belong to them.

###Every User Story and Business Rule and State of Implementation

YES  US01.   As a Program Chair, I want to view a list of all submitted manuscripts and the acceptance status (yes/no/undecided) for each.

YES  US02.   As a Program Chair, I want to make an acceptance decision (yes or no) on a submitted manuscript.

YES  US03.   As an Author, I want to submit a manuscript to a conference.

YES US04.   As a Reviewer, I want to submit a review for a manuscript to which I have been assigned.

YES  US05.     As a Program Chair I want to designate a Subprogram Chair for a manuscript.

YES  US06.     As a Program Chair, I want to see which papers are assigned to which Subprogram chairs.

YES  US07.     As a Subprogram Chair, I want to assign a paper to reviewers.

YES   US08.     As an Author I want to obtain the reviews for the manuscripts that I have submitted.

YES   US09.     As a Subprogram Chair, I want to submit my recommendation for a paper.

YES   US10.     As an Author, I want to make changes to my submission, including unsubmitting my manuscript.

YES   US11.     As a user, I want to log in.

YES   US12.     As a user, I want to create a new conference.

####Business rules 

YES  BR01.    Role designations for a user are conference-specific. In other words having a particular role for conference X has no effect on the role or roles that this user has for conference Y. 
(User Stories: all)

YES  BR02.    A particular user can carry out only those tasks for a conference related to the roles that he or she is assigned for that conference. 
(User Stories: all)

YES  BR03.    For each conference, only one user is designated the Program Chair. 
(User Stories: 1, 2, others)

YES  BR04.    New conferences are only setup by head office staff. 
(User Stories: 12)

YES  BR05.    The designated Program Chair is assigned to a new conference when it is first setup. 
(User Stories: 12)

NO  BR06.    A user can be designated as a Subprogram chair of a conference only if he or she is already a Reviewer for the conference. ** 
(User Stories: 6)
All reviewers past and present are able to be selected for SubProgram Chair.  There was an issue where a program chair could not activate a person for a subprogram chair position because no reviewers had yet been assigned to the conference.  It was a chicken and egg problem.  For this iteration we decided to accept all reviewers as possible subprogram chairs (except the author of the paper.)

YES  BR07.    A user becomes an Author only by submitting a manuscript. 
(User Stories: 3, others)

YES  BR08.    A user can take on more than one role for a given conference. 
(User Stories: all)

YES  BR09.    An Author cannot review his or her own paper. 
(User Stories: 4, 8)

YES  BR10.    A Subprogram Chair cannot be designated for a paper that he or she authored.

YES  BR11.    A Reviewer cannot review a paper that he or she authored.

YES  BR12.    A Reviewer can only access those manuscripts assigned to him or her.

YES BR13.    A Reviewer can only access the reviews that he or she submits.

YES BR14.    An Author can only access his or her submitted manuscripts and reviews for these manuscripts.

YES  BR15.    Only the Program/Subprogram Chair submitting the recommendation can access that Subprogram Chair’s recommendation.

NO  BR16.    An Author can submit a maximum of 4 papers to any conference. *
Authors can submit more than 4 papers to any conference.

NO  BR17.    A Reviewer can be assigned to review a maximum of 4 papers to review. *
Reviewers can have more than 4 papers to review

NO BR18.    A Subprogram Chair can be designated no more than 4 papers. *
SubProgram Chairs can have more than 4 papers designated to them

YES  BR19.    Authors can only access reviews after the program chair has made a decision

YES  BR20.    Only the Program Chair can see which Subprogram Chairs have been designated for which papers.

YES   BR21.    Only the Program Chair and the Subprogram Chair designated for a particular paper can see which Reviewers have been assigned to review that paper.

BR22.    All paper submissions must be made on or before the submission deadline. 
(User Stories: 3, others)

* Maximum submissions and reviews are arbitrary for first iterations.
* For first iterations the list of reviewers for a conference is already available
* Not all business rules have been explicitly associated to user stories - you will elaborate additional associations in your deliverables

###Non-functional requirements

YES  NF01.    The system will be executed on a single machine as a desktop application with only a single user at a time.
YES  NF02.    The system will not have secure user authentication, but the user will identify himself/herself after system startup using a unique username. 

NO  Each user will be associated with a single user role.  Violates the business rules above!!

YES  NF03.    Persistent data will be stored in one or more files on the local file system.

YES  NF04.    The programming will be carried out using Java 6.x and JUnit 4.x.

###Description of Authorship

####Controller Package
* AuthorViewController
  * Authors: Yong 
  * Editors: Roshun
  
* Controller
  * Authors: Roshun
  
* PaperSubmissionController
  * Authors: Roshun
  
* PGChairViewController
  * Authors: Roshun
  
* ReviewerViewController
  * Authors: Roshun(template) and Levon
  
* RevisedAuthorViewController
  * Authors: Yong
  
* RevisedPaperSubmissionController
  * Authors: Yong
  * Editor: Roshun
  
* SubPGChairController
  * Authors: Roshun(template) and Danielle

####DAO Package

* AbstractDAO.
  * Authors: Roshun
  
* CategoryDAO
  * Authors: Danielle
  * Editors: Roshun
  
* ConferenceDAO.
  * Authors: Roshun
  * Editors:  Danielle
  
* PaperDAO
  * Authors: Danielle
  * Editors: Roshun and Levon(bug fix)
  
* UserDAO.java
  * Authors: Roshun
  * Editors: Danielle

####GUI Package

* AdminView
  * Authors: Levon
  * Editors: Danielle
  
* AuthorView
  * Authors: Yong
  * Editors: Roshun
  
* ConferenceForm
  * Authors: Levon
  * Editors: Danielle
  
* HeaderView
  * Authors: Danielle
  * Editors: Roshun (tie views together through this one view)
  
* LoginView
  * Authors: Yong
  * Editors: Roshun
  
* MainView
  * Authors: Yong
  * Editors: Roshun(Abstraction of Roles with Views) and Danielle(logout menu)
  
* OldAuthorView
  * Authors: Roshun and Yong
  
* PaperSubmissionForm
  Authors: Yong
  Editors: Roshun(adjustments after a code refactor)  and Danielle(category dropdown)
  
* PGChairDialog
  * Authors: Roshun
  * Editors: Danielle(minor)
  
* PGChairView
  * Authors: Roshun (served as template for all other Views)

* ReviewerView
  * Authors: Roshun(template) and Levon

* ReviewForm
  * Authors: Levon
  * Editors: Danielle and Roshun
  
* SubPGChairDialog
  * Authors: Danielle
  * Editors: Roshun
  
* SubPGChairView
  * Authors: Roshun(template) and Danielle
  
* TablePanel
  * Authors: Roshun

####Model Package

* Administrator
  * Authors: Danielle
  * Editors: Levon and Roshun
  
* Author
  * Authors: Roshun
  * Editors: Danielle(business logic) and Levon
  
* AuthorTableModel
  * Authors: Yong
  * Editors: Roshun(minor)
  
* Conference
  * Authors: Roshun and Danielle
  * Editors: Levon
  
* Paper
  * Authors: Roshun and Danielle
  * Editors: Levon
  
* ProgramChair
  * Authors: Danielle 
  * Editors: Levon (initial code stubs and constructor)
  
* Recommendation
  * Authors:Danielle
  * Editors: Levon
  
* Review
  * Authors: Danielle
  * Editors: Levon
  
* Reviewer
  * Authors: Danielle
  * Editors: Levon
  
* Role
  * Authors: Danielle and Roshun
  
* Status
  * Authors: Danielle
  * Editors: Roshun
  
* SubProgramChair
  * Authors: Levon
  * Editors: Danielle
  
* User
  * Authors: Roshun and Danielle
  * Editors: Levon

####Service Package

* ConferenceService
  * Authors:Roshun
  * Editors: Danielle(clean up after a refactor)
  
* PaperService
  * Authors: Danielle
  * Editors: Roshun
  
* UserService
  * Authors: Roshun and Danielle

####sql Package --

Note: Database Design by Roshun

* populate_tables.sql
  * Authors: Roshun and Danielle
  
* tables.sql
  * Authors: Roshun and Danielle

####Test Package

* CategoryDAOTest
  * Authors: Danielle
  * Editors: Roshun(initial code)
  
* ConferenceDAOTest
  * Authors: Roshun
  * Editors: Danielle
  
* PaperDAOTest
  * Authors: Danielle
  * Editors: Roshun
  
* ProgramChairTest
  * Authors:Danielle
  
* RecommendationTest
  Authors:Danielle
  
* ReviewerTest
  * Authors: Danielle
  
* UserDAOTest
  * Authors: Roshun
  * Editors: Danielle
  
* UserTest.java
  * Authors: Danielle

##Other Comments

###Design Choices for this Iteration

* Users are hard-coded into this iteration.

* Once a conference is created, only dates can be changed, not Program Chairs or Categories.

* Categories are hard-coded into the system for conferences  This is for ease of error checking and later possible sorting by category

* Once a review, recommendation, or assignment of roles for a paper have been assigned, these cannot change.

* Only three reviewers are allowed for each paper.

##Changes From Original Design

* Display changed a bit as it was decided that in our paper prototype the pane on the left side was not really necessary.  We also simplified the view a bit as the addition of all the buttons on the row for each paper was not as graphically pleasing when rendered in a JTable as we had hoped.  General overall layout is very similar to what we had before.

* Added Controller, DataAccessObject (DAO) and Service package so that models would not have to talk directly to the database and gui’s did not have to talk directly to the database as well.  In general, gui’s communicate with Service objects or Models’s directly.  Service object communicate with the DataAccessObject and package things together if needed from several data access object calls while also performing some business logic.  Controllers are how the models were being updated when actions happen in the gui.

* Viewer Interface was removed as it ended up being irrelevant.
