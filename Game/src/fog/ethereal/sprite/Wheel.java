package fog.ethereal.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import fog.ethereal.util.Projection;
import fog.ethereal.util.VectorCT;
import fog.ethereal.world.Platform;



public class Wheel extends Sprite{
	private double radius;
	private VectorCT center;
	
	public Wheel(Image i) {
		super(i);
		
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void handleCollision(Platform p) {
		VectorCT norm = p.getNormal();
		double pScal = (new VectorCT(p.getStartX(), p.getStartY())).dot(norm);
		Projection p1 = new Projection(pScal - p.getStrokeWidth(), pScal + p.getStrokeWidth());
		double wScal = center.dot(norm);
		Projection p2 = new Projection(wScal - radius, wScal + radius);
		if(p1.overlaps(p2)) {
			
		}
	}
}
