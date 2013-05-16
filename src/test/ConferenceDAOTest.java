package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.ConferenceDAO;
import dao.UserDAO;

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
  public void testAddConference() {
    fail();
  }
  
  /**
   * Tests getConferences().
   */
  @Test
  public void testGetConferences() {
    fail();
  }
}
