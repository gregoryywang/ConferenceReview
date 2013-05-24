package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import common.ReferenceObject;

import dao.CategoryDAO;

/**
 * Tests for CategoryDAO class.
 * @author Roshun Jones
 * @version 1.0
 */
public class CategoryDAOTest {
  /**
   * Used for all tests.
   */
  private CategoryDAO categoryDao;
  
  public CategoryDAOTest() {}
  
  
  @Before
  public void setUp() {
    categoryDao = new CategoryDAO();
  }
  
  @Test
  public void testGetCategoriesRef() {
    List<ReferenceObject> testList = categoryDao.getCategoriesRef();
    assertTrue(testList.size() != 0); //shouldnt be empty
    assertEquals("First category did not match", "Curriculum", testList.get(0).getDisplay());
    assertEquals("Second category did not match", "Software", testList.get(1).getDisplay());
    assertEquals("Third category did not match", "Hardware", testList.get(2).getDisplay());
    assertEquals("Fourth category did not match", "Other", testList.get(3).getDisplay());
    assertEquals("First id did not match", 1, testList.get(0).getData());
    assertEquals("Second id did not match", 2, testList.get(1).getData());
    assertEquals("Third id did not match", 3, testList.get(2).getData());
    assertEquals("Fourth id did not match", 4, testList.get(3).getData()); 
  }
}
