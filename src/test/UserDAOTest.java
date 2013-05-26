package test;

import static org.junit.Assert.*;

import java.util.List;

import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;

/**
 * 
 * @author Roshun Jones
 * @author Danielle Tucker
 *
 */
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

		User not_admin = userDao.authenticate("UserTest", "UserTest");
		result = userDao.isAdmin(not_admin.getID());
		assertFalse(result);
	}

	/**
	 * Tests getRoles() method.
	 */
	@Test
	public void testGetRoles() 
	{
		List<Role> result = userDao.getRoles(2, 1);
		assertTrue(result.contains(Role.PROGRAM_CHAIR));
		assertTrue(result.contains(Role.AUTHOR));
		assertTrue(result.size() == 2);
	}

	/**
	 * Test getUser() method with user that exists.
	 */
	@Test
	public void testUser()
	{
		User admin_user = userDao.getUser(1);
		assertEquals("AdminTest", admin_user.getFirstName());
		assertEquals("AdminTest", admin_user.getLastName());
		assertEquals("AdminTest@uw.edu", admin_user.getEmail());
		assertEquals("AdminTest", admin_user.getUsername());
	}
	
	/**
	 * Test getUser() method with user that does not exist.
	 */
	@Test (expected = Exception.class)
	public void testUserFail()
	{
		userDao.getUser(-1);
	}
	/*  LIST of methods to test!
  setRole(int, Role, int)
  getUser(int)
  getUsers(Role)
  getUsers()
  getUsersRef()
  getUsersRef(Role)
	 */
}

