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
	
	@Override
	public void initialize(Stage primaryStage) {
		stage = primaryStage;
		setNodes(new Pane());
		primaryStage.setScene(getSurface());
		primaryStage.setTitle("Motor Meander: " + level.getName());
		primaryStage.getIcons().add(new Image("file:resources/assets/bikeicon3.png"));
		getNodes().getChildren().addAll(level.getAllPlatforms());
		bike = new Bike(1);
		bike.addTo(getNodes());
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
	}
	
	public void makeTimer() {
		Label timeLabel = new Label("TIME:");
		timeLabel.setStyle("-fx-font-weight: bold;");
		timerLabel = new Label("00:00.00");
		timerLabel.setStyle("-fx-effect: dropshadow( gaussian, #1d1d1d, 1, 1, 0, 0 );"
				 		  + "-fx-text-fill: rgb(255, 255, 255);"
				 		  + "-fx-font-size: 60px;");
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

	public void setupKeyMappings(KeyCode accel, KeyCode brake, KeyCode right, KeyCode left) {
		getSurface().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(accel)) {
					bike.setAccel(true);
					bike.setBrake(false);
				} else if(e.getCode().equals(brake)) {
					bike.setBrake(true);
					bike.setAccel(false);
				} else if(e.getCode().equals(right)) {
					bike.setRight(true);
					bike.setLeft(false);
				} else if(e.getCode().equals(left)) {
					bike.setLeft(true);
					bike.setRight(false);
				}
			}
		});
		
		getSurface().setOnKeyPressed(new EventHandler<KeyEvent> () {
			public void handle(KeyEvent e) {
				if(e.getCode().equals(KeyCode.ESCAPE)) {
					if(getTimer().isSuspended()) {
						playGameLoop();
						hidePauseMenu();
					} else {
						pauseGameLoop();
						showPauseMenu();
					}
				}
			}
		});
		
		getSurface().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(accel)) {
					bike.setAccel(false);
				} else if(e.getCode().equals(brake)) {
					bike.setBrake(false);
				} else if(e.getCode().equals(right)) {
					bike.setRight(false);
				} else if(e.getCode().equals(left)) {
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
		
	}
	
	public void hidePauseMenu() {
		((Group)getSurface().getRoot()).getChildren().remove(glass);
	}
	
	public void resume() {
		
	}
	
	public void quit() {
		
	}
	
	public void showSettings() {
		
	}

	public PauseMenuController getPMC() {
		if(pmc != null) {
			return pmc;
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(new URL("file:src/fog/ethereal/view/PauseMenu.fxml"));
				pauseMenu = (VBox)loader.load();
				pauseMenu.setVisible(false);
				
				pmc = loader.getController();
			} catch (Exception e) {e.printStackTrace();}
		}
		return pmc;
	}
}
