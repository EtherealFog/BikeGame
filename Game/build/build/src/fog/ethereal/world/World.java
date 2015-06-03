package fog.ethereal.world;

import java.awt.Dimension;

import org.apache.commons.lang3.time.StopWatch;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Mode;
import fog.ethereal.util.Quadtree;
import fog.ethereal.util.Translation;

public abstract class World {
	private Translation pos;
	private Dimension size;
	private Scene surface;
	private Pane nodes;
	private StopWatch timer;
	private static Timeline gameLoop;
	private final int fps;
	private final Duration singleFrame;
	
	public World() {
		this((int)Constants.FPS);
	}
	
	public World(final int fps) {
		pos = new Translation(0, 0);
		this.fps = fps;
		singleFrame = Duration.millis(1000/fps);
		timer = new StopWatch();
		setupGameLoop();
	}
	
	public Duration getSingleFrame() {
		return singleFrame;
	}
	
	public abstract void setupGameLoop();
	
	public abstract void initialize(final Stage primaryStage);
	
	public void addSize(int ax, int ay) {
		size.setSize(size.getWidth() + ax, size.getHeight() + ay);
	}
	
	public void playGameLoop() {
		getGameLoop().play();
		if(timer.isSuspended())
			timer.resume();
		else
			timer.start();
	}
	
	public void pauseGameLoop() {
		getGameLoop().pause();
		timer.suspend();
	}
	
	public void stopGameLoop() {
		getGameLoop().stop();
		timer.stop();
	}
	
	public long getTime() {
		return timer.getTime();
	}
	
	public StopWatch getTimer() {
		return timer;
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
	
	public void setNodes(Pane nodes) {
		Group wrapper = new Group();
		this.nodes = nodes;
		wrapper.getChildren().add(nodes);
		setSurface(new Scene(wrapper, 840, 480));
	}
	
	public Pane getNodes() {
		return nodes;
	}
	
	public Scene getSurface() {
		return surface;
	}
	
	public abstract void setupKeyMappings();
	
}
