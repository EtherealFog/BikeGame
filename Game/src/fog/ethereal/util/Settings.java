package fog.ethereal.util;

import java.io.File;

import javafx.scene.input.KeyCode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "settings")
public class Settings {
	private String accel;
	private String brake;
	private String left;
	private String right;
	private String pause;
	private double gravity;
	private double acceleration;
	private double speed;
	
	public Settings() {
	}
	
	@XmlElement(name = "accel")
	public String getAccel() {
		return accel;
	}
	public void setAccel(String accel) {
		this.accel = accel;
	}
	@XmlElement(name = "brake")
	public String getBrake() {
		return brake;
	}
	public void setBrake(String brake) {
		this.brake = brake;
	}
	@XmlElement(name = "left")
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	@XmlElement(name = "right")
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	@XmlElement(name = "pause")
	public String getPause() {
		return pause;
	}
	public void setPause(String pause) {
		this.pause = pause;
	}
	@XmlElement(name = "gravity")
	public double getGravity() {
		return gravity;
	}
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	@XmlElement(name = "acceleration")
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	@XmlElement(name = "speed")
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public static void saveSettings() {
		Settings settings = new Settings();
		settings.setAccel(Constants.ACCEL_CODE.getName());
		settings.setBrake(Constants.BRAKE_CODE.getName());
		settings.setLeft(Constants.LEFT_CODE.getName());
		settings.setRight(Constants.RIGHT_CODE.getName());
		settings.setPause(Constants.PAUSE_CODE.getName());
		settings.setGravity(Constants.GRAVITY);
		settings.setAcceleration(Constants.ACCEL);
		settings.setSpeed(Constants.SPEED);
		try {
			JAXBContext context = JAXBContext.newInstance(Settings.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File file = new File("resources/settings.xml");
			if(!file.exists()) {
				file.createNewFile();
			}
			m.marshal(settings, file);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void loadSettings() {
		File file = new File("resources/settings.xml");
		Settings settings = null;
		if(file.exists()) {
			try {
				JAXBContext context = JAXBContext.newInstance(Settings.class);
				Unmarshaller um = context.createUnmarshaller();
				settings = (Settings)um.unmarshal(file);
			} catch (Exception e) {e.printStackTrace();}
		} else {
			settings.setAccel(Constants.ACCEL_CODE.getName());
			settings.setBrake(Constants.BRAKE_CODE.getName());
			settings.setLeft(Constants.LEFT_CODE.getName());
			settings.setRight(Constants.RIGHT_CODE.getName());
			settings.setPause(Constants.PAUSE_CODE.getName());
			settings.setGravity(Constants.GRAVITY);
			settings.setAcceleration(Constants.ACCEL);
			settings.setSpeed(Constants.SPEED);
		}
		Constants.ACCEL_CODE = KeyCode.getKeyCode(settings.getAccel());
		Constants.BRAKE_CODE = KeyCode.getKeyCode(settings.getBrake());
		Constants.LEFT_CODE = KeyCode.getKeyCode(settings.getLeft());
		Constants.RIGHT_CODE = KeyCode.getKeyCode(settings.getRight());
		Constants.PAUSE_CODE = KeyCode.getKeyCode(settings.getPause());
		Constants.GRAVITY = settings.getGravity();
		Constants.ACCEL = settings.getAcceleration();
		Constants.SPEED = settings.getSpeed();
	}
}
