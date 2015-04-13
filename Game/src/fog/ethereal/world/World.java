package fog.ethereal.world;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import fog.ethereal.sprite.Sprite;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Translation;
import javafx.scene.canvas.GraphicsContext;

public class World {
	private Translation pos;
	private Dimension maxSize;
	private Dimension size;
	private ArrayList<Sprite> sprites;
	
	public World(Dimension maxSize) {
		pos = new Translation(0, 0);
		this.maxSize = maxSize;
	}
	
	public World() {
		pos = new Translation(0, 0);
		maxSize = Constants.DEFAULT_SIZE;
	}
	
	public void add(Sprite a) {
		Translation aPos = a.getAbsPos();
		Rectangle bounds = new Rectangle(maxSize);
		if(bounds.contains(aPos.getX(), aPos.getY())) {
			sprites.add(a);
		}
	}
}
