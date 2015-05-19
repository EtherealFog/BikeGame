package fog.ethereal.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.Translation;

public class Level {
	private ArrayList<Section> sections;
	private ArrayList<DragNode> dragpoints;
	//private String name;
	private ObjectProperty<LevelData> dataProperty;
	
	public Level(String name) {
		setName(name);
		setBestTime(0);
		sections = new ArrayList<Section>();
		dragpoints = new ArrayList<DragNode>();
	}
	
	public Level(SaveableLevel sl) {
		setData(new LevelData(sl.getName(), sl.getBestTime(), null));
		setImage(getImage());
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
		return dataProperty().get().getName();
	}
	
	public long getBestTime() {
		return dataProperty().get().getBestTime();
	}
	
	public LevelData getData() {
		return dataProperty().get();
	}
	
	public ObjectProperty<LevelData> dataProperty() {
		if(dataProperty == null)
			dataProperty = new SimpleObjectProperty<LevelData>(this, "data");
		return dataProperty;
	}
	
	public void setName(String name) {
		dataProperty().get().setName(name);
	}
	
	public void setBestTime(long bestTime) {
		dataProperty().get().setBestTime(bestTime);
	}
	
	public void setImage(Image image) {
		dataProperty().get().setImage(image);
	}
	
	public void setData(LevelData data) {
		dataProperty().set(data);
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
		temp.setBestTime(getBestTime());
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
			FileInputStream stream = new FileInputStream(new File("resources/worlds/" + getName() + "/icon.png"));
			temp = new Image(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FileInputStream stream = new FileInputStream(new File("resources/assets/notfound.png"));
				temp = new Image(stream);
				stream.close();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return temp;
	}
}
