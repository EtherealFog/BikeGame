package fog.ethereal.client;

import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.SaveableLevel;

public class TestClient {
	public static void main(String[] args) {
		SaveableLevel temp = LevelSaver.load("newOldAgain");
		temp.setName("newOldAgain");
		LevelSaver.save(temp);
	}
}
