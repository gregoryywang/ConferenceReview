package model;

import java.util.List;

import service.PaperService;

/**
 * Class to create a SubProgramChair Object and its functionalities.
 * 
 * @author Levon Kechichian
 * @version Spring 2013
 */
public class SubProgramChair extends User
{	
	/**
	 * Constructs a SubProgramChair Object based
	 * on the given parameters.
	 * 
	 * @param the_conference the Conference the User is a SubProgramChair for
	 * @param the_first_name the first name of the User
	 * @param the_last_name	the last name of the User
	 * @param the_password the Users password
	 * @param the_username the Users screen name
	 * @param the_email the Users e-mail
	 */
	public SubProgramChair(final Conference the_conference, final String the_first_name, 
		final String the_last_name, final String the_password, final String the_username, 
		final String the_email)
	{
		super(the_conference, Role.SUB_PROGRAM_CHAIR, the_first_name, the_last_name, 
			the_password, the_username, the_email);
	}
	
	
	public SubProgramChair(final User the_user)
	{
		super(the_user);
		setRole(Role.PROGRAM_CHAIR);
	}
	
	public SubProgramChair()
	{
		//
	}
	
	/**
	 * 
	 * Assigns a Reviewer to a specified Paper.
	 * 
	 * @param the_user the Reviewer that is being assigned to the Paper
	 * @param the_paper the Paper that the User is being assigned to
	 */
	public void assignReviewer(final User the_user, final Paper the_paper, 
		final Conference the_conference)
	{
		PaperService.getInstance().assignPaper(the_paper.getID(), the_user.getID(), 
			the_conference.getID(), Role.REVIEWER);
	}
	
	/**
	 * Submits a Recommendation for a specified Paper.
	 * 
	 * @param the_paper the Paper the Recommendation is being
	 * assigned to
	 */
	public void submitRecommendation(final Recommendation the_recommendation, final Paper the_paper)
	{//need to add date checking to make sure the recommendation is submitted before deadline.
		PaperService.getInstance().addRecommendation(the_recommendation, the_paper);
	}
	
	/**
	 * Method to grab or create a Recommendation for a given paper.
	 * 
	 * @param the_paper the paper you are creating or viewing
	 * the Recommendation for
	 * @return returns the Recommendation associated with the paper;
	 * if none exists it will return a new default Recommendation
	 */
	public Recommendation viewRecommendation(final Paper the_paper)
	{
		Recommendation recommendation = new Recommendation();
		
		if (null != PaperService.getInstance().getRecommendation(the_paper.getID()))
		{
			recommendation = PaperService.getInstance().getRecommendation(the_paper.getID());
		}
		return recommendation;
	}
	
	@Override
	public void setRole(final Role the_role)
	{
		//does nothing because SubProgramChair's role is ALWAYS Role.SUBPROGRAM_CHAIR
	}
	
	public List<Paper> getPapers()
	{
		return PaperService.getInstance().getAssignedPapers(getID(), getConference().getID(), Role.SUB_PROGRAM_CHAIR);
	}
	
	public List<Review> getReviews(final Paper the_paper)
	{
		return PaperService.getInstance().getReviews(the_paper.getID());
	}
}

