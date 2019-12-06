import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
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
	private static HashMap<Integer, int[]> internalStationMap = new HashMap<>();
	
	/**
	 * Internal map of the pixel locations of each station by ID
	 */
	private static HashMap<Integer, int[]> locationsMap = new HashMap<>();
	
	/**
	 * The image of the map to display
	 */
	private BufferedImage map;
	
	
	public GraphicUtil() {
		
		try {   
			map = ImageIO.read(new File("data-files/VBMap.png"));
		} catch (IOException ex) {
	        System.out.println("Error: Map image could not be found.");
	       
	    }
		
		
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		g.setColor(Color.black);
		g.drawImage(map, 0, 0, 600, 400, this);
		for(Map.Entry<Integer, int[]> entry: locationsMap.entrySet()) {
			int[] xy = entry.getValue();
			int x = xy[0]/2;
			int y = xy[1]/2;
			String stationName = ValleyBikeSim.stationsMap.get(entry.getKey()).getName();
			//g.fillRect(xy[0]/2, xy[1]/2, 10, 10);
			g.fillOval(x, y, 5, 5);
			g.drawString(stationName, x, y);
			
		}
		
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
