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
	USER,
	
	/**
	 * Role which can create conferences only.
	 */
	ADMIN,
	
	/**
	 * Role which can submit and edit papers.
	 */
	AUTHOR,
	
	/**
	 * Role which can review a paper, and view papers
	 * assigned to them.
	 */
	REVIEWER,
	
	/**
	 * Role which can assign papers to reviewers, view and make
	 * recommendation for papers assigned to them.
	 */
	SUB_PROGRAM_CHAIR,
	
	/**
	 * Role which can assign papers to sub program chairs,
	 * make decisions regarding final status of paper submitted
	 * to a conference, view papers associated with a conference.
	 */
	PROGRAM_CHAIR;
}
