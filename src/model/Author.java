package model;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import model.Conference.Deadline;
import service.ConferenceService;
import service.PaperService;

public class Author extends User
{

	/**
	 * Create an author with Author's functionality and business rules.
	 * @param the_user the user to convert to an author.
	 */
	public Author(final User the_user)
	{
		super(the_user);
		setRole(Role.AUTHOR);
	}

	/**
	 * View all papers associated with this author.
	 * @return all papers which are associated with this author.
	 */
	public List<Paper> viewPapers()
	{
		return PaperService.getInstance().getAssignedPapers(getID(), getID(), Role.AUTHOR);
	}

	/**
	 * Submit a paper for this author.
	 * @param the_paper the paper to submit for this author.
	 * @throws Exception if attempting to submit a paper after the submission deadline
	 * for Author's current conference.
	 */
	public void submitPaper(final Paper the_paper) throws Exception
	{
		if (getConference().getDeadline(Deadline.SUBMIT_PAPER).after(currentDate()))
		{
			PaperService.getInstance().savePaper(the_paper);
			ConferenceService.getInstance().addPaper(the_paper, getConference().getID());
			//			PaperService.getInstance().assignPaper(the_paper.getID(), getID(), getConference().getID(), current_role);
		}
		else 
		{
			throw new Exception("Submission must be before Submission Deadline for this conference.");
		}
	}

	/**
	 * Submit a revised document for this paper after it has been approved.
	 * @param the_paper the paper object to append the revised document to.
	 * @param the_path the user's file path of the revised document.
	 * @throws Exception if attempting to submit a revised document after the revision 
	 * deadline for the conference or if the paper has not been approved.
	 */
	public void submitRevisedDocument(final Paper the_paper, final String the_path) throws Exception
	{
		if(getConference().getDeadline(Deadline.REVISE_PAPER).after(currentDate()) &&
				the_paper.getStatus() == Status.ACCEPT)
		{
			the_paper.setRevisedDocumentPath(the_path);
		}
		else throw new Exception("Papers may only be revised after the initial submission" +
				"has been approved and before the Revise Paper Date for this Conference");
	}

	/**
	 * Remove a paper which has been submitted for review.
	 * @param the_paper the paper which to remove
	 * @throws Exception if attempting to delete a paper after the submission deadline.
	 */
	public void deletePaper(final Paper the_paper) throws Exception
	{
		if (getConference().getDeadline(Deadline.SUBMIT_PAPER).after(currentDate()))
		{
			//			PaperService.getInstance().deletePaper(the_paper);
		}
		else
		{
			throw new Exception("Papers may only be deleted before submission deadline.");
		}
	}

	/**
	 * Modify a paper which has already been submitted.
	 * @param the_paper the paper with the fields modified.
	 * @throws Exception if attempting to modify paper submission after submission deadline.
	 */
	public void modifyPaper(final Paper the_paper) throws Exception
	{
		if(getConference().getDeadline(Deadline.SUBMIT_PAPER).after(currentDate()))
		{
			PaperService.getInstance().savePaper(the_paper);
		}
		else
		{
			throw new Exception("Papers may only be modified before submission deadline.");
		}
	}
	

	@Override
	/**
	 * Will always set the role to Role.AUTHOR
	 */
	public void setRole(final Role the_role)
	{
		super.setRole(Role.AUTHOR);
	}

	/**
	 * Get the current sql date for comparison against the deadlines for the conference.
	 * @return current date and time.
	 */
	private Date currentDate() {
		Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
		return timeNow;
	}

}
