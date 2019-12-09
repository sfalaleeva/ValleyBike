import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValleyBikeSimTest {
	
	private int numBikesStations = 0;
	private int numBikesStationMap = 0;
	private boolean isConsistent;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		getNumBikesStation();
		getNumBikesStationMap();
		isConsistent = consistentStationData();
	}
	
	@Test
	/** Tests that equalize maintains the bikes in the station and doesn't
	 *  result in inconsistent station data.
	 */
	public void testEqualize() {
		assertEquals(true, isConsistent);
		assertEquals(numBikesStations, numBikesStationMap);
		ValleyBikeSim.equalizeStations();
		
		// recalculate values after equalize
		getNumBikesStation();
		getNumBikesStationMap();
		isConsistent = consistentStationData();
		
		assertEquals(true, isConsistent);
		assertEquals(numBikesStations, numBikesStationMap);
	}
	
	@Test
	/** Test that rides are correctly added to dailyRideMap according to yyyy-MM-dd (RIDE_DATE_FORMAT).
	 *  Additional rides in the same day end up in the same mapping. */
	public void testAddCurrentRideToMap() {
		String date = "2019-3-4 12:12:12";
		LocalDateTime formattedDate = inputUtil.toLocalDateTime(date, inputUtil.LOCAL_DATE_TIME_FORMAT);
		String key = inputUtil.localDateTimeToString(formattedDate, inputUtil.LOCAL_DATE_FORMAT);
		Ride ride = new Ride(1,1,1,1,formattedDate, formattedDate);
		Ride ride2 = new Ride(2,1,1,1,formattedDate, formattedDate);
		
		// add one ride
		ValleyBikeSim.addCurrentRideToMap(ride);
		assertNotNull(ValleyBikeSim.dailyRidesMap.get(key));
		
		// add the second ride in same day
		ValleyBikeSim.addCurrentRideToMap(ride2);
		
		ArrayList<Ride> expectedRides = new ArrayList<>();
		expectedRides.add(ride);
		expectedRides.add(ride2);
		
		ArrayList<Ride> storedRides = ValleyBikeSim.dailyRidesMap.get(key);
		
		assertEquals(expectedRides, storedRides);
		
	}

	/**
	 * Get number of bikes according to station.getBikes().
	 */
	private void getNumBikesStation() {
		for (Station s: ValleyBikeSim.stationsMap.values()) {
			numBikesStations += s.getBikes();
		}
	}
	
	/**
	 * Get number of bikes according to stationToBikeMap.
	 */
	private void getNumBikesStationMap() {
		for (ArrayList<Integer> bikeIds: ValleyBikeSim.stationToBikeMap.values()) {
			numBikesStationMap += bikeIds.size();
		}
	}
	
	/** Determine if number of bikes, available docks, and 
	 * capacity are consistent.
	 * @return true if all stations consistent
	 */
	private boolean consistentStationData() {
		for (Station s: ValleyBikeSim.stationsMap.values()) {
			if (s.getBikes() + s.getAvailableDocks() != s.getCapacity()) {
				return false;
			}
		}
		return true;
	}
}
