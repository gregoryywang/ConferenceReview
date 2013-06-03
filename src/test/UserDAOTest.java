package test;

import static org.junit.Assert.*;

import java.util.List;

import model.Role;
import model.User;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;

/**
 * Class to Test UserDAO class.
 * @author Roshun Jones
 * @author Danielle Tucker
 * @version 2013 Spring (done)
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
	 * @author Roshun
	 */
	@Test
	public void testAuthenticateFail() {
		User user = userDao.authenticate("bob", "barker");
		assertNull(user);
	}

	/**
	 * Tests the authentication system with a good combination.
	 * @author Roshun
	 */
	@Test
	public void testAuthenticatePass() {
		User user = userDao.authenticate("AdminTest", "AdminTest");
		assertNotNull(user);
		assertEquals("Values did not match", user.getFirstName(), user.getLastName());
	}

	/**
	 * Tests isAdmin method.
	 * @author Danielle
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
	 * @author Danielle
	 */
	@Test
	public void testGetRoles() 
	{
		User program_ch = userDao.authenticate("PrgmChairTest", "PrgmChairTest");
		assertNotNull(program_ch);
		List<Role> result = userDao.getRoles(program_ch.getID(), 1);
		assertTrue(result.contains(Role.PROGRAM_CHAIR));
		assertFalse(result.contains(Role.AUTHOR));
	}

	/**
	 * Test getUser() method with user that exists.
	 * @author Danielle
	 */
	@Test
	public void testUser()
	{
		User admin_user = userDao.authenticate("AdminTest", "AdminTest");
		assertNotNull(admin_user);
		User same_user = userDao.getUser(admin_user.getID());
		assertEquals("AdminTest", same_user.getFirstName());
		assertEquals("AdminTest", same_user.getLastName());
		assertEquals("AdminTest@uw.edu", same_user.getEmail());
		assertEquals(admin_user.getID(), same_user.getID());
	}

	/**
	 * Test getUsers(Role)
	 * @author Danielle
	 */
	@Test
	public void testgetUsersRole()
	{
		List<User> users = userDao.getUsers(Role.PROGRAM_CHAIR);
		assertTrue(users.size() > 0);
		User prgm_ch = userDao.authenticate("PrgmChairTest", "PrgmChairTest");
		boolean result = false;
		for(User user: users)
		{
			if(user.getFullName().equals(prgm_ch.getFullName()))
			{
				result = true;
			}
		}
		assertTrue(result);	
	}
	
	/**
	 * Testing getUsers()
	 * @author Danielle
	 */
	@Test
	public void testGetUsers()
	{
		List<User> users = userDao.getUsers();
		assertTrue(users.size() > 0);
	}
	
	/**
	 * Testing getUsers(ConfID, Role)
	 * @author Danielle
	 */
	@Test
	public void testGetUserConfRole()
	{
		List<User> users = userDao.getUsers(1, Role.ADMIN);
		assertTrue(users.size() == 0);
	}
}

