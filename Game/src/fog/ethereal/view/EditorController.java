package fog.ethereal.view;



import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
	private Rectangle backdrop;
	private Pane content;
	
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
		Section temp = new Section(new Point[] {popupPos, new Point((int)popupPos.getX() + 100, (int)popupPos.getY())});
		level.addSection(temp);
		content.getChildren().addAll(temp.getDragpoints());
	}
	
	@FXML
	private void initialize() {
		content = new Pane();
		nodes.setContent(content);
		backdrop = new Rectangle();
		backdrop.setHeight(nodes.getViewportBounds().getHeight());
		backdrop.setWidth(nodes.getViewportBounds().getWidth());
		backdrop.heightProperty().bind(content.heightProperty());
		backdrop.widthProperty().bind(content.widthProperty());
		backdrop.setFill(Color.rgb(255,  255,  255, 0));
		
		popupPos = new Point();
	}
	
	public void setLevel(Level l) {
		level = l;
		level.getAllPlatforms().stream()
							   .forEach(p -> p.setupEditFunctions());
		List<Circle> circles = new ArrayList<>();
		level.getSections().stream()
						   .forEach(s -> circles.addAll(s.getDragpoints()));
		List<Platform> all = new ArrayList<>();
		level.getSections().stream()
						   .forEach(s -> all.addAll(s.getPlatforms()));
		content.getChildren().add(backdrop);
		content.getChildren().addAll(all);
		content.getChildren().addAll(circles);
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
		MenuItem setstart = new MenuItem("Set Level Start Point");
		setstart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setStartPoint();
			}
		});
		MenuItem setend = new MenuItem("Set Level End Point");
		setend.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				setEndPoint();
			}
		});
		addPopup.getItems().addAll(add, new SeparatorMenuItem(), setstart, setend);
		backdrop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY) {
					popupPos.setLocation(e.getX(), e.getY());
					addPopup.show(nodes.getContent(), e.getScreenX(), e.getScreenY());
				}
			}
		});
	}
	
	public void setStartPoint() {
		level.setStartPoint(popupPos.getX(), popupPos.getY());
	}
	
	public void setEndPoint() {
		level.setEndPoint(popupPos.getX(), popupPos.getY());
	}
	
	@SuppressWarnings("rawtypes")
	public void setupPlatformUpdates() {
		level.getAllPlatforms().addListener(new ListChangeListener<Platform>() {
		     public void onChanged(Change c) {
		    	 while(c.next()) {
		    		 if(c.wasAdded()) {
		    			 List<Platform> l = c.getAddedSubList();
		    			 content.getChildren().addAll(c.getAddedSubList());
		    			 for(Platform p: l) {
		    				 p.toBack();
		    			 }
		    			 
		    		 } else if(c.wasRemoved()) {
		    			 content.getChildren().removeAll(c.getRemoved());
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
