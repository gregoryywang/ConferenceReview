package test;

import static org.junit.Assert.*;

import model.User;

import org.junit.Test;

import service.UserService;

public class UserTest {

	User my_admin = UserService.getInstance().authenticateUser("AdminTest", "AdminTest");
	
	@Test
	public void getUserTest(){
		assertEquals("AdminTest", my_admin.getFirstName());
		assertEquals("AdminTest AdminTest", my_admin.getFullName());
	}
	
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
