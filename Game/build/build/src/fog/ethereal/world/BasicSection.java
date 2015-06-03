package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
public class BasicSection {
	private ArrayList<BasicPlatform> platforms;
	private ArrayList<SavePoint> points;
	
	public BasicSection() {
		points = new ArrayList<SavePoint>();
	}
	
	public BasicSection(ArrayList<Point> points) {
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		this.points = new ArrayList<>();
		for(Point p: points) {
			this.points.add(new SavePoint(p.getX(), p.getY()));
		}
	}
	
	
	@XmlElement(name = "p")
	public ArrayList<SavePoint> getPoints() {
		return points;
	}
	
	public void setPoints(ArrayList<SavePoint> points) {
		this.points.clear();
		this.points.addAll(points);
	}
	
	public String toString() {
		String temp = "Section: " + platforms.size() + " platforms:";
		for(int i = 0; i < platforms.size(); i++) {
			temp += "\n\tp" + i + ": " + platforms.get(i);
		}
		return temp;
	}
}
