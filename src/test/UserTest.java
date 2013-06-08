package test;

import static org.junit.Assert.*;

import model.User;

import org.junit.Before;
import org.junit.Test;

import service.UserService;

/**
 * Tests for the User class
 * @author Danielle Tucker
 * @version 2013 Spring (done)
 */
public class UserTest {

	User my_admin;
	
	/**
	 * Setup
	 */
	@Before
	public void setUp()
	{
		User my_admin = UserService.getInstance().authenticateUser("AdminTest", "AdminTest");
	}
	
	/**
	 * Testing setup got the correct user.
	 */
	@Test
	public void getUserTest(){
		assertEquals("AdminTest", my_admin.getFirstName());
		assertEquals("AdminTest AdminTest", my_admin.getFullName());
		assertEquals("AdminTest AdminTest", my_admin.toString());
	}
	
	/**
	 * Checking the copy constructor.
	 */
	@Test
	public void copyConstructorTest() {
		User admin = new User(my_admin);
		assertNotNull(admin);
		assertEquals(my_admin.getID(), admin.getID());
		assertEquals(my_admin.getConference(), admin.getConference());
		assertEquals(my_admin.getEmail(), admin.getEmail());
		assertEquals(my_admin.getFirstName(), admin.getFirstName());
		assertEquals(my_admin.getLastName(), admin.getLastName());
		assertEquals(my_admin.getPassword(), admin.getPassword());
		assertEquals(my_admin.getUsername(), admin.getUsername());
	}
}
