package model;

import dao.UserDAO;

/**
 * Class to encapsulate a Review in our system.
 * (This Review is based on a Paper Object)
 * @author Levon (method stubs)
 * @author Danielle Tucker
 * @version 1.0 (Spring 2013)
 */
public class Review 
{
	public static final String[] INSTRUCTIONS = {
		"Please provide a numeric rating on a 5-point scale for each question, along with a brief " +
		"rationale for each numeric rating. In doing so, please discuss both the strengths and the " +
		"weaknesses of each paper so that the editors and authors can understand your reasoning./n" +
		"Please phrase your reviews politely; even 'bad' papers represent a lot of work on the part of " + 
		"the authors. The review may be the basis for further revisions of the paper or the work that " +
		"the paper reports. We all know how hurtful a needlessly negative review can be, and how " +
		"helpful a positive one can be; please try to bear that in mind when you are writing yours.",
		
		"Can the content be directly applied by classroom instructors or curriculum designers?",
		
		"Does the work appeal to a broad readership interested in engineering education or is " +
		"it narrowly specialized?",
		
		"Does the work address a significant problem?",
		
		"Does the author build upon relevant references and bodies of knowledge?",
		
		"If a teaching intervention is reported, is it adequately evaluated in terms of its impact " +
		"on learning in actual use?",
		
		"Does the author use methods appropriate to the goals, both for the instructional " +
		"intervention and the evaluation of impact on learning?",
		
		"Did the author provide sufficient detail to replicate and evaluate?",
		
		"Is the paper clearly and carefully written?",
		
		"Does the paper adhere to accepted standards of style, usage, and composition?"};
	
	public static final String[] RATING_SCALE_DESCRIPTORS = {};
	
	private User my_owner;
	
	private int[] my_ratings;
	
	private String my_private_comment;
	
	private String[] my_comments;
	
	public Review()
	{
		this(null);
	}
	
	public Review(final User the_reviewer)
	{
		my_owner = the_reviewer;
		my_comments = new String[INSTRUCTIONS.length];
		my_ratings = new int[INSTRUCTIONS.length];
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
			my_private_comment = the_comment;
		}
		else
		{
			my_private_comment = "No comment";
		}
	}
	
	public String getSPChairComment()
	{
		return my_private_comment;
	}
	
	/**
	 * Set the overall rating for this review of the paper.
	 * @param the_rating the rating (valid 1 - 5)
	 */
	public void setSummaryRating(final int the_rating)
	{
		my_ratings[0] = validateRating(the_rating);
	}
	
	/**
	 * Get the overall rating for this paper
	 * @return the overall rating for this paper
	 */
	public int getSummaryRating()
	{
		return my_ratings[0];
	}
	
	/**
	 * Summary comment to the author
	 * @param the_string the summary overall comment to the author.
	 */
	public void setSummaryComment(final String the_string)
	{
		if(the_string != null)
		{
			my_comments[0] = the_string;
		}
	}
	
	/**
	 * Get the overall comment for the review
	 * @return the overall comment to the author to describe the reasoning for overall rating.
	 */
	public String getSummaryComment()
	{
		return my_comments[0];
	}
	
	/**
	 * Set the author of this review.
	 * @param the_reviewer the author of this review.
	 */
	public void setReviewer(final User the_reviewer) 
	{
		my_owner = the_reviewer;
	}

	/**
	 * Get the author of this review
	 * @return the author of this review.
	 */
	public User getReviewer()
	{
		return my_owner;
	}
	
	/**
	 * Set the rating for the questions.  Invalid question numbers are ignored.
	 * @param the_q the question number on the review form
	 * @param the_value the value (1-5) invalid values are changes to value (1-5)
	 */
	public void setRating(final int the_q, final int the_value)
	{
		if(the_q > 0 && the_q < INSTRUCTIONS.length)
		{
			my_ratings[the_q] = validateRating(the_value);
		}
	}

	/**
	 * Get the rating for a question.
	 * @param the_q the question
	 * @return the rating 1 - 5.  Will return 0 if no rating has been set.
	 */
	public int getRating(final int the_q)
	{
		int result = 0;
		if(the_q > 0 && the_q < my_ratings.length)
		{
			result = my_ratings[the_q];
		}
		return result;
	}
	
	/**
	 * Set the comment for the reason behind the rating for a question
	 * @param the_q the question number
	 * @param the_comment the comment associated with a question's rating.
	 */
	public void setComment(final int the_q, final String the_comment)
	{
		if(the_q > 0 && the_q < INSTRUCTIONS.length)
			my_comments[the_q] = the_comment;
	}

	/**
	 * Get the comment regarding why a rating was set for a particular question
	 * @param the_question the question number (must be valid otherwise "" returned)
	 * @return the comment associated with this question
	 */
	public String getComment(final int the_question)
	{
		if(the_question > 0 && the_question < INSTRUCTIONS.length)
			return my_comments[the_question];
		else
			return "";
	}
	
	/**
	 * The string is the summary rating for this review.
	 * @return the summary rating for this review
	 */
	@Override
	public String toString()
	{
		return Integer.toString(getSummaryRating());
	}

	/**
	 * Sets rating to between 1 and 5 inclusive.
	 * @param the_rating the rating to validate
	 * @return the rating between 1 and 5
	 */
	private int validateRating(int the_rating) 
	{
		int rating = the_rating;
		if(the_rating < 1)
		{
			rating = 1;
		}
		else if(the_rating > 5)
		{
			rating = 5;
		}
		return rating;
	}
}
