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
}
//?? Should this be part of the Paper??