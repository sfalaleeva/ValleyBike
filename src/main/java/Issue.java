/**
 * Represents an issue that can be reported by users
 * that can be addressed based on their typeIssue
 * and marked as resolved.
 * 
 * @author Group 6
 *
 */
public class Issue {
	
	

	/**
	 * The issue id.
	 */
	private Integer issueID; 
	
	/**
	 * The user id.
	 */
	private int userID;
	
	/**
	 * The bike id, if applicable
	 */
	private int bikeID = -1;
	
	
	/**
	 * Boolean indicates status of issue.
	 * True if resolved.
	 */
	private boolean resolved;
	
	/**
	 * String specified issue details.
	 */
	private String description;
	
	/**
	 * TypeIssue specifies what type of issue.
	 */
	private TypeIssue typeIssue;
	
	/**
	 * Integer of next available id.
	 */
	private static int nextIssueID;
	
	
	/**
	 * Creates a new issue	
	 * @param userID - ID of the user reporting a problem
	 * @param description - description of the problem, provided by the user
	 * @param typeIssue - problem type enum
	 */
	public Issue(int userID, String description, TypeIssue typeIssue) {
		issueID = nextIssueID;
		this.userID = userID;
		this.description = description;
		this.typeIssue = typeIssue;
		resolved = false;
		
		
		nextIssueID ++;
	}
	
	/*
	 * Accessor Methods
	 */
	
	/**
	 * Gets issue's id.
	 * @return this issue's id.
	 */
	public Integer getIssueID() {
		return issueID;
	}
	
	/**
	 * Gets user's id.
	 * @return this user's id.
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Gets bike id.
	 * @return the bike id.
	 */
	public int getBikeID() {
		return bikeID;
	}
	
	/**
	 * Gets boolean indicating issue's status.
	 * @return this issue's status.
	 */
	public boolean getResolvedStatus() {
		return resolved;
	}
	
	/**
	 * Gets issue description.
	 * @return this issue's description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the issue's type.
	 * @return this issue's type.
	 */
	public TypeIssue getTypeIssue() {
		return typeIssue;
	}
	
	/**
	 * Gets the next issue id.
	 * @return next issue id.
	 */
	public Integer getNextIssueID() {
		return nextIssueID;
	}
	
	/*
	 * Setters
	 */
	
	/**
	 * Sets the issue's status.
	 * @param the resolved status.
	 */
	public void setResolvedStatus(boolean resolved) {
		this.resolved = resolved;
	}
	
	/**
	 * Sets the issue's TypeIssue.
	 * @param the TypeIssue.
	 */
	public void setTypeIssue(TypeIssue typeIssue) {
		this.typeIssue = typeIssue;
	}
	
	/**
	 * Sets the bike ID. Needs to be called if a bike is involved in the issue.
	 * @param the bike ID
	 */
	public void setBikeID(int bikeID) {
		this.bikeID = bikeID;
	}
	
	
	/*
	 * Enum Class.
	 */
	
	
	/**
	 * Defines the types of issues that can be reported.
	 */
	public enum TypeIssue {
		STATION_EMPTY,
		STATION_FULL,
		BIKE_MAINTENANCE,
		ACCOUNT,
		OTHER
	}
}
