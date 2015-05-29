package fog.ethereal.world;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import fog.ethereal.sprite.Bike;
import fog.ethereal.view.PauseMenuController;

public class BikeWorld extends World {
	private Bike bike;
	private PauseMenuController pmc;
	private VBox pauseMenu;
	
	@Override
	public void initialize(Stage primaryStage) {
		
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
		
		getSurface().setOnKeyTyped(new EventHandler<KeyEvent> () {
			public void handle(KeyEvent e) {
				if(e.getCode().equals(KeyCode.ESCAPE)) {
					pauseGameLoop();
					showPauseMenu();
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
		Rectangle glass = new Rectangle(getSurface().getWidth(), getSurface().getHeight());
		glass.setFill(Color.rgb(50, 50, 50, 0.33));
		((Group)getSurface().getRoot()).getChildren().add(glass);
		glass.toFront();
		
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
