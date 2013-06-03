package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CategoryDAO;

/**
 * Tests for CategoryDAO class.
 * @author Roshun Jones
 * @author Danielle
 * @version 2013 Spring (done)
 */
public class CategoryDAOTest {
  /**
   * Used for all tests.
   */
  private CategoryDAO categoryDao;
  
  @Before
  public void setUp() {
    categoryDao = new CategoryDAO();
  }
  
  /**
   * Test getting all Categories
   */
  @Test
  public void testGetCategories()
  {
	  List<String> categories = categoryDao.getCategories();
	  assertTrue(categories.contains("Other"));
	  assertTrue(categories.contains("Hardware"));
	  assertTrue(categories.size() == 4);
  }
  
  /**
   * Test getting category id from the string of the category.
   */
  @Test
  public void testGetCategory()
  {
	  assertEquals(0, categoryDao.getCategory("Hosta"));
	  assertEquals(1, categoryDao.getCategory("Curriculum"));
	  assertEquals(2, categoryDao.getCategory("Software"));
  }
}
