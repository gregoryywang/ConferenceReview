package model;

import java.util.Collection;
import java.util.Observable;

/**
 * Test Class for User
 * @author yongyuwang
 *
 */
public class User extends Observable
{
	private final int my_id;
	
	//push this down to childern?
	private Conference my_conference;
	//push this down to children?
	private Role my_role;
	
	private String my_first_name;
	
	private String my_last_name;
	
	private final String my_username;

	private String my_password;
	
	private String my_email;
	
	public User(final int the_id, final Conference the_conference, final Role the_role, 
			final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		my_id = the_id;
		my_conference = new Conference(the_conference);
		my_role = the_role;
		my_first_name = the_first_name;
		my_last_name = the_last_name;
		my_username = the_username;
		my_password = the_password;
		my_email = the_email;
	}

	//what would default conference be?????
	/*
	public User(final int the_id, final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		this(the_id, new Conference(), Role.USER, the_first_name, the_last_name, 
				the_username, the_password, the_email);
	}
	*/
	
	public User(final User the_user)
	{
		this(the_user.getID(), the_user.getConference(), the_user.getRole(),
				the_user.getFirstName(), the_user.getLastName(), 
				the_user.getUsername(), the_user.getPassword(), the_user.getEmail());		
	}

	public int getID()
	{
		return my_id;
	}
	
	public void setConference(final Conference the_conference)
	{
		my_conference = the_conference; //need to make a copy to be safe...
		setChanged();
		notifyObservers(the_conference);
	}
	
	public Conference getConference()
	{
		return my_conference; //need to make sure a copy is returned...
	}

	public void setRole(final Role the_role)
	{
		my_role = the_role;
		setChanged();
		notifyObservers(the_role);
	}
	
	public Role getRole()
	{
		return my_role;
	}
	
	public void setFirstName(final String the_first_name)
	{
		my_first_name = the_first_name;
		setChanged();
		notifyObservers(my_first_name);
	}
	
	public String getFirstName()
	{
		return my_first_name;
	}
	
	public void setLastName(final String the_last_name)
	{
		my_last_name = the_last_name;
		setChanged();
		notifyObservers(my_last_name);
	}
	
	public String getLastName()
	{
		return my_last_name;
	}
	
	public String getFullName()
	{
		return my_first_name + " " + my_last_name;
	}
	
	public String getUsername()
	{
		return my_username;
	}
	
	public String getPassword()
	{
		return my_password;
	}
	
	public void setEmail(final String the_email)
	{
		my_email = the_email;
		setChanged();
		notifyObservers(my_email);
	}
	
	public String getEmail()
	{
		return my_email;
	}
	
}
