package model;

import java.util.List;

import model.Conference.Deadline;

import service.PaperService;

/**
 * Encapsulates the functionality of a Reviewer for a paper.
 * @author Danielle Tucker
 * @version 2013 May
 */
public class Reviewer extends User
{
	/**
	 * Create a Reviewer 
	 * @param the_user the user to make a reviewer with all associated functionality
	 */
	public Reviewer(final User the_user)
	{
		super(the_user);
		setRole(Role.REVIEWER);
	}
	
	/**
	 * Add a review to the paper (make a review for this paper)
	 * @param the_review the review to attach to this paper
	 * @param the_paper the paper
	 * @throws Exception if trying to submit the paper after the deadline
	 */
	public void addReview(Review the_review, Paper the_paper) throws Exception
	{
		if(getConference().getDeadline(Deadline.REVIEW_PAPER).after(currentDate()))
		{
			PaperService.getInstance().addReview(the_review, the_paper);
		}
		else
		{
			throw new Exception("Submission must be before Review Deadline of " + getConference().getDeadline(Deadline.SUBMIT_PAPER) +
					"for this conference. Today's date is: " + currentDate() );
		}
	}
	
	/**
	 * Get the review associated with this reviewer and paper
	 * @param the_paper_id the id of the paper
	 * @return the review from this user.  If none exists a default Review is returned.
	 */
	public Review getReview(final int the_paper_id)
	{
		Review the_review = new Review();
		List<Review> reviews = PaperService.getInstance().getReviews(the_paper_id);
		for(int i = 0; i < reviews.size(); i++)
		{
			Review curr_review = reviews.get(i);
			if(curr_review.getReviewer().getID() == getID())
			{
				the_review = curr_review;
			}
		}
		return the_review;
	}

	/**
	 * View a list of all papers assigned to this reviewer.
	 * @return a list of papers which the reviewer has been assigned.
	 * All reviews except any which this user have been assigned have been stripped out.
	 */
	public List<Paper> viewPapers()
	{
		List<Paper> papers = PaperService.getInstance().getAssignedPapers(getID(), getConference().getID(), getRole());
		for(Paper paper: papers)
		{
			List<Review> reviews = paper.getReviews();
			for(int i = 0; i < reviews.size(); i++)
			{
				if(reviews.get(i).getReviewer().getID() != getID())
				{ //if reviewer of the review is not current user, remove from paper so
					//only reviews which were written by this user may be seen.
					reviews.remove(i);
				}
			}
		}
		return papers;
	}
	
	/**
	 * Determine if the Reviewer had completed their review.
	 * @param the paper to check.
	 * @return if the reviewer has made a review for this paper.
	 */
	public boolean reviewComplete(final Paper the_paper)
	{
		List<Review> the_reviews = the_paper.getReviews();
		boolean result = false;
		for(Review review: the_reviews)
		{
			if(review.getReviewer().getID() == getID())
			{
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Determine if a review can be submitted.  Reviews must
	 * be submitted before the review paper deadline for this conference.
	 * @return if the paper is open for review
	 */
	public boolean canAddReview()
	{
		boolean result = false;
		if (getConference().getDeadline(Deadline.REVIEW_PAPER).after(currentDate()))
		{
			result = true;
		}
		return result;
	}
	
	@Override
	/**
	 * Will always set the role to Role.REVIEWER
	 * @param the_role the role to change this user to (ignored);
	 */
	public void setRole(Role the_role)
	{
		super.setRole(Role.REVIEWER);
	}
	
}
