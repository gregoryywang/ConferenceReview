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
import java.sql.SQLException;
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
			"WHERE CONF_ID = ? ";

	/**
	 * Insert new conference record.
	 */
	private static String INSERT_CONFERENCE = "INSERT INTO CONFERENCE(TOPIC, CONFERENCE_DATE, SUBMIT_PAPER, " +
			"REVIEW_PAPER, FINAL_DECISION, MAKE_RECOMMENDATION, REVISE_PAPER) VALUES(?,?,?,?,?,?,?) ";


	/**
	 * Returns a collection of available conferences with all relevant
	 * fields populated.
	 * @return A collection of available conferences.
	 * @author Danielle
	 */
	public List<Conference> getConferences() {
		ResultSet result = null;
		List<Conference> refs = new ArrayList<Conference>();

		try {
			Statement stmt = AbstractDAO.getConnection().createStatement();
			result = stmt.executeQuery(GET_CONFERENCES);

			while ( result.next() ) {
				UserDAO user_dao = new UserDAO();
				int conf_id = result.getInt("conf_id");
				List<User> program_chair = user_dao.getUsers(conf_id, Role.PROGRAM_CHAIR);
				Conference conf = new Conference(conf_id, 
						result.getDate("conference_date"), 
						program_chair.get(0),
						result.getString("topic"),
						result.getDate("submit_paper"),
						result.getDate("review_paper"), 
						result.getDate("make_recommendation"),
						result.getDate("final_decision"), 
						result.getDate("revise_paper"));
				conf.setCategories(getCategories(conf_id));

				refs.add(conf);
			}
		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}

		return refs;  
	}
	
	/**
	 * Returns a collection of available conferences.
	 * @return A collection of available conferences.
	 * @deprecated
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
	 * Get all categories associated with a conference.
	 * @param the_conf_id the id of the conference
	 * @return a list of categories for papers at the conference.
	 * @author Danielle
	 */
	public List<String> getCategories(final int the_conf_id)
	{
		List<String> categories = new ArrayList<String>();
		String get_categories = "SELECT * FROM category WHERE cat_id IN " +
				"(SELECT cat_id FROM conference_category WHERE conf_id = ?);";
		try {
			PreparedStatement stmt = getConnection().prepareStatement(get_categories);
			ResultSet result = stmt.executeQuery();
			
			while(result.next())
			{
				categories.add(result.getString("display"));
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return categories;
	}
	/**
	 * FIX ME!! To Do: associate categories with conf and pgchair with conf.
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

				stmt.executeUpdate();
				stmt.close();
				UserDAO user_dao = new UserDAO();
				///need to associate program chair with conference now.
				//also need to associated categories with conference.
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
				stmt.setInt(9, aConference.getID());
				stmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get single conference object.
	 * @param aConfId The conference id.
	 * @author Danielle (program chair and categories edits)
	 */
	public Conference getConference(final int aConfId) {
		Conference conference = new Conference();

		try {
			Connection con = AbstractDAO.getConnection();
			PreparedStatement stmt = con.prepareStatement(GET_CONFERENCE);
			stmt.setInt(1, aConfId);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				int conf_id = result.getInt("conf_id");
				conference.setID(conf_id);				
				UserDAO user_dao = new UserDAO();
				List<User> program_chair = user_dao.getUsers(conf_id, Role.PROGRAM_CHAIR);
				conference.setProgramChair(program_chair.get(0));
				conference.setTopic(result.getString("TOPIC"));
				conference.setDate(result.getDate("CONFERENCE_DATE"));
				conference.setDeadline(Deadline.FINAL_DECISION, result.getDate("FINAL_DECISION"));
				conference.setDeadline(Deadline.MAKE_RECOMMENDATION, result.getDate("MAKE_RECOMMENDATION"));
				conference.setDeadline(Deadline.REVIEW_PAPER, result.getDate("REVIEW_PAPER"));
				conference.setDeadline(Deadline.REVISE_PAPER, result.getDate("REVISE_PAPER"));
				conference.setDeadline(Deadline.SUBMIT_PAPER, result.getDate("SUBMIT_PAPER"));
				conference.setCategories(getCategories(conf_id));
			}
		} catch( Exception e) {}

		return conference;
	}
}
