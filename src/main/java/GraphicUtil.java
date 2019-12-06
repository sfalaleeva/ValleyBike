import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * A utility that controls the graphical representation of the bikes
 * @author laurenmeyer
 *
 */
@SuppressWarnings("serial")
public class GraphicUtil extends JPanel {
	
	/**
	 * Internal map of station ID, bike count and available dock count
	 */
	private static HashMap<Integer, int[]> internalStationMap;
	
	/**
	 * Internal map of the pixel locations of each station by ID
	 */
	private static HashMap<Integer, int[]> locationsMap;
	
	
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	/**
	 * Sets the internal map of stations, bikes and docks to the given one. 
	 * @param inputMap - Station Id mapped to a list of two integers, the number of bikes and available docks at the station.
	 */
	public static void setParams(HashMap<Integer,int[]> inputMap) {
		internalStationMap = inputMap;
	}
	
	
	public static void setRawLocation(Integer ID, int[] location) {
		locationsMap.put(ID,location);
	}
}
