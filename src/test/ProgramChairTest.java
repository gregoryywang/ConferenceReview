package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import dao.PaperDAO;

import model.Conference.Deadline;
import model.Paper;
import model.ProgramChair;
import model.Role;
import model.Status;
import model.User;

import service.ConferenceService;
import service.PaperService;
import service.UserService;

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
	public void testAssignSPChair()
	{
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
		pg_chair.setConference(ConferenceService.getInstance().getConference(1));
		List<Paper> papers = pg_chair.viewPapers();
		Paper paper = papers.get(0);
		System.out.println(paper.getTitle());
		System.out.println(paper.getID());
		try {
			pg_chair.assignDecision(true, paper);
		} catch (Exception e) { //should not happen unless this test is being run after
			//the deadline.
			e.printStackTrace();
		}
		assertTrue(Status.ACCEPT == paper.getStatus());
		PaperDAO paper_dao = new PaperDAO();
		Paper paper_db = paper_dao.getPaper(paper.getID());
		assertEquals(Status.ACCEPT, paper_db.getStatus());
	}
/*
assignSPChair(User, Paper)
 */
}
