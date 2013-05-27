package service;

import java.util.List;

import common.ReferenceObject;

import model.Role;
import model.User;
import dao.UserDAO;

public class UserService {
  private final UserDAO userDao;
  
  /**
   * Private constructor prevents public instantiation.
   */
  private UserService() {
    userDao = new UserDAO();
  }
  
  /**
   * Authenticates user based on username and password.
   * @param userName The username credential.
   * @param password The password credential.
   * @return null or User object.
   */
  public User authenticateUser(final String aUserName, final String aPassword) {
    return userDao.authenticate(aUserName, aPassword);
  }
  
  /**
   * Checks whether a user with the provided userid is an Admin.
   * @param aUserid The user's id.
   * @return true or false.
   */
  public boolean isAdmin(final int aUserid) {
    return userDao.isAdmin(aUserid);
  }
  
  /**
   * Retruns a list of User Roles for a given user and conference.
   * @param aUserid The user's id.
   * @param aConfid The conference id.
   * @return a list of User Roles for a given user and conference.
   * @deprecated
   */
  public List<ReferenceObject> getRolesRef(final int aUserid, final int aConfid) {
    return userDao.getRolesRef(aUserid, aConfid);
  }
  
  /**
   * Get a list of Roles associated with a user and conference.
   * @param the_user_id the user id.
   * @param the_conf_id the conference id.
   * @return a list of Roles associated with a user and conference.
   * @author Danielle
   */
  public List<Role> getRoles(final int the_user_id, final int the_conf_id)
  {
	  return userDao.getRoles(the_user_id, the_conf_id);
  }
  
  /**
   * Get all users which are not admins.
   * @return a list of all users who are not adminstrators.
   */
  public List<User> getAllUsers()
  {
	  return userDao.getUsers();
  }
  
  public static UserService getInstance() {
    return new UserService();
  }
}
