package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Paper;
import model.Reviewer;
import model.User;

/**
 * Constructs a new form from a Review
 * 
 * @author Levon
 * @version Spring 2013
 */
public class ReviewerForm extends JFrame
{
	private User my_user;
	private Paper my_paper;
	private Reviewer my_reviewer;
	
	public ReviewerForm()
	{
		super("Reviewer Form");
		my_user = new User();
		my_paper = new Paper();
		my_reviewer = new Reviewer(my_user);
	}
	
	public ReviewerForm(final User the_user, final Paper the_paper, final Reviewer the_reviewer)
	{
		super("Reviewer Form");
		my_user = the_user;
		my_paper = the_paper;
		my_reviewer = the_reviewer;
	}
	
	/**
	 * Private class to populate the panel with the
	 * given Reviewer.
	 * 
	 * @author Levon
	 * @version Spring 2013
	 */
	private class ReviewerPanel extends JPanel
	{
		
	}
}
