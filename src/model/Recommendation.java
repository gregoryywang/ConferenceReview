 package model;


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
	private String my_comments = "No Comment.";
	
	/**
	 * Default constructor.
	 */
	public Recommendation()
	{
		//Left blank on purpose.
		my_owner = new User();
		my_comments = "";
		my_rating = 0;
	}

	/**
	 * Constructor which takes the author, rating, and comments
	 * @param the_subPrg_Chair the author of the recommendation
	 * @param the_rating the rating (1 to 5)
	 * @param the_comments the comments
	 */
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
	
	/**
	 * Set the owner of this recommendation
	 * @param the_user the user (subprogram chair)
	 */
	public void setRecommender(final User the_user)
	{
		my_owner = the_user;
	}
	
	/**
	 * Get the author of this recommendation (subprogram chair)
	 * @return the author of this recommendation
	 */
	public User getRecommender()
	{
		return my_owner;
	}
	
	/**
	 * Set the rating
	 * @param the_rating the value (valid values 1 - 5 and other values adjusted to this range)
	 */
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
	
	/**
	 * Get the rating value
	 * @return the rating
	 */
	public int getRating()
	{
		return my_rating;
	}
	
	/**
	 * Set the comments to go along with the rating
	 * @param the_comments the comments
	 */
	public void setComments(final String the_comments)
	{
		if(the_comments != null)
		{
			my_comments = the_comments;
		}
	}
	
	/**
	 * Get the comments for this rating
	 * @return the comments
	 */
	public String getComments()
	{
		return my_comments;
	}
	
	/**
	 * The string rating of this recommendation
	 * @return the string representation of this object which is the value of the rating.
	 */
	public String toString()
	{
		String result = "--none--";
		if(my_rating != 0)
		{
			result = String.valueOf(my_rating); 
		}
		return result;
	}
}
