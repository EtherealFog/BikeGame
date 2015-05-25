package fog.ethereal.world;

import java.awt.Point;
import java.awt.Rectangle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import fog.ethereal.util.Constants;
import fog.ethereal.util.Translation;
import fog.ethereal.util.VectorCT;
import fog.ethereal.util.WorldObject;

public class Platform extends Line implements WorldObject{
	private ContextMenu rightClickMenu;
	
	public Platform(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		setStrokeWidth(Constants.DEFAULT_PLATFORM_WIDTH);
		setStrokeLineCap(StrokeLineCap.ROUND);
	}
	
	public Platform(Point startPoint, Point endPoint) {
		this(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}
	
	public Platform(BasicPlatform basic) {
		this(basic.getStart(), basic.getEnd());
	}
	
	public void update(Translation t) {
		
	}
	
	public void setupEditFunctions() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY) {
					getRightClickMenu().show(getParent(), e.getScreenX(), e.getScreenY());
				}
			}
		});
	}
	
	public ContextMenu getRightClickMenu() {
		if(rightClickMenu != null) {
			return rightClickMenu;
		}
		rightClickMenu = new ContextMenu();
		MenuItem remove = new MenuItem("Remove");
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(getParent() instanceof Group)
					((Group)getParent()).getChildren().remove(this);
				//Handle removing self from section, etc.
			}
		});
		rightClickMenu.getItems().add(remove);
		return rightClickMenu;
	}
	
	public VectorCT getNormal() {
		//Make a vector equivalent of this Platform
		VectorCT edge = toVector();
		//edge[x, y] -> edge.normal[-y, x]
		return new VectorCT(-1 * edge.getY(), edge.getX());
	}
	
	public VectorCT toVector() {
		return (new VectorCT(getStartX(), getStartY())).subtract(new VectorCT(getEndX(), getEndY()));
	}
	
	public boolean intersects(Rectangle rect) {
		return rect.intersectsLine(getStartX(), getStartY(), getEndX(), getEndY());
	}
	
	public boolean fitsWithin(Rectangle rect) {
		return intersects(rect);
	}
	
	public BasicPlatform toBasicPlatform() {
		return new BasicPlatform(getStartX(), getStartY(), getEndX(), getEndY());
	}
	
	public String toString() {
		return "(" + getStartX() + ", " + getStartY() + ") -> (" + getEndX() + ", " + getEndY() + ")";
	}
	
	public void addTo(Group parent) {
		
	}
	
	public void setStart(double x, double y) {
		setStartX(x);
		setStartY(y);
	}
	
	public void setEnd(double x, double y) {
		setEndX(x);
		setEndY(y);
	}
}
