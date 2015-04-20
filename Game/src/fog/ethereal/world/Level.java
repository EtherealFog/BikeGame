package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fog.ethereal.sprite.DragNode;

@XmlRootElement(name = "level")
public class Level {
	private ArrayList<Section> sections;
	private ArrayList<DragNode> dragpoints;
	
	public Level() {
		
	}
	
	public void addDragpoints() {
		for(Section s: sections) {
			dragpoints.addAll(s.getDragpoints());
		}
	}
	
	public void removeDragpoints() {
		dragpoints.clear();
	}
	
	@XmlElement(name = "section")
	public List<Section> getSections() {
		return sections;
	}
}
