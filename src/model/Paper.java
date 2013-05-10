
package model;

import java.io.File;
import java.util.Collection;
import java.util.Observable;


/**
 * This class represents a paper an author may submit for review.
 * @author Danielle Tucker
 *
 */
public class Paper extends Observable
{
	private int my_author_ID;
	
	private String my_title;
	
	private String[] my_keywords;
	
	private String my_category;
	
	private File my_document;
	
	private File my_revised_document;
	
	private Recommendation my_recommendation;
	
	private Collection<Review> my_reviews;
	
	// yeah.... private enum my_status;
	
	private int my_final_status;
	
	public Paper()
	{
		
	}
	
	public Collection<Review> getReviews()
	{
		return null;
	}
	
	public Recommendation getRecommendation()
	{
		return null;
	}

	public User getSPChair()
	{
		return null;
	}
	
	/*  ???what is our enum????
	public enum getStatus()
	{
		
	}
	*/
}
