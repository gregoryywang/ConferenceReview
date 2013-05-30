package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
		test_user.setID(3);
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Paper Stuff");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");
		test_paper.setContent("Content here");

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

	@Test
	public void savePaperUpdate()
	{
		test_paper = new Paper();
		User test_user = new User();
		test_user.setID(3);
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

	@Test
	public void getPapers()
	{		
		test_paper = new Paper();
		User test_user = new User();
		test_user.setID(3);
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
		System.out.println(papers.get(0).getID() + papers.get(0).getTitle());
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

	@Test
	public void testAssignPaper() {
		paper_dao.assignPaper(1, 1, 4, Role.REVIEWER);
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
