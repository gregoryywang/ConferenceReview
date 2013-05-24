package service;

import java.util.List;

import common.ReferenceObject;

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
   */
  public List<ReferenceObject> getRoles(final int aUserid, final int aConfid) {
    return userDao.getRolesRef(aUserid, aConfid);
  }
  
  public static UserService getInstance() {
    return new UserService();
  }
}
