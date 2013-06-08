package dao;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.Reviewer;
import model.Role;
import model.Status;
import model.SubProgramChair;
import model.User;

/**
 * Class to get/set information about papers in the database.
 * @author Danielle Tucker
 * @author Roshun (edits and slq query assistance)
 * @version 2013 Spring
 */
public class PaperDAO extends AbstractDAO {

	/**
	 * Insert new Paper record.
	 */
	private static final String INSERT_PAPER = "INSERT INTO "+
			"paper(author_id, title, keywords, cat_id, status, abstract, content, acceptance_status, active) " + 
			"VALUES(?,?,?,?,?,?,?,?,?);";

	/**
	 * Update an already created paper.
	 */
	private final String UPDATE_PAPER = "UPDATE paper SET author_id = ?, title = ?, " +
			"keywords = ?, cat_id = ?, " +
			"recomm_rating = ?, recomm_comments = ?, status = ?, abstract = ?, acceptance_status = ?" +
			"WHERE paper_ID = ? ";

	/**
	 * Get paper based on paper_id
	 */
	private static final String GET_PAPER = "SELECT * FROM PAPER AS A " +
			"INNER JOIN CATEGORY AS B ON A.CAT_ID = B.CAT_ID " +
			"WHERE paper_id = ? AND active = 1";

	/**
	 * Assign paper to a user/role/conference.
	 */
	private static final String ASSIGN_PAPER = "INSERT INTO USER_ROLE_PAPER_CONFERENCE_JOIN" +
			"(USER_ID, ROLE_ID, PAPER_ID, CONF_ID) Select ?,?,?,? FROM DUAL " +
			"WHERE NOT EXISTS(SELECT NULL FROM USER_ROLE_PAPER_CONFERENCE_JOIN AS B WHERE B.USER_ID = ?  "+
			"AND B.ROLE_ID = ? AND B.PAPER_ID = ? AND B.CONF_ID = ?) ";

	/**
	 * Get assigned papers based on conference and role
	 */
	private static final String GET_ASSIGNED_PAPERS = "SELECT PAPER_ID FROM USER_ROLE_PAPER_CONFERENCE_JOIN " +
			"WHERE USER_ID = ? AND ROLE_ID = ? AND CONF_ID = ?";


	/**
	 * Default constructor
	 */
	public PaperDAO() 
	{
		//default constructor
	}

	/**
	 * Adds new paper to the data source or update a current paper. Reviews are NOT added
	 * to this paper..Use addReview for this purpose.
	 * @param the_paper the paper to save to the data storage.
	 * @author Danielle
	 * @author Roshun (Edits: get generated key, insert content field)
	 */
	public void savePaper(Paper the_paper)
	{
		try 
		{
			Connection con = AbstractDAO.getConnection();
			PreparedStatement stmt;
			if (the_paper.getID() == 0) 
			{//New record to insert
				stmt = con.prepareStatement(INSERT_PAPER, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, the_paper.getAuthor().getID());
				stmt.setString(2, the_paper.getTitle());
				stmt.setString(3, the_paper.getKeywords());

				CategoryDAO categories = new CategoryDAO();
				int category_int = categories.getCategory(the_paper.getCategory());
				stmt.setInt(4, category_int);

				stmt.setString(5, the_paper.getStatus().name());
				stmt.setString(6, the_paper.getAbstract());
				stmt.setCharacterStream(7, new StringReader(the_paper.getContent()));
				stmt.setString(8, the_paper.getAcceptanceStatus().toString());
				stmt.setInt(9, 1);				
				stmt.executeUpdate();

				//Get generated primary key @author Roshun
				ResultSet key = stmt.getGeneratedKeys();
				if (key.next()) {
					int id = key.getInt(1);
					the_paper.setID(id);
				} else {
					throw new Exception("Primary key could not be generated.");
				}
			}
			else
			{//updating existing record.

				stmt = con.prepareStatement(UPDATE_PAPER);
				stmt.setInt(1, the_paper.getAuthor().getID());
				stmt.setString(2, the_paper.getTitle());
				stmt.setString(3, the_paper.getKeywords());

				CategoryDAO cat_dao = new CategoryDAO();
				int category = cat_dao.getCategory(the_paper.getCategory());
				stmt.setInt(4, category);  

				if(the_paper.getRecommendation() == null)
				{
					stmt.setInt(5, Types.NULL);
					stmt.setString(6, "");
				}
				else
				{
					stmt.setInt(5, the_paper.getRecommendation().getRating());
					stmt.setString(6, the_paper.getRecommendation().getComments());
				}
				stmt.setString(7, the_paper.getStatus().name());
				stmt.setString(8, the_paper.getAbstract());
				stmt.setString(9, the_paper.getAcceptanceStatus().toString());
				stmt.setInt(10, the_paper.getID());
				stmt.executeUpdate();
			}
			stmt.close();
		} 
		catch (Exception e) {e.printStackTrace();}
	}

