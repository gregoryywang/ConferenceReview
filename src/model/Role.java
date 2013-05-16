package model;

/**
 * Describes the type of roles a user can be.
 * @author Danielle Tucker
 * @version 14 May 2013
 */
public enum Role {
	/**
	 * Default role type.
	 */
	USER("User"),
	
	/**
	 * Role which can create conferences only.
	 */
	ADMIN("Administrator"),
	
	/**
	 * Role which can submit and edit papers.
	 */
	AUTHOR("Author"),
	
	/**
	 * Role which can review a paper, and view papers
	 * assigned to them.
	 */
	REVIEWER("Reviewer"),
	
	/**
	 * Role which can assign papers to reviewers, view and make
	 * recommendation for papers assigned to them.
	 */
	SUB_PROGRAM_CHAIR("SubProgram Chair"),
	
	/**
	 * Role which can assign papers to sub program chairs,
	 * make decisions regarding final status of paper submitted
	 * to a conference, view papers associated with a conference.
	 */
	PROGRAM_CHAIR("Program Chair");
	
	private String my_text;
	
	Role(final String the_string)
	{
		my_text = the_string;
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
