package model;

import java.sql.Date;
import java.util.Calendar;
import java.util.Observable;

/**
 * Test Class for User
 * @author yongyuwang and Danielle Tucker
 * @version 2013 Spring
 */
public class User extends Observable
{
	/**
	 * The unique id that identifies this user.
	 */
	private int user_id;

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
	private String my_username;

	/**
	 * The User's password.
	 */
	private String my_password;

	/**
	 * The contact email.
	 */
	private String my_email;

	/**
	 * The User's current role.
	 */
	private Role my_role;

	/**
	 * The User's current conference.
	 */
	private Conference my_conference;

	/**
	 * TEST CONSTRUCTOR.
	 */
	public User()
	{
		my_first_name = "first";
		my_last_name = "last";
		my_username = "user name";
		my_password = "password";
		my_email = "email";
		my_conference = new Conference();
		my_role = Role.USER;
	}

	/**
	 * Create a user.
	 * @param the_conference the current conference the user is part of
	 * @param the_role the current role of the user
	 * @param the_first_name the first name
	 * @param the_last_name the last name
	 * @param the_username the username for this user which is assumed unique for all users
	 * @param the_password the password
	 * @param the_email the contact email.
	 */
	public User(final Conference the_conference, final Role the_role, 
			final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		my_first_name = the_first_name;
		my_last_name = the_last_name;
		my_username = the_username;
		my_password = the_password;
		my_email = the_email;
		my_conference = the_conference;
		my_role = the_role;
	}

	/**
	 * Create a generic user with no associated conferences or roles specified.
	 * Will have null conference and Role.USER for the user.
	 * 
	 * @param the_first_name the first name
	 * @param the_last_name the last name
	 * @param the_username the username for this user which is assumed unique for all users
	 * @param the_password the password
	 * @param the_email the contact email.
	 */
	public User(final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		this(null, Role.USER, the_first_name, the_last_name, 
				the_username, the_password, the_email);
	}

	/**
	 * Create a deep copy of the provided User. (Copy constructor.)
	 * @param the_user the user to make a copy of.
	 */
	public User(final User the_user)
	{
		this(the_user.getFirstName(), the_user.getLastName(), 
				the_user.getUsername(), the_user.getPassword(), the_user.getEmail());	
		user_id = the_user.getID();
		my_role = the_user.getRole();
		my_conference = the_user.getConference();
	}

	/**
	 * Get the userid.
	 * @return the userid
	 */
	public int getID()
	{
		return user_id;
	}

	public void setID(final int aId) {
		user_id = aId;
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

	/**
	 * Get the current role of the user
	 * @return current role of the user which defaults to Role.User
	 */
	public Role getRole() {
		return my_role;
	}

	/**
	 * Set the role of this user
	 * @param the_role the role
	 */
	public void setRole(Role the_role) {
		my_role = the_role;
		setChanged();
		notifyObservers(my_role);
	}

	/**
	 * Get the conference
	 * @return the conference associated with this user
	 */
	public Conference getConference()
	{
		return my_conference;
	}

	/**
	 * Set the conference associated with this user
	 * @param the_conference the conference
	 */
	public void setConference(final Conference the_conference)
	{
		my_conference = the_conference;
		setChanged();
		notifyObservers(my_conference);
	}


	/**
	 * Get the current sql date for comparison against the deadlines for the conference.
	 * @return current date and time.
	 */
	protected Date currentDate() {
		Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
		return timeNow;
	}

	/**
	 * String representation of this object (just the name)
	 * @return the Full Name of the user
	 */
	@Override
	public String toString() {
		return getFullName();
	}

	/**
	 * Equals method
	 * @param the_object the object
	 * @return if the object is equal to this user
	 */
	@Override
	public boolean equals(final Object the_object)
	{
		boolean result = false;
		if( the_object == this )
		  return true;
		
		if(the_object == null || the_object.getClass() != getClass())
			return false;
		
		User other_user = (User) the_object;
		if(other_user.getID() == getID())
		  return true;
		
		return false;
	}

}
