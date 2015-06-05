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
	
	public Wheel(int type, Bike parent, Mode which) throws FileNotFoundException {
		super(new FileInputStream(new File("resources/assets/Wheel" + type + ".png")));
		
		this.parent = parent;
		this.which = which;
		rot = new Rotate(0);
		centerpoint = new Line(getX(), getY(), getX() + getFitWidth(), getY() + getFitHeight());
		
		//getTransforms().add(rot);
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
			}
		}
	}
	
	public void setRotate(Rotate rotate) {
		getTransforms().add(rotate);
		centerpoint.getTransforms().add(rotate);
		this.rotate = rotate;
	}
	
	public void update(List<Platform> platforms) {
		if(which.equals(BACK))
			updateRotationBack();
	}
	
	public void translate(double dx, double dy) {
		setTranslateX(getTranslateX() + dx);
		setTranslateY(getTranslateY() + dy);
		centerpoint.setTranslateX(centerpoint.getTranslateX() + dx);
		centerpoint.setTranslateY(centerpoint.getTranslateY() + dy);
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
		rot.setAngle((rot.getAngle() + (rotV / 2.16 /*<- approx circumference in meters*/ / Constants.FPS /*<- fps*/ * 360)) % 360);
		setRotate(rot.getAngle());
	}
	
	public void updateRotationFront(Platform p) {
		
	}
	
	public Point getCenter() {
		return new Point((int)(getCenterX()), (int)(getCenterY()));
	}
	
	public void setCenter(Point center) {
		setX(center.getX() - getImage().getWidth() / 2);
		setY(center.getY() - getImage().getHeight() / 2);
	}
	
	public void setCenter(Translation t) {
		setCenter(new Point(t.getX(), t.getY()));
	}
	
	public double getCenterX() {
		return rotate.deltaTransform(getX() + getFitWidth() / 2, getY() + getFitHeight() / 2).getX();
	}
	
	public double getCenterY() {
		return rotate.deltaTransform(getX() + getFitWidth() / 2, getY() + getFitHeight() / 2).getY();
	}
}
