package fog.ethereal.sprite;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;
import fog.ethereal.world.Platform;


public class Sprite extends ImageView implements WorldObject{
	private Translation absPos;
	
	public Sprite(FileInputStream stream) {
		super(new Image(stream));
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setFitWidth(getImage().getWidth());
		setFitHeight(getImage().getHeight());
	}
	
	public Translation getAbsPos() {
		return absPos;
	}
	
	@Override
	public void update(List<Platform> platforms) {
		
	}
	
	public boolean fitsWithin(Rectangle rect) {
		Rectangle bounds = new Rectangle((int)getX(), (int)getY(), (int)getFitWidth(), (int)getFitHeight());
		return rect.intersects(bounds);
	}
	
	public void rotate(double angle, double centerRotX, double centerRotY) {
		getTransforms().add(new Rotate(angle, centerRotX, centerRotY));
	}
	
	public double getCenterX() {
		return getLayoutX() + getFitWidth() / 2;
	}
	
	public double getCenterY() {
		return getLayoutY() + getFitHeight() / 2;
	}
}
