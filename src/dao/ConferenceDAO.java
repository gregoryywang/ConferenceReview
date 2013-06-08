package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Conference.Deadline;
import model.Role;
import model.User;


/**
 * DAO used to communicate with Conference related data.
 * @author Roshun Jones
 * @author Danielle (some methods and edits to associate categories and program chair with conf)
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
			"SUBMIT_PAPER = ?, REVIEW_PAPER = ?, FINAL_DECISION = ?, MAKE_RECOMMENDATION = ?, REVISE_PAPER = ? " +
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
			stmt.setInt(1, the_conf_id);
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
				
				//Get generated primary key
				ResultSet key = stmt.getGeneratedKeys();
				if (key.next()) {
					int id = key.getInt(1);
					aConference.setID(id);
				} else {
					throw new Exception("Primary key could not be generated.");
				}	
				stmt.close();
				///need to associate program chair with conference now.
				UserDAO user_dao = new UserDAO();
				user_dao.setRole(aConference.getProgramChair().getID(), Role.PROGRAM_CHAIR, aConference.getID());
				//also need to associated categories with conference.
				saveCategories(aConference.getID(), aConference.getCategories());
			} else {
				//Update existing record  
				// FIX ME?? (program chair and conferences cannot be updated?)
				PreparedStatement stmt = con.prepareStatement(UPDATE_CONFERENCE);
				stmt.setString(1, aConference.getTopic());
				stmt.setDate(2, aConference.getDate());
				stmt.setDate(3, aConference.getDeadline(Deadline.SUBMIT_PAPER));
				stmt.setDate(4, aConference.getDeadline(Deadline.REVIEW_PAPER));
				stmt.setDate(5, aConference.getDeadline(Deadline.FINAL_DECISION));
				stmt.setDate(6, aConference.getDeadline(Deadline.MAKE_RECOMMENDATION));
				stmt.setDate(7, aConference.getDeadline(Deadline.REVISE_PAPER));
				stmt.setInt(8, aConference.getID());
				stmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
	
	/**
	 * Associate all categories with a conference.
	 * @param the_conf_id the id of the conference.
	 * @param the_categories the categories
	 */
	private void saveCategories(final int the_conf_id, final List<String> the_categories)
	{
		final String find_category_id = "SELECT cat_id FROM category WHERE display = ?;";
		final String add_category_to_conf = "INSERT INTO conference_category(conf_id, cat_id) values (?, ?);";
		try
		{
			PreparedStatement add_cat_stmt = AbstractDAO.getConnection().prepareStatement(add_category_to_conf);
			add_cat_stmt.setInt(1, the_conf_id);
			
			PreparedStatement find_cat_id_stmt = AbstractDAO.getConnection().prepareStatement(find_category_id);
			
			for(String cat_text: the_categories)
			{
				//System.out.println("ConfDAO_saveCat()_MSG: " + cat_text);
				find_cat_id_stmt.setString(1, cat_text);
				ResultSet cat_ids = find_cat_id_stmt.executeQuery();
				while(cat_ids.next())
				{
					//System.out.println("ConfDAO_saveCat()_MSG: " + cat_ids.getInt("cat_id"));
					add_cat_stmt.setInt(2, cat_ids.getInt("cat_id"));
					add_cat_stmt.executeUpdate();
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println("ConfDAO_saveCategories()_MSG: " + e);
		}
	}
}
