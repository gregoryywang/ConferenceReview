package model;

import service.PaperService;
import dao.UserDAO;

/**
 * Recommendations by a SubProgram Chair for a paper.
 * @author Danielle Tucker
 * @version 21 May 2013
 *
 */
public class Recommendation 
{
	/**
	 * Descriptors for each value of the rating scale from high value to low.
	 */
	public static final String[] RATING_SCALE_HIGH_TO_LOW = {
		"Strong Accept", "Accept", "Neutral", "Reject", "Strong Reject"};
	/**
	 * Maximum rating for a recommendation.
	 */
	private static final int MAX_RATING = 5;
	
	/**
	 * Minimum rating for a recommendation.
	 */
	private static final int MIN_RATING = 1;
	
	/**
	 * The SubProgram Chair User for this Recommendation.
	 */
	private User my_owner;
	
	/**
	 * The rating given to the paper.
	 */
	private int my_rating;
	
	/**
	 * The comments provided with the rating.
	 */
	private String my_comments;
	
	/**
	 * Default constructor.
	 */
	public Recommendation()
	{
		//Left blank on purpose.
		my_owner = new User();
		setRating(0);
		my_comments = "";
	}
	
	public Recommendation(final User the_subPrg_Chair, final int the_rating, final String the_comments)
	{
		my_owner = the_subPrg_Chair;
		setRating(the_rating);
		if(the_comments != null)
		{
			my_comments = the_comments;
		}
		else
		{
			my_comments = "";
		}
	}
	
	
	
	public void setRecommender(final User the_user)
	{
		my_owner = the_user;
	}
	
	public User getRecommender()
	{
		return my_owner;
	}
	
	public void setRating(final int the_rating)
	{
		if(the_rating < MIN_RATING)
		{
			my_rating = 1;
		}
		else if(the_rating > MAX_RATING)
		{
			my_rating = 5;
		}
		else
		{
			my_rating = the_rating;
		}
	}
	public int getRating()
	{
		return my_rating;
	}
	
	public void setComments(final String the_comments)
	{
		my_comments = the_comments;
	}
	public String getComments()
	{
		return my_comments;
	}
}
