package test;

import static org.junit.Assert.*;

import java.util.List;

import model.Author;
import model.Conference;
import model.Paper;
import model.ProgramChair;
import model.Recommendation;
import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

import service.ConferenceService;
import service.PaperService;
import service.UserService;

/**
 * Testing methods in Recommendation class
 * @author Danielle
 * @version 2013 Spring (done)
 */
public class RecommendationTest {

	/**
	 * Testing constructor and some getters.
	 */
	@Test
	public void testConstructor() {
		Recommendation rec = new Recommendation(new User(), 4, "comments");
		assertEquals(0, rec.getRecommender().getID());
		assertEquals(4, rec.getRating());
		assertEquals("comments", rec.getComments());
	}
	
	/**
	 * Testing the automatic rating setting for out of bound ratings.
	 */
	@Test
	public void testValitatingBadRatings()
	{
		Recommendation rec = new Recommendation(new User(), 4, "comments");
		rec.setRating(-1);
		assertEquals(1, rec.getRating());

		rec.setRating(0);
		assertEquals(1, rec.getRating());
		rec.setRating(1);
		assertEquals(1, rec.getRating());
		
		rec.setRating(5);
		assertEquals(5, rec.getRating());
		rec.setRating(6);
		assertEquals(5, rec.getRating());
		
		rec.setRating(10);
		assertEquals(5, rec.getRating());
	}
	
	@Test
	public void testSetComments()
	{
		Recommendation rec = new Recommendation();
		assertEquals("No Comment.", rec.getComments());
		rec.setComments(null);
		assertEquals("No Comment.", rec.getComments());
		
		rec.setComments("I am a comment");
		assertEquals("I am a comment", rec.getComments());
	}

}
