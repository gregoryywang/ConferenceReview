package dao;

import java.sql.*;
import model.User;
import model.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class used for system authentication.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public final class UserDAO extends AbstractDAO {
  /**
   * Query used to authenticate a given user.
   */
  private static final String AUTHENTICATE = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
  
  /**
   * Query used to return user roles based on provided conference.
   */
  private static final String GET_ROLES = "SELECT B.ROLE_ID, B.TITLE " +
  		                                      "FROM USER_ROLE_CONFERENCE AS A" +
  		                                      "INNER JOIN ROLE AS B ON A.ROLE_ID = B.ROLE_ID " +
  		                                     "WHERE CONF_ID = ? AND USER_ID = ?";
  
  
  
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
        user = new User();
        user.setID(result.getInt("USER_ID"));
        user.setFirstName(result.getString("FIRST_NAME"));
        user.setLastName(result.getString("LAST_NAME"));
        user.setEmail(result.getString("EMAIL_ADDRESS"));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    return user;
  }
  
  /**
   * Gets user roles based on conference and user id.
   */
  public List<Role> getRoles(final int aUserId, final int aConfId) {
    ResultSet result = null;
    List<Role> roles = new ArrayList<Role>();
    
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_ROLES);
      stmt.setInt(1, aUserId);
      stmt.setInt(2, aConfId);
      result = stmt.executeQuery();
      
      while ( result.next() ) {
        ;
      }
    } catch (Exception e) {}
    
    return roles;
  }
}
