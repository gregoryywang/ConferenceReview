package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Conference extends Observable
{
	public static enum Deadline {SUBMIT_PAPER, REVIEW_PAPER, MAKE_RECOMMENDATION,
		FINAL_DECISION, REVISE_PAPER};
		
	private Date my_date;
	
	private User my_PG_chair;
	
	private String my_topic;
	
	private List<String> my_categories = new ArrayList<String>();
	
	private List<Paper> my_papers = new ArrayList<Paper>();
	
	private Map<Deadline, Date> my_deadlines = new HashMap<Deadline,Date>();
	
	public Conference(final Date the_date, final User the_PG_Chair, final String the_topic,
			final List<String> the_categories, final Map<Deadline,Date> the_deadlines)
	{
	}
	public Conference(final Conference the_conference)
	{
		
	}
	public void setDate(final Date the_date)
	{
		my_date = (Date)the_date.clone();
	}
	public Date getDate()
	{
		return (Date)my_date.clone();
	}
	public void addPaper(final Paper the_paper)
	{
		my_papers.add(the_paper);
	}
	public void set_PG_Chair(final User the_pg_chair)
	{
		my_PG_chair = new User(the_pg_chair);
	}
	public void setDeadline(final Deadline the_deadline, final Date the_date)
	{
		my_deadlines.put(the_deadline, (Date)the_date.clone());
	}
	public Date getDeadline(final Deadline the_deadline)
	{
		return (Date)my_deadlines.get(the_deadline).clone();
	}
}
