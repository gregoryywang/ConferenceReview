package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Reviewer;
import model.Role;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import service.UserService;
import dao.PaperDAO;

/**
 * Test methods associated with the PaperDAO
 * @author Danielle
 * @version 2013 Spring
 */
public class PaperDAOTest {


	private PaperDAO paper_dao;
	private Paper test_paper;
	private Paper test_paper2;

	private List<Paper> papers_to_remove = new ArrayList<Paper>();
	/**
	 * Test fixture for tests.
	 */
	@Before
	public void setUp() {
		paper_dao = new PaperDAO();

		test_paper = new Paper();
		User test_user = UserService.getInstance().authenticateUser("UserTest", "UserTest");
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Paper Stuff");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");
		test_paper.setContent("Content here");

		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);
		
		test_paper2 = new Paper();
		test_paper2.setAuthor(test_user);
		test_paper2.setTitle("Testing Paper Stuff 2");
		test_paper2.setCategory("Software");
		test_paper2.setKeywords("testing, stuff2");
		test_paper2.setAbstract("Write an abstract.");
		test_paper2.setContent("Content was here");

		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);
	}

	/**
	 * Tests adding a new paper.
	 */
	@Test
	public void savePaperNew() {
		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);

		assertFalse(test_paper.getID()== 0);
		assertEquals("Testing Paper Stuff", test_paper.getTitle());
		assertEquals("Software", test_paper.getCategory());
		assertNull(test_paper.getRecommendation());
	}

	/**
	 * Testing updating an existing paper.
	 */
	@Test
	public void savePaperUpdate()
	{
		test_paper = new Paper();
		User test_user = UserService.getInstance().authenticateUser("UserTest", "UserTest");
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Another Paper Stuff.");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");

		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);

		test_paper.setTitle("I have changed the title");
		paper_dao.savePaper(test_paper);

		Paper paper_from_db = paper_dao.getPaper(test_paper.getID());
		assertEquals("I have changed the title", paper_from_db.getTitle());
	}

	/**
	 * Tests assignPaper() and getPapers(id, role, conf)
	 */
	@Test
	public void getPapers()
	{		
		test_paper = new Paper();
		User test_user = UserService.getInstance().authenticateUser("AuthorTest", "AuthorTest");
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Yet Another Paper Stuff.");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");
		test_paper.setContent("this paper's content is awesome!!!");

		paper_dao.savePaper(test_paper);
		papers_to_remove.add(test_paper);
		
		paper_dao.assignPaper(test_paper.getID(), test_paper.getAuthor().getID(), 1, Role.AUTHOR);
		List<Paper> papers = paper_dao.getPapers(test_paper.getAuthor().getID(), Role.AUTHOR, 1);
		assertTrue(papers.size() > 0);
		assertTrue(papers.contains(test_paper));
	}

	@Test
	public void getPaper()
	{
		Paper my_paper = paper_dao.getPaper(test_paper.getID());
		assertEquals(test_paper.getID(), my_paper.getID());
		assertEquals(test_paper.getTitle(), my_paper.getTitle());
		assertEquals(test_paper.getContent(), my_paper.getContent());
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

	@After
	public void cleanUp()
	{
		for(Paper paper: papers_to_remove)
		{
			paper_dao.deletePaper(paper.getID());
		}
	}
}
