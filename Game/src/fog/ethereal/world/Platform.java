package fog.ethereal.world;

import java.awt.Point;
import java.awt.Rectangle;

import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Translation;
import fog.ethereal.util.VectorCT;

public class Platform extends Line {
	
	public Platform(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		setStrokeWidth(Constants.DEFAULT_PLATFORM_WIDTH);
		setStrokeLineCap(StrokeLineCap.ROUND);
	}
	
	public Platform(Point startPoint, Point endPoint) {
		this(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}
	
	public Platform(BasicPlatform basic) {
		this(basic.getStart(), basic.getEnd());
	}
	
	public void update(Translation t) {
		
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
	
	public boolean intersects(Rectangle rect) {
		return rect.intersectsLine(getStartX(), getStartY(), getEndX(), getEndY());
	}
	
	public boolean fitsWithin(Rectangle rect) {
		return intersects(rect);
	}
	
	public BasicPlatform toBasicPlatform() {
		return new BasicPlatform(getStartX(), getStartY(), getEndX(), getEndY());
	}
	
	public String toString() {
		return "(" + getStartX() + ", " + getStartY() + ") -> (" + getEndX() + ", " + getEndY() + ")";
	}
}
