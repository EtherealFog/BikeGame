package fog.ethereal.world;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

public class SavePoint {
	private double x, y;
	
	public SavePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public SavePoint() {
		x = 0;
		y = 0;
	}
	
	@XmlElement(name = "x")
	public double getX() {
		return x;
	}
	
	@XmlElement(name = "y")
	public double getY() {
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Point toPoint() {
		return new Point((int) x, (int) y);
	}
}
