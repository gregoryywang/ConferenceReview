package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import common.ReferenceObject;

/**
 * 
 * @author Roshun
 *
 */
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

	/**
	 * Returns a list of system-wide categories.
	 * @return all categories available for creating new conferences.
	 * @author Danielle
	 */
	public List<String> getCategories() 
	{
		List<String> categories = new ArrayList<String>();

		try {
			Statement stmt = AbstractDAO.getConnection().createStatement();
			ResultSet result = stmt.executeQuery(GET_CATEGORIES);

			while ( result.next() ) {
				categories.add(result.getString("DISPLAY"));
			}
		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}

		return categories; 
	}
	
	/**
	 * TO DO: Implement!
	 * Get the category id from the string text of the category.
	 * @param the_category the name of the paper category
	 * @return the id of the category.  If not valid, will return 0
	 * @author Danielle
	 */
	public int getCategory(final String the_category)
	{
		final String find_category_id = "SELECT cat_id FROM category WHERE display = ?;";
		PreparedStatement find_cat_id_stmt;
		int id = 0;
		try {
			find_cat_id_stmt = AbstractDAO.getConnection().prepareStatement(find_category_id);
			find_cat_id_stmt.setString(1, the_category);
			ResultSet cat_id = find_cat_id_stmt.executeQuery();
			while(cat_id.next())
			{
				id = cat_id.getInt("cat_id");
			}
			
		} catch (SQLException e) {
			System.err.println("CAT_DAO_getCategory()_MSG: " + e);
		}
		return id;
	}
}
