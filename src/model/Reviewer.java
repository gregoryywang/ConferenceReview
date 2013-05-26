package model;

import java.util.List;

import model.Conference.Deadline;

import service.PaperService;

public class Reviewer extends User
{
	public Reviewer(final User the_user)
	{
		super(the_user);
		setRole(Role.REVIEWER);
	}
	
	
	public void writeReview()
	{
		
	}
	
	public void modifyReview()
	{
		if(getConference().getDeadline(Deadline.REVIEW_PAPER).after(currentDate()))
		{
			
		}
	}
	
	public Review getReview()
	{
		//check business rules
		return new Review();
	}
	
	public List<Paper> viewPapers()
	{
		//Check on business rules for viewing papers by a reviewer.  How do we limit the reviews seen?
		return PaperService.getInstance().getAssignedPapers(getID(), getConference().getID(), getRole());
	}
	
	@Override
	/**
	 * Will always set the role to Role.REVIEWER
	 */
	public void setRole(Role the_role)
	{
		super.setRole(Role.REVIEWER);
	}
	
}
