package fog.ethereal.world;

import java.awt.Dimension;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Mode;
import fog.ethereal.util.Translation;

public abstract class World {
	private Translation pos;
	private Dimension size;
	private Scene surface;
	private Mode currentMode;
	private static Timeline gameLoop;
	private final int fps;
	private final Duration singleFrame;
	
	public World() {
		this(60);
	}
	
	public World(final int fps) {
		pos = new Translation(0, 0);
		this.fps = fps;
		singleFrame = Duration.millis(1000/fps);
		setupGameLoop();
		currentMode = null;
	}
	
	public void setupGameLoop() {
		KeyFrame frame = new KeyFrame(singleFrame, 
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					
					
				}
		});
		Timeline tempLoop = new Timeline(frame);
		tempLoop.setCycleCount(Animation.INDEFINITE);
		setGameLoop(tempLoop);
	}
	
	public abstract void initialize(final Stage primaryStage);
	
	public void addSize(int ax, int ay) {
		size.setSize(size.getWidth() + ax, size.getHeight() + ay);
	}
	
	public void setMode(Mode mode) {
		currentMode = mode;
	}
	
	public Mode getMode() {
		return currentMode;
	}
	
	public void playGameLoop() {
		getGameLoop().play();
	}
	
	public void pauseGameLoop() {
		getGameLoop().pause();
	}
	
	public void stopGameLoop() {
		getGameLoop().stop();
	}
	
	public static Timeline getGameLoop() {
		return gameLoop;
	}
	
	public static void setGameLoop(Timeline loop) {
		gameLoop = loop;
	}
	
	public void setSurface(Scene surface) {
		this.surface = surface;
	}
	
	public Scene getSurface() {
		return surface;
	}
	
	public abstract void setupKeyMappings(KeyCode accel, KeyCode brake, KeyCode right, KeyCode left);
	
	public void setupKeyMappings() {
		setupKeyMappings(KeyCode.UP, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.LEFT);
	}
}
