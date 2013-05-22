package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.User;

import common.ReferenceObject;

public class PaperDAO extends AbstractDAO {

	/**
	 * Get paper based on paper_id
	 */
	private static final String GET_PAPER = "SELECT * FROM paper WHERE paper_id = ?";

	/**
	 * Update existing paper record.
	 */
	private static final String UPDATE_PAPER = "UPDATE paper SET user_id = ?, title = ?, " +
			"keywords = ?, cat_id = ?, document_path = ?, revised_document_path = ? " +
			"recomm_id = ?, status = ?, abstract = ?" +
			"WHERE paper_ID = ? ";

	/**
	 * Insert new Paper record.
	 */
	private static final String INSERT_PAPER = "INSERT INTO "+
			"paper(user_id, title, keywords, cat_id, document_path, status, abstract) " + 
			"VALUES(?,?,?,?,?,?,?);";

	private static final String SELECT_CATEGORY = "SELECT cat_id FROM category WHERE display = ?";

	private static final String GET_REVIEWS = "SELECT review_id FROM paper_review WHERE paper_id = ?";
	private static final String GET_REVIEW = "SELECT * FROM review WHERE review_id = ?";


	public PaperDAO() 
	{
		//default constructor
	}

	/**
	 * Adds new paper to the data source
	 * @param the_paper
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

				/* Use when category table has been set up
				PreparedStatement secondary_stmt = AbstractDAO.getConnection().prepareStatement(SELECT_CATEGORY);
				secondary_stmt.setString(1, the_paper.getCategory());
				ResultSet cat_result = secondary_stmt.executeQuery();
				stmt.setInt(4, cat_result.getInt("cat_id"));
				secondary_stmt.close();
				 */
				stmt.setInt(4, 2); //setting category_id = 2 temp...
				stmt.setString(5, the_paper.getDocumentPath());
				stmt.setString(6, the_paper.getStatus().name());
				stmt.setString(7, the_paper.getAbstract());

				stmt.executeUpdate();
				ResultSet key = stmt.getGeneratedKeys();
				if (key.next()) {
					int id = key.getInt(1);
					the_paper.setID(id);
				} else {
					System.out.println("no key");
				}
			}
			else
			{//updating existing record.
				stmt = con.prepareStatement(UPDATE_PAPER);
				stmt.setInt(1, the_paper.getAuthor().getID());
				stmt.setString(2, the_paper.getTitle());
				stmt.setString(3, the_paper.getKeywords());
				/* Use when category table has been set up
				PreparedStatement secondary_stmt = AbstractDAO.getConnection().prepareStatement(SELECT_CATEGORY);
				secondary_stmt.setString(1, the_paper.getCategory());
				ResultSet cat_result = secondary_stmt.executeQuery();
				stmt.setInt(4, cat_result.getInt("cat_id"));
				secondary_stmt.close();
				 */
				//temp line below!
				stmt.setInt(4, 2); //setting category_id = 2 temp...

				stmt.setString(5, the_paper.getDocumentPath());
				stmt.setString(6, the_paper.getRevisedDocumentPath());
				if(the_paper.getRecommendation() == null)
				{
					System.out.println("The recommendation is null");
					stmt.setNull(7, Types.NULL);
				}
				else
				{
					stmt.setInt(7, the_paper.getRecommendation().getID());
				}
				stmt.setString(8, the_paper.getStatus().name());
				stmt.setString(9, the_paper.getAbstract());
				
				stmt.setInt(10, the_paper.getID());
			}


			stmt.close();
		} 
		catch (Exception e) {System.out.println(e.toString());}
	}

	/**
	 * Adds a new Review to the data source and connects it to the paper.
	 */
	public void addReview(final Review the_review, final int the_paper_id)
	{
	}
	/**
	 * Gets a paper object based on paper ID.
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

			while (result.next() ) 
			{
				paper.setID(result.getInt("PAPER_ID"));
				UserDAO user_dao = new UserDAO();
				paper.setAuthor(user_dao.getUser(result.getInt("USER_ID")));
				//paper.setCategory(result.getString());
				paper.setDocumentPath(result.getString("DOCUMENT_PATH"));
				paper.setID(paper_ID);
				paper.setKeywords(result.getString("KEYWORDS"));
				paper.setRevisedDocumentPath(result.getString("REVISED_DOCUMENT_PATH"));
				paper.setTitle(result.getString("TITLE"));
			} 
			stmt.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return paper;
	}

	public List<ReferenceObject> getReviewsRef(final int the_paper_ID) {
		return new ArrayList<ReferenceObject>();
	}  
	public Review getReview(final int the_review_id)
	{
		return new Review();
	}

	public Recommendation getRecommendation(final int the_paper_ID)
	{
		return new Recommendation();
	}
}