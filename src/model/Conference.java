package model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Observable;

public class Conference extends Observable
{
	private Date my_date;
	
	private User my_PG_chair;
	
	private String my_topic;
	
	private Collection<String> my_categories;
	
	private Collection<Paper> my_papers;
	
	private Map<String, Date> my_deadlines;
	
	public Conference()
	{
		
	}
}
