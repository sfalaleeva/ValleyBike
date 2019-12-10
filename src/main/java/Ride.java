import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class Ride {
		
		/** 
		 * ID to be assigned to next ride
		 */
		static int nextRideID = 1;
		
		/**
		 * RideID, unique to each ride object
		 */
		private int rideID;
		
		/**
		 * User ID of the user on the ride
		 */
		private int userID; 
		
		
		/**
		 * Integer ID of the bike used on the ride
		 */
		private int bikeID;
		
		
		/**
		 * Station ID the bike was taken from
		 */
		private int fromStationID;
		
		
		/**
		 * Station ID the bike was checked into
		 */
		private int toStationID;
		
		/**
		 * Start time of the ride
		 */
		private LocalDateTime startTime;
		
		
		/**
		 * End time of the ride
		 */
		private LocalDateTime endTime;
		
		
		/**
		 * Duration of the ride in seconds
		 */
		private long rideDuration;
		
		/**
		 * Ride constructor
		 * @param userID - ID of the user taking the ride
		 * @param bikeID - ID of the bike used
		 * @param fromStationID - ID of the origin station
		 * @param toStationID - ID of the destination station
		 * @param startTime - start time of the ride
		 * @param endTime - end time of the ride
		 */
		public Ride(int userID, int bikeID, int fromStationID, int toStationID, LocalDateTime startTime, LocalDateTime endTime) {
			this.rideID = nextRideID;
			this.userID = userID;
			this.bikeID = bikeID;
			this.fromStationID = fromStationID;
			this.toStationID = toStationID;
			this.startTime = startTime;
			this.endTime = endTime;
			calculateDuration();
			nextRideID++;
		} 
		
		
		/**
		 * Getter methods for the Ride objects.
		 */


		/**
		 * Gets ID of the ride
		 * @return - ride ID for this ride
		 */
		public int getID() {
			return this.rideID;
		}
		
		
		/**
		 * Gets the ID of the ride user
		 * @return - user ID of the rider
		 */
		public int getUserID() {
			return this.userID;
		}
		
		
		/**
		 * Gets the ID of the bike
		 * @return - bike ID
		 */
		public int getBikeID() {
			return this.bikeID;
		}
		
		/**
		 * Gets the ID of the origin station
		 * @return - ID of the station the ride started from
		 */
		public int getFromStationID() {
			return this.fromStationID;
		}
		
		
		/**
		 * Gets the ID of the destination station
		 * @return - ID of the destination station
		 */
		public int getToStationID() {
			return this.toStationID;
		}
		
		
		/**
		 * Gets the start time of the ride
		 * @return - ride start time
		 */
		public LocalDateTime getStartTime() {
			return this.startTime;
		}
		
		/**
		 * Gets the end time of the ride
		 * @return - ride end time
		 */
		public LocalDateTime getEndTime() {
			return this.endTime;
		}
		
		
		/**
		 * Gets the ride duration in second
		 * @return - ride duration in seconds
		 */
		public long getRideDuration() {
			return this.rideDuration;
		}
		
		
		/**
		 * Setters for the ride object attributes
		 */
		
		/**
		 * Sets the user ID of the ride
		 * @param newUserID - user ID of the rider
		 */
		public void setUserID(int newUserID) {
			this.userID = newUserID;
		}
		
		
		/**
		 * Sets the bike ID
		 * @param bikeID - id of bike ridden
		 */
		public void setBikeID(int newbikeID) {
			this.bikeID = newbikeID;
		}
		
		/**
		 * Sets the origin station
		 * @param newFromStation - the ID of the origin station
		 */
		public void setFromStationID(int newFromStation) {
			this.fromStationID = newFromStation;
		}
		
		
		/**
		 * Sets the destination station
		 * @param newToStation - ID of destination station
		 */
		public void setToStationID(int newToStation) {
			this.toStationID = newToStation;
		}
		
		
		/**
		 * Sets the start time of the ride
		 * @param newStartTime - start time of the ride
		 */
		public void setStartTime(LocalDateTime newStartTime) {
			this.startTime = newStartTime;
		}
		
		/**
		 * Sets end time of the ride
		 * @param newEndTime - new end time of the ride
		 */
		public void setEndTime(LocalDateTime newEndTime) {
			this.endTime = newEndTime;
			calculateDuration();
		}
		
		
		/**
		 * If ride is complete, calculate the duration of the ride in seconds.
		 */
		private void calculateDuration() {
			//if ride is incomplete, end time will be null
			if (this.endTime == null || this.startTime == null) {
				this.rideDuration = 0;
				return;
			}
			long duration = java.time.Duration.between(this.startTime, this.endTime).getSeconds();
			this.rideDuration = duration;
		}
		
		/**
		 * calculate the duration of an ongoing ride in milliseconds
		 * mainly used to check if bike has been out over 24 hours and still not returned. 
		 * @return - long - returns in minutes how long the ongoing ride is 
		 */
		public long calculateOngoingRide() {
			long duration = java.time.Duration.between(this.startTime, LocalDateTime.now()).getSeconds();
			return duration;
		}
		
	}

