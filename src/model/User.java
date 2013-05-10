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
	
	private Collection<Conference> my_conferences;
	
	// ?????Organizer ???? //private Organizer my_role;
	
	private String my_first_name;
	
	private String my_last_name;
	
	private String my_password;
	
	private String my_username;
	
	private String my_email;
	
	public User()
	{
		
	}
	
	/* ?Organizer?
	public Organizer getRole(final Conference the_conf)
	{
	}
	*/
	
	public Collection<Conference> getConferences()
	{
		return null;
	}
	
	public String getFullName()
	{
		return "";
	}
	
}
