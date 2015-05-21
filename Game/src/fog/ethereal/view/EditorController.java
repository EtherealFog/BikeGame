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
import javafx.scene.layout.AnchorPane;
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
	
	@FXML
	private ContextMenu addPopup;
	
	private Mode mode;
	private Point popupPos;
	private Level level;
	
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
	
	@FXML
	public void addPlatform() {
		level.addSection(new Section(new Point[] {popupPos, new Point((int)popupPos.getX(), (int)popupPos.getY())}));
	}
	
	@FXML
	private void initialize() {
		nodes.setContent(new Group());
	}
	
	public void setLevel(Level l) {
		((Group)nodes.getContent()).getChildren().addAll(level.getAllPlatforms());
	}
	
	@FXML
	public void updatePopupPos() {
		popupPos.setLocation(addPopup.getAnchorX(), addPopup.getAnchorY());
	}
	
	public void setupPlatformUpdates() {
		level.getAllPlatforms().addListener(new ListChangeListener<Platform>() {
		     public void onChanged(Change c) {
		    	 while(c.next()) {
		    		 if(c.wasAdded()) {
		    			 ((Group)nodes.getContent()).getChildren().addAll(c.getAddedSubList());
		    		 } else if(c.wasRemoved()) {
		    			 ((Group)nodes.getContent()).getChildren().removeAll(c.getRemoved());
		    		 }
		    	 }
		     }
		});
	}
}
