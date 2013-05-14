package dao;

import java.sql.*;
import model.User;
import model.Role;

import java.util.ArrayList;
import java.util.Collection;

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
  private static final String AUTHENTICATE = "SELECT * FROM USER WHERE USERID = ? AND PASSWORD = ?";
  
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
  public User authenticate(final int aUserid, final int aPassword) {
    ResultSet result = null;
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(AUTHENTICATE);
      stmt.setInt(1, aUserid);
      stmt.setInt(2, aPassword);
      result = stmt.executeQuery();
    } catch (Exception e) {}
    
    return (User) getRowsFromResultSet(result);
  }
  
  /**
   * Gets user roles based on conference and user id.
   */
  public Collection<Role> getRoles(final int aUserId, final int aConfId) {
    ResultSet result = null;
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_ROLES);
      stmt.setInt(1, aUserId);
      stmt.setInt(2, aConfId);
      result = stmt.executeQuery();
    } catch (Exception e) {}
    
    return (Collection<Role>) getRowsFromResultSet(result);
  }

  @Override
  protected Object getRowsFromResultSet(ResultSet aResult) {
    Collection<Role> roleObject = new ArrayList<Role>();
    try {
      while ( aResult.next() ) {
        ;;
      }
    } catch (Exception e) {}
    
    return roleObject;
  }
}
