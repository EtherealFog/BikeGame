package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import javax.xml.bind.annotation.XmlElement;

import fog.ethereal.sprite.DragNode;
import fog.ethereal.util.Mode;
import fog.ethereal.util.Translation;

public class Section {
	private ObservableList<Platform> platforms;
	private ObservableList<DragNode> dragpoints;
	private Level parent;
	
	public Section(Point[] points) {
		this(Arrays.asList(points));
	}
	
	public Section(List<Point> points) {
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		platforms = FXCollections.observableArrayList();
		dragpoints = FXCollections.observableArrayList();
		for(int i = 1; i < points.size(); i++) {
			platforms.add(new Platform(points.get(i - 1), points.get(i)));
		}
	}
	
	public Section(BasicSection s) {
		ArrayList<BasicPlatform> basics = s.getPlatforms();
		platforms = FXCollections.observableArrayList();
		dragpoints = FXCollections.observableArrayList();
		for(BasicPlatform bp: basics) {
			platforms.add(new Platform(bp));
		}
	}
	
	
	public void setParent(Level l) {
		this.parent = l;
	}
	
	public Mode getMode() {
		return parent.getMode();
	}
	
	public Level getParent() {
		return parent;
	}
	
	public void update(Translation t) {
		
	}
	
	public List<DragNode> addDragpoints() {
		ArrayList<DragNode> nodes = new ArrayList<DragNode>();
		nodes.add(new DragNode(null, platforms.get(0), this));
		for(int i = 1; i < platforms.size() - 1; i++) {
			nodes.add(new DragNode(platforms.get(i - 1), platforms.get(1), this));
		}
		nodes.add(new DragNode(platforms.get(platforms.size() - 1), null, this));
		dragpoints.setAll(nodes);
		return dragpoints;
	}
	
	public List<Circle> getDragpoints() {
		ArrayList<Circle> nodes = new ArrayList<>();
		nodes.add(makeDragpoint(platforms.get(0), false));
		for(int i = 1; i < platforms.size() - 1; i++) {
			nodes.add(makeDragpoint(platforms.get(i - 1), platforms.get(i)));
		}
		nodes.add(makeDragpoint(platforms.get(platforms.size() - 1), true));
		return nodes;
	}
	
	//Precondition: both platforms have been instantiated (are not null)
	public Circle makeDragpoint(Platform p1, Platform p2) {
		Circle c = new Circle();
		c.setCenterX(p1.getEndX());
		c.setCenterY(p1.getEndY());
		setupMoveListeners(p1, p2, c);
		setupHandlers(c);
		c.setFill(getGrad(c));
		return c;
	}
	
	public Circle makeDragpoint(Platform p, boolean end) {
		Circle c = new Circle();
		if(end) {//dragpoint located at end of given platform
			c.setRadius(10);
			c.setCenterX(p.getEndX());
			c.setCenterY(p.getEndY());
			setupMoveListeners(p, null, c);
		} else {//dragpoint located at start of given platform
			c.setRadius(10);
			c.setCenterX(p.getStartX());
			c.setCenterY(p.getStartY());
			setupMoveListeners(null, p, c);
		}
		setupHandlers(c);
		c.setFill(getGrad(c));
		return c;
	}
	
	
	public void setupMoveListeners(Platform p1, Platform p2, Circle c) {
		c.centerXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue observable, Number oldValue, Number newValue) {
				if(p1 != null)
					p1.setEndX(c.getCenterX());
				if(p2 != null)
					p2.setStartX(c.getCenterX());
			}
		});
		c.centerYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue observable, Number oldValue, Number newValue) {
				if(p1 != null)
					p1.setEndY(c.getCenterY());
				if(p2 != null)
					p2.setStartY(c.getCenterY());
			}
		});
	}
	
	public ArrayList<DragNode> removeDragpoints() {
		ArrayList<DragNode> temp = new ArrayList<DragNode>(dragpoints);
		dragpoints.clear();
		return temp;
	}
	
	public void addTo(Group parent) {
		
	}
	
	@XmlElement(name = "platform")
	public List<Platform> getPlatforms() {
		return platforms;
	}
	
	public BasicSection toBasicSection() {
		BasicSection temp = new BasicSection();
		ArrayList<BasicPlatform> temps = new ArrayList<BasicPlatform>();
		for(Platform p: platforms) {
			temps.add(p.toBasicPlatform());
		}
		temp.addAll(temps);
		return temp;
	}
	
	public String toString() {
		String temp = "Section: " + platforms.size() + " platforms:";
		for(int i = 0; i < platforms.size(); i++) {
			temp += "\n\tp" + i + ": " + platforms.get(i);
		}
		return temp;
	}
	
	public void setupHandlers(Circle c) {
		c.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				c.setCenterX(x);
				c.setCenterY(y);
				c.setFill(getGrad(c));
			}
		});
		
		c.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.setRadius(10 * 1.5);
				c.setFill(getGrad(c));
			}
		});
		
		c.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.setRadius(10);
				c.setFill(getGrad(c));
			}
		});
	}
	
	public RadialGradient getGrad(Circle c) {
		return new RadialGradient(0, 0, c.getCenterX(), c.getCenterY(), c.getRadius(), false, 
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.75)), new Stop(1.0, Color.rgb(150, 150, 150, 0.75)));
	}
}
