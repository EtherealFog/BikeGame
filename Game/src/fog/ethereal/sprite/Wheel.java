package fog.ethereal.sprite;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Mode;
import fog.ethereal.util.Projection;
import fog.ethereal.util.Translation;
import fog.ethereal.util.VectorCT;
import fog.ethereal.world.Platform;



public class Wheel extends Sprite{
	public static final Mode FRONT = new Mode("front");
	public static final Mode BACK = new Mode("back");
	private Mode which;
	private double radius;
	private VectorCT center;
	private Bike parent;
	private double rot;//current wheel angle (degrees)
	private double rotV;//current speed (m/s)
	
	public Wheel(int type, Bike parent, Mode which) throws FileNotFoundException {
		super(new FileInputStream(new File("resources/assets/Wheel" + type + ".png")));
		this.parent = parent;
		this.which = which;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void handleCollision(Platform p) {
		VectorCT norm = p.getNormal();
		double pScal = (new VectorCT(p.getStartX(), p.getStartY())).dot(norm);
		Projection p1 = new Projection(pScal - p.getStrokeWidth() / 2, pScal + p.getStrokeWidth() / 2);
		double wScal = center.dot(norm);
		Projection p2 = new Projection(wScal - radius, wScal + radius);
		if(p1.overlaps(p2)) {
			if(which.equals(FRONT)) {
				updateRotationFront(p);
			} else if(which.equals(BACK)) {
				updateRotationBack();
			}
		}
	}
	
	public void update(Translation t) {
		center.set(getCenter().getX(), getCenter().getY());
	}
	
	public void updateRotationBack() {
		rot = (rot + (rotV / 2.16 /*<- approx circumference in meters*/ / Constants.FPS /*<- fps*/ * 360)) % 360;
		getTransforms().clear();
		Rotate current = new Rotate(rot);
		getTransforms().add(current);
	}
	
	public void updateRotationFront(Platform p) {
		
	}
	
	public Point getCenter() {
		return new Point((int)(getX() + getFitWidth() / 2), (int)(getY() + getFitHeight() / 2));
	}
	
	public void setCenter(Point center) {
		setX(center.getX() - getFitWidth() / 2);
		setY(center.getY() - getFitHeight() / 2);
	}
	
	public void setCenter(Translation t) {
		setCenter(new Point(t.getX(), t.getY()));
	}
	
	public void rotate(double angle, double centerRotX, double centerRotY) {
		getTransforms().add(new Rotate(angle, centerRotX, centerRotY));
	}
}
