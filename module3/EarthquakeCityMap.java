package module3;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

import parsing.ParseFeed;
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }  
	    int yellow = color(255, 255, 0);
	    int red = color(255,0,0);
	    int blue = color(0,0,255);
	        
	    for (PointFeature eq: earthquakes) {
	    	markers.add(createMarker(eq));
	    }
	    
	    
	    for (Marker mk: markers) {
	    	float magnit = (float) mk.getProperty("magnitude");
	    	if (  magnit <  4 ) {
	    		mk.setColor(blue);
	    	    ((SimplePointMarker) mk).setRadius(6);
	    	}
	    	else{
	    		if ( magnit >= 4  && magnit <=4.9){
	    			mk.setColor(yellow);
		    		((SimplePointMarker) mk).setRadius(12);
	    		}
	    		else{
	    			mk.setColor(red);
		    		((SimplePointMarker) mk).setRadius(18);
	    		}
	    	}
	    } 
	    
	    map.addMarkers(markers);
	}
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation(), feature.getProperties());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(153);
		rect(10, 250, 150, 150);
		textSize(18);
		fill(0, 102, 153, 51);
		text("Earthquake key", 15, 270);
		fill(color(255,0,0));
		ellipse(30, 290, 18, 18);
		textSize(12);
		text("5.0+ magnitude", 50, 290);
		fill(color(255, 255, 0));
		ellipse(30, 330, 12, 12);
		textSize(12);
		text("4.0+ magnitude", 50, 330);
		fill(color(0,0,255));
		ellipse(30, 360, 6, 6);
		textSize(12);
		text("Below 4.0 magnitude", 35, 360);
		

	
	}
}
