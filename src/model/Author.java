package model;

import java.util.List;

import model.Conference.Deadline;
import service.ConferenceService;
import service.PaperService;

public class Author extends User
{

	private List<Paper> papers;
	
  /**
	 * Create an author with Author's functionality and business rules.
	 * @param the_user the user to convert to an author.
	 */
	public Author(final User the_user)
	{
		super(the_user);
		setRole(Role.AUTHOR);
		papers = PaperService.getInstance().getAssignedPapers(getID(), getConference().getID(), Role.AUTHOR); 
	}

	/**
	 * View all papers associated with this author.
	 * @return all papers which are associated with this author.
	 */
	public List<Paper> viewPapers()
	{
		return papers;
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
			PaperService.getInstance().assignPaper(the_paper.getID(), getID(), getConference().getID(), Role.AUTHOR);
			setChanged();
      notifyObservers();
		}
		else 
		{
			throw new Exception("Submission must be before Submission Deadline of " +getConference().getDeadline(Deadline.SUBMIT_PAPER) +"for this conference. Today's date is: " + currentDate() );
		}
	}

	/**
	 * Submit a revised document (content) for this paper after it has been approved.
	 * @param the_paper the paper object to append the revised document to.
	 * @param the_text the text of the revised document.
	 * @throws Exception if attempting to submit a revised document after the revision 
	 * deadline for the conference or if the paper has not been approved.
	 */
	public void submitRevisedDocument(Paper the_paper, final String the_text) throws Exception
	{
		if(getConference().getDeadline(Deadline.REVISE_PAPER).after(currentDate()) &&
				the_paper.getStatus() == Status.ACCEPT)
		{
			the_paper.setRevisedContent(the_text);
			PaperService.getInstance().savePaper(the_paper);
		}
		else throw new Exception("Papers may only be revised after the initial submission" +
				"has been approved and before the Revise Paper Date for this Conference." +
				"\nYour paper has status: " + the_paper.getStatus() + "\nThe revision deadline is: " +
				getConference().getDeadline(Deadline.REVISE_PAPER));
	}

	/**
	 * TO DO: Implement!!
	 * 
	 * Remove a paper which has been submitted for review.
	 * @param the_paper the paper which to remove
	 * @throws Exception if attempting to delete a paper after the submission deadline.
	 */
	public void deletePaper(final Paper the_paper) throws Exception
	{
		if (getConference().getDeadline(Deadline.SUBMIT_PAPER).after(currentDate()))
		{
			PaperService.getInstance().deletePaper(the_paper.getID());
		}
		else
		{
			throw new Exception("Papers may only be deleted before submission deadline of " +
						getConference().getDeadline(Deadline.SUBMIT_PAPER));
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
			throw new Exception("Papers may only be modified before submission deadline of " +
					getConference().getDeadline(Deadline.SUBMIT_PAPER));
	}	}
	

	@Override
	/**
	 * Will always set the role to Role.AUTHOR
	 */
	public void setRole(final Role the_role)
	{
		super.setRole(Role.AUTHOR);
	}
	
	/**
	 * Papers can only be submitted, modified, or deleted before the submission deadline.
	 * @return if a paper can be submitted, modified, or deleted.
	 */
	public boolean canSubmitOrModify()
	{
		boolean result = false;
		if(getConference().getDeadline(Deadline.SUBMIT_PAPER).after(currentDate()))
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * Determine if a paper can be revised.  Once the deadline for submission has
	 * passed, if the paper has been approved and it is before the revise paper
	 * deadline then the author can submit an updated revision of the paper text.
	 * @param the_paper the paper which you want to revise.
	 * @return if the paper is open for revision.
	 */
	public boolean canRevise(final Paper the_paper)
	{
		boolean result = false;
		if(getConference().getDeadline(Deadline.REVISE_PAPER).after(currentDate()) &&
				the_paper.getStatus()==Status.ACCEPT)
		{
			result =  true;
		}
		return result;
	}
}
