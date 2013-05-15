package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Conference extends Observable
{
	/**
	 * The deadline types of papers in the review process.
	 * @author Danielle T
	 * @version 2013 May
	 */
	public static enum Deadline {SUBMIT_PAPER, REVIEW_PAPER, MAKE_RECOMMENDATION,
		FINAL_DECISION, REVISE_PAPER};
		
	/**
	 * The date of the conference event.
	 */
	private Date my_date;
	
	/**
	 * The PG chair for this conference.
	 */
	private User my_PG_chair;
	
	/**
	 * The topic for this conference.
	 */
	private String my_topic;
	
	/**
	 * The list of categories for the conference.
	 */
	private List<String> my_categories;
	
	/**
	 * The list of papers associated with this conference.
	 */
	private List<Paper> my_papers = new ArrayList<Paper>();
	
	/**
	 * The Map of Deadlines and Dates associated with these deadlines for
	 * submitting and reviewing papers.
	 */
	private Map<Deadline, Date> my_deadlines;
	
	/**
	 * Create a new conference.
	 * @param the_date the date of the event
	 * @param the_PG_chair the Program Chair for the event.
	 * @param the_topic the topic for the event.
	 * @param the_categories the categories for papers.
	 * @param the_papers the papers associated with this conference.
	 * @param the_deadlines the deadlines associated with submitting and reviewing papers.
	 */
	public Conference(final Date the_date, final User the_PG_chair, final String the_topic,
			final List<String> the_categories, 
			final List<Paper> the_papers, final Map<Deadline,Date> the_deadlines)
	{
		my_date = (Date)the_date.clone();
		my_PG_chair = new User(the_PG_chair);
		my_topic = the_topic;
		my_categories = new ArrayList<String>(the_categories);
		my_papers = new ArrayList<Paper>(the_papers);
		my_deadlines = new HashMap<Deadline, Date>(the_deadlines);
	}
	
	/**
	 * Create a new conference with no associated papers yet.
	 * @param the_date the date of the event
	 * @param the_PG_chair the Program Chair for the event.
	 * @param the_topic the topic for the event.
	 * @param the_categories the categories for papers.
	 * @param the_deadlines the deadlines associated with submitting and reviewing papers.
	 */
	public Conference(final Date the_date, final User the_PG_chair, final String the_topic,
			final List<String> the_categories, final Map<Deadline,Date> the_deadlines)
	{
		this(the_date, the_PG_chair, the_topic, the_categories, new ArrayList<Paper>(),
				the_deadlines);
	}
	
	/**
	 * Create a deep copy of the conference.
	 * @param the_conference the conference.
	 */
	public Conference(final Conference the_conference)
	{
		this(the_conference.my_date, the_conference.my_PG_chair, the_conference.my_topic,
				the_conference.my_categories, the_conference.my_papers, the_conference.my_deadlines);
	}
	
	/**
	 * Change the date of the conference event.
	 * @param the_date the conference event date.
	 */
	public void setDate(final Date the_date)
	{
		my_date = (Date)the_date.clone();
		setChanged();
		notifyObservers(the_date);
	}
	
	/**
	 * Return the date of the conference event.
	 * @return the conference event date.
	 */
	public Date getDate()
	{
		return (Date)my_date.clone();
	}
	
	/**
	 * Add a paper to the conference.
	 * @param the_paper the paper to add to the event.
	 */
	public void addPaper(final Paper the_paper)
	{
		my_papers.add(the_paper);  //should it be a copy of the paper or the paper itself?
	}
	
	/**
	 * Change the Program Chair for this conference.
	 * @param the_pg_chair the program chair for this conference.
	 */
	public void set_PG_Chair(final User the_pg_chair)
	{
		my_PG_chair = new User(the_pg_chair);  //should it be a copy or not?
	}
	
	/**
	 * Change a deadline for the conference.  Assumes dates are valid, meaning
	 * that the change of a deadline does not conflict with another deadline.
	 * submit_paper < review_paper < make_recommendation < final_decision < revise_paper
	 * @param the_deadline the deadline type
	 * @param the_date the date for the deadline
	 */
	public void setDeadline(final Deadline the_deadline, final Date the_date)
	{
		my_deadlines.put(the_deadline, (Date)the_date.clone());
	}
	
	/**
	 * Return the deadline date for the the type of deadline.
	 * @param the_deadline the deadline type
	 * @return the date for the deadline type
	 */
	public Date getDeadline(final Deadline the_deadline)
	{
		return (Date)my_deadlines.get(the_deadline).clone();
	}
}
