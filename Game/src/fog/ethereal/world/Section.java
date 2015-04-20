package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;

public class Section {
	private ArrayList<Platform> platforms;
	
	public Section(ArrayList<Point> points) {
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		for(int i = 1; i < points.size(); i++) {
			platforms.add(new Platform(points.get(i - 1), points.get(i)));
		}
	}
}
