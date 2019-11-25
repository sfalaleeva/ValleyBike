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
		private Date startTime;
		
		
		/**
		 * End time of the ride
		 */
		private Date endTime;
		
		
		/**
		 * Duration of the ride
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
		public Ride(int userID, int bikeID, int fromStationID, int toStationID, Date startTime, Date endTime) {
			this.rideID = nextRideID;
			this.userID = userID;
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
		public Date getStartTime() {
			return this.startTime;
		}
		
		/**
		 * Gets the end time of the ride
		 * @return - ride end time
		 */
		public Date getEndTime() {
			return this.endTime;
		}
		
		
		/**
		 * Gets the ride duration
		 * @return - ride duration
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
		public void setStartTime(Date newStartTime) {
			this.startTime = newStartTime;
		}
		
		/**
		 * Sets end time of the ride
		 * @param newEndTime - new end time of the ride
		 */
		public void setEndTime(Date newEndTime) {
			this.endTime = newEndTime;
		}
		
		
		/**
		 * Calculate the duration of the ride 
		 */
		private void calculateDuration() {
			//if ride is incomplete, end time will be null
			if (this.endTime == null) {
				this.rideDuration = 0;
				return;
			}
			long duration = this.endTime.getTime() - this.startTime.getTime();
			long intoMinutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
			this.rideDuration = intoMinutes;
		}
		
	}

