package fog.ethereal.sprite;

import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;

public class Bike implements WorldObject{
	private Frame frame;
	private Wheel front, back;
	private int bikeType;
	
	public Bike(int type) {
		bikeType = type;
		
	}

	@Override
	public void update(Translation t) {
		
		
	}
}
