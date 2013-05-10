import java.sql.*;

public class h2Test {
  public static void main(String[] args) {
    try {
      Class.forName("org.h2.Driver"); 
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", ""); // add application code here
      
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery("Select * from user");
      
      while (result.next()) {
        System.out.println(result.getString("name"));
      }
      
      con.close();
      stmt.close();
      
    } catch( Exception e ) {
      System.out.println(e.getMessage());
    }

  }
}

