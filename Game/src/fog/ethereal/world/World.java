package fog.ethereal.world;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class World {
	private Translation pos;
	private Dimension maxSize;
	private Dimension size;
	private ArrayList<Actor> actors;
	
	public World(Dimension maxSize) {
		pos = new Translation(0, 0);
		this.maxSize = maxSize;
	}
	
	public World() {
		pos = new Translation(0, 0);
		maxSize = Constants.DEFAULT_SIZE;
	}
	
	public void draw(GraphicsContext g) {
		actors.parallelStream()
			.filter((Actor a) -> a.canDraw(pos))
			.forEach((Actor a) -> a.draw(pos, g));
	}
	
	public void add(Actor a) {
		Translation aPos = a.getAbsPos();
		Rectangle bounds = new Rectangle(maxSize);
		if(bounds.contains(aPos.getX(), aPos.getY())) {
			actors.add(a);
		}
	}
}
