package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import model.Conference;
import model.Conference.Deadline;

import org.junit.Before;
import org.junit.Test;

import dao.ConferenceDAO;

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
    conference.setTopic("Test Topic");
    conference.setDate(new Date(0));
    conference.setDeadline(Deadline.SUBMIT_PAPER, new Date(0));
    conference.setDeadline(Deadline.REVISE_PAPER, new Date(0));
    conference.setDeadline(Deadline.REVIEW_PAPER, new Date(0));
    conference.setDeadline(Deadline.MAKE_RECOMMENDATION, new Date(0));
    conference.setDeadline(Deadline.FINAL_DECISION, new Date(0));
    //conference.setProgramChair("Bill");  FIX ME!!
    
    //Save conference
    conferenceDao.saveConference(conference);
  }
  
  /**
   * Tests updating an existing conference.
   */
  @Test
  public void testSaveConferenceUpdate() {
    Conference conference = new Conference();
    conference.setTopic("Updated Test Topic");
    conference.setID(2);
    conference.setDate(new Date(0));
    conference.setDeadline(Deadline.SUBMIT_PAPER, new Date(0));
    conference.setDeadline(Deadline.REVISE_PAPER, new Date(0));
    conference.setDeadline(Deadline.REVIEW_PAPER, new Date(0));
    conference.setDeadline(Deadline.MAKE_RECOMMENDATION, new Date(0));
    conference.setDeadline(Deadline.FINAL_DECISION, new Date(0));
    //conference.setProgramChair("Bill");  FIX ME!!
    
    //Save conference
    conferenceDao.saveConference(conference);
  }
  
  /**
   * Tests getting single conference.
   */
  @Test
  public void TestGetConference() {
    Conference conf = conferenceDao.getConference(1);
    assertTrue(conf != null);
  }
}
