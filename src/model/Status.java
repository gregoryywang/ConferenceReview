package model;

/**
 * Describes the workflow status of a paper to be reviewed.
 * @author Danielle Tucker
 * @version 14 May 2013
 */
public enum Status {
	SUBPROGRAM_CHAIR_NEEDED,
	REVIEWS_NEEDED,
	RECOMMENDATION_NEEDED,
	DECISION_NEEDED,
	ACCEPT,
	DECLINE,
	UNDECIDED;

	/**
	 * Name in a Pretty Print for GUI consumption.
	 * @return the name in a way which is ideal for Displays
	 */
	public String displayName()
	{
		String result = "";
		switch(this)
		{
		case SUBPROGRAM_CHAIR_NEEDED:
			result = "SubProgram Chair Needed";
			break;
		case REVIEWS_NEEDED:
			result = "Reviews Needed";
			break;
		case RECOMMENDATION_NEEDED:
			result = "Recommendation Needed";
			break;
		case DECISION_NEEDED:
			result = "Final Decision Needed";
		case ACCEPT:
			result = "Paper Accepted";
		case DECLINE:
			result = "Paper Declined";
		case UNDECIDED:
			result = "Undecided";
		}
		return result;
	}
}