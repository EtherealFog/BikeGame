package fog.ethereal.world;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import fog.ethereal.sprite.Sprite;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Translation;

public class World {
	private Translation pos;
	private Dimension maxSize;
	private Dimension size;
	private ArrayList<Sprite> sprites;
	private static Timeline gameLoop;
	
	public World(Dimension maxSize) {
		pos = new Translation(0, 0);
		this.maxSize = maxSize;
	}
	
	public World() {
		pos = new Translation(0, 0);
		maxSize = Constants.DEFAULT_SIZE;
	}
	
	public void add(Sprite a) {
		Translation aPos = a.getAbsPos();
		Rectangle bounds = new Rectangle(maxSize);
		if(bounds.contains(aPos.getX(), aPos.getY())) {
			sprites.add(a);
		}
	}
	
	public void setupGameLoop() {
		Duration singleFrame = Constants.DEFAULT_FPS;
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
	
	public void beginGameLoop() {
		getGameLoop().play();
	}
	
	public static Timeline getGameLoop() {
		return gameLoop;
	}
	
	public static void setGameLoop(Timeline loop) {
		gameLoop = loop;
	}
}
