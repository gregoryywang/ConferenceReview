package dao;

import model.Conference;
import java.util.Collection;

/**
 * DAO used to communicate with Conference related data.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public class ConferenceDAO {

  private static String GET_CONFERENCES = "SELECT * FROM CONFERENCE WHERE ACTIVE = 1";
  
  /**
   * Returns a collection of available conferences.
   * @return A collection of available conferences.
   */
  private Collection<Conference> getConferences() {
    return null;
  }
  
  /**
   * Adds new conference to data source.
   * @param aConference The conference to persist.
   */
  public void addConference(final Conference aConference) {
    
  }
}
