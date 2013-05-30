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
import model.Role;
import model.User;

public class PaperDAO extends AbstractDAO {

	/**
	 * Insert new Paper record.
	 */
	private static final String INSERT_PAPER = "INSERT INTO "+
			"paper(author_id, title, keywords, cat_id, status, abstract, content, active) " + 
			"VALUES(?,?,?,?,?,?,?, 1);";

	/**
	 * Update an already created paper.
	 */
	private final String UPDATE_PAPER = "UPDATE paper SET author_id = ?, title = ?, " +
			"keywords = ?, cat_id = ?, " +
			"recomm_rating = ?, recomm_comments = ?, status = ?, abstract = ?" +
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
	 "(USER_ID, ROLE_ID, PAPER_ID, CONF_ID) VALUES(?,?,?,?)";
	
	/**
	 * Get assigned papers based on conference and role
	 */
	private static final String GET_ASSIGNED_PAPERS = "SELECT PAPER_ID FROM USER_ROLE_PAPER_CONFERENCE_JOIN " +
      "WHERE USER_ID = ? AND ROLE_ID = ? AND CONF_ID = ?";
  
	
	private static final String GET_QUESTION_RESULTS = "SELECT * FROM rating_comment WHERE review_id = ?";

	
	
	public PaperDAO() 
	{
		//default constructor
	}

	/**
	 * TO DO: update/edit of paper.
	 * Adds new paper to the data source or update a current paper.
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

				stmt.setInt(9, the_paper.getID());
				stmt.executeUpdate();
			}
			stmt.close();
		} 
		catch (Exception e) {System.out.println("PDAO_savePaper()_MSG: " + e);}
	}

	
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
   * Assigns a paper to a user.
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
      stmt.executeUpdate();
      stmt.close();
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
	/**
	 * JUST A STUB, NOT FUNCTIONAL YET.
	 * Adds a new Review to the data source and connects it to the paper.
	 * @param the_review the review to associate with a paper.
	 * @param the_paper_id the unique identifier of the paper for which the review is written for.
	 */
	public void addReview(final Review the_review, final int the_paper_id)
	{
		
	}
	
	/**
	 * Get all papers associated with a user role, conference, and user.
	 * @param the_user_id the id of the user
	 * @param the_role the role of the user
	 * @param the_conference the conference id of the papers to retrieve.
	 * @return the papers associated with a conference, user, in a particular role
	 * @author Danielle
	 */
	public List<Paper> getPapers(final int the_user_id, final Role the_role, final int the_conference)
	{
		List<Paper> papers = new ArrayList<Paper>();
		
		try 
		{
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_ASSIGNED_PAPERS);
			stmt.setInt(1, the_user_id);
			stmt.setInt(2, the_role.ordinal());
			stmt.setInt(3, the_conference);
			ResultSet result_set = stmt.executeQuery();
			
			while(result_set.next())
			{
				System.out.println(result_set.getInt("paper_id"));
				papers.add(getPaper(result_set.getInt("paper_id"))); //NEEDS REFACTORING!!!!!!
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
	 * TO DO: attach revised_content to the paper object
	 * Gets a paper object based on paper ID.
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
				paper.addRecommendation(new Recommendation());
				
				//Convert CLOB to string builder @author Roshun
				BufferedReader buffer = new BufferedReader(result.getCharacterStream("CONTENT"));
				String line = null;
				while( null != (line = buffer.readLine())) {
				  builder.append(line);
				}
				
				paper.setContent(builder.toString());
				
				//FIX ME!! Still need to do the same thing for "CONTENT_REVISED" which may be null.
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

		ResultSet result;
		Review review = new Review();
		
		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_review);
			stmt.setInt(1,the_review_id);
			result = stmt.executeQuery();
			stmt.close();
			
			while ( result.next() ) 
			{//COMPLETE ME!!  //MAKE ME NOT SO UGLY TOO!!
				UserDAO user_dao = new UserDAO();
				review.setReviewer(user_dao.getUser(result.getInt("user_id")));
				review.setSPChairComment(result.getString("CMMT_SUBPGRMCHAIR"));
				review.setSummaryRating(result.getInt("SUMMARY_RATING"));
				
				stmt = AbstractDAO.getConnection().prepareStatement(GET_QUESTION_RESULTS);
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
	 * NOT DONE YET.
	 * @param paper_id
	 * @return
	 */
	public User getAssignedSubProgramChair(final int paper_id)
	{
		User user = new User();
		final String get_spg_chair = "SELECT user_id FROM user_role_paper_conference_join WHERE "+
				"paper_id = ?, role = ?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(get_spg_chair);
			statement.setInt(1, paper_id);
			statement.setInt(2, Role.SUB_PROGRAM_CHAIR.ordinal());
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				
			}
		} catch (SQLException e) 
		{
			System.out.println("PDAO_getAssgnSPChair()_MSG: " + e);
		}
		return user;
	}
}