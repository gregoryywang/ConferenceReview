package model;

/**
 * Class to encapsulate a Review in our system.
 * (This Review is based on a Paper Object)
 * 
 * @author Levon Kechichian
 * @version 1.0 (Spring 2013)
 */
public class Review 
{
	public static final String[] INSTRUCTIONS = {};
	
	private int my_owner_ID;
	
	private int[] my_ratings;
	
	private String[] my_comments;
	
	public Review()
	{
		my_comments = new String[11];
		my_ratings = new int[11];
	}
	
	/**
	 * Change the comments to the SubProgram Chair.
	 * @param the_comment The private comment that only the SubProgram
	 * Chair can see.
	 */
	public void setSPChairComment(final String the_comment)
	{
		if(the_comment != null)
		{
			my_comments[0]= the_comment;
		}
		else
		{
			my_comments[0] = "No comment";
		}
	}
	
	public String getSPChairComment()
	{
		return my_comments[0];
	}
	
	/**
	 * Set the overall rating for this review of the paper.
	 * @param the_rating the rating (valid 1 - 5)
	 */
	public void setSummaryRagint(final int the_rating)
	{
		
	}
	public int getSummaryRating()
	{
		return my_ratings[11];
	}
	
	public String getSummaryComment()
	{
		return "";
	}
	
	public User getReviewer()
	{
		return null;
	}
	
	public int getRating(final int the_q)
	{
		return 0;
	}
	
	public String getComment(final int the_q)
	{
		return "";
	}
	
	public void setRating(final int the_q, final int the_value)
	{
		
	}
}
