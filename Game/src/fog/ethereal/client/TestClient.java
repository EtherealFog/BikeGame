package fog.ethereal.client;

import java.awt.Point;

import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.Level;
import fog.ethereal.world.Section;

public class TestClient {
	public static void main(String[] args) {
		Section s = new Section(new Point(100, 100), new Point(100, 200), new Point(100, 300), new Point(100, 400), new Point(100, 500), new Point(100, 600));
		Level l = new Level("Long Section");
		l.addSection(s);
		LevelSaver.save(l.toSaveableLevel());
	}
}
