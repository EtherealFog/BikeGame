package fog.ethereal.world;

import javafx.scene.canvas.GraphicsContext;


public interface Drawable {
	public void draw(Translation worldpos, GraphicsContext g);
	public boolean canDraw(Translation worldpos);
}
