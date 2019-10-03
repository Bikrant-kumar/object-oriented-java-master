package module1;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class HelloWorld extends PApplet
{
	private static final long serialVersionUID = 1L;
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	private static final boolean offline = false;
	UnfoldingMap map1;
	UnfoldingMap map2;

	public void setup() {
		size(800, 600, P2D);  
		this.background(200, 200, 200);
		
		// Select a map provider
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		// Set a zoom level
		int zoomLevel = 10;
		
		if (offline) {  
			provider = new MBTilesMapProvider(mbTilesString);
			// 3 is the maximum zoom level for working offline
			zoomLevel = 3;
		}
		
		map1 = new UnfoldingMap(this, 50, 50, 350, 500, provider);
		map2 = new UnfoldingMap(this, 450, 50, 350, 500, provider);

	    map1.zoomAndPanTo(zoomLevel, new Location(32.9f, -117.2f));
	    map2.zoomAndPanTo(zoomLevel, new Location(19.0760f, 72.8777f));
		// This line makes the map interactive
		MapUtils.createDefaultEventDispatcher(this, map1);
		MapUtils.createDefaultEventDispatcher(this, map2);
		}
	public void draw() {
		map1.draw();
		map2.draw();
	}

	
}
