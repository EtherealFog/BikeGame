package fog.ethereal.world;

import java.awt.Dimension;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import fog.ethereal.sprite.Sprite;
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
	
	public abstract void initialize(final Stage primaryStage);
	
	public void beginGameLoop() {
		getGameLoop().play();
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
}
