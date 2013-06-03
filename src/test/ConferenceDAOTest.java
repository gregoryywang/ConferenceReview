package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Conference.Deadline;
import model.User;

import org.junit.Before;
import org.junit.Test;

import service.UserService;
import dao.ConferenceDAO;

/**
 * Tests methods in ConferenceDAO class
 * @author Roshun
 * @author Danielle
 * @version 2013 Spring (done)
 */
public class ConferenceDAOTest {

	/**
	 * Used for test setup.
	 */
	private ConferenceDAO conferenceDao = null;
	
	/**
	 * Test fixture for tests.
	 */
	@Before
	public void setUp() {
		conferenceDao = new ConferenceDAO();

	}

	/**
	 * Tests adding a conference.
	 */
	@Test
	public void testSaveConference() {
		Conference conference = new Conference();
		conference.setTopic("Test Conference");
		Date test_date = new Date(0);
		conference.setDate(test_date);
		conference.setDeadline(Deadline.SUBMIT_PAPER, test_date);
		conference.setDeadline(Deadline.REVISE_PAPER, test_date);
		conference.setDeadline(Deadline.REVIEW_PAPER, test_date);
		conference.setDeadline(Deadline.MAKE_RECOMMENDATION, test_date);
		conference.setDeadline(Deadline.FINAL_DECISION, test_date);
		List<String> categories = new ArrayList<String>();
		categories.add("Other");
		categories.add("Hardware");
		conference.setCategories(categories);
		User user = UserService.getInstance().authenticateUser("PrgmChairTest", "PrgmChairTest");
		conference.setProgramChair(user);

		//Save conference
		conferenceDao.saveConference(conference);

		//Assertion Statements @author Danielle
		Conference conf = conferenceDao.getConference(conference.getID());
		assertEquals("Test Conference", conf.getTopic());
		assertEquals(test_date.toString(), conf.getDate().toString());
		assertEquals(test_date.toString(), conf.getDeadline(Deadline.SUBMIT_PAPER).toString());
		assertEquals(user.getID(), conf.getProgramChair().getID());
	}

	/**
	 * Tests updating an existing conference.
	 */
	@Test
	public void testSaveConferenceUpdate() {

		Conference conference = new Conference();
		conference.setTopic("Test Update Conf in ConferenceDao Test");
		Date test_date = new Date(0);
		conference.setDate(test_date);
		conference.setDeadline(Deadline.SUBMIT_PAPER, test_date);
		conference.setDeadline(Deadline.REVISE_PAPER, test_date);
		conference.setDeadline(Deadline.REVIEW_PAPER, test_date);
		conference.setDeadline(Deadline.MAKE_RECOMMENDATION, test_date);
		conference.setDeadline(Deadline.FINAL_DECISION, test_date);
		User user = UserService.getInstance().authenticateUser("PrgmChairTest", "PrgmChairTest");
		conference.setProgramChair(user);
		conferenceDao.saveConference(conference);
		
		conference.setTopic("Updated Test Topic");
		//Update conference
		conferenceDao.saveConference(conference);

		//Assertion Statements @author Danielle
		Conference conf = conferenceDao.getConference(conference.getID());
		assertEquals("Updated Test Topic", conf.getTopic());
		assertEquals(test_date.toString(), conf.getDate().toString());
		assertEquals(test_date.toString(), conf.getDeadline(Deadline.SUBMIT_PAPER).toString());
		assertEquals(user.getID(), conf.getProgramChair().getID());		
		
	}

	/**
	 * Tests getting single conference.
	 */
	@Test
	public void TestGetConference() {
		Conference conf = conferenceDao.getConference(1);
		assertTrue(conf != null);
	}
	
	/**
	 * Tests getting all conferences.
	 * @author Danielle
	 */
	@Test
	public void testGetConferences()
	{
		List<Conference> conferences = conferenceDao.getConferences();
		assertTrue(conferences.size()>0);
	}
}
