package model;

import java.util.Collection;

public interface Viewer 
{
	//??? private int my_user_ID;
	
	public Collection<Paper> viewPapers();
	
	public Collection<Review> viewReviews();
}
