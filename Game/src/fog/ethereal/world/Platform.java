package fog.ethereal.world;

import java.awt.Rectangle;

import javafx.scene.shape.Line;
import fog.ethereal.util.VectorCT;

public class Platform extends Line {
	
	public Platform() {
		
	}
	
	public VectorCT getNormal() {
		//Make a vector equivalent of this Platform
		VectorCT edge = toVector();
		//edge[x, y] -> edge.normal[-y, x]
		return new VectorCT(-1 * edge.getY(), edge.getX());
	}
	
	public VectorCT toVector() {
		return (new VectorCT(getStartX(), getStartY())).subtract(new VectorCT(getEndX(), getEndY()));
	}
	
	public boolean fitsWithin(Rectangle rect) {
		return rect.contains(getStartX(), getStartY()) && rect.contains(getEndX(), getEndY());
	}
}
