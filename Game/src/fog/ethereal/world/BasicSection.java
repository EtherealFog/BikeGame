package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
public class BasicSection {
	private ArrayList<BasicPlatform> platforms;
	
	public BasicSection() {
		
	}
	
	public BasicSection(ArrayList<Point> points) {
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		platforms = new ArrayList<BasicPlatform>();
		for(int i = 1; i < points.size(); i++) {
			platforms.add(new BasicPlatform(points.get(i - 1), points.get(i)));
		}
	}
	
	@XmlElement(name = "platform")
	public List<BasicPlatform> getPlatforms() {
		return platforms;
	}
	
}
