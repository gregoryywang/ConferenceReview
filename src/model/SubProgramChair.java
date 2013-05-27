package model;

import service.PaperService;
import dao.UserDAO;

/**
 * Class to create a SubProgramChair Object and its functionalities.
 * 
 * @author Levon Kechichian
 * @version Spring 2013
 */
public class SubProgramChair extends User
{
	// Hmmmmm.....do I need any fields?
	
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
	public SubProgramChair(final Conference the_conference, final String the_first_name, final String the_last_name, 
		final String the_password, final String the_username, final String the_email)
	{
		super(the_conference, Role.SUB_PROGRAM_CHAIR, the_first_name, the_last_name, the_password, the_username, the_email);
	}
	
	/**
	 * Assigns a Reviewer to a Collection of Reviewers.
	 * 
	 * @param the_user the User that is being assigned to the Collection
	 */
	public void assignReviewerToPool(final User the_user)
	{
		// What the ????
		// How is this method different than the one below????
		// I am assuming we just add the_user to the database of Users?
		// and I will code it that way...
		new UserDAO().getUsers(Role.REVIEWER).add(the_user);
	}
	
	/**
	 * Assigns a Reviewer to a specified Paper.
	 * 
	 * @param the_user the Reviewer that is being assigned to the Paper
	 * @param the_paper the Paper that the User is being assigned to
	 */
	public void assignReviewer(final User the_user, final Paper the_paper)
	{
		//We need a assignReviewer method in the PaperService class
		//The following will work to assign a Reviewer to the Paper until that method is created
		final Review user_review = new Review(the_user);
		user_review.setReviewer(the_user.getID());
		PaperService.getInstance().addReview(user_review, the_paper.getID());
	}
	
	/**
	 * Submits a Recommendation for a specified Paper.
	 * 
	 * @param the_paper the Paper the Recommendation is being
	 * assigned to
	 */
	public void submitRecommendation(final Recommendation the_recommendation, final Paper the_paper)
	{
		PaperService.getInstance().addRecommendation(the_recommendation, the_paper.getID());
	}
}
