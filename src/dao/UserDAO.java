package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;

/**
 * This class used for system authentication.
 * @author Roshun Jones
 * @author Danielle (few methods and edits)
 * @version 1.0
 *
 */
public final class UserDAO extends AbstractDAO {
	/**
	 * Query used to authenticate a given user.
	 */
	private static final String AUTHENTICATE = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";

	/**
	 * DB command to set user to have a particular role for a conference.
	 * @author Danielle T
	 */
	private static final String SET_ROLE = "INSERT INTO " + 
			"user_role_paper_conference_join(user_id, role_id, conf_id)" +
			"VALUES(?,?,?);";
	;
	/**
	 * Determines whether a user is an Administrator.
	 * @author Danielle
	 */
	private static final String IS_ADMIN = "SELECT * FROM " +
			"USER_ROLE_PAPER_CONFERENCE_JOIN WHERE user_id = ? and role_id = ?";

	/**
	 * Returns a user based on userid.
	 */
	private static final String GET_USER = "SELECT * FROM USER WHERE USER_ID = ?";

	/**
	 * Authenticates a user based on userid and password.
	 * @param userid Unique id that identifies a user.
	 * @param password The password passed by the user.
	 * @return Returns a User Object if validated, null otherwise.
	 */
	public User authenticate(final String aUsername, final String aPassword) {
		ResultSet result = null;
		User user = null;

		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(AUTHENTICATE);
			stmt.setString(1, aUsername);
			stmt.setString(2, aPassword);
			result = stmt.executeQuery();

			while (result.next() ) {
				user = createUser(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return user;
	}

	/**
	 * Set the role for this user for a given conference.
	 * @author Danielle Tucker
	 * @param the_user_id the user unique identifier
	 * @param the_role the role to associate with this user
	 * @param the_conf_id the conference associated with this user and role pair (may be null)
	 */
	public void setRole(final int the_user_id, final Role the_role, final int the_conf_id)
	{
		try
		{
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(SET_ROLE);
			stmt.setInt(1, the_user_id);
			stmt.setInt(2, the_role.ordinal());
			stmt.setInt(3, the_conf_id);
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get all roles associated with this user and conference.
	 * @param the_user_id the user id
	 * @param the_conf_id the conference id
	 * @return all roles associated with this user and conference.
	 */
	public List<Role> getRoles(final int the_user_id, final int the_conf_id)
	{
		List<Role> roles = new ArrayList<Role>();
		ResultSet result;
		
		final String get_roles = "SELECT DISTINCT role_id FROM user_role_paper_conference_join " +
				"WHERE user_id = ? AND conf_id = ?";
		
		try
		{
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_roles);
			stmt.setInt(1, the_user_id);
			stmt.setInt(2, the_conf_id);
			result = stmt.executeQuery();
			
			while(result.next())
			{
				roles.add(Role.values()[result.getInt("role_id")]);
			}
		}
		catch (Exception e) {System.err.println(e);}
		
		return roles;
	}

	/**
	 * Returns whether a user is an administrator or not.
	 * @return whether a user is an administrator.
	 * @param aUserid the user id
	 */
	public boolean isAdmin(final int aUserid) {
		ResultSet rs = null;
		boolean result = false;
		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(IS_ADMIN);
			stmt.setInt(1, aUserid);
			stmt.setInt(2, Role.ADMIN.ordinal());
			rs = stmt.executeQuery();

			result = rs.first();

		} catch (Exception e) {System.err.println(e);}

		return result;
	}

	/**
	 * Gets a user object based on userid.
	 * @param aUserid the user id
	 * @return the user associated with this id.
	 */
	public User getUser(final int aUserid) {
		ResultSet result = null;
		User user = null;

		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_USER);
			stmt.setInt(1, aUserid);
			result = stmt.executeQuery();

			while (result.next() ) {
				user = createUser(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return user;
	}

	/**
	 * Get all users associated with a conference and role.
	 * @param conf_id the conference id
	 * @param the_role the role in the conference
	 * @return a list of all users with the role for that conference.
	 * @author Danielle
	 */
	public List<User> getUsers(final int conf_id, final Role the_role)
	{
		List<User> users = new ArrayList<User>();

		ResultSet result;

		final String get_users_by_role = "SELECT * FROM user WHERE user_id IN " +
				"(SELECT DISTINCT user_id FROM user_role_paper_conference_join " +
				"WHERE role_id = ? AND conf_id = ?)";
	
		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_users_by_role);
			stmt.setInt(1, the_role.ordinal());
			stmt.setInt(2, conf_id);
			result = stmt.executeQuery();

			while ( result.next() ) {
				User user = createUser(result);
				user.setRole(the_role);
				users.add(user);
			}
		} catch (Exception e) {}

		return users; 		
	}
	
	/**
	 * Return a list of all users with the particular role.
	 * @param the_role_type the role to search by
	 * @author Danielle
	 * @return a list of all users with the given role
	 */
	public List<User> getUsers(final Role the_role_type)
	{
		List<User> users = new ArrayList<User>();

		ResultSet result;

		final String get_users_by_role = "SELECT * FROM user WHERE user_id IN " +
				"(SELECT DISTINCT user_id FROM user_role_paper_conference_join " +
				"WHERE role_id = ?)";
		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_users_by_role);
			stmt.setInt(1, the_role_type.ordinal());
			result = stmt.executeQuery();

			while ( result.next() ) {
				User user = createUser(result);
				user.setRole(the_role_type);
				users.add(user);
			}
		} catch (Exception e) {}

		return users; 		
	}

	/**
	 * Return a list of all users who are not admins.
	 * @author Danielle
	 * @return a list of all users (not admins)
	 */
	public List<User> getUsers()
	{
		List<User> users = new ArrayList<User>();

		ResultSet result;

		final String get_all_users = "SELECT * FROM user WHERE user_id NOT IN " +
				"(SELECT DISTINCT user_id FROM user_role_paper_conference_join " +
				"WHERE role_id = ?)";
		try {
			PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(get_all_users);
			stmt.setInt(1, Role.ADMIN.ordinal());
			result = stmt.executeQuery();

			while ( result.next() ) {
				users.add(createUser(result));
			}
		} catch (Exception e) {System.err.println(e);}

		return users;
	}
	
	
	/**
	 * Convert result set which utalized SELECT * to fill in user_id, first and last name
	 * and email.
	 * @param result the result set from the sql query
	 * @return user with user_id, first_name, last_name, and email fields set.
	 * @throws SQLException
	 * @author Danielle
	 */
	private User createUser(ResultSet result) throws SQLException {
		User user;
		user = new User();
		user.setID(result.getInt("USER_ID"));
		user.setFirstName(result.getString("FIRST_NAME"));
		user.setLastName(result.getString("LAST_NAME"));
		user.setEmail(result.getString("EMAIL_ADDRESS"));
		return user;
	}  
}
