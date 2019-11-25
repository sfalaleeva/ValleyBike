/**
 * Represents an issue that can be reported by users
 * that can be addressed based on their typeIssue
 * and marked as resolved.
 * 
 * @author Group 6
 *
 */
public class Issue {
	
	//TODO: Constructor for the issue class

	/**
	 * The issue id.
	 */
	private int issueID; 
	
	/**
	 * The user id.
	 */
	private int userID;
	
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
	private static int nextIssueId;
	
	/*
	 * Accessor Methods
	 */
	
	/**
	 * Gets issue's id.
	 * @return this issue's id.
	 */
	public int getIssueID() {
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
	public int getNextIssueId() {
		return nextIssueId;
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
