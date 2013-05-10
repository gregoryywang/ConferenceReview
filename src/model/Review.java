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
		
	}
	
	public String getSPChairComment()
	{
		return "";
	}
	
	public int getSummaryRating()
	{
		return 0;
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
