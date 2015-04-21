package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.WorldObject;

@XmlRootElement(name = "level")
public class Level {
	private ArrayList<Section> sections;
	private ArrayList<DragNode> dragpoints;
	private String name;
	
	public Level(String name) {
		this.name = name;
		sections = new ArrayList<Section>();
		dragpoints = new ArrayList<DragNode>();
	}
	
	public void addDragpoints() {
		for(Section s: sections) {
			dragpoints.addAll(s.getDragpoints());
		}
	}
	
	public void removeDragpoints() {
		dragpoints.clear();
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement(name = "section")
	public List<Section> getSections() {
		return sections;
	}
	
	public void addSections(List<Section> sections) {
		this.sections.addAll(sections);
	}
}
