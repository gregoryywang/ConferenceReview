package dao;

import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Conference.Deadline;
import model.Paper;
import model.Role;
import model.User;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import common.ReferenceObject;

/**
 * DAO used to communicate with Conference related data.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public class ConferenceDAO extends AbstractDAO {

  /**
   * Get all active conferences.
   */
  private static String GET_CONFERENCES = "SELECT * FROM CONFERENCE";
  
  /**
   * Get conference based on conf_id
   */
  private static String GET_CONFERENCE = "SELECT * FROM CONFERENCE WHERE CONF_ID = ?";
  
  /**
   * Update existing conference record.
   */
  private static String UPDATE_CONFERENCE = "UPDATE CONFERENCE SET TOPIC = ?, CONFERENCE_DATE = ?, " +
      "AUTHOR_SUB_DEADLINE = ?, REVIEWER_SUB_DEADLINE = ?, AUTHOR_NOTIFICATION_DEADLINE = ? " +
      "WHERE CONF_ID = ? ";
  
  /**
   * Insert new conference record.
   */
  private static String INSERT_CONFERENCE = "INSERT INTO CONFERENCE(TOPIC, CONFERENCE_DATE, AUTHOR_SUB_DEADLINE, " +
      "REVIEWER_SUB_DEADLINE, AUTHOR_NOTIFICATION_DEADLINE) VALUES(?,?,?,?,?) ";
  
  
  /**
   * Returns a collection of available conferences.
   * @return A collection of available conferences.
   */
  public List<ReferenceObject> getConferencesRef() {
      ResultSet result = null;
      List<ReferenceObject> refs = new ArrayList<ReferenceObject>();
                                 
      try {
        Statement stmt = AbstractDAO.getConnection().createStatement();
        result = stmt.executeQuery(GET_CONFERENCES);
        
        while ( result.next() ) {
          refs.add(new ReferenceObject(result.getString("TOPIC"),
                                       result.getObject("CONF_ID")));
        }
      } catch (Exception e) {}
      
      return refs;  
  }
  
  /**
   * Adds new conference to data source.
   * @param aConference The conference to persist.
   */
  public void saveConference(final Conference aConference) {
    try {
      Connection con = AbstractDAO.getConnection();
      
      //New record to insert
      if ( aConference.getID() == 0 ) {
        PreparedStatement stmt = con.prepareStatement(INSERT_CONFERENCE);
        stmt.setString(1, aConference.getTopic());
        stmt.setDate(2, aConference.getDate());
        stmt.setDate(3, aConference.getDeadline(Deadline.SUBMIT_PAPER));
        stmt.setDate(4, aConference.getDeadline(Deadline.REVIEW_PAPER));
        stmt.setDate(5, aConference.getDeadline(Deadline.FINAL_DECISION));
        stmt.executeUpdate();
      } else {
        //Update existing record
        PreparedStatement stmt = con.prepareStatement(UPDATE_CONFERENCE);
        stmt.setString(1, aConference.getTopic());
        stmt.setDate(2, aConference.getDate());
        stmt.setDate(3, aConference.getDeadline(Deadline.SUBMIT_PAPER));
        stmt.setDate(4, aConference.getDeadline(Deadline.REVIEW_PAPER));
        stmt.setDate(5, aConference.getDeadline(Deadline.FINAL_DECISION));
        stmt.setInt(6, aConference.getID());
        stmt.executeUpdate();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
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
    Connection con = AbstractDAO.getConnection();
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
