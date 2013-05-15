package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Administrator extends User
{

	public Administrator(final int the_id, final String the_first_name, final String the_last_name, 
			final String the_username, final String the_password, final String the_email)
	{
		//super(the_id, the_first_name, the_last_name, 
			//	the_username, the_password, the_email);
		super.setRole(Role.ADMIN); //not sure if this is a good idea.  What is another way?
	}
	
	public void createConference()
	{
		Map<Conference.Deadline, Date> deadlines = new HashMap<Conference.Deadline, Date>();
		
		
	}

	public void changeDate(final Date the_date)
	{
		
	}
	
}
