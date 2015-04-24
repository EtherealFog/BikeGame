package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

import javax.xml.bind.annotation.XmlElement;

import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.Translation;

public class Section {
	private ArrayList<Platform> platforms;
	private ArrayList<DragNode> dragpoints;
	
	public Section(ArrayList<Point> points) {
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		platforms = new ArrayList<Platform>();
		for(int i = 1; i < points.size(); i++) {
			platforms.add(new Platform(points.get(i - 1), points.get(i)));
		}
	}
	
	public Section(BasicSection s) {
		ArrayList<BasicPlatform> basics = s.getPlatforms();
		platforms = new ArrayList<Platform>();
		for(BasicPlatform bp: basics) {
			platforms.add(new Platform(bp));
		}
	}
	
	public void update(Translation t) {
		
	}
	
	public ArrayList<DragNode> addDragpoints() {
		ArrayList<DragNode> nodes = new ArrayList<DragNode>();
		nodes.add(new DragNode(null, platforms.get(0)));
		for(int i = 1; i < platforms.size() - 1; i++) {
			nodes.add(new DragNode(platforms.get(i - 1), platforms.get(1)));
		}
		nodes.add(new DragNode(platforms.get(platforms.size() - 1), null));
		dragpoints = nodes;
		return nodes;
	}
	
	public ArrayList<DragNode> removeDragpoints() {
		ArrayList<DragNode> temp = new ArrayList<DragNode>(dragpoints);
		dragpoints.clear();
		return temp;
	}
	
	public void addSelfTo(Group parent) {
		
	}
	
	@XmlElement(name = "platform")
	public List<Platform> getPlatforms() {
		return platforms;
	}
	
	public BasicSection toBasicSection() {
		BasicSection temp = new BasicSection();
		ArrayList<BasicPlatform> temps = new ArrayList<BasicPlatform>();
		for(Platform p: platforms) {
			temps.add(p.toBasicPlatform());
		}
		temp.addAll(temps);
		return temp;
	}
	
	public String toString() {
		String temp = "Section: " + platforms.size() + " platforms:";
		for(int i = 0; i < platforms.size(); i++) {
			temp += "\n\tp" + i + ": " + platforms.get(i);
		}
		return temp;
	}
}
