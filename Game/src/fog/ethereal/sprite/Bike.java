package fog.ethereal.sprite;

import java.awt.Point;

import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;

public class Bike implements WorldObject{
	public static final Point FRONT_WHEEL_POS = new Point(184, 87);
	public static final Point BACK_WHEEL_POS = new Point(34, 87);
	private Frame frame;
	private Wheel front, back;
	private int bikeType;
	private double rotation;
	
	public Bike(int type) {
		bikeType = type;
		rotation = 0;
		frame = new Frame(1);
	}

	@Override
	public void update(Translation t) {
		
		
	}
}
