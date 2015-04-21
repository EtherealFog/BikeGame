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
	
	public String getName() {
		return name;
	}
	
	@XmlElement(name = "section")
	public List<BasicSection> getSections() {
		return sections;
	}
	
	public void addSections(List<BasicSection> sections) {
		this.sections.addAll(sections);
	}
}
