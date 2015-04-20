package fog.ethereal.sprite;

import java.awt.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;


public class Sprite extends ImageView implements WorldObject{
	private Translation absPos;
	
	public Sprite(Image i) {
		super(i);
	}
	
	public Translation getAbsPos() {
		return absPos;
	}
	
	@Override
	public void update(Translation t) {
		
	}
	
	public boolean fitsWithin(Rectangle rect) {
		Rectangle bounds = new Rectangle((int)getX(), (int)getY(), (int)getFitWidth(), (int)getFitHeight());
		return rect.intersects(bounds);
	}
}
