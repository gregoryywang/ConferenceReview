package model;

import service.ConferenceService;
import service.PaperService;

public class Author extends User
{
	private final Role currentRole = Role.AUTHOR;
	
  public Author() {
	}
	
	public void submitPaper(Paper aPaper, final int aConfId) {
		ConferenceService.getInstance().addPaper(aPaper, aConfId);
		PaperService.getInstance().assignPaper(aPaper.getID(), getID(), aConfId, currentRole);
	}
	
	public void deletePaper(final Paper the_paper)
	{
		
	}
	
	public void modifyPaper()
	{
		
	}
}
