package fog.ethereal.world;

import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Binding;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import fog.ethereal.sprite.Bike;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Quadtree;
import fog.ethereal.view.MenuController;
import fog.ethereal.view.PauseMenuController;

public class BikeWorld extends World {
	private Bike bike;
	private PauseMenuController pmc;
	private VBox pauseMenu;
	private Level level;
	private Quadtree tree;
	private Label timerLabel;
	private Stage stage;
	private Rectangle glass;
	private Pane nodes;
	
	@Override
	public void initialize(Stage primaryStage) {
		stage = primaryStage;
		nodes = new Pane();
		setNodes(nodes);
		primaryStage.setScene(getSurface());
		primaryStage.setTitle("Motor Meander: " + level.getName());
		primaryStage.getIcons().add(new Image("file:resources/assets/bikeicon3.png"));
		getNodes().getChildren().addAll(level.getAllPlatforms());
		bike = new Bike(1);
		bike.addTo(nodes);
		nodes.translateXProperty().bind(bike.getFrame().layoutXProperty().multiply(-1)
				.add(getSurface().widthProperty().divide(2).subtract(bike.getFrame().getImage().widthProperty().divide(2))));
		nodes.translateYProperty().bind(bike.getFrame().layoutYProperty().multiply(-1)
				.add(getSurface().heightProperty().divide(2).subtract(bike.getFrame().getImage().heightProperty().divide(2))));
		makeTimer();
		setupKeyMappings();
		setupGameLoop();
		playGameLoop();
	}
	
	public BikeWorld() {
		super();
	}
	
	public void update() {
		timerLabel.setText(MenuController.millisToString(getTime()));
		bike.translate(0.5, 0);
		
	}
	
	public void makeTimer() {
		Label timeLabel = new Label("TIME:");
		timeLabel.setStyle("-fx-font-weight: bold;");
		timerLabel = new Label("00:00.00");
		timerLabel.getStylesheets().add("file:src/fog/ethereal/view/Theme1.css");
		timerLabel.getStyleClass().add("timer-label");
		timerLabel.layoutXProperty().bind(getSurface().widthProperty().divide(2).subtract(timerLabel.widthProperty().divide(2)));
		timerLabel.setLayoutY(15 - timerLabel.getLayoutBounds().getMinY());
		timeLabel.layoutXProperty().bind(getSurface().widthProperty().divide(2).subtract(timeLabel.widthProperty().divide(2)));
		((Group)getNodes().getParent()).getChildren().addAll(timeLabel, timerLabel);
	}
	
	@Override
	public void setupGameLoop() {
		KeyFrame frame = new KeyFrame(getSingleFrame(), 
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					update();
				}
		});
		Timeline tempLoop = new Timeline(frame);
		tempLoop.setCycleCount(Animation.INDEFINITE);
		setGameLoop(tempLoop);
	}
	
	public void setLevel(Level l) {
		level = l;
	}
	
	@Override
	public void setupKeyMappings() {
		getSurface().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(Constants.ACCEL_CODE)) {
					bike.setAccel(true);
					bike.setBrake(false);
				} else if(e.getCode().equals(Constants.BRAKE_CODE)) {
					bike.setBrake(true);
					bike.setAccel(false);
				} else if(e.getCode().equals(Constants.RIGHT_CODE)) {
					bike.setRight(true);
					bike.setLeft(false);
				} else if(e.getCode().equals(Constants.LEFT_CODE)) {
					bike.setLeft(true);
					bike.setRight(false);
				}
			}
		});
		
		getSurface().setOnKeyPressed(new EventHandler<KeyEvent> () {
			public void handle(KeyEvent e) {
				if(e.getCode().equals(Constants.PAUSE_CODE)) {
					if(getTimer().isSuspended()) {
						resume();
					} else {
						pause();
					}
				}
			}
		});
		
		getSurface().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(Constants.ACCEL_CODE)) {
					bike.setAccel(false);
				} else if(e.getCode().equals(Constants.BRAKE_CODE)) {
					bike.setBrake(false);
				} else if(e.getCode().equals(Constants.RIGHT_CODE)) {
					bike.setRight(false);
				} else if(e.getCode().equals(Constants.LEFT_CODE)) {
					bike.setLeft(false);
				}
			}
		});
	}
	
	public void showPauseMenu() {
		if(glass == null) {
			glass = new Rectangle(getSurface().getWidth(), getSurface().getHeight());
			glass.setFill(Color.rgb(0, 0, 0, 0.8));
			glass.widthProperty().bind(getSurface().widthProperty());
			glass.heightProperty().bind(getSurface().heightProperty());
		}
		((Group)getSurface().getRoot()).getChildren().add(glass);
		glass.toFront();
		if(pauseMenu == null) {
			pauseMenu = new VBox();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(new URL("file:src/fog/ethereal/view/PauseMenu.fxml"));
				pauseMenu = (VBox)loader.load();
				
				pmc = loader.getController();
				pmc.setWorld(this);
			} catch (Exception e) {e.printStackTrace();}
			pauseMenu.layoutXProperty().bind(getSurface().widthProperty().divide(2).subtract(pauseMenu.widthProperty().divide(2)));
			pauseMenu.layoutYProperty().bind(getSurface().heightProperty().divide(2).subtract(pauseMenu.heightProperty().divide(2)));
		}
		((Group)getSurface().getRoot()).getChildren().add(pauseMenu);
		pauseMenu.toFront();
		
	}
	
	public void hidePauseMenu() {
		((Group)getSurface().getRoot()).getChildren().remove(glass);
		((Group)getSurface().getRoot()).getChildren().remove(pauseMenu);
	}
	
	public void resume() {
		playGameLoop();
		hidePauseMenu();
	}
	
	public void pause() {
		pauseGameLoop();
		showPauseMenu();
	}
	
	public void quit() {
		stage.close();
		stopGameLoop();
	}
	
	public void showSettings() {
		
	}
}
