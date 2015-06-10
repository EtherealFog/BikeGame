package fog.ethereal.sprite;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import fog.ethereal.util.Constants;
import fog.ethereal.util.VectorCT;
import fog.ethereal.util.WorldObject;
import fog.ethereal.world.Platform;

public class Bike implements WorldObject{
	public static final Point FRONT_WHEEL_POS = new Point(183, 88);
	public static final Point BACK_WHEEL_POS = new Point(34, 88);
	private Point2D frontPos, backPos; //relative position of front and back wheels to the center of the frame.
	private Frame frame;
	private Wheel front, back;
	private int bikeType;
	private double rot;
	private Rotate rotate;
	private double rotV;
	private boolean accel, brake, right, left;
	private Rectangle bounds;
	private VectorCT vector;
	
	public Bike(int type) {
		bikeType = type;
		rot = 0;
		rotV = 0;
		try {
			frame = new Frame(1, this);
			front = new Wheel(1, this, Wheel.FRONT);
			front.setCenter(FRONT_WHEEL_POS);
			back = new Wheel(1, this, Wheel.BACK);
			back.setCenter(BACK_WHEEL_POS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bounds = new Rectangle();
		rotate = new Rotate();
		frontPos = new Point2D(FRONT_WHEEL_POS.getX(), FRONT_WHEEL_POS.getY());
		backPos = new Point2D(BACK_WHEEL_POS.getX(), BACK_WHEEL_POS.getY());
		frame.getTransforms().add(rotate);
		updateBounds();
		vector = new VectorCT(0, 0);
	}

	@Override
	public void update(List<Platform> platforms) {
		vector.add(new VectorCT(0, Constants.GRAVITY / Constants.FPS));
		frame.update(platforms);
		front.update(platforms);
		back.update(platforms);
		//translate(vector.getX(), vector.getY());
		updateRotation();
		updateBounds();
	}
	
	public double getRot() {
		return rot;
	}
	
	public VectorCT getVector() {
		return vector;
	}
	
	public void setVector(VectorCT vector) {
		this.vector = vector;
	}
	
	public Rectangle getBounds() {
		updateBounds();
		return bounds;
	}
	
	public void updateBounds() {
		bounds.setLocation((int)frame.getX() - 80, (int)frame.getY() - 80);
		bounds.setSize((int)frame.getFitWidth() + 160, (int)frame.getFitHeight() + 160);
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public void setVisible(boolean arg) {
		front.setVisible(arg);
		back.setVisible(arg);
		frame.setVisible(arg);
	}
	
	public void translate(double dx, double dy) {
		frame.setTranslateX(frame.getTranslateX() + dx);
		frame.setTranslateY(frame.getTranslateY() + dy);
		front.setCenterX(front.getCenterX() + dx);
		front.setCenterY(front.getCenterY() + dy);
		frontPos = new Point2D(front.getCenterX(), front.getCenterY());
		back.setCenterX(back.getCenterX() + dx);
		back.setCenterY(back.getCenterY() + dy);
		backPos = new Point2D(back.getCenterX(), back.getCenterY());
	}
	
	public void setLocation(double x, double y) {
		frame.setTranslateX(x);
		frame.setTranslateY(y);
		front.setTranslateX(FRONT_WHEEL_POS.getX() + x);
		front.setTranslateY(FRONT_WHEEL_POS.getY() + y);
		frontPos = new Point2D(front.getTranslateX(), front.getTranslateY());
		back.setTranslateX(BACK_WHEEL_POS.getX() + x);
		back.setTranslateY(BACK_WHEEL_POS.getY() + y);
		backPos = new Point2D(back.getTranslateX(), back.getTranslateY());
	}
	
	public void rotate(double degrees) {
		double centerx = frame.getCenterX();
		double centery = frame.getCenterY();
		rotate(degrees, centerx, centery);
	}
	
	public void rotate(double degrees, double anchorX, double anchorY) {
		rotate.setAngle(degrees);
		rotate.setPivotX(anchorX);
		rotate.setPivotY(anchorY);
		Point2D frontRelPos = rotate.deltaTransform(frontPos);
		Point2D backRelPos = rotate.deltaTransform(backPos);
		front.setTranslateX(frame.getCenterX() + frontRelPos.getX());
		front.setTranslateY(frame.getCenterY() + frontRelPos.getY());
		back.setTranslateX(frame.getCenterX() + backRelPos.getX());
		back.setTranslateY(frame.getCenterY() + backRelPos.getY());
	}
	
	public void updateRotation() {
		if(right) {
			rotV += 0.5;
			if(rotV > 4.5) {
				rotV = 5;
			}
		} else if(left) {
			rotV -= 0.5;
			if(rotV < -4.5) {
				rotV = -5;
			}
		} else {
			rotV *= 0.9;
		}
		rotate.setPivotX(frame.getCenterX());
		rotate.setPivotY(frame.getCenterY());
		rotate.setAngle(rotate.getAngle() + rotV);
		
		Rotate wheels = rotate.clone();
		wheels.setPivotX(frame.getTranslateX() + frame.getFitWidth() / 2);
		wheels.setPivotY(frame.getTranslateY() + frame.getFitHeight() / 2);
		
		Point2D frontPos = wheels.transform(this.frontPos);
		Point2D backPos = wheels.transform(this.backPos);
		
		front.setCenterX(frontPos.getX());
		front.setCenterY(frontPos.getY());
		
		back.setCenterX(backPos.getX());
		back.setCenterY(backPos.getY());
		
	}
	
	public void setAccel(boolean accel) {
		this.accel = accel;
	}
	
	public void setBrake(boolean brake) {
		this.brake = brake;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean getAccel() {
		return accel;
	}
	
	public boolean getBrake() {
		return brake;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getLeft() {
		return left;
	}
	
	public void addTo(Pane parent) {
		parent.getChildren().addAll(front, back, frame);
	}
}
