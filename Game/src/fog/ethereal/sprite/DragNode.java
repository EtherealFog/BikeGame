package fog.ethereal.sprite;

import java.awt.Point;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import fog.ethereal.util.Translation;
import fog.ethereal.util.WorldObject;
import fog.ethereal.world.Platform;
import fog.ethereal.world.Section;

public class DragNode implements WorldObject{
	public static final double DEFAULT_RADIUS = 10;
	private Platform p1;
	private Platform p2;
	private Circle c;
	private Section parent;
	
	public DragNode(Platform p1, Platform p2, Section parent) {
		/*
		if(p1 != null && p2 != null && (!p1.getEnd().equals(p2.getStart()))) {
			throw new IllegalArgumentException("The endpoint of p1 must coincide with the startpoint of p2.");
		} else if(p1 == null && p2 == null) {
			throw new IllegalArgumentException("At least one of the two Platforms must not be null.");
		}
		*/
		c = new Circle(p1 == null ? p2.getStartX() : p1.getEndX(), p1 == null ? p2.getStartY() : p1.getEndY(), DEFAULT_RADIUS);
		this.p1 = p1;
		this.p2 = p2;
		this.parent = parent;
		setupHandlers();
		c.setFill(getGrad());
	}
	
	public void setupHandlers() {
		c.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				
				c.setCenterX(x);
				c.setCenterY(y);
				c.setFill(getGrad());
			}
		});
		
		c.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.setRadius(DEFAULT_RADIUS * 1.5);
				c.setFill(getGrad());
			}
		});
		
		c.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.setRadius(DEFAULT_RADIUS);
				c.setFill(getGrad());
			}
		});
		
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
		ContextMenu menu = new ContextMenu();
		
		MenuItem add = new MenuItem("Add Platform From Here");
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(p1 == null)
					parent.add(c.getCenterX(), c.getCenterY(), c.getCenterX() + (p2.getStartX() - p2.getEndX()), c.getCenterY() + (p2.getStartY() - p2.getEndY()));
				else if(p2 == null)
					parent.add(c.getCenterX(), c.getCenterY(), c.getCenterX() + (p1.getEndX() - p1.getStartX()), c.getCenterY() + (p1.getEndY() - p1.getStartY()));
				
			}
		});
		menu.getItems().add(add);
		c.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY && (p1 == null || p2 == null)) {
					menu.show(c, e.getScreenX(), e.getScreenY());
				} else {
					menu.hide();
				}
			}
		});
	}
	
	public void setP1(Platform p1) {
		if(p1 == null && p2 == null) {
			throw new IllegalArgumentException("At least one of the two Platforms must not be null.");
		}
		this.p1 = p1;
	}
	
	public void setP2(Platform p2) {
		if(p1 == null && p2 == null) {
			throw new IllegalArgumentException("At least one of the two Platforms must not be null.");
		}
		this.p2 = p2;
	}
	
	public Platform getP1() {
		return p1;
	}
	
	public Platform getP2() {
		return p2;
	}
	
	public RadialGradient getGrad() {
		return new RadialGradient(0, 0, c.getCenterX(), c.getCenterY(), c.getRadius(), false, 
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.75)), new Stop(1.0, Color.rgb(150, 150, 150, 0.75)));
	}
	
	public void addSelfTo(Pane content) {
		content.getChildren().add(c);
		c.toFront();
	}

	@Override
	public void update(Translation t) {
		// TODO Auto-generated method stub
		
	}
}
