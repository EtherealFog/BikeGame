package fog.ethereal.client;

import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.Level;

public class TestClient {
	public static void main(String[] args) {
		Level temp = new Level(LevelSaver.load("TestLevel"));
		temp.setName("TestLevelDupe");
		temp.setBestTime(123592);
		LevelSaver.save(temp.toSaveableLevel());
		temp = new Level(LevelSaver.load("TestLevelDupe"));
		temp.setName("Again");
		temp.setBestTime(83729144);
		LevelSaver.save(temp.toSaveableLevel());
	}
}
