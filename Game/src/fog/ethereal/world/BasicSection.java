package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
public class BasicSection {
	private ArrayList<BasicPlatform> platforms;
	
	public BasicSection() {
		platforms = new ArrayList<BasicPlatform>();
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
	public ArrayList<BasicPlatform> getPlatforms() {
		return platforms;
	}
	
	public void setPlatforms(ArrayList<BasicPlatform> platforms) {
		this.platforms.clear();
		this.platforms.addAll(platforms);
	}
	
	public void addAll(ArrayList<BasicPlatform> platforms) {
		this.platforms.addAll(platforms);
	}
	
	public String toString() {
		String temp = "Section: " + platforms.size() + " platforms:";
		for(int i = 0; i < platforms.size(); i++) {
			temp += "\n\tp" + i + ": " + platforms.get(i);
		}
		return temp;
	}
}
