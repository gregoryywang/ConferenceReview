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
	private int my_id;
	
	private Conference my_conference;
	
	private Role my_role;
	
	private String my_first_name;
	
	private String my_last_name;
	
	private String my_password;
	
	private String my_username;
	
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
	
	public Conference getConference()
	{
		return my_conference; //need to make sure a copy is returned...
	}

	public Role getRole()
	{
		return my_role;
	}
	
	public String getFullName()
	{
		return my_first_name + " " + my_last_name;
	}
	
}
