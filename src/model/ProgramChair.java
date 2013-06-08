package model;

import java.util.List;

import model.Conference.Deadline;

import service.PaperService;

/**
 * A Class to describe the functionality of a Program Chair
 * @author Danielle Tucker
 * @version 2013 June
 */
public class ProgramChair extends User
{
	/**
	 * Devault constructor.
	 */
	public ProgramChair()
	{
		super();
		super.setRole(Role.PROGRAM_CHAIR);
	}
	
	/**
	 * Copy constructor
	 * @param the_user the user to turn into a Program Chair
	 */
	public ProgramChair(final User the_user)
	{
		super(the_user);
		super.setRole(Role.PROGRAM_CHAIR);
	}
	
	/**
	 * Assign a true(accept) or false(decline) decision to this paper.
	 * @param the_decision the decision (true = accept, false = decline)
	 * @param the_paper the paper to assign the decision.
	 * @throws Exception if the paper is not open for making a decision (past the deadline)
	 */
	public void assignDecision(final boolean the_decision, Paper the_paper) throws Exception
	{
		if(!canAssignDecision())
		{
			throw new Exception("You are past the " + getConference().getDeadline(Deadline.FINAL_DECISION)
					+" deadline for making a decision.");
			
		}
		if(the_decision)
		{
			the_paper.setStatus(Status.ACCEPT);
			PaperService.getInstance().savePaper(the_paper);
		}
		else
		{
			the_paper.setStatus(Status.ACCEPT);
		}
		PaperService.getInstance().savePaper(the_paper);
	}
	
	/**
	 * Assign a Subprogram Chair to a paper.
	 * @param the_user the subprogram chair to assign to this paper
	 * @param the_paper the paper
	 * @throws Exception if the proposed subprogram chair is the author of this paper (business rule)
	 */
	public void assignSPChair(final User the_user, final Paper the_paper) throws Exception
	{
		if(the_user.getID()!= the_paper.getAuthor().getID())
		{
			PaperService.getInstance().assignPaper(the_paper.getID(), the_user.getID(), 
				this.getConference().getID(), Role.SUB_PROGRAM_CHAIR);
		}
		else
		{
			throw new Exception("Subprogram Chair may not be Author of the Paper.");
		}
	}
	
	/**
	 * View all papers associated with this users current conference.
	 * @return a list of all papers associated with this user and the current conference.
	 */
	public List<Paper> viewPapers()
	{

		return PaperService.getInstance().getAssignedPapers(getID(), getConference().getID(), Role.PROGRAM_CHAIR);
	}
	
	/**
	 * Does nothing. (override so role cannot be changed)
	 * @param the_role the role to change this user to (ignored)
	 */
	@Override
	public void setRole(final Role the_role)
	{
		//do nothing as role is final and is set in constructor
	}
	
	/**
	 * If the program chair's current conference is still open for
	 * the program chair to write a decision.
	 * @return if this program chair can assign a final decision based upon the conference
	 * deadline date.
	 */
	public boolean canAssignDecision()
	{
		boolean result = false;
		if(getConference().getDeadline(Deadline.FINAL_DECISION).after(currentDate()))
		{
			result = true;
		}
		return result;
	}
	
	/**
   * Get all reviewers assigned to the paper.
   * @param the_paper the reviewers assigned to the paper
   * @return all reviewers assigned to the paper.
   */
  public List<Reviewer> getReviewers(final Paper the_paper)
  {
    return PaperService.getInstance().getAssignedReviewers(the_paper.getID());
  }
}
