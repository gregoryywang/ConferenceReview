package gui;

import java.util.Collection;

import model.Paper;
import model.Review;
import model.Viewer;

/**
 * Class to create a AdministratorView Object that
 * represents what an Administrator User Interface
 * looks like.
 * 
 * @author Levon Kechichian
 * @version 1.0
 */
public class AdminView implements Viewer
{
	/**
	 * Creates a default AdminView Object.
	 */
	public AdminView()
	{
		
	}
	
	/**
	 * Returns a Collection of Paper Objects.
	 * 
	 * @return the Collection of Paper Objects
	 */
	@Override
	public Collection<Paper> viewPapers() {
		return null;
	}

	/**
	 * Returns a Collection of Review Objects.
	 * 
	 * @return the Collection of Review Objects
	 */
	@Override
	public Collection<Review> viewReviews() {
		return null;
	}
	
}
