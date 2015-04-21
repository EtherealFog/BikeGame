package fog.ethereal.world;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

public class BasicPlatform {
	private double startX, startY, endX, endY;
	
	public BasicPlatform(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public BasicPlatform(Point start, Point end) {
		startX = start.getX();
		startY = start.getY();
		endX = end.getX();
		endY = end.getY();
	}
	
	public Point getStart() {
		return new Point((int)startX, (int)startY);
	}
	
	public Point getEnd() {
		return new Point((int)endX, (int)endY);
	}
	
	@XmlElement(name = "startX")
	public double getStartX() {
		return startX;
	}
	
	@XmlElement(name = "startY")
	public double getStartY() {
		return startY;
	}
	
	@XmlElement(name = "endX")
	public double getEndX() {
		return endX;
	}
	
	@XmlElement(name = "endY")
	public double getEndY() {
		return endY;
	}
}
