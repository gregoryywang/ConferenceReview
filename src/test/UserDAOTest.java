package test;

import static org.junit.Assert.*;

import java.util.Collection;

import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;

public class UserDAOTest {

  /**
   * Used for test setup.
   */
  private UserDAO userDao = null;
  
  /**
   * Test fixture for tests.
   */
  @Before
  public void setUp() {
    userDao = new UserDAO();
  }
  
  /**
   * Tests the authentication system with a bad combination.
   */
  @Test
  public void testAuthenticateFail() {
    boolean result = userDao.authenticate("bob".hashCode(), "barker".hashCode());
    assertFalse(result);
  }
  
  /**
   * Tests the authentication system with a good combination.
   */
  @Test
  public void testAuthenticatePass() {
    boolean result = userDao.authenticate("robert".hashCode(), "barker".hashCode());
    assertTrue(result);
  }
  
  /**
   * Tests with roles collection returns not empty with valid input.
   */
  @Test
  public void testGetRoles() {
    Collection<Role> result = userDao.getRoles("test".hashCode(), "test".hashCode());
    assertFalse(result.isEmpty());
  }
}