	/**
	 * Remove a paper from active status.  Will no longer appear with getPaper()
	 * @param the_paper_id the paper id
	 */
	public void deletePaper(final int the_paper_id)
	{
		final String delete_paper = "UPDATE paper SET active = 0 WHERE paper_id = ?";
		try {
			PreparedStatement stmt = getConnection().prepareStatement(delete_paper);
			stmt.setInt(1, the_paper_id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) 
		{
			System.err.println("PDAO_deletePaper()_MSG: " + e);
		}
	}


	/**
	 * Associate a user with a role, paper, and/or conference.
	 * @param aPaperId the paper_id
	 * @param aUserId the user id
	 * @param aConfId the conference id
	 * @param aRole the role
	 * @author Roshun
	 */
	public void assignPaper(final int aPaperId, 
			final int aUserId, 
			final int aConfId,
			final Role aRole) {

		try {
			Connection con = AbstractDAO.getConnection();
			PreparedStatement stmt = con.prepareStatement(ASSIGN_PAPER);
			stmt.setInt(1, aUserId);
			stmt.setInt(2, aRole.ordinal());
			stmt.setInt(3, aPaperId);
			stmt.setInt(4, aConfId);
			stmt.setInt(5, aUserId);
			stmt.setInt(6, aRole.ordinal());
			stmt.setInt(7, aPaperId);
			stmt.setInt(8, aConfId);
			stmt.executeUpdate();
			stmt.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a new Review to the data source and connects it to the paper.
	 * @param the_review the review to associate with a paper.
	 * @param the_paper_id the unique identifier of the paper for which the review is written for.
	 */
	public void addReview(Review the_review, final int the_paper_id)
	{
		final String add_review = "INSERT INTO review(paper_id, reviewer_id, " + 
				"cmmt_subpgrmchair, summary_rating, summary_cmmt, active) VALUES(?,?,?,?,?, 1)";
		final String add_responses = "INSERT INTO rating_comment(review_id, question_id, rating," +
				"comment_text) VALUES (?,?,?,?)";

		try {
			//Make Review
			PreparedStatement stmt_rev = getConnection().prepareStatement(add_review);
			stmt_rev.setInt(1, the_paper_id);
			stmt_rev.setInt(2, the_review.getReviewer().getID());
			stmt_rev.setString(3, the_review.getSPChairComment());
			stmt_rev.setInt(4, the_review.getSummaryRating());
			stmt_rev.setString(5, the_review.getSummaryComment());
			stmt_rev.execute();

			//Get generated primary key @author Roshun
			ResultSet key = stmt_rev.getGeneratedKeys();
			if (key.next()) {
				int id = key.getInt(1);
				the_review.setID(id);
			} else {
				throw new Exception("Primary key could not be generated.");
			}

			stmt_rev.close();

			//Populate answers to the comments
			//NOT SURE ABOUT THE LOOP>>>>
			PreparedStatement quest = getConnection().prepareStatement(add_responses);
			for(int i = 1; i <= Review.QUESTIONS.length; i++)
			{
				quest.setInt(1, the_review.getID());
				quest.setInt(2, i);
				quest.setInt(3, the_review.getRating(i));
				quest.setString(4, the_review.getComment(i));
				quest.execute();
			}
			quest.close();

		} catch (Exception e) {
			System.out.println("PDAO_addReview()_MSG: " + e);
		}
	}

	/**
	 * Get all papers associated with a user role, conference, and user.
	 * Reviews and Recommendations are NOT associated with the paper at this stage.
	 * @param the_user_id the id of the user
	 * @param the_role the role of the user
	 * @param the_conference the conference id of the papers to retrieve.
	 * @return the papers associated with a conference, user, in a particular role
	 */
	public List<Paper> getPapers(final int the_user_id, final Role the_role, final int the_conference)
	{
		List<Paper> papers = new ArrayList<Paper>();

		try 
		{
			PreparedStatement stmt;
			if(the_role == Role.PROGRAM_CHAIR)
			{
				String pg_get_assigned = "SELECT PAPER_ID FROM USER_ROLE_PAPER_CONFERENCE_JOIN " +
						"WHERE ROLE_ID = ? AND CONF_ID = ?";
				stmt = AbstractDAO.getConnection().prepareStatement(pg_get_assigned);
				stmt.setInt(1, Role.AUTHOR.ordinal());
				stmt.setInt(2, the_conference);
			}
			else
			{
				stmt = AbstractDAO.getConnection().prepareStatement(GET_ASSIGNED_PAPERS);
				stmt.setInt(1, the_user_id);
				stmt.setInt(2, the_role.ordinal());
				stmt.setInt(3, the_conference);
			}
			ResultSet result_set = stmt.executeQuery();

			while(result_set.next())
			{
				//System.out.println(result_set.getInt("paper_id"));
				Paper paper = getPaper(result_set.getInt("paper_id"));//NEEDS REFACTORING!!!!!!
				if(paper.getID() != 0)
				{
					papers.add(paper); 
				}
			}

			stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return papers;
	}

	/**
	 * Gets a paper object based on paper ID.  Reviews and Recommendations are NOT attached.
	 * @param paper_ID the unique paper_ID.
	 * @return a Paper object associated with this paper_ID.  Will return
	 * default Paper object if no paper is associated with this paper_ID.
	 * @author Danielle
	 * @author Roshun (Clob converter for content of papers)
	 */
	public Paper getPaper(final int paper_ID) 
	{

		ResultSet result = null;
		Paper paper = new Paper();

		try 
		{
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_PAPER);

			stmt.setInt(1, paper_ID);
			result = stmt.executeQuery();
			StringBuilder builder = new StringBuilder();

			if(result.next()) 
			{
				paper.setID(result.getInt("PAPER_ID"));
				UserDAO user_dao = new UserDAO();
				paper.setAuthor(user_dao.getUser(result.getInt("AUTHOR_ID")));
				paper.setCategory(result.getString("DISPLAY"));
				paper.setID(paper_ID);
				paper.setKeywords(result.getString("KEYWORDS"));
				paper.setTitle(result.getString("TITLE"));
				paper.setRecommendation(new Recommendation());
				paper.setStatus(Status.valueOf(result.getString("STATUS")));
				paper.setAbstract(result.getString("ABSTRACT"));
				paper.setAcceptanceStatus(Status.valueOf(result.getString("ACCEPTANCE_STATUS")));

				//Convert CLOB to string builder @author Roshun
				BufferedReader buffer = new BufferedReader(result.getCharacterStream("CONTENT"));
				String line = null;
				while( null != (line = buffer.readLine())) {
					builder.append(line);
				}

				paper.setContent(builder.toString());

				if(result.getCharacterStream("Content_revised") != null)
				{
					buffer = new BufferedReader(result.getCharacterStream("CONTENT_REVISED"));
					line = null;
					while( null != (line = buffer.readLine())) {
						builder.append(line);
					}

					paper.setRevisedContent(builder.toString());
				}
				Recommendation rec = new Recommendation();
				rec.setRecommender(getAssignedSubProgramChair(paper_ID));
				rec.setRating(result.getInt("recomm_rating"));
				rec.setComments(result.getString("recomm_comments"));
				paper.setRecommendation(rec);
			}

			stmt.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return paper;
	}

	/**
	 * Get a Review object based upon a review_id.
	 * @param the_review_id The unique identifier for the review.
	 * @return the review associated with the review_id
	 */
	public Review getReview(final int the_review_id)
	{	
		final String get_review = "SELECT * FROM review WHERE review_id = ?";
		final String get_question_results = "SELECT * FROM rating_comment WHERE review_id = ?";

		ResultSet result;
		Review review = new Review();

		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_review);
			stmt.setInt(1,the_review_id);
			result = stmt.executeQuery();
			stmt.close();

			while ( result.next() ) 
			{
				review.setID(result.getInt("review_id"));
				UserDAO user_dao = new UserDAO();
				review.setReviewer(user_dao.getUser(result.getInt("user_id")));
				review.setSPChairComment(result.getString("CMMT_SUBPGRMCHAIR"));
				review.setSummaryRating(result.getInt("SUMMARY_RATING"));

				stmt = AbstractDAO.getConnection().prepareStatement(get_question_results);
				stmt.setInt(1, the_review_id);
				ResultSet questions_result = stmt.executeQuery();
				stmt.close();

				while (questions_result.next())
				{
					int question_id = questions_result.getInt("question_id");
					review.setRating(question_id, questions_result.getInt("rating"));
					review.setComment(question_id, questions_result.getString("comment_text"));
				}
			}
		} catch (Exception e) {System.err.println("PDAO_MSG: " + e);}

		return review;
	}

	/**
	 * Get all reviews for a paper
	 * @param the_paper_id the paper
	 * @return the reviews for the paper.  If none are associated then an empty list is returned.
	 */
	public List<Review> getReviews(final int the_paper_id)
	{
		List<Review> revs = new ArrayList<Review>();
		final String query = "SELECT * FROM review WHERE paper_id = ?";
		final String get_question_results = "SELECT * FROM rating_comment WHERE review_id = ?";
		try
		{
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setInt(1, the_paper_id);
			ResultSet result = stmt.executeQuery();
			while ( result.next() ) 
			{		
				Review review = new Review();
				review.setID(result.getInt("review_id"));
				UserDAO user_dao = new UserDAO();
				review.setReviewer(user_dao.getUser(result.getInt("reviewer_id")));
				review.setSPChairComment(result.getString("CMMT_SUBPGRMCHAIR"));
				review.setSummaryRating(result.getInt("SUMMARY_RATING"));

				stmt = AbstractDAO.getConnection().prepareStatement(get_question_results);
				stmt.setInt(1, result.getInt("review_id"));
				ResultSet questions_result = stmt.executeQuery();

				while (questions_result.next())
				{
					int question_id = questions_result.getInt("question_id");
					review.setRating(question_id, questions_result.getInt("rating"));
					review.setComment(question_id, questions_result.getString("comment_text"));
				}
				revs.add(review);
			}
			stmt.close();
		} catch (Exception e)  {System.err.println("PDAO_getReviews_MSG: " + e);}

		return revs;
	}

	/**
	 * Get a Recommendation for the paper
	 * @param the_paper_id the id of the paper
	 * @return the recommendation for the paper.  If none exists, a
	 * default recommendation will be returned.
	 */
	public Recommendation getRecommendation(final int the_paper_id)
	{
		Recommendation rec = new Recommendation();
		try {
			PreparedStatement stmt = getConnection().prepareStatement(GET_PAPER);
			stmt.setInt(1, the_paper_id);
			ResultSet result = stmt.executeQuery();
			if(result.next())
			{
				rec.setRecommender(getAssignedSubProgramChair(the_paper_id));
				rec.setRating(result.getInt("recomm_rating"));
				rec.setComments(result.getString("recomm_comments"));
			}
			stmt.close();
		} catch (SQLException e) 
		{
			System.out.println("PDAO_getRecomm()_MSG: " + e);
		}

		return rec;
	}

	/**
	 * Get the subprogram chair which has been assigned to this paper.
	 * @param paper_id the id of the paper to find the subprogram chair for
	 * @return the subprogram chair (if none is assigned a default Subprogram Chair will be returned.)
	 */
	public SubProgramChair getAssignedSubProgramChair(final int paper_id)
	{
		SubProgramChair user = new SubProgramChair();
		final String get_spg_chair =  "SELECT * FROM user u INNER JOIN " +
				"user_role_paper_conference_join j ON u.user_id = j.user_id WHERE j.paper_id = ? AND j.role_id = ?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(get_spg_chair);
			statement.setInt(1, paper_id);
			statement.setInt(2, Role.SUB_PROGRAM_CHAIR.ordinal());
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				user.setID(result.getInt("USER_ID"));
				user.setFirstName(result.getString("FIRST_NAME"));
				user.setLastName(result.getString("LAST_NAME"));
				user.setEmail(result.getString("EMAIL_ADDRESS"));
			}
		} catch (SQLException e) 
		{
			System.out.println("PDAO_getAssgnSPChair()_MSG: " + e);
		}
		return user;
	}

	/**
	 * Get the reviewers which have been assigned to this paper.
	 * @param paper_id the id of the paper to find the reviewers for
	 * @return the reviewers (if none is assigned an empty list will be returned.)
	 */
	public List<Reviewer> getAssignedReviewers(final int paper_id)
	{
		List<Reviewer> users = new ArrayList<Reviewer>();
		final String get_spg_chair =  "SELECT * FROM user u INNER JOIN " +
				"user_role_paper_conference_join j ON u.user_id = j.user_id WHERE j.paper_id = ? AND j.role_id = ?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(get_spg_chair);
			statement.setInt(1, paper_id);
			statement.setInt(2, Role.REVIEWER.ordinal());
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				User user = new User();
				user.setID(result.getInt("USER_ID"));
				user.setFirstName(result.getString("FIRST_NAME"));
				user.setLastName(result.getString("LAST_NAME"));
				user.setEmail(result.getString("EMAIL_ADDRESS"));
				users.add(new Reviewer(user));
			}
		} catch (SQLException e) 
		{
			System.out.println("PDAO_getAssgnSPChair()_MSG: " + e);
		}
		return users;
	}

}