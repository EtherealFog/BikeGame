package fog.ethereal.sprite;

import java.awt.Point;
import java.io.FileNotFoundException;

import javafx.scene.layout.Pane;
import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;

public class Bike implements WorldObject{
	public static final Point FRONT_WHEEL_POS = new Point(183, 88);
	public static final Point BACK_WHEEL_POS = new Point(34, 88);
	private Frame frame;
	private Wheel front, back;
	private int bikeType;
	private double rot;
	private double rotV;
	private boolean accel, brake, right, left;
	
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
	}

	@Override
	public void update() {
		updateRotation();
		frame.update();
		front.update();
		back.update();
	}
	
	public double getRot() {
		return rot;
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
		frame.rotate(degrees, anchorX, anchorY);
		front.rotate(degrees, anchorX, anchorY);
		back.rotate(degrees, anchorX, anchorY);
	}
	
	public void updateRotation() {
		
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
