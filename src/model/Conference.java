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
	/*
	public Conference(final int the_conf_id, final Date the_date, final User the_PG_chair, final String the_topic,
			final List<String> the_categories, 
			final List<Paper> the_papers, final Map<Deadline,Date> the_deadlines)
	{
		my_id = the_conf_id;
		my_date = (Date)the_date.clone();
		my_PG_chair = the_PG_chair; //worry about deep copy?
		my_topic = the_topic;
		my_categories = new ArrayList<String>(the_categories);
		my_papers = the_papers;  //should we worry about deep copies?
		my_deadlines = the_deadlines; //should we worry about deep copies?
	}
	*/
	
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
			final List<String> the_categories, 
			final List<Paper> the_papers, final Date the_submit_paper_deadline,
			final Date the_review_paper_deadline, final Date the_make_recommendation_deadline,
			final Date the_final_decision_deadline, final Date the_revise_paper_deadline)
	{
		my_id = the_conf_id;
		my_date = (Date)the_date.clone();
		my_PG_chair = the_PG_chair; //worry about deep copy?
		my_topic = the_topic;
		my_categories = new ArrayList<String>(the_categories);
		my_papers = the_papers;  //should we worry about deep copies?
		my_deadlines = new HashMap<Deadline, Date>();
		my_deadlines.put(Deadline.SUBMIT_PAPER, (Date)the_submit_paper_deadline.clone());
		my_deadlines.put(Deadline.REVIEW_PAPER, (Date)the_review_paper_deadline.clone());
		my_deadlines.put(Deadline.MAKE_RECOMMENDATION, (Date)the_make_recommendation_deadline.clone());
		my_deadlines.put(Deadline.FINAL_DECISION, (Date)the_final_decision_deadline.clone());
		my_deadlines.put(Deadline.REVISE_PAPER, (Date)the_revise_paper_deadline.clone());
	}
	
	/**
	 * Create a conference with no associated papers yet.
	 * @param the_conf_id the unique id of the event
	 * @param the_date the date of the event
	 * @param the_PG_chair the Program Chair for this event
	 * @param the_topic the title (topic) of this event
	 * @param the_categories the categories for paper submissions
	 * @param the_submit_paper_deadline the last day for authors to submit papers
	 * @param the_review_paper_deadline the last day for reviewers to submit reviews
	 * @param the_make_recommendation_deadline the last day for SubProgram Chairs to make recommendations
	 * @param the_final_decision_deadline the last day for Program Chairs to accept/decline papers
	 * @param the_revise_paper_deadline the last day for accepted papers to be revised
	 */
	public Conference(final int the_conf_id, final Date the_date, final User the_PG_chair, final String the_topic,
			final List<String> the_categories, final Date the_submit_paper_deadline,
			final Date the_review_paper_deadline, final Date the_make_recommendation_deadline,
			final Date the_final_decision_deadline, final Date the_revise_paper_deadline)
	{
		this(the_conf_id, the_date, the_PG_chair, the_topic, the_categories, new ArrayList<Paper>(),
				the_submit_paper_deadline, the_review_paper_deadline, the_make_recommendation_deadline,
				the_final_decision_deadline, the_revise_paper_deadline);		
	}
	
	/**
	 * Create a new conference with no associated papers yet.
	 * @param the_conf_id the unique id of the event
	 * @param the_date the date of the event
	 * @param the_PG_chair the Program Chair for the event.
	 * @param the_topic the topic for the event.
	 * @param the_categories the categories for papers.
	 * @param the_deadlines the deadlines associated with submitting and reviewing papers.
	 */
	/*
	public Conference(final int the_conf_id, final Date the_date, final User the_PG_chair, final String the_topic,
			final List<String> the_categories, final Map<Deadline,Date> the_deadlines)
	{
		this(the_conf_id, the_date, the_PG_chair, the_topic, the_categories, new ArrayList<Paper>(),
				the_deadlines);
	}
	*/
	
	/**
	 * Default Constructor.
	 */
	public Conference()
	{
		//nothing here
	}
	/**
	 * Create a deep copy of the conference.
	 * @param the_conference the conference.
	 */
	public Conference(final Conference the_conference)
	{
		this(the_conference.my_id, the_conference.my_date, the_conference.my_PG_chair, the_conference.my_topic,
				the_conference.my_categories, the_conference.my_papers, the_conference.my_deadlines.get(Deadline.SUBMIT_PAPER),
				the_conference.my_deadlines.get(Deadline.REVIEW_PAPER), the_conference.my_deadlines.get(Deadline.MAKE_RECOMMENDATION),
				the_conference.my_deadlines.get(Deadline.FINAL_DECISION), the_conference.my_deadlines.get(Deadline.REVISE_PAPER));
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
	 * Change the Program Chair for this conference.
	 * @param the_pg_chair the program chair for this conference.
	 */
	public void set_PG_Chair(final User the_pg_chair)
	{
		my_PG_chair = new User(the_pg_chair);  //should it be a copy or not?
	}
	
	/**
	 * Get the Program Chair for this conference.  Any change made
	 * to this user will be reflected in the conference copy as well.
	 * @return 
	 */
	public User getPG_Chair()
	{
		return my_PG_chair;
	}

	public void setTopic(final String the_topic)
	{
		my_topic = the_topic;
	}
	
	public String getTopic()
	{
		return my_topic;
	}
	
	/**
	 * Add a paper to the conference.
	 * @param the_paper the paper to add to the event.
	 */
	public void addPaper(final Paper the_paper)
	{
		my_papers.add(the_paper);
	}
	
	/**
	 * Return a list of the papers for this conference.  This list is backed
	 * by the list so any changes to the papers will be reflected in the 
	 * the conference's list.
	 * @return a list of papers for this conference.
	 */
	public List<Paper> getPapers()
	{
		return my_papers;
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
	
	/**
	 * Provide a short title to be displayed.
	 * @return a short title for display purposes.
	 */
	public String shortTitle()
	{
		return my_topic;
	}
}
