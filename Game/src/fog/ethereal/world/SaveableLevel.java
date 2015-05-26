package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "level")
public class SaveableLevel {
	private ArrayList<BasicSection> sections;
	private String name;
	private long bestTime;
	private double startX, startY, endX, endY;
	
	public SaveableLevel(String name) {
		this.name = name;
		sections = new ArrayList<BasicSection>();
	}
	
	public SaveableLevel() {
		name = "Default";
		sections = new ArrayList<BasicSection>();
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	
	@XmlElement(name = "bestTime")
	public long getBestTime() {
		return bestTime;
	}
	
	@XmlElement(name = "startX")
	public double getStartX() {
		return startX;
	}
	
	@XmlElement(name = "startY")
	public double getStartY() {
		return startY;
	}
	
	@XmlElement(name = "endX")
	public double getEndX() {
		return endX;
	}
	
	@XmlElement(name = "endY")
	public double getEndY() {
		return endY;
	}
	
	public void setBestTime(long bestTime) {
		this.bestTime = bestTime;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStartX(double x) {
		startX = x;
	}
	
	public void setStartY(double y) {
		startY = y;
	}
	
	public void setEndX(double x) {
		endX = x;
	}
	
	public void setEndY(double y) {
		endY = y;
	}
	
	@XmlElement(name = "section")
	public List<BasicSection> getSections() {
		return sections;
	}
	
	public void setSections(ArrayList<BasicSection> sections) {
		this.sections.clear();
		this.sections.addAll(sections);
	}
	
	public void addSections(List<BasicSection> sections) {
		this.sections.addAll(sections);
	}
	
	public String toString() {
		String temp = "SaveableLevel: name = " + name;
		for(int i = 0; i < sections.size(); i++) {
			temp += "\n" + sections.get(i);
		}
		return temp;
	}
}
