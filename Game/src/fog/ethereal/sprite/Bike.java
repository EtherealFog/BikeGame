package fog.ethereal.sprite;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import fog.ethereal.util.WorldObject;
import fog.ethereal.world.Platform;

public class Bike implements WorldObject{
	public static final Point FRONT_WHEEL_POS = new Point(183, 88);
	public static final Point BACK_WHEEL_POS = new Point(34, 88);
	private Frame frame;
	private Wheel front, back;
	private int bikeType;
	private double rot;
	private Rotate rotate;
	private double rotV;
	private boolean accel, brake, right, left;
	private Rectangle bounds;
	
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
		frame.getTransforms().add(rotate);
		front.setRotate(rotate);
		back.setRotate(rotate);
		updateBounds();
	}

	@Override
	public void update(List<Platform> platforms) {
		updateRotation();
		frame.update(platforms);
		front.update(platforms);
		back.update(platforms);
	}
	
	public double getRot() {
		return rot;
	}
	
	public Rectangle getBounds() {
		updateBounds();
		return bounds;
	}
	
	public void updateBounds() {
		bounds.setLocation((int)frame.getX() - 30, (int)frame.getY() - 30);
		bounds.setSize((int)frame.getFitWidth() + 60, (int)frame.getFitHeight() + 60);
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
		front.setTranslateX(front.getTranslateX() + dx);
		front.setTranslateY(front.getTranslateY() + dy);
		back.setTranslateX(back.getTranslateX() + dx);
		back.setTranslateY(back.getTranslateY() + dy);
	}
	
	public void setLocation(double x, double y) {
		frame.setTranslateX(x);
		frame.setTranslateY(y);
		front.setTranslateX(FRONT_WHEEL_POS.getX() + x);
		front.setTranslateY(FRONT_WHEEL_POS.getY() + y);
		back.setTranslateX(BACK_WHEEL_POS.getX() + x);
		back.setTranslateY(BACK_WHEEL_POS.getY() + y);
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
		rotate.setAngle(rotate.getAngle() + rotV);
		rotate.setPivotX(frame.getCenterX());
		rotate.setPivotY(frame.getCenterY());
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
