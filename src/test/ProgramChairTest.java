package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import model.Author;
import model.Conference;
import model.Conference.Deadline;
import model.Paper;
import model.ProgramChair;
import model.Role;
import model.Status;
import model.User;

import org.junit.Test;

import service.ConferenceService;
import service.PaperService;
import service.UserService;
import dao.PaperDAO;

/**
 * Testing ProgramChair class
 * @author Danielle
 * @version 2013 Spring (done)
 *
 */
public class ProgramChairTest {

	private User pg_user = UserService.getInstance().authenticateUser("PrgmChairTest", "PrgmChairTest");
	
	@Test
	public void testConstructor() {
		assertNotNull(pg_user);
		assertEquals("PrgmChairTest", pg_user.getFirstName());
		ProgramChair pg_chair = new ProgramChair(pg_user);
		assertEquals(pg_user.getID(), pg_chair.getID());
	}
	
	@Test
	public void testSetRole()
	{
		ProgramChair pg_chair = new ProgramChair(pg_user);
		pg_chair.setRole(Role.AUTHOR);
		assertEquals(Role.PROGRAM_CHAIR, pg_chair.getRole());
	}
	
	@Test
	public void testCanAssignDecision()
	{
		ProgramChair pg_chair = new ProgramChair(pg_user);
		pg_chair.setConference(ConferenceService.getInstance().getConference(1));
		if(pg_chair.getConference().getDeadline(Deadline.FINAL_DECISION).
				after(new Date(Calendar.getInstance().getTimeInMillis())))
		{
			assertTrue(pg_chair.canAssignDecision());
		}
		else
		{
			assertFalse(pg_chair.canAssignDecision());
		}
	}
	
	@Test
	public void viewPapers()
	{
		ProgramChair pg_chair = new ProgramChair(pg_user);
		pg_chair.setConference(ConferenceService.getInstance().getConference(2));
		List<Paper> papers = pg_chair.viewPapers();
		assertFalse(papers.contains(new Paper()));
		assertTrue(papers.size()>0);
	}
	
	@Test
	public void testAssignDecision()
	{
		ProgramChair pg_chair = new ProgramChair(pg_user);
		pg_chair.setConference(ConferenceService.getInstance().getConference(2));
		List<Paper> papers = pg_chair.viewPapers();
		Paper paper = papers.get(0);
		try {
			pg_chair.assignDecision(true, paper);
			assertTrue(Status.ACCEPT == paper.getStatus());
		} catch (Exception e) { //should not happen unless this test is being run after
			//the deadline.
			e.printStackTrace();
		}
		PaperDAO paper_dao = new PaperDAO();
		Paper paper_db = paper_dao.getPaper(paper.getID());
		assertEquals(Status.ACCEPT, paper_db.getStatus());
	}
	
	@Test
	public void testAssignSPChair()
	{
		//Setup of test stuff for this one test!
		User author_user = UserService.getInstance().authenticateUser("AuthorTest", "AuthorTest");
		Paper new_paper = new Paper(author_user, "TestAssignSPChair", "keywords here", 
				"hi abstract", "Other", "test assign sp chair");
		Conference conf =ConferenceService.getInstance().getConference(2);
		author_user.setConference(conf);
		Author author = new Author(author_user);
		try {
			author.submitPaper(new_paper);
		} catch (Exception e) {//should not reach unless over the deadline for submission.
			e.printStackTrace();
		}
		ProgramChair pg_chair = new ProgramChair(pg_user);
		pg_chair.setConference(conf);
		List<Paper> papers = pg_chair.viewPapers();
		int sp_user_id = 0;
		for(Paper paper: papers)
		{
			if(paper.getID() == new_paper.getID())
			{
				try {
					User user = UserService.getInstance().authenticateUser("UserTest", "UserTest");
					if (user != null)
					{
						pg_chair.assignSPChair(user, new_paper);
						sp_user_id = user.getID();
					}
				} catch (Exception e) {//will not get here unless UserTest is author of paper WHICH IS FALSE!!
					e.printStackTrace();
				}
			}
		}
		User asgn_SPChair = PaperService.getInstance().getAssignedSubprogramChair(new_paper.getID());
		assertEquals(sp_user_id, asgn_SPChair.getID());
		PaperService.getInstance().deletePaper(new_paper.getID());
		
	}
/*
assignSPChair(User, Paper)
 */
}
