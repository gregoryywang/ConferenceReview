package dao;

import java.sql.*;

import model.User;

/**}
 * This class used for system authentication.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public final class AuthenticationDAO extends AbstractDAO {
  /**
   * Query used to authenticate a given user.
   */
  private static final String AUTHENTICATE = "SELECT * FROM USER WHERE USERID = ? AND PASSWORD = ?";
  
  /**
   * Authenticates a user based on userid and password.
   * @param userid Unique id that identifies a user.
   * @param password The password passed by the user.
   * @return Returns a User Object if validated, null otherwise.
   */
  User authenticate(final int aUserid, final int aPassword) {
    ResultSet result = null;
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(AUTHENTICATE);
      stmt.setInt(1, aUserid);
      stmt.setInt(2, aPassword);
      result = stmt.executeQuery();
    } catch (Exception e) {}
    
    return (User) getRowsFromResultSet(result);
  }

  @Override
  protected Object getRowsFromResultSet(ResultSet aResult) {
    User userObject = new User();
    try {
      while ( aResult.next() ) {
        ;;
      }
    } catch (Exception e) {}
    
    return userObject;
  }
}
