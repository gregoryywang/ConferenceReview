package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Paper;
import model.Role;
import model.User;



import dao.PaperDAO;

public class PaperDAOTest {


	private PaperDAO paper_dao;
	private Paper test_paper;

	private List<Paper> papers_to_remove = new ArrayList<Paper>();
	/**
	 * Test fixture for tests.
	 */
	@Before
	public void setUp() {
		paper_dao = new PaperDAO();
		
		test_paper = new Paper();
		User test_user = new User();
		test_user.setID(1);
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Paper Stuff.");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");

		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);
	}

	/**
	 * Tests adding a new paper.
	 */
	@Test
	public void savePaperNew() {
		Paper paper = new Paper();
		User test_user = new User();
		test_user.setID(1);
		int old_id = paper.getID();
		paper.setAuthor(test_user);
		paper.setTitle("Titles are cool");
		paper.setCategory("Software");
		paper.setKeywords("test, greatness");
		paper.setAbstract("This is an abstract.  WEEEE.");
		paper.setContent("This is a test");

		paper_dao.savePaper(paper);
		papers_to_remove.add(paper);
		
		assertFalse(paper.getID()==old_id);
		assertEquals("Titles are cool", paper.getTitle());
		assertEquals("Software", paper.getCategory());
		assertNull(paper.getRecommendation());
	}
	
	@Test
	public void savePaperUpdate()
	{
		test_paper.setTitle("I have changed the title");
		paper_dao.savePaper(test_paper);
		
		Paper paper_from_db = paper_dao.getPaper(test_paper.getID());
		assertEquals(test_paper, paper_from_db);
	}

	@Test
	public void getPapers()
	{
		fail();
	}
	
	@Test
	public void getPaper()
	{
		Paper my_paper = paper_dao.getPaper(test_paper.getID());
		assertEquals(test_paper.getID(), my_paper.getID());
		assertEquals(test_paper.getTitle(), my_paper.getTitle());
		assertEquals("Content did not match", "This is a test", my_paper.getContent());
	}
	
	@Test
	public void getRecommendation()
	{
		fail();
	}
	
	@Test
	public void getReviews()
	{
		fail();
	}
	
	@Test
	public void getReview()
	{
		fail();
	}
	
	
	@Test
	public void addReview()
	{
		fail();
	}
	
	@Test
	public void testAssignPaper() {
	  paper_dao.assignPaper(1, 1, 4, Role.REVIEWER);
	}
}
