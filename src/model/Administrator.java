package model;

import java.sql.Date;

import service.ConferenceService;

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
	}
	
	/**
	 * Method to create a Conference Object and add it to the Database
	 * Collection of Conference Objects.
	 * 
	 * @param the_conference the Conference Object to be added
	 * to the Collection.
	 */
	public void createConference(final Conference the_conference)
	{	
		ConferenceService.getInstance().saveConference(the_conference);
	}

	/**
	 * Method to change the date of a Conference Object.
	 * 
	 * @param the_conference the Conference Object that needs it's Date field
	 * changed
	 * @param the_date the new Date Object
	 */
	public void changeDate(final Conference the_conference, final Date the_date)
	{
		the_conference.setDate(the_date);
	}
	
}
