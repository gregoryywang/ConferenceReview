package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
}
