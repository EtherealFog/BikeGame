package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.WorldObject;

public class Level {
	private ArrayList<Section> sections;
	private ArrayList<DragNode> dragpoints;
	private String name;
	
	public Level(String name) {
		this.name = name;
		sections = new ArrayList<Section>();
		dragpoints = new ArrayList<DragNode>();
	}
	
	public Level(SaveableLevel sl) {
		name = sl.getName();
		sections = new ArrayList<Section>();
		ArrayList<BasicSection> basics = new ArrayList<BasicSection>();
		for(BasicSection bs: basics) {
			sections.add(new Section(bs));
		}
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public void addSections(List<Section> sections) {
		this.sections.addAll(sections);
	}
	
	public SaveableLevel toSaveableLevel() {
		SaveableLevel temp = new SaveableLevel(name);
		ArrayList<BasicSection> bss = new ArrayList<BasicSection>();
		for(Section s: sections) {
			bss.add(s.toBasicSection());
		}
		temp.addSections(bss);
		return temp;
	}
}
