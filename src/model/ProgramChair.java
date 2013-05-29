package model;

public class ProgramChair extends User
{
	private User my_user;
	
	public ProgramChair()
	{
		my_user = new User();
	}
	
	public ProgramChair(final User the_user)
	{
		my_user = new User(the_user);
		my_user.setRole(Role.PROGRAM_CHAIR);
	}
	
	public void assignDecision(final boolean the_decision)
	{
		
	}
	
	public void assignSPChairToPool(final User the_user)
	{
		
	}
	
	public void assignSPChair(final User the_user, final Paper the_paper)
	{
		
	}
}
