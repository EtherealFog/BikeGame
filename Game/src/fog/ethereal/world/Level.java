package fog.ethereal.world;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.Mode;
import fog.ethereal.util.Translation;

public class Level {
	private ObservableList<Section> sections;
	private ObservableList<DragNode> dragpoints;
	private ObservableList<Platform> allplatforms;
	//private String name;
	private ObjectProperty<LevelData> dataProperty;
	private double startX, startY, endX, endY;
	private World world;
	
	public Level(String name) {
		setData(new LevelData(name, 0, null));
		setStartPoint(0, 0);
		setEndPoint(0, 0);
		sections = FXCollections.observableArrayList();
		dragpoints = FXCollections.observableArrayList();
		allplatforms = FXCollections.observableArrayList();
	}
	
	public Level(SaveableLevel sl) {
		setData(new LevelData(sl.getName(), sl.getBestTime(), null));
		setStartPoint(sl.getStartX(), sl.getStartY());
		setEndPoint(sl.getEndX(), sl.getEndY());
		setImage(getImage());
		sections = FXCollections.observableArrayList();
		dragpoints = FXCollections.observableArrayList();
		allplatforms = FXCollections.observableArrayList();
		ArrayList<BasicSection> basics = (ArrayList<BasicSection>) sl.getSections();
		for(BasicSection bs: basics) {
			sections.add(new Section(bs));
			allplatforms.addAll(new Section(bs).getPlatforms());
		}
		for(Section s: sections) {
			s.setParent(this);
		}
	}
	
	public void update(Translation t) {
		
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setStartPoint(double x, double y) {
		startX = x;
		startY = y;
	}
	
	public void setEndPoint(double x, double y) {
		endX = x;
		endY = y;
	}
	
	public double getStartX() {
		return startX;
	}
	
	public double getStartY() {
		return startY;
	}
	
	public double getEndX() {
		return endX;
	}
	
	public double getEndY() {
		return endY;
	}
	
	public void addDragpoints() {
		for(Section s: sections) {
			dragpoints.addAll(s.addDragpoints());
		}
	}
	
	public void addDragpoints(Pane g) {
		if(dragpoints.size() == 0) {
			addDragpoints();
		}
		for(DragNode d: dragpoints) {
			d.addSelfTo(g);
		}
	}
	
	public void remove(Section s) {
		sections.remove(s);
		allplatforms.removeAll(s.getPlatforms());
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
	
	public ObservableList<Section> getSections() {
		return sections;
	}
	
	public ObservableList<Platform> getAllPlatforms() {
		return allplatforms;
	}
	
	public void addSections(List<Section> sections) {
		this.sections.addAll(sections);
		for(Section s: sections) {
			allplatforms.addAll(s.getPlatforms());
			s.setParent(this);
		}
	}
	
	public void addSection(Section section) {
		this.sections.add(section);
		allplatforms.addAll(section.getPlatforms());
		section.setParent(this);
	}
	
	public SaveableLevel toSaveableLevel() {
		SaveableLevel temp = new SaveableLevel(getName());
		if(sections.size() > 0) {
			ArrayList<BasicSection> bss = new ArrayList<BasicSection>();
			for(Section s: sections) {
				bss.add(s.toBasicSection());
			}
			temp.addSections(bss);
		}
		temp.setBestTime(getBestTime());
		temp.setStartX(startX);
		temp.setStartY(startY);
		temp.setEndX(endX);
		temp.setEndY(endY);
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
			File hmm = new File("resources/worlds/" + getName().replaceAll(" ", "_") + "/icon.png");
			FileInputStream stream;
			if(hmm.exists())
				stream = new FileInputStream(hmm);
			else
				stream = new FileInputStream(new File("resources/assets/notfound.png"));
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
	
	public void addTo(Group p, Mode m) {
		if(m.equals(Mode.ADD) || m.equals(Mode.MOVE)) {
			
		} else if(m.equals(Mode.PLAY)) {
			
		}
	}
}
