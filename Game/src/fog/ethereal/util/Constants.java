package fog.ethereal.util;

import java.awt.Dimension;

import javafx.util.Duration;

public class Constants {
	public static final Dimension DEFAULT_SIZE = new Dimension(1920 * 4, 1080 * 2);
	public static final Duration DEFAULT_FPS = Duration.millis(1000/60);
	public static final int DEFAULT_PLATFORM_WIDTH = 10;
	public static double GRAVITY = 9.8; //m/s/s
	public static double ACCEL = 5.36; //m/s/s
}
