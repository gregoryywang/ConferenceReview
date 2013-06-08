package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Represents a conference with associated dates and deadlines and program chair.
 * @author Roshun
 * @version 2013 Spring
 */
public class Conference extends Observable
{
	/**
	 * The deadline types of papers in the review process.
	 * @author Danielle 
	 */
	public static enum Deadline {SUBMIT_PAPER, REVIEW_PAPER, MAKE_RECOMMENDATION,
		FINAL_DECISION, REVISE_PAPER};
	
	/**
	 * The conference id.
	 */
	private int my_id;
		
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
	 * The list of categories associated with the conference.
	 */
	private List<String> my_categories;
	
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
	 * @param the_submit_paper_deadline the date for authors to submit papers by
	 * @param the_review_paper_deadline the last date for reviewers to review papers
	 * @param the_make_recommendaiton_deadline the last date for subprogram chairs to give recommendations
	 * @param the_final_decision_deadline the last date for a program chair decision
	 * @param the_revise_paper_deadline the last date for an approved paper to be revised
	 */
	public Conference(final int the_conf_id, final Date the_date, final User the_PG_chair, final String the_topic,
			final Date the_submit_paper_deadline,
			final Date the_review_paper_deadline, final Date the_make_recommendation_deadline,
			final Date the_final_decision_deadline, final Date the_revise_paper_deadline)
	{
		super();
	    my_id = the_conf_id;
		my_date = (Date)the_date.clone();
		my_PG_chair = the_PG_chair; //worry about deep copy?
		my_topic = the_topic;
		my_deadlines = new HashMap<Deadline, Date>();
		my_deadlines.put(Deadline.SUBMIT_PAPER, (Date)the_submit_paper_deadline.clone());
		my_deadlines.put(Deadline.REVIEW_PAPER, (Date)the_review_paper_deadline.clone());
		my_deadlines.put(Deadline.MAKE_RECOMMENDATION, (Date)the_make_recommendation_deadline.clone());
		my_deadlines.put(Deadline.FINAL_DECISION, (Date)the_final_decision_deadline.clone());
		my_deadlines.put(Deadline.REVISE_PAPER, (Date)the_revise_paper_deadline.clone());
	}
	
	/**
	 * TEST CONSTRUCTOR.
	 */
	public Conference() {
	  this(0, new Date(System.currentTimeMillis()), new ProgramChair(new User("test", "test", "test", "test", "test")),
		"topic", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
		new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
	  my_categories = new ArrayList<String>();
	}
	
	/**
	 * Create a deep copy of the conference.
	 * @param the_conference the conference.
	 */
	public Conference(final Conference the_conference)
	{
		this(the_conference.my_id, the_conference.my_date, the_conference.my_PG_chair, the_conference.my_topic,
				the_conference.my_deadlines.get(Deadline.SUBMIT_PAPER),
				the_conference.my_deadlines.get(Deadline.REVIEW_PAPER), the_conference.my_deadlines.get(Deadline.MAKE_RECOMMENDATION),
				the_conference.my_deadlines.get(Deadline.FINAL_DECISION), the_conference.my_deadlines.get(Deadline.REVISE_PAPER));
	}
	
	/**
	 * Set the ID of the conference
	 * @param the_conf_id the conference id
	 */
	public void setID(final int the_conf_id)
	{
		my_id = the_conf_id;
	}
	
	/**
	 * Get the conference id
	 * @return the conference id.  Default is 0
	 */
	public int getID()
	{
		return my_id;
	}
	
	/**
	 * Change the date of the conference event.
	 * @param the_date the conference event date.
	 */
	public void setDate(final Date the_date)
	{
		my_date = the_date;
		setChanged();
		notifyObservers(the_date);
	}
	
	/**
	 * Return the date of the conference event.
	 * @return the conference event date.
	 */
	public Date getDate()
	{
		return my_date;
	}
	
	/**
	 * Change the Program Chair for this conference.
	 * @param the_pg_chair the program chair for this conference.
	 */
	public void setProgramChair(final User the_pg_chair)
	{
		my_PG_chair = the_pg_chair;
	}
	
	/**
	 * Get the Program Chair for this conference.  Any change made
	 * to this user will be reflected in the conference copy as well.
	 * @return 
	 */
	public User getProgramChair()
	{
		return my_PG_chair;
	}

	/**
	 * Set the categories for this conference
	 * @param the_categories the categories for papers at the conference.
	 */
	public void setCategories(final List<String> the_categories)
	{
		my_categories = new ArrayList<String>(the_categories);
	}
	
	/**
	 * Get a list of all categories for papers
	 * @return a list of categories for papers
	 */
	public List<String> getCategories()
	{
		return new ArrayList<String>(my_categories);
	}
	
	/**
	 * Set the topic of the conference
	 * @param the_topic the topic (title)
	 */
	public void setTopic(final String the_topic)
	{
		my_topic = the_topic;
	}
	
	/**
	 * Get the title (topic) of this conference
	 * @return the topic
	 */
	public String getTopic()
	{
		return my_topic;
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
		my_deadlines.put(the_deadline, the_date);
	}
	
	/**
	 * Return the deadline date for the the type of deadline.
	 * @param the_deadline the deadline type
	 * @return the date for the deadline type
	 */
	public Date getDeadline(final Deadline the_deadline)
	{
		return my_deadlines.get(the_deadline);
	}
	
	/**
	 * Provide a short title to be displayed.
	 * @return a short title for display purposes.
	 */
	public String shortTitle()
	{
		return my_topic;
	}
	
	/**
	 * @return the short title of this conference (the topic)
	 */
	public String toString()
	{
		return shortTitle();
	}
}
