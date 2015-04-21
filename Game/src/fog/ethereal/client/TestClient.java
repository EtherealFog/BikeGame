package fog.ethereal.client;

import java.awt.Point;
import java.util.ArrayList;

import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.BasicSection;
import fog.ethereal.world.Level;
import fog.ethereal.world.SaveableLevel;

public class TestClient {

	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(10, 2));
		points.add(new Point(3, 1));
		points.add(new Point(6, 2));
		SaveableLevel l = new SaveableLevel("TestLevel");
		ArrayList<BasicSection> sections = new ArrayList<BasicSection>();
		sections.add(new BasicSection(points));
		l.addSections(sections);
		System.out.println("Initial Level: " + l);
		LevelSaver.save(l);
		SaveableLevel temp = LevelSaver.load("TestLevel");
		temp.setName("TestLevelDupe");
		System.out.println("Loaded Level:" + new Level(temp));
		LevelSaver.save(temp);
	}

}
