package fog.ethereal.sprite;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import fog.ethereal.util.Translation;

public class Frame extends Sprite {
	private Bike parent;
	
	public Frame(int type, Bike parent) throws FileNotFoundException {
		super(new FileInputStream(new File("resources/assets/BikeFrame" + type + ".png")));
		this.parent = parent;
		
	}

	public Point getCenter() {
		return new Point((int)(getX() + getFitWidth() / 2), (int)(getY() + getFitHeight() / 2));
	}
	
	@Override
	public void update(Translation t) {
		
	}
}
