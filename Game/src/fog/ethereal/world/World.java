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
	private Dimension maxSize;
	private Dimension size;
	private Scene surface;
	private Mode currentMode;
	private static Timeline gameLoop;
	private static Timeline editLoop;
	private final int fps;
	private final Duration singleFrame;
	
	public World(Dimension maxSize) {
		this(60, maxSize);
	}
	
	public World(final int fps, Dimension maxSize) {
		pos = new Translation(0, 0);
		this.maxSize = maxSize;
		this.fps = fps;
		singleFrame = Duration.millis(1000/fps);
		setupGameLoop();
		currentMode = Mode.MENU;
	}
	
	public World() {
		this(60, Constants.DEFAULT_SIZE);
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
	
	public void setupEditLoop() {
		KeyFrame frame = new KeyFrame(singleFrame, 
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					
					
				}
		});
		Timeline tempLoop = new Timeline(frame);
		tempLoop.setCycleCount(Animation.INDEFINITE);
		setEditLoop(tempLoop);
	}
	
	public abstract void initialize(final Stage primaryStage);
	
	public void playGameLoop() {
		getGameLoop().play();
	}
	
	public void pauseGameLoop() {
		getGameLoop().pause();
	}
	
	public void stopGameLoop() {
		getGameLoop().stop();
	}
	
	public void playEditLoop() {
		getEditLoop().play();
	}
	
	public void pauseEditLoop() {
		getEditLoop().pause();
	}
	
	public void stopEditLoop() {
		getEditLoop().stop();
	}
	
	public static Timeline getGameLoop() {
		return gameLoop;
	}
	
	public static Timeline getEditLoop() {
		return editLoop;
	}
	
	public static void setGameLoop(Timeline loop) {
		gameLoop = loop;
	}
	
	public static void setEditLoop(Timeline loop) {
		editLoop = loop;
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
