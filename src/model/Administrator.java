package model;

import java.util.Date;

import dao.AbstractDAO;
import dao.ConferenceDAO;
import dao.UserDAO;

/**
 * Class to create an Administrator Object which
 * has all the functionality of a User plus
 * the ability to create conferences as well as
 * change the date associate with that conference.
 * 
 * @author Levon Kechichian
 * @version 1.0
 */
public class Administrator extends User
{
	/**
	 * Reference to the Conference Data Access Object 
	 * that manipulates the Conference Object/s in the
	 * Database.
	 */
	private ConferenceDAO my_conference_dao;
	
	/*
	/**
	 * Reference to the User Data Access Object
	 * that manipulates the Database based on
	 * the current users Role (i.e. Role.ADMIN)
	 */
	 //	private UserDAO my_user_dao;
	 /*
	 * NOT SURE IF I WILL NEED THIS GUY
	 */
	
	
	/**
	 * Constructs an Administrator object from the
	 * given parameters.
	 * 
	 * @param the_id the User id
	 * @param the_first_name the first name of the user
	 * @param the_last_name the last name of the user
	 * @param the_username the account name associated with the user
	 * @param the_password the account password associated with the username
	 * @param the_email the email address associated with the users account
	 */
	public Administrator(final int the_id, 
			final String the_first_name, final String the_last_name, 
				final String the_username, final String the_password, final String the_email)
	{
		super(null, Role.ADMIN, the_first_name, the_last_name, 
				the_username, the_password, the_email);
		my_conference_dao = new ConferenceDAO();
		
		/*
		 * Not sure if I will need this guys
		 */ 
		 // my_user_dao = new UserDAO();
		 
	}
	
	/**
	 * Method to create a Conference Object and add it to the Collection
	 * of Conference Objects.
	 * 
	 * @param the_conference the Conference Object to be added
	 * to the Collection.
	 */
	public void createConference(final Conference the_conference)
	{	
		my_conference_dao.addConference(the_conference);
	}

	/**
	 * Method to change the date of a Conference Object.
	 * 
	 * @param the_date the new Date Object
	 */
	public void changeDate(final Date the_date)
	{
		/*
		 * depends how I know which conference I am manipulating
		 * I am assuming it is by ID but not sure how to
		 * obtain the correct conference to change its Date Object. 
		 */
		final int stub_id = 0;
		
		my_conference_dao.getConferences()
		  .get(stub_id).setDate(the_date);
	}
	
	public AbstractDAO getConferenceDAO()
	{
		return my_conference_dao;
	}
	
}
