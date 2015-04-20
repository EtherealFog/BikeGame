package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import fog.ethereal.sprite.DragNode;

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
	
	public ArrayList<DragNode> getDragpoints() {
		ArrayList<DragNode> nodes = new ArrayList<DragNode>();
		nodes.add(new DragNode(null, platforms.get(0)));
		for(int i = 1; i < platforms.size() - 1; i++) {
			nodes.add(new DragNode(platforms.get(i - 1), platforms.get(1)));
		}
		nodes.add(new DragNode(platforms.get(platforms.size() - 1), null));
		return nodes;
	}
	
	@XmlElement(name = "platform")
	public List<Platform> getPlatforms() {
		return platforms;
	}
}
