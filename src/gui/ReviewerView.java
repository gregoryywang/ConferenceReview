package gui;

import javax.swing.JPanel;

import model.Paper;
import model.Review;
import model.Reviewer;
import model.User;

public class ReviewerView extends JPanel
{
	private User my_user;
	
	private Review my_review;
	
	private Paper my_paper;
	
	public ReviewerView()
	{
		super();
		my_user = new Reviewer(new User());
		my_paper = new Paper();
		my_review = new Review();
	}
	
	public ReviewerView(final User the_user, final Paper the_paper)
	{
		super();
		my_user = the_user;
		my_paper = the_paper;
		my_review = ((Reviewer) the_user).getReview(the_paper.getID());
	}
	
	public void start()
	{
		
	}
}
