package model;

import gui.AdminView;
import gui.AuthorView;
import gui.AuthorView;
import gui.PGChairView;
import gui.ReviewerView;
import gui.SubPGChairView;

/**
 * Describes the type of roles a user can be.
 * @author Danielle Tucker
 * @version 14 May 2013
 */
public enum Role {
	/**
	 * Default role type.
	 */
	USER("User", AuthorView.class),
	
	/**
	 * Role which can create conferences only.
	 */
	ADMIN("Administrator", AdminView.class),
	
	/**
	 * Role which can submit and edit papers.
	 */
	AUTHOR("Author", AuthorView.class),
	
	/**
	 * Role which can review a paper, and view papers
	 * assigned to them.
	 */
	REVIEWER("Reviewer", ReviewerView.class),
	
	/**
	 * Role which can assign papers to reviewers, view and make
	 * recommendation for papers assigned to them.
	 */
	SUB_PROGRAM_CHAIR("SubProgram Chair", SubPGChairView.class),
	
	/**
	 * Role which can assign papers to sub program chairs,
	 * make decisions regarding final status of paper submitted
	 * to a conference, view papers associated with a conference.
	 */
	PROGRAM_CHAIR("Program Chair", PGChairView.class);
	
	private String my_text;
	private Class viewClass;
	
	Role(final String the_string, Class viewClass)
	{
		my_text = the_string;
		this.viewClass = viewClass;
	}
	
	/**
	 * Returns role's view class.
	 * @param returns role's view class.
	 */
	public Class getView() {
	  return viewClass;
	}
	
	/**
	 * Provides Client-friendly output for enum.
	 * @returns properly formatted string of enum value.
	 */
	@Override
	public String toString()
	{
		return my_text;
	}
	
	/**
	 * Provides role given text of enum.  (Will convert to-string
	 * Representation of enum back to Role.)
	 * @param the_text the enum in client-frendly output
	 * @return role associated with the text
	 * @throws IllegalArgumentException() if the_text is null or does
	 * not match enum values
	 */
	public static Role getEnum(String the_text)
	{
		if(the_text == null)
		{
			throw new IllegalArgumentException("String cannont be null");
		}
		for (Role r: values())
		{
			if(the_text.equalsIgnoreCase(r.my_text))
			{
				return r;
			}
		}
		throw new IllegalArgumentException("No matching Role");
	}
}
