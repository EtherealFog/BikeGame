package fog.ethereal.sprite;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import fog.ethereal.world.Platform;

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
	public void update(List<Platform> platforms) {
		
	}
	
	@Override
	public boolean fitsWithin(Rectangle rect) {
		Rectangle test = new Rectangle((int)getX() - 15, (int)getY(), (int)getFitWidth() + 30, (int)getFitHeight() + 15);
		return rect.intersects(test);
	}
	
	public void rotate() {
		
	}
}
