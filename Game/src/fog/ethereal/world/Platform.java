package fog.ethereal.world;

import javafx.scene.shape.Line;
import fog.ethereal.util.VectorCT;

public class Platform extends Line {
	
	public Platform() {
		
	}
	
	public VectorCT getNormal() {
		//Make a vector equivalent of this Platform
		VectorCT edge = (new VectorCT(getStartX(), getStartY())).subtract(new VectorCT(getEndX(), getEndY()));
		//edge[x, y] -> edge.normal[-y, x]
		return new VectorCT(-1 * edge.getY(), edge.getX());
	}
}
