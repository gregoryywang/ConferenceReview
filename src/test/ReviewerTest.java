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

/**
 * Tests for methods of Reviewer
 * @author Danielle
 * @version 2013 Spring
 */
public class ReviewerTest {
	
	Conference past_conf;
	Conference future_conf;
	Reviewer reviewer;
	
	@Before
	public void setUp()
	{
		past_conf = new Conference();
		past_conf.setDeadline(Deadline.REVIEW_PAPER, Date.valueOf(("2000-01-01")));
		
		future_conf = new Conference();
		future_conf.setDeadline(Deadline.REVIEW_PAPER, Date.valueOf(("2999-01-01")));
		
		reviewer = new Reviewer(new User());
	}

	/**
	 * Test the method which allows reviews to be added.
	 */
	@Test
	public void testCanAddReview()
	{
		reviewer.setConference(past_conf);
		assertFalse(reviewer.canAddReview());
		reviewer.setConference(future_conf);
		assertTrue(reviewer.canAddReview());
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
