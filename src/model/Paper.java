
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import common.ReferenceObject;

import dao.ConferenceDAO;
import dao.PaperDAO;


/**
 * This class represents a paper an author may submit for review.
 * @author Danielle Tucker
 *
 */
public class Paper extends Observable
{
	private final PaperDAO paper_dao = new PaperDAO();
	private final ConferenceDAO conference_dao = new ConferenceDAO();
	
	private int my_paper_ID;
		
	private User my_author;
	
	private String my_title;
	
	private String my_keywords;
	
	private String my_abstract;
	
	private String my_category;
	
	private String my_document_path;
	
	private String my_revised_document_path;
	
	private Recommendation my_recommendation;
	
	private List<Review> my_reviews;
	
	private Status my_status = Status.SUBPROGRAM_CHAIR_NEEDED;
	
	public Paper()
	{
		
	}
	public void setID(final int the_id)
	{
		my_paper_ID = the_id;
	}
	public int getID()
	{
		return my_paper_ID;
	}
	public void setAuthor(final User the_author)
	{
		my_author = the_author;
	}
	public User getAuthor()
	{
		return my_author;
	}
	public void setTitle(final String the_title)
	{
		my_title = the_title;
	}
	public String getTitle()
	{
		return my_title;
	}
	public void setKeywords(final String the_keywords)
	{
		my_keywords = the_keywords;
	}
	public String getKeywords()
	{
		return my_keywords;
	}
	public String getAbstract()
	{
		return my_abstract;
	}
	public void setAbstract(final String the_abstract)
	{
		my_abstract = the_abstract;
	}
	public String getCategory()
	{
		return my_category;
	}
	public void setCategory(final String the_category)
	{
		my_category = the_category;
	}
	public void setDocumentPath(final String the_document_path)
	{
		my_document_path = the_document_path;
	}
	public String getDocumentPath()
	{
		return my_document_path;
	}
	public void setRevisedDocumentPath(final String the_path)
	{
		my_revised_document_path = the_path;
	}
	public String getRevisedDocumentPath()
	{
		return my_revised_document_path;
	}
	
	/**
	 * 
	 * @return List of reference objects of reviews (summary score, review_id)
	 */
	public List<ReferenceObject> getReviews()
	{
//		return paper_dao.getReviewsRef(my_paper_ID);
		return new ArrayList<ReferenceObject>();
	}
	
	public Recommendation getRecommendation()
	{
		return null;
	}

	public User getSPChair()
	{
		return null;
	}
	public Status getStatus() //fix me
	{
		return my_status;
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
