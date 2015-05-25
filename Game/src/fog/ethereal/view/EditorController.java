package fog.ethereal.view;



import java.awt.Point;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import fog.ethereal.util.LevelSaver;
import fog.ethereal.util.Mode;
import fog.ethereal.world.Level;
import fog.ethereal.world.Platform;
import fog.ethereal.world.Section;

public class EditorController {
	@FXML
	private ToggleGroup modes;
	
	@FXML
	private ScrollPane nodes;
	
	@FXML
	private MenuItem addPlatform;
	
	private ContextMenu addPopup;
	private Mode mode;
	private Point popupPos;
	private Level level;
	private Stage window;
	
	@FXML
	public void setMove() {
		mode = Mode.MOVE;
	}
	
	@FXML
	public void setAdd() {
		mode = Mode.ADD;
	}
	
	public ScrollPane getScrollPane() {
		return nodes;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setStage(Stage stage) {
		window = stage;
		setupContextMenu();
	}
	
	@FXML
	public void addPlatform() {
		level.addSection(new Section(new Point[] {popupPos, new Point((int)popupPos.getX() + 100, (int)popupPos.getY())}));
	}
	
	@FXML
	private void initialize() {
		nodes.setContent(new Group());
		popupPos = new Point();
	}
	
	public void setLevel(Level l) {
		level = l;
		((Group)nodes.getContent()).getChildren().addAll(level.getAllPlatforms());
		level.getAllPlatforms().parallelStream()
							   .forEach(p -> p.setupEditFunctions());
		((Group)nodes.getContent()).getChildren().addAll(level.getDragpoints());
		setupPlatformUpdates();
	}
	
	public void setupContextMenu() {
		addPopup = new ContextMenu();
		MenuItem add = new MenuItem("Add Platform");
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				addPlatform();
			}
		});
		addPopup.getItems().add(add);
		nodes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY) {
					popupPos.setLocation(e.getX(), e.getY());
					addPopup.show(nodes.getContent(), e.getScreenX(), e.getScreenY());
				}
			}
		});
	}
	
	
	@SuppressWarnings("rawtypes")
	public void setupPlatformUpdates() {
		level.getAllPlatforms().addListener(new ListChangeListener<Platform>() {
		     public void onChanged(Change c) {
		    	 while(c.next()) {
		    		 if(c.wasAdded()) {
		    			 List<Platform> l = c.getAddedSubList();
		    			 ((Group)nodes.getContent()).getChildren().addAll(c.getAddedSubList());
		    			 for(Platform p: l) {
		    				 p.toBack();
		    			 }
		    			 
		    		 } else if(c.wasRemoved()) {
		    			 ((Group)nodes.getContent()).getChildren().removeAll(c.getRemoved());
		    		 }
		    	 }
		     }
		});
	}
	
	@FXML
	public void save() {
		LevelSaver.save(level.toSaveableLevel());
	}
	
	@FXML
	public void saveAndExit() {
		save();
		
	}
	
	@FXML
	public void saveAs() {
		
	}
}
