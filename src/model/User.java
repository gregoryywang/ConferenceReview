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
	
	private Conference my_conference;
	
	private Role my_role;
	
	private String my_first_name;
	
	private String my_last_name;
	
	private final String my_username;

	private String my_password;
	
	private String my_email;
	
	public User(final int the_id, final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		my_id = the_id;
		my_first_name = the_first_name;
		my_last_name = the_last_name;
		my_username = the_username;
		my_password = the_password;
		my_email = the_email;
	}
	
	public int getID()
	{
		return my_id;
	}
	
	public void setConference(final Conference the_conference)
	{
		my_conference = the_conference; //need to make a copy to be safe...
	}
	
	public Conference getConference()
	{
		return my_conference; //need to make sure a copy is returned...
	}

	public void setRole(final Role the_role)
	{
		my_role = the_role;
	}
	
	public Role getRole()
	{
		return my_role;
	}
	
	public void setFirstName(final String the_first_name)
	{
		my_first_name = the_first_name;
	}
	
	public String getFirstName()
	{
		return my_first_name;
	}
	
	public void setLastName(final String the_last_name)
	{
		my_last_name = the_last_name;
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
	}
	
	public String getEmail()
	{
		return my_email;
	}
	
}
