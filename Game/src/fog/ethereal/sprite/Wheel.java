package fog.ethereal.sprite;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.scene.shape.Line;
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
	private Line centerpoint;
	private Bike parent;
	private Rotate rot;
	private Rotate rotate;
	private double rotV;//current speed (m/s)
	private boolean collFront, collBack;
	
	public Wheel(int type, Bike parent, Mode which) throws FileNotFoundException {
		super(new FileInputStream(new File("resources/assets/Wheel" + type + ".png")));
		if(!which.equals(BACK) && !which.equals(FRONT)) {
			throw new IllegalArgumentException("Mode has to be one of the two constants.");
		}
		this.parent = parent;
		this.which = which;
		rot = new Rotate(0);
		centerpoint = new Line(getX(), getY(), getX() + getFitWidth(), getY() + getFitHeight());
		radius = getImage().getWidth() / 2;
		
		//getTransforms().add(rot);
	}
	
	public double getRadius() {
		return radius;
	}
	
	public boolean handleCollision(Platform p) {
		VectorCT norm = p.getNormal();
		double pScal = (new VectorCT(p.getStartX(), p.getStartY())).dot(norm);
		Projection p1 = new Projection(pScal - p.getStrokeWidth() / 2, pScal + p.getStrokeWidth() / 2);
		double wScal = center.dot(norm);
		Projection p2 = new Projection(wScal - radius, wScal + radius);
		if(p1.overlaps(p2)) {
			norm.mult(p1.getOverlap(p2));
			parent.getVector().add(norm);
			parent.setVector(parent.getVector().dotVector(p.toVector()));
			if(which.equals(FRONT)) {
				updateRotationFront(p, true);
			} else {
	
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void setCollideBack(boolean cb) {
		collBack = cb;
	}
	
	public void setCollideFront(boolean cf) {
		collFront = cf;
	}
	
	public void setRotate(Rotate rotate) {
		getTransforms().add(rotate);
		centerpoint.getTransforms().add(rotate);
		this.rotate = rotate;
	}
	
	public void update(List<Platform> platforms) {
		for(Platform p: platforms) {
			handleCollision(p);
		}
	}
	
	public void translate(double dx, double dy) {
		setTranslateX(getTranslateX() + dx);
		setTranslateY(getTranslateY() + dy);
		centerpoint.setTranslateX(centerpoint.getTranslateX() + dx);
		centerpoint.setTranslateY(centerpoint.getTranslateY() + dy);
		center.add(new VectorCT(dx, dy));
	}
	
	public void updateRotationBack() {
		if(parent.getAccel()) {
			rotV += Constants.ACCEL / Constants.FPS;
		} else if(parent.getBrake()) {
			rotV -= 15 / Constants.FPS;
			if(rotV < 0) {
				rotV = 0;
			}
		} else {
			rotV *= 0.995;
			if(rotV < 0.1)
				rotV = 0;
		}
		rotate();
	}
	
	public void updateRotationFront(Platform p, boolean collide) {
		if(collide) {
			VectorCT platform = new VectorCT(p.getEndX() - p.getStartX(), p.getEndY() - p.getStartY());
			rotV = platform.dot(parent.getVector());
		} else {
			rotV *= 0.995;
			if(rotV < 0.1)
				rotV = 0;
		}
	}
	
	public void rotate() {
		rot.setPivotX(getCenterX());
		rot.setPivotY(getCenterY());
		rot.setAngle((rot.getAngle() + (rotV / 2.16 /*<- approx circumference in meters*/ / Constants.FPS /*<- fps*/ * 360)) % 360);
		setRotate(rot.getAngle());
	}
	
	public Point getCenter() {
		return new Point((int)(getCenterX()), (int)(getCenterY()));
	}
	
	public void setCenter(Point center) {
		setCenterX(center.getX());
		setCenterY(center.getY());
		center.setLocation(center.getX(), center.getY());
	}
	
	public void setCenterX(double x) {
		setTranslateX(x - getRadius());
	}
	
	public void setCenterY(double y) {
		setTranslateY(y - getRadius());
	}
	
	public void setCenter(Translation t) {
		setCenter(new Point(t.getX(), t.getY()));
	}
	
	public double getCenterX() {
		return this.getTranslateX() + getRadius();
	}
	
	public double getCenterY() {
		return this.getTranslateY() + getRadius();
	}
}
