package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.Translation;

public class Level {
	private ArrayList<Section> sections;
	private ArrayList<DragNode> dragpoints;
	//private String name;
	private StringProperty nameProperty;
	
	public Level(String name) {
		setName(name);
		sections = new ArrayList<Section>();
		dragpoints = new ArrayList<DragNode>();
	}
	
	public Level(SaveableLevel sl) {
		setName(sl.getName());
		sections = new ArrayList<Section>();
		dragpoints = new ArrayList<DragNode>();
		ArrayList<BasicSection> basics = (ArrayList<BasicSection>) sl.getSections();
		for(BasicSection bs: basics) {
			sections.add(new Section(bs));
		}
	}
	
	public void update(Translation t) {
		
	}
	
	public void addDragpoints() {
		for(Section s: sections) {
			dragpoints.addAll(s.addDragpoints());
		}
	}
	
	public void removeDragpoints() {
		dragpoints.clear();
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public StringProperty nameProperty() {
		if(nameProperty == null)
			nameProperty = new SimpleStringProperty(this, "name");
		return nameProperty;
	}
	
	public void setName(String name) {
		nameProperty().set(name);
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public void addSections(List<Section> sections) {
		this.sections.addAll(sections);
	}
	
	public SaveableLevel toSaveableLevel() {
		SaveableLevel temp = new SaveableLevel(getName());
		ArrayList<BasicSection> bss = new ArrayList<BasicSection>();
		for(Section s: sections) {
			bss.add(s.toBasicSection());
		}
		temp.addSections(bss);
		return temp;
	}
	
	public String toString() {
		String temp = "Level: name = " + getName();
		for(int i = 0; i < sections.size(); i++) {
			temp += "\n" + sections.get(i);
		}
		return temp;
	}
	
	public Image getImage() {
		Image temp;
		try {
			temp = new Image("resources/worlds/" + getName() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
			temp = new Image("resources/assets/nofound.png");
		}
		return temp;
	}
}
