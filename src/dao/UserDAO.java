package dao;

import java.sql.*;
import model.User;
import model.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.ReferenceObject;

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
  		                                      "INNER JOIN ROLE_TYPE AS B ON A.ROLE_ID = B.ROLE_ID " +
  		                                     "WHERE CONF_ID = ? AND USER_ID = ?";
  /**
   * Determines whether a user is an Administrator.
   */
  private static final String IS_ADMIN = "SELECT NULL " +
                                           "FROM USER_ROLE_PAPER_CONFERENCE_JOIN AS A " +
                                           " INNER JOIN ROLE_TYPE AS B ON A.ROLE_ID = B.ROLE_ID " +
                                           "WHERE B.ROLE_TYPE = ? ";
  
  /**
   * Returns a user based on userid.
   * @param aUserid The user id of the User to return.
   * @return Returns the User object.
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
   * Returns a list of Roles as Reference Objects.
   * @return Reference Objects of Roles.
   */
  public List<ReferenceObject> getRolesRef(final int aUserId, final int aConfId) {
    List<ReferenceObject> refs = new ArrayList<ReferenceObject>();
    
    ResultSet result = null;
    
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_ROLES);
      stmt.setInt(1, aUserId);
      stmt.setInt(2, aConfId);
      result = stmt.executeQuery();
      
      while ( result.next() ) {
        refs.add(new ReferenceObject(result.getString("TITLE"), result.getObject("ROLE_ID")));
      }
    } catch (Exception e) {}
    
    return refs;
  }
   
  /**
   * Returns whether a user is an administrator.
   */
  public boolean isAdmin(final int aUserid) {
    ResultSet rs = null;
    boolean result = false;
    
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(IS_ADMIN);
      stmt.setInt(1, aUserid);
      stmt.setInt(2, Role.ADMIN.ordinal()); //DANIELLE!!!!!!!! DID THIS!!!!!
      rs = stmt.executeQuery();
      
      result = rs.next();
    
    } catch (Exception e) {}
    
    return result;
   }
  
  /**
   * Gets a user object based on userid.
   */
  public User getUser(final int aUserid) {
    ResultSet result = null;
    User user = null;
    
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_USER);
      stmt.setInt(1, aUserid);
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
   * Returns a list of system users based on role type.
   * @param aRoleType The role type.
   */
  public List<ReferenceObject> getUsersRef(final Role aRoleType) {
    List<ReferenceObject> refs = new ArrayList<ReferenceObject>();
    
    ResultSet result = null;
    
    final String USER_REFS = "SELECT * FROM USER AS A " +
                             " INNER JOIN USER_ROLE_PAPER_CONFERENCE_JOIN AS B ON A.USER_ID = B.USER_ID " +
                             "WHERE ";
    try {
      PreparedStatement stmt = AbstractDAO.getConnection().prepareStatement(GET_ROLES);
      //stmt.setInt(1, aUserId);
      //stmt.setInt(2, aConfId);
      result = stmt.executeQuery();
      
      while ( result.next() ) {
        refs.add(new ReferenceObject(result.getString("TITLE"), result.getObject("ROLE_ID")));
      }
    } catch (Exception e) {}
    
    return refs; 
  }
  
  /**
   * Returns a list of system users based on role type.
   * @param aRoleType The role type.
   */
  public List<ReferenceObject> getUsersRef() {
    ResultSet result = null;
    List<ReferenceObject> refs = new ArrayList<ReferenceObject>();
    
    final String USER_REFS = "SELECT * FROM USER AS A ";
                             
    try {
      Statement stmt = AbstractDAO.getConnection().createStatement();
      result = stmt.executeQuery(USER_REFS);
      
      while ( result.next() ) {
        refs.add(new ReferenceObject(result.getString("LAST_NAME") + ", " + result.getString("LAST_NAME"),
                                     result.getObject("USER_ID")));
      }
    } catch (Exception e) {}
    
    return refs; 
  } 
}
