package model;

import java.util.Date;

public class Administrator extends User
{

	public Administrator(final int the_id, 
			final String the_first_name, final String the_last_name, 
				final String the_username, final String the_password, final String the_email)
	{
		super(the_id, null, Role.ADMIN, the_first_name, the_last_name, 
				the_username, the_password, the_email);
	}
	
	public void createConference(final Conference the_conference)
	{	
		
	}

	public void changeDate(final Date the_date)
	{
		
	}
	
}
