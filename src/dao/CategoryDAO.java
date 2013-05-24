package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import common.ReferenceObject;

public class CategoryDAO {
  public static String GET_CATEGORIES = "SELECT * FROM CATEGORY";
  
  public CategoryDAO() {}
  
  /**
   * Returns a list of categories.
   */
  public List<ReferenceObject> getCategoriesRef() {
    List<ReferenceObject> refs = new ArrayList<ReferenceObject>();
    ResultSet result = null;
   
    try {
      Statement stmt = AbstractDAO.getConnection().createStatement();
      result = stmt.executeQuery(GET_CATEGORIES);

      while ( result.next() ) {
        refs.add(new ReferenceObject(result.getString("DISPLAY"), result.getObject("CAT_ID")));
      }
    } catch (Exception e) {}

    return refs; 
  }
}
