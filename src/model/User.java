package model;

import java.util.Observable;

/**
 * Test Class for User
 * @author yongyuwang and Danielle Tucker
 *
 */
public class User extends Observable
{
	/**
	 * Unique integer id for the user.
	 */
	private final int my_id;
	
	/**
	 * The current conference for this user.
	 * ???Should this be pushed down to the children?
	 */
	private Conference my_conference;
	
	/**
	 * The current role for this user.
	 * ???Should this be pushed down to the children?
	 */
	private Role my_role;
	
	/**
	 * The User's first name.
	 */
	private String my_first_name;
	
	/**
	 * The User's last name.
	 */
	private String my_last_name;
	
	/**
	 * The User's username to log in (assumed unique).
	 */
	private final String my_username;

	/**
	 * The User's password.
	 */
	private String my_password;
	
	/**
	 * The contact email.
	 */
	private String my_email;
	
	/**
	 * DO NOT USE!!!!!!
	 */
	public User()
	{
		my_username = "";
		my_id = -1;
	}
	
	/**
	 * Create a user.
	 * @param the_id a unique userid
	 * @param the_conference the current conference the user is part of
	 * @param the_role the current role of the user
	 * @param the_first_name the first name
	 * @param the_last_name the last name
	 * @param the_username the username for this user which is assumed unique for all users
	 * @param the_password the password
	 * @param the_email the contact email.
	 */
	public User(final int the_id, final Conference the_conference, final Role the_role, 
			final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		my_id = the_id;
		my_conference = the_conference; //should we worry about cloning/copying?
		my_role = the_role;
		my_first_name = the_first_name;
		my_last_name = the_last_name;
		my_username = the_username;
		my_password = the_password;
		my_email = the_email;
	}

	/**
	 * Create a generic user with no associated conferences or roles specified.
	 * Will have null conference and Role.USER for the user.
	 * 
	 * @param the_id a unique userid
	 * @param the_first_name the first name
	 * @param the_last_name the last name
	 * @param the_username the username for this user which is assumed unique for all users
	 * @param the_password the password
	 * @param the_email the contact email.
	 */
	public User(final int the_id, final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		this(the_id, null, Role.USER, the_first_name, the_last_name, 
				the_username, the_password, the_email);
	}
	
	/**
	 * Create a deep copy of the provided User. (Copy constructor.)
	 * @param the_user the user to make a copy of.
	 */
	public User(final User the_user)
	{
		this(the_user.getID(), the_user.getConference(), the_user.getRole(),
				the_user.getFirstName(), the_user.getLastName(), 
				the_user.getUsername(), the_user.getPassword(), the_user.getEmail());		
	}

	/**
	 * Get the userid.
	 * @return the userid
	 */
	public int getID()
	{
		return my_id;
	}
	
	/**
	 * Set the current conference for the user.
	 * @param the_conference the conference
	 */
	public void setConference(final Conference the_conference)
	{
		my_conference = new Conference(the_conference);
		setChanged();
		notifyObservers(the_conference);
	}
	
	/**
	 * Get the current conference for this user.
	 * @return the current conference
	 */
	public Conference getConference()
	{
		return new Conference(my_conference);
	}

	/**
	 * Change the role for this user.
	 * @param the_role the role
	 */
	public void setRole(final Role the_role)
	{
		my_role = the_role;
		setChanged();
		notifyObservers(the_role);
	}
	
	/**
	 * Get the role of the user.
	 * @return the current role
	 */
	public Role getRole()
	{
		return my_role;
	}
	
	/**
	 * Set the first name of the user.
	 * @param the_first_name the name to be used for the first name
	 */
	public void setFirstName(final String the_first_name)
	{
		my_first_name = the_first_name;
		setChanged();
		notifyObservers(my_first_name);
	}
	
	/**
	 * Returns the first name.
	 * @return first name of the user.
	 */
	public String getFirstName()
	{
		return my_first_name;
	}
	
	/**
	 * Change the last name of the user.
	 * @param the_last_name the name to be used for the last name.
	 */
	public void setLastName(final String the_last_name)
	{
		my_last_name = the_last_name;
		setChanged();
		notifyObservers(my_last_name);
	}
	
	/**
	 * Returns the last name.
	 * @return last name of the user.
	 */
	public String getLastName()
	{
		return my_last_name;
	}
	
	/**
	 * Return the full name First Name Last Name
	 * @return the full name of the user First then Last name order.
	 */
	public String getFullName()
	{
		return my_first_name + " " + my_last_name;
	}
	
	/**
	 * Return the username
	 * @return the username
	 */
	public String getUsername()
	{
		return my_username;
	}
	
	/**
	 * Return the password (Warning: this is in plaintext.)
	 * @return the password
	 */
	public String getPassword()
	{
		return my_password;
	}
	
	/**
	 * Change the email contact information.
	 * Assumes a valid email address format.
	 * @param the_email the email
	 */
	public void setEmail(final String the_email)
	{
		my_email = the_email;
		setChanged();
		notifyObservers(my_email);
	}
	
	/**
	 * Return the email contact information.
	 * @return the email contact information.
	 */
	public String getEmail()
	{
		return my_email;
	}
}
