package fog.ethereal.world;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "level")
public class SaveableLevel {
	private ArrayList<BasicSection> sections;
	private String name;
	
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
	
	public void setName(String name) {
		this.name = name;
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
