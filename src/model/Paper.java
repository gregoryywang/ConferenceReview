
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import common.ReferenceObject;

import dao.ConferenceDAO;
import dao.PaperDAO;
import dao.UserDAO;


/**
 * This class represents a paper an author may submit for review.
 * @author Danielle Tucker
 *
 */
public class Paper extends Observable
{
	private final PaperDAO paper_dao = new PaperDAO();
	private final ConferenceDAO conference_dao = new ConferenceDAO();
	private final UserDAO user_dao = new UserDAO();

	private int my_paper_ID;
		
	private User my_author;
	
	private String my_title;
	
	private String my_keywords;
	
	private String my_abstract;
	
	private String my_category;
	
	private String my_document_path;
	
	private String my_revised_document_path;
	
	private Status my_status = Status.SUBPROGRAM_CHAIR_NEEDED;
	
	public Paper()
	{
		
	}
	
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
			final String the_abstract, final String the_category, final String the_document_path)
	{
		my_author = the_author;
		my_title = the_title;
		my_keywords = the_keywords;
		my_abstract = the_abstract;
		my_document_path = saveDocument(the_document_path);
		
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
	
	/**
	 * Set the document path for the text of the paper.
	 * Do not use to put in file of user's document path!! Use saveDocument() first.
	 * @param the_document_path the document path on the program's file system.
	 */
	public void setDocumentPath(final String the_document_path)
	{
		my_document_path = the_document_path;
	}
	
	/**
	 * The internal document path for the text of this paper.
	 * @return the document path for the text of this paper.
	 */
	public String getDocumentPath()
	{
		return my_document_path;
	}
	
	/**
	 * Set the document path for the revised text of the paper
	 * after the paper has been approved.  Do not use to put in file of 
	 * user's document path!!  Use saveDocument() first.
	 * @param the_path the document path on the program's file system.
	 */
	public void setRevisedDocumentPath(final String the_path)
	{
		my_revised_document_path = the_path;
	}
	
	/**
	 * The internal document path for the text of this revised paper.
	 * @return the document path for the text of this revised paper.  (May be null.)
	 */
	public String getRevisedDocumentPath()
	{
		return my_revised_document_path;
	}
	
	public User getSPChair()
	{
		return null;
	}
	public Status getStatus() //fix me
	{
		return my_status;
	}
	
	public void savePaper()
	{
		
	}
	public void addReview(Review the_review)
	{
		the_review.saveReview(my_paper_ID);
	}

	public List<Review> getReviews()
	{
		return new ArrayList<Review>();
	}
	
	public void addRecommendation(Recommendation the_recommendation)
	{
		the_recommendation.saveRecommendation(my_paper_ID);
	}
	
	/**
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
