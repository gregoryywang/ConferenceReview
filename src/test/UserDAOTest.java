package test;

import static org.junit.Assert.*;

import java.util.Collection;

import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

import common.ReferenceObject;

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
    User user = userDao.authenticate("bob", "barker");
    assertNull(user);
  }
  
  /**
   * Tests the authentication system with a good combination.
   */
  @Test
  public void testAuthenticatePass() {
    User user = userDao.authenticate("AdminTest", "AdminTest");
    assertNotNull(user);
    assertEquals("Values did not match", user.getFirstName(), user.getLastName());
  }
  
  /**
   * Tests isAdmin method.
   */
  @Test
  public void testIsAdmin() {
    User user = userDao.authenticate("AdminTest", "AdminTest");
    boolean result = userDao.isAdmin(user.getID());
    assertTrue(result);
  }
  /**
   * Tests with roles collection returns not empty with valid input.
   */
 /* @Test
  public void testGetRoles() {
    Collection<ReferenceObject> result = userDao.getRolesRef("test".hashCode(), "test".hashCode());
    assertFalse(result.isEmpty());
  }*/
}
