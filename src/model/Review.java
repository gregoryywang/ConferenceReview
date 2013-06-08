package model;


/**
 * Class to encapsulate a Review in our system.
 * (This Review is based on a Paper Object)
 * @author Levon (method stubs & QUESTIONS array & updating comments
 * & ratings references)
 * @author Danielle Tucker
 * @version Spring 2013
 */
public class Review 
{
	/**
	 * The instructions for the reviewer.
	 */
	public static final String INSTRUCTIONS =
		"Please provide a numeric rating on a 5-point scale for each question, along with a brief " +
		"rationale for each numeric rating. In doing so, please discuss both the strengths and the " +
		"weaknesses of each paper so that the editors and authors can understand your reasoning." +
		"Please phrase your reviews politely; even 'bad' papers represent a lot of work on the part of " + 
		"the authors. The review may be the basis for further revisions of the paper or the work that " +
		"the paper reports. We all know how hurtful a needlessly negative review can be, and how " +
		"helpful a positive one can be; please try to bear that in mind when you are writing yours.";
	
	/**
	 * The questions for the reviewer.  Note: question 1 is in location 0 in the array!!!
	 */
	public static final String[] QUESTIONS = {
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
		
		"Does the paper adhere to accepted standards of style, usage, and composition?"
	};
	
	/**
	 * Descriptors for each value of the rating scale from low value to high.  Position 0 is 
	 * instruction to select a rating and position 1 equates to a rating of 1.
	 */
	public static final String[] RATING_SCALE_LOW_TO_HIGH = {"--select a rating--",
		"Strong Reject",  "Reject", "Neutral","Accept", "Strong Accept" 
	};
	
	/**
	 * The default comment text.
	 */
	private static final String DEFAULT_TEXT = "Enter a comment here.";

	/**
	 * The id of the review.
	 */
	private int my_id = 0;
	
	/**
	 * The author of the review.
	 */
	private User my_owner;
	
	/**
	 * The ratings for this review.
	 */
	private int[] my_ratings;
	
	/**
	 * The summary rating
	 */
	private int my_summary_rating;
	
	/**
	 * The comment for the subprogram chair to read.
	 */
	private String my_subprogramchair_comment;
	
	/**
	 * The overall comment for this review.
	 */
	private String my_summary_comment;
	
	/**
	 * Array of comments.  Position 0 is not used.
	 */
	private String[] my_comments;
	
	/**
	 * Default constructor (TEST)
	 */
	public Review()
	{
		this(new User());
	}
	
	/**
	 * Create a Review for a paper
	 * @param the_reviewer the author of this review.
	 */
	public Review(final User the_reviewer)
	{
		my_owner = the_reviewer;
		my_comments = new String[QUESTIONS.length];
		my_ratings = new int[QUESTIONS.length];
		my_subprogramchair_comment = DEFAULT_TEXT;
		my_summary_comment = DEFAULT_TEXT;
	}
	
	/**
	 * Set the unique id for this review
	 * @param the_id the id
	 */
	public void setID(final int the_id)
	{
		my_id = the_id;
	}
	
	/**
	 * @return the unique id for this review.  Default is 0;
	 */
	public int getID()
	{
		return my_id;
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
			my_subprogramchair_comment = the_comment;
		}
	}
	
	public String getSPChairComment()
	{
		return my_subprogramchair_comment;
	}
	
	/**
	 * Set the overall rating for this review of the paper.
	 * @param the_rating the rating (valid 1 - 5)
	 */
	public void setSummaryRating(final int the_rating)
	{
		my_summary_rating = the_rating;
	}
	
	/**
	 * Get the overall rating for this paper
	 * @return the overall rating for this paper
	 */
	public int getSummaryRating()
	{
		return my_summary_rating;
	}
	
	/**
	 * Summary comment to the author
	 * @param the_string the summary overall comment to the author.
	 */
	public void setSummaryComment(final String the_string)
	{
		if(the_string != null)
		{
			my_summary_comment = the_string;
		}
	}
	
	/**
	 * Get the overall comment for the review
	 * @return the overall comment to the author to describe the reasoning for overall rating.
	 */
	public String getSummaryComment()
	{
		return my_summary_comment;
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
		if(the_q >= 0 && the_q < QUESTIONS.length)
		{
			my_ratings[the_q] = the_value;
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
	 * Set the comment for the reason behind the rating for a question. Valid
	 * questions are 1 to Review.Questions.length - 1
	 * @param the_q the question number
	 * @param the_comment the comment associated with a question's rating.
	 */
	public void setComment(final int the_q, final String the_comment)
	{
		if(the_q >= 0 && the_q < QUESTIONS.length)
		{
			my_comments[the_q] = the_comment;
		}
	}

	/**
	 * Get the comment regarding why a rating was set for a particular question
	 * @param the_question the question number (must be valid otherwise "" returned)
	 * @return the comment associated with this question
	 */
	public String getComment(final int the_question)
	{
		String result = "";
		if(the_question >= 0 && the_question < QUESTIONS.length)
		{
			result = my_comments[the_question];
		}
		return result;
	}
	
	/**
	 * The string is the summary rating for this review.
	 * @return the summary rating for this review
	 */
	@Override
	public String toString()
	{
		return my_owner.toString();
	}
}

