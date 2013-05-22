package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Review;

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
			"recommen_id = ?, status = ?," +
			"WHERE paper_ID = ? ";

	/**
	 * Insert new Paper record.
	 */
	private static final String INSERT_PAPER = "INSERT INTO paper VALUES(?,?,?,?,?,?,?,?,?) ";

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
	public void savePaper(final Paper the_paper)
	{
		try 
		{
			Connection con = AbstractDAO.getConnection();
			PreparedStatement stmt;
			if (the_paper.getID() == 0 ) 
			{//New record to insert
				stmt = con.prepareStatement(INSERT_PAPER, Statement.RETURN_GENERATED_KEYS);
			}
			else
			{//updating existing record.
				stmt = con.prepareStatement(UPDATE_PAPER);
				stmt.setInt(9, the_paper.getID());
			}
			stmt.setInt(1, the_paper.getAuthor().getID());
			stmt.setString(2, the_paper.getTitle());
			stmt.setString(3, the_paper.getKeywords());

			PreparedStatement secondary_stmt = AbstractDAO.getConnection().prepareStatement(SELECT_CATEGORY);
			secondary_stmt.setString(1, the_paper.getCategory());
			ResultSet cat_result = secondary_stmt.executeQuery();
			stmt.setInt(4, cat_result.getInt("cat_id"));
			secondary_stmt.close();

			stmt.setString(5, the_paper.getDocumentPath());
			stmt.setString(6, the_paper.getRevisedDocumentPath());
			stmt.setInt(7, the_paper.getRecommendation().getID());
			stmt.setString(8, the_paper.getStatus().name());
			
			stmt.executeUpdate();
			secondary_stmt.close();
			stmt.close();
		} 
		catch (Exception e) {}
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
	/*
	ResultSet result = null;
	Paper paper = null;

	try {
		PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_PAPER);
		stmt.setInt(1, paper_ID);
		result = stmt.executeQuery();

		while (result.next() ) {
			paper = new Paper();
			paper.setID(result.getInt("PAPER_ID"));
			paper.setLastName(result.getString("LAST_NAME"));
			paper.setEmail(result.getString("EMAIL_ADDRESS"));
		}
		paper.setAuthorID(result.getInt("AUTHOR"));


	} catch (Exception e) {
		System.out.println(e.getMessage());
	}

	return paper;
	*/
	return new Paper();
}

public List<ReferenceObject> getReviewsRef(int my_paper_ID) {
	return new ArrayList<ReferenceObject>();
}  

}
