package dao;

import java.util.List;

import model.Conference;
import model.Paper;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.util.List;
import common.ReferenceObject;

/**
 * DAO used to communicate with Conference related data.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public class ConferenceDAO extends AbstractDAO {

  private static String GET_CONFERENCES = "SELECT * FROM CONFERENCE WHERE ACTIVE = 1";
  
  /**
   * Returns a collection of available conferences.
   * @return A collection of available conferences.
   */
  public List<ReferenceObject> getConferencesRef() {
    return null;
  }
  
  /**
   * Adds new conference to data source.
   * @param aConference The conference to persist.
   */
  public void saveConference(final Conference aConference) {
    
  }
  
  /**
   * Adds a new paper to the conference
   * @param conf_id the conference id
   * @param the_paper the paper
   */
  public void addPaper(final int conf_id, final Paper the_paper){
	  
  }
  
  /**
   * Get single conference object.
   * @param aConfId The conference id.
   */
  public Conference getConference(final int aConfId) {
    return new Conference();
  }
  
  /**
   * Adds a user to the conference.
   * @param aUser the User object to add.
   * @param aRoleType the type of the user.
   */
  public void addUser(final User aUser, final Role aRoleType) {
    
  }
}
