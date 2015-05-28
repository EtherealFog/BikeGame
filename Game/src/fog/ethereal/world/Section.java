package fog.ethereal.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.DragEvent;
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
	
	public Section(Point... points) {
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
		ArrayList<SavePoint> sps = s.getPoints();
		ArrayList<Point> points = new ArrayList<>();
		for(SavePoint sp: sps) {
			points.add(sp.toPoint());
		}
		if(points.size() < 2) {
			throw new IllegalArgumentException("Arg 'points' must have size of at least 2.");
		}
		platforms = FXCollections.observableArrayList();
		dragpoints = FXCollections.observableArrayList();
		for(int i = 1; i < points.size(); i++) {
			platforms.add(new Platform(points.get(i - 1), points.get(i)));
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
	
	public void add(double x1, double y1, double x2, double y2) {
		if(platforms.size() > 0) {
			Platform end = platforms.get(platforms.size() - 1);
			Platform start = platforms.get(0);
			System.out.print("Adding Platform ");
			Platform p;
			if((x1 == start.getStartX() && y1 == start.getStartY()) || (x2 == end.getEndX() && y2 == end.getEndY())) {
				p = new Platform(x2, y2, x1, y1);
			} else {
				p = new Platform(x1, y1, x2, y2);
			} 
			if(p.getStartX() == end.getEndX() && p.getStartY() == end.getEndY()) {
				System.out.println("At End of Section.");
				platforms.add(p);
				parent.getAllPlatforms().add(p);
				dragpoints.get(dragpoints.size() - 1).setP2(p);
				dragpoints.add(new DragNode(p, null, this));
			} else if(p.getEndX() == start.getStartX() && p.getEndY() == start.getStartY()) {
				System.out.println("At Beginning of Section.");
				platforms.add(0, p);
				parent.getAllPlatforms().add(p);
				dragpoints.get(0).setP1(p);
				dragpoints.add(0, new DragNode(null, p, this));
			} else {
				System.out.println("... JK");
				System.out.println("Start: " + start);
				System.out.println("End: " + end);
				System.out.println("To Be Added: " + p);
			}
		} else {
			
		}
	}
	
	public List<DragNode> addDragpoints() {
		ArrayList<DragNode> nodes = new ArrayList<DragNode>();
		System.out.println(platforms);
		nodes.add(new DragNode(null, platforms.get(0), this));
		for(int i = 1; i < platforms.size(); i++) {
			
			nodes.add(new DragNode(platforms.get(i - 1), platforms.get(i), this));
		}
		nodes.add(new DragNode(platforms.get(platforms.size() - 1), null, this));
		dragpoints.setAll(nodes);
		setupPlatformsListener();
		return nodes;
	}
	
	public ObservableList<DragNode> getDragpoints() {
		return dragpoints;
	}
	
	public void setupPlatformsListener() {
		
	}
	
	public Section get() {
		return this;
	}
	
	//Precondition: both platforms have been instantiated (are not null)
	public Circle makeDragpoint(Platform p1, Platform p2) {
		Circle c = new Circle();
		c.setRadius(10);
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
		ArrayList<Point> sps = new ArrayList<>();
		sps.add(new Point((int)platforms.get(0).getStartX(), (int)platforms.get(0).getStartY()));
		for(Platform p: platforms) {
			sps.add(new Point((int)p.getEndX(), (int)p.getEndY()));
		}
		return new BasicSection(sps);
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
		
		c.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent e) {
				if(e.getSource() instanceof Circle) {
					Circle temp = (Circle)e.getSource();
					c.setFill(getConfirmGrad(c));
					temp.setFill(getConfirmGrad(temp));
				}
			}
		});
		
		c.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent e) {
				if(e.getSource() instanceof Circle) {
					Circle temp = (Circle)e.getSource();
					c.setFill(getGrad(c));
					temp.setFill(getGrad(temp));
				}
			}
		});
		
		c.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent e) {
				if(e.getSource() instanceof Circle) {
					
				}
			}
		});
	}
	
	public RadialGradient getGrad(Circle c) {
		return new RadialGradient(0, 0, c.getCenterX(), c.getCenterY(), c.getRadius(), false, 
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.75)), new Stop(1.0, Color.rgb(150, 150, 150, 0.75)));
	}
	
	public RadialGradient getConfirmGrad(Circle c) {
		return new RadialGradient(0, 0, c.getCenterX(), c.getCenterY(), c.getRadius(), false, 
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(100, 255, 100, 0.75)), new Stop(1.0, Color.rgb(50, 150, 50, 0.75)));
	}
}
