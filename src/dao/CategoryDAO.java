package dao;

import java.sql.ResultSet;
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
}
