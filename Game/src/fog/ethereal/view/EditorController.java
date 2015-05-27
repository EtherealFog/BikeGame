package fog.ethereal.view;



import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import fog.ethereal.sprite.DragNode;
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
	private ImageView startIcon, endIcon;
	
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
		
		List<Platform> all = new ArrayList<>();
		level.getSections().stream()
						   .forEach(s -> all.addAll(s.getPlatforms()));
		List<DragNode> circles = new ArrayList<>();
		level.getSections().stream()
						   .forEach(s -> circles.addAll(s.addDragpoints()));
		content.getChildren().add(backdrop);//basically the thing that you click on to get the main right click menu
		content.getChildren().addAll(all);//adds the platforms.
		circles.stream()
			   .forEach(d -> d.addSelfTo(content));
		//content.getChildren().addAll(circles);//adds the dragpoints
		if(!(level.getStartX() == 0 && level.getEndX() == 0))
			setStartPoint(level.getStartX(), level.getStartY());
		if(!(level.getEndX() == 0 && level.getEndY() == 0))
			setEndPoint(level.getEndX(), level.getEndY());
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
				} else {
					addPopup.hide();
				}
			}
		});
	}
	
	public void setStartPoint() {
		setStartPoint(popupPos.getX(), popupPos.getY());
	}
	
	public void setStartPoint(double x, double y) {
		level.setStartPoint(x, y);
		if(startIcon == null) {
			startIcon = makeIcon("start");
			content.getChildren().add(startIcon);
			startIcon.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					level.setStartPoint(e.getX(), e.getY());
					startIcon.toFront();
					startIcon.setX(e.getX() - startIcon.getFitWidth() / 2);
					startIcon.setY(e.getY() - startIcon.getFitWidth() / 2);
				}
			});
		}
		startIcon.setX(x - startIcon.getFitWidth() / 2);
		startIcon.setY(y - startIcon.getFitWidth() / 2);
	}
	
	public void setEndPoint() {
		setEndPoint(popupPos.getX(), popupPos.getY());
	}
	
	public void setEndPoint(double x, double y) {
		level.setEndPoint(x, y);
		if(endIcon == null) {
			endIcon = makeIcon("end");
			content.getChildren().add(endIcon);
			endIcon.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					level.setEndPoint(e.getX(), e.getY());
					endIcon.toFront();
					endIcon.setX(e.getX() - endIcon.getFitWidth() / 2);
					endIcon.setY(e.getY() - endIcon.getFitWidth() / 2);
				}
			});
		}
		endIcon.setX(x - endIcon.getFitWidth() / 2);
		endIcon.setY(y - endIcon.getFitWidth() / 2);
	}
	
	public ImageView makeIcon(String name) {
		ImageView icon = new ImageView("file:resources/assets/" + name + "icon.png");
		icon.setPreserveRatio(true);
		icon.setFitWidth(70);
		return icon;
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
		window.close();;
	}
	
	@FXML
	public void saveAs() {
		
	}
	
	@FXML
	public void saveIcon() {
		BufferedImage icon = SwingFXUtils.fromFXImage(nodes.snapshot(null, new WritableImage((int)content.getWidth() - 10, (int)content.getHeight() - 10)), null);
		File loc = new File("resources/worlds/" + level.getName().replaceAll(" ",  "_") + "/icon.png");
		try {
			ImageIO.write(icon, "PNG", loc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
