import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
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
	
	/**
	 * Whether or not the graphic is open: governs repaint() calls in ValleyBikeSim
	 */
	public static boolean running = false;
	
	/**
	 * Utility that deals with painting and updating the map
	 */
	public GraphicUtil() {
		//Attempt to read in the image
		try {   
			map = ImageIO.read(new File("data-files/VBMap.png"));
		} catch (IOException ex) {
	        System.out.println("Error: Map image could not be found.");
	       
	    }
		
		
	}
	
	/**
	 * Method that actually repaints the window
	 */
	public void paint(Graphics g) {
		super.paint(g);
		running = true;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		
		//Draw the map image in the background
		g.drawImage(map, 0, 0, 600, 400, this);
		
		//For every station on the map, draw a dot and a label
		for(Map.Entry<Integer, int[]> entry: locationsMap.entrySet()) {
			int[] xy = entry.getValue();
			int x = xy[0]/2;
			int y = xy[1]/2;
			String stationName = ValleyBikeSim.stationsMap.get(entry.getKey()).getName();
			g.setColor(Color.black);
			g.fillOval(x, y, 5, 5);
			
			int stringSize = g.getFontMetrics().stringWidth(stationName);
			
			//Move the labels so they don't overlap too badly
			int[] xyAdj = getModified(x,y,stringSize);
			g.setColor(new Color(255,255,255,127));
			g.fillRect(xyAdj[0], xyAdj[1] - 10, stringSize, 12);
			g.setColor(Color.black);
			
			g.drawString(stationName, xyAdj[0], xyAdj[1]);
			
			//Draw a helper line if the label is too far from the dot
			if(Math.abs(xyAdj[1] - y) > 15) {
				g.drawLine(x, y, xyAdj[0] + stringSize/2, xyAdj[1]);
			}
			
			int bikesX = xyAdj[0] + stringSize/2;
			int dotsY = xyAdj[1];
			int docksX = xyAdj[0] + stringSize/2 + 12;
			
			int[] bikesDocks = internalStationMap.get(entry.getKey());
			Color bikesColor;
			Color docksColor;
			
			// Draws two green or red dots for each station, corresponding to the availability
			// of at least one bike and dock respectively
			if(bikesDocks[0] < 1) {
				bikesColor = Color.red;
			} else { bikesColor = Color.green; }
			if(bikesDocks[1] < 1) {
				docksColor = Color.red;
			} else { docksColor = Color.green; }
			g.setColor(bikesColor);
			g.fillOval(bikesX, dotsY, 5, 5);
			g.setColor(docksColor);
			g.fillOval(docksX, dotsY, 5, 5);
			
			
			//Puts a key in the bottom left for the dots
			g.setColor(Color.black);
			g.drawString("Availability:",10,360);
			g.drawString("Bikes:", 10, 370);
			int bikesLen = g.getFontMetrics().stringWidth("Bikes:");
			int docksLen = g.getFontMetrics().stringWidth("Docks:");
			g.drawString("Docks:", 25 + bikesLen, 370);
			g.setColor(Color.green);
			g.fillOval(11 + bikesLen, 365, 5,5);
			g.fillOval(26 + bikesLen + docksLen,365,5,5);
			g.setColor(Color.red);
			g.fillOval(18 + bikesLen, 365, 5, 5);
			g.fillOval(33 + bikesLen + docksLen, 365, 5, 5);
		
		}
		
	}
	
	/**
	 * Sets the internal map of stations, bikes and docks to the given one. 
	 * @param inputMap - Station Id mapped to a list of two integers, the number of bikes and available docks at the station.
	 */
	public static void setParams(HashMap<Integer,int[]> inputMap) {
		internalStationMap = inputMap;
	}
	
	/**
	 * Adds location pixel data to the map
	 * @param ID - station id
	 * @param location - [x,y] pixel location
	 */
	public static void setRawLocation(Integer ID, int[] location) {
		locationsMap.put(ID,location);
	}
	
	/**
	 * Moves label x and y to be further from congested areas, for maximum readability
	 * @param x - original x
	 * @param y - original y
	 * @param sSize - pixel length of the string label
	 * @return - [x,y] modified coordinates to draw the label at
	 */
	private static int[] getModified(int x, int y, int sSize) {
		int centerX = 375;
		int centerY = 265;
		double multiplier = 2.25;
		int newX;
		int newY;
		
		if(x > 300 && y > 250) {
			newX = (int)Math.round((x - centerX) * multiplier + centerX);
			newY = (int)Math.round((y - centerY) * multiplier + centerY);
		} else {
			newX = x;
			if(y > 200) {
				newY = y + 12;
			} else {
				newY = y;
			}
		}
		
		newX -= sSize/2;
		
		return new int[] {newX,newY};
	}
}
