package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import model.Conference;
import model.Conference.Deadline;
import model.Reviewer;
import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

public class ReviewerTest {
	
	Conference past_conf;
	Conference future_conf;
	
	@Before
	public void setUp()
	{
		past_conf = new Conference();
		past_conf.setDeadline(Deadline.REVIEW_PAPER, Date.valueOf(("2000-01-01")));
		
		future_conf = new Conference();
		future_conf.setDeadline(Deadline.REVIEW_PAPER, Date.valueOf(("2050-01-01")));	
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddReview()
	{
		fail();
	}
	
	@Test
	public void testGetReview()
	{
		fail();
	}
	
	@Test
	public void testViewPapers()
	{
		fail();
	}
	
	@Test
	public void testCanAddReview()
	{
		fail();
	}
	
	/**
	 * Test setting role.  (It should not change!)
	 */
	@Test
	public void testSetRole()
	{
		User rev_user = new User();
		Reviewer rev = new Reviewer(rev_user);
		
		rev.setRole(Role.ADMIN);
		assertEquals(Role.REVIEWER, rev.getRole());
	}
}
