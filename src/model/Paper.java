
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import service.PaperService;
/**
 * This class represents a paper an author may submit for review.
 * @author Danielle Tucker
 */
public class Paper extends Observable
{
	/**
	 * The unique identifier for the paper.
	 */
	private int my_paper_ID;
		
	/**
	 * The author of this paper.
	 */
	private User my_author;
	
	/**
	 * The title of this paper.
	 */
	private String my_title;
	
	/**
	 * Keywords used for searching for this paper.
	 */
	private String my_keywords;
	
	/**
	 * The abstract.
	 */
	private String my_abstract;
	
	/**
	 * The conference category this paper belongs to.
	 */
	private String my_category;
	
	/**
	 * Stores the contents of the paper.
	 */
	private String content;
	
	/**
	 * The status of the paper in the review process.
	 */
	private Status my_status = Status.SUBPROGRAM_CHAIR_NEEDED;
	
	/**
	 * Null constructor for a paper.
	 */
	public Paper(){}
	
	/**
	 * Create a paper with no associated reviews or decisions.
	 * @param the_author the author of this paper.
	 * @param the_title the title of this paper.
	 * @param the_keywords the keywords to describe this paper.
	 * @param the_abstract the abstract for this paper.
	 * @param the_category the conference category associated with this paper.
	 * @param the_document_path the user path for this document.
	 */
	public Paper(final User the_author, final String the_title, final String the_keywords,
			final String the_abstract, final String the_category, final String content)
	{
		my_author = the_author;
		my_title = the_title;
		my_keywords = the_keywords;
		my_abstract = the_abstract;
		my_category = the_category;
		this.content = content;
	}
	
	/**
	 * Set the unique id of this paper.
	 * @param the_id the unique id of this paper.
	 */
	public void setID(final int the_id)
	{
		my_paper_ID = the_id;
	}
	
	/**
	 * The id of this paper
	 * @return the id of this paper
	 */
	public int getID()
	{
		return my_paper_ID;
	}
	
	/**
	 * Set the author of this paper.
	 * @param the_author
	 */
	public void setAuthor(final User the_author)
	{
		my_author = the_author;
	}
	
	/**
	 * The author of this paper.
	 * @return the author of this paper.
	 */
	public User getAuthor()
	{
		return my_author;
	}
	
	/**
	 * The title of the paper
	 * @param the_title the title of the paper.
	 */
	public void setTitle(final String the_title)
	{
		my_title = the_title;
	}
	
	/**
	 * Get the title of the paper.
	 * @return the title of the paper.
	 */
	public String getTitle()
	{
		return my_title;
	}
	
	/**
	 * Set the keywords for the search of this paper.
	 * @param the_keywords the string of keywords used to search the paper.
	 */
	public void setKeywords(final String the_keywords)
	{
		my_keywords = the_keywords;
	}
	
	/**
	 * The keywords for searching this paper.
	 * @return the keywords for searching this paper.
	 */
	public String getKeywords()
	{
		return my_keywords;
	}
	
	/**
	 * View the abstract for this paper.
	 * @return the abstract for this paper.
	 */
	public String getAbstract()
	{
		return my_abstract;
	}
	
	/**
	 * Set the abstract for this paper.
	 * @param the_abstract the abstract
	 */
	public void setAbstract(final String the_abstract)
	{
		my_abstract = the_abstract;
	}
	
	/**
	 * The conference category that this paper belongs to.
	 * @return the conference category associated with this paper
	 */
	public String getCategory()
	{
		return my_category;
	}
	
	/**
	 * Set the conference category that this paper belongs to.
	 * @param the_category the conference category
	 */
	public void setCategory(final String the_category)
	{
		my_category = the_category;
	}
	
	public String getContent() {
	  return content;
	}
	
	public void setContent(final String content) {
	  this.content = content;
	}
	
	/**
	 * FIX ME!
	 * @return
	 */
	public User getSPChair()
	{
		return null;
	}
	
	/**
	 * FIX ME!
	 * Determine the status of the Paper in the review process.
	 * @return the status of the paper.
	 */
	public Status getStatus() //fix me
	{
		return my_status;
	}
	
	/**
	 * FIX ME!  Do we even need?
	 */
	public void savePaper()
	{
		
	}
	
	/**
	 * FIX ME!  Should not use PaperService.
	 * Associate a review with this paper.
	 * @param the_review the review
	 */
	public void addReview(Review the_review)
	{
		PaperService.getInstance().addReview(the_review, my_paper_ID);
	}

	/**
	 * FIX ME!
	 * All Reviews associated with this paper.
	 * @return all reviews associated with this paper.
	 */
	public List<Review> getReviews()
	{
		return new ArrayList<Review>();
	}
	
	/**
	 * FIX ME!
	 * Associate a recommendation with this paper.
	 * @param the_recommendation the recommendation
	 */
	public void addRecommendation(Recommendation the_recommendation)
	{
		the_recommendation.saveRecommendation(my_paper_ID);
	}
	
	/**
	 * FIX ME!
	 * Get the SubProgram Chair's recommendation associated with this paper.
	 * @return the SubProgram Chair's recommendation.  May be null.
	 */
	public Recommendation getRecommendation()
	{
		return null;
	}

	/**
	 * Copies file into program's file storage area and returns the location
	 * of the storage.
	 * @param the_local_path the path of the file on the user's local file system
	 * @return the path of the file in the program's space.
	 */
	public final String saveDocument(String the_local_path)
	{
		return "";
	}
	
	public boolean equals(Object the_object)
	{
		boolean result = false;
		if(this == the_object)
		{
			result = true;
		}
		if(the_object != null && the_object.getClass()==getClass())
		{
			final Paper other_paper = (Paper) the_object;
			if(other_paper.getID() == getID() &&
				other_paper.getAuthor().equals(getAuthor()) &&
				other_paper.getTitle().equals(getTitle()))
			{
				result = true;
			}
		}
		return result;
	}
}
