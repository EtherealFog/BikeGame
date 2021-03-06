package fog.ethereal.util;

import java.awt.Dimension;

import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Constants {
	public static double GRAVITY = 9.8; //m/s/s
	public static double ACCEL = 5.36; //m/s/s
	public static double FPS = 60;
	public static double SPEED = 1.0;
	public static Duration DEFAULT_FPS = Duration.millis(1000/FPS);
	public static final Dimension DEFAULT_SIZE = new Dimension(1920 * 4, 1080 * 2);
	public static final int DEFAULT_PLATFORM_WIDTH = 10;
	public static final double DEFAULT_GRAVITY = 9.8;
	public static final double DEFAULT_ACCEL = 5.36;
	public static final double DEFAULT_SPEED = 1.0;
	public static KeyCode ACCEL_CODE = KeyCode.UP;
	public static KeyCode BRAKE_CODE = KeyCode.DOWN;
	public static KeyCode LEFT_CODE = KeyCode.LEFT;
	public static KeyCode RIGHT_CODE = KeyCode.RIGHT;
	public static KeyCode PAUSE_CODE = KeyCode.ESCAPE;
}
