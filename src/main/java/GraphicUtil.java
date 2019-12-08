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
	
	
	public static boolean running = false;
	
	
	public GraphicUtil() {
		
		try {   
			map = ImageIO.read(new File("data-files/VBMap.png"));
		} catch (IOException ex) {
	        System.out.println("Error: Map image could not be found.");
	       
	    }
		
		
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		running = true;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		g.drawImage(map, 0, 0, 600, 400, this);
		for(Map.Entry<Integer, int[]> entry: locationsMap.entrySet()) {
			int[] xy = entry.getValue();
			int x = xy[0]/2;
			int y = xy[1]/2;
			String stationName = ValleyBikeSim.stationsMap.get(entry.getKey()).getName();
			//g.fillRect(xy[0]/2, xy[1]/2, 10, 10);
			g.setColor(Color.black);
			g.fillOval(x, y, 5, 5);
			
			int stringSize = g.getFontMetrics().stringWidth(stationName);
			int[] xyAdj = getModified(x,y,stringSize);
			g.setColor(new Color(255,255,255,127));
			g.fillRect(xyAdj[0], xyAdj[1] - 10, stringSize, 12);
			g.setColor(Color.black);
			
			g.drawString(stationName, xyAdj[0], xyAdj[1]);
			
			if(Math.abs(xyAdj[1] - y) > 15) {
				g.drawLine(x, y, xyAdj[0] + stringSize/2, xyAdj[1]);
			}
			
			int bikesX = xyAdj[0] + stringSize/2;
			int dotsY = xyAdj[1];
			int docksX = xyAdj[0] + stringSize/2 + 12;
			
			int[] bikesDocks = internalStationMap.get(entry.getKey());
			Color bikesColor;
			Color docksColor;
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
			
			
			//10,380
			//10,360
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
	
	
	public static void setRawLocation(Integer ID, int[] location) {
		locationsMap.put(ID,location);
	}
	
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
