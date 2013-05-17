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
	 * Default constructor.
	 */
	public User()
	{
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
}
