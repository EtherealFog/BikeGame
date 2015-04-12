package fog.ethereal.sprite;

import fog.ethereal.util.Translation;
import fog.ethereal.world.Drawable;
import javafx.scene.canvas.GraphicsContext;


public class Actor implements Drawable {
	private Translation absPos;
	
	@Override
	public void draw(Translation worldpos, GraphicsContext g) {

	}
	
	@Override
	public boolean canDraw(Translation worldpos) {
		
		
		return false;
	}
	
	public Translation getAbsPos() {
		return absPos;
	}
}
