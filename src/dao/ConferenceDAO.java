package dao;

import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Conference.Deadline;
import model.Paper;
import model.Role;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
      "SUBMIT_PAPER = ?, REVIEW_PAPER = ?, FINAL_DECISION = ?, MAKE_RECOMMENDATION = ?, REVISE_PAPER = ?, " +
      "PROGRAM_CHAIR = ? WHERE CONF_ID = ? ";
  
  /**
   * Insert new conference record.
   */
  private static String INSERT_CONFERENCE = "INSERT INTO CONFERENCE(TOPIC, CONFERENCE_DATE, SUBMIT_PAPER, " +
      "REVIEW_PAPER, FINAL_DECISION, MAKE_RECOMMENDATION, REVISE_PAPER, PROGRAM_CHAIR) VALUES(?,?,?,?,?,?,?,?) ";
  
  
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
        stmt.setDate(6, aConference.getDeadline(Deadline.MAKE_RECOMMENDATION));
        stmt.setDate(7, aConference.getDeadline(Deadline.REVISE_PAPER));
        stmt.setString(8,  aConference.getPG_Chair());
        
        stmt.executeUpdate();
      } else {
        //Update existing record
        PreparedStatement stmt = con.prepareStatement(UPDATE_CONFERENCE);
        stmt.setString(1, aConference.getTopic());
        stmt.setDate(2, aConference.getDate());
        stmt.setDate(3, aConference.getDeadline(Deadline.SUBMIT_PAPER));
        stmt.setDate(4, aConference.getDeadline(Deadline.REVIEW_PAPER));
        stmt.setDate(5, aConference.getDeadline(Deadline.FINAL_DECISION));
        stmt.setDate(6, aConference.getDeadline(Deadline.MAKE_RECOMMENDATION));
        stmt.setDate(7, aConference.getDeadline(Deadline.REVISE_PAPER));
        stmt.setString(8,  aConference.getPG_Chair());
        stmt.setInt(9, aConference.getID());
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
    Conference conference = new Conference();
    
    try {
      Connection con = AbstractDAO.getConnection();
      PreparedStatement stmt = con.prepareStatement(GET_CONFERENCE);
      stmt.setInt(1, aConfId);
      ResultSet result = stmt.executeQuery();
       
      while (result.next()) {
        conference.setID(result.getInt("CONF_ID"));
        conference.set_PG_Chair(result.getString("PROGRAM_CHAIR"));
        conference.setTopic(result.getString("TOPIC"));
        conference.setDate(result.getDate("CONFERENCE_DATE"));
        conference.setDeadline(Deadline.FINAL_DECISION, result.getDate("FINAL_DECISION"));
        conference.setDeadline(Deadline.MAKE_RECOMMENDATION, result.getDate("MAKE_RECOMMENDATION"));
        conference.setDeadline(Deadline.REVIEW_PAPER, result.getDate("REVIEW_PAPER"));
        conference.setDeadline(Deadline.REVISE_PAPER, result.getDate("REVISE_PAPER"));
        conference.setDeadline(Deadline.SUBMIT_PAPER, result.getDate("SUBMIT_PAPER"));
      }
    } catch( Exception e) {}
    
    return conference;
  }
}
