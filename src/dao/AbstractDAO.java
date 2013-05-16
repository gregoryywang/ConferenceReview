package dao;

import java.sql.*;

/**
 * Parent class for system DAO classes.
 * @author Roshun Jones
 * @version 1.0
 *
 */
public abstract class AbstractDAO {
  /**
   * Class connection object.
   */
  private static Connection connection = null;
  
  /**
   * Database Driver.
   */
  private static final String DRIVER = "org.h2.Driver";
  
  /**
   * Database URL.
   */
  private static final String URL = "jdbc:h2:ConferenceReview";
  
  /**
   * Database Username
   */
  private static final String USERNAME = "sa";
  
  /**
   * Database Password.
   */
  private static final String PASSWORD = "";
  
  /**
   * Returns a connection object to data source.
   * @return
   */
  protected static Connection getConnection() {
    //Utilizes a singleton pattern.
    if ( connection == null ) {
      try {
        Class.forName(DRIVER); //Load db driver 
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      } catch (Exception e) {}
    }
    
    return connection;
  }
}
