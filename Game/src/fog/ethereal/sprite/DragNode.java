package fog.ethereal.sprite;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import fog.ethereal.util.Mode;
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
		if(p1 != null && p2 != null && (p1.getEndX() != p2.getStartX() || p1.getEndY() != p2.getStartY())) {
			throw new IllegalArgumentException("The endpoint of p1 must coincide with the startpoint of p2.");
		} else if(p1 == null && p2 == null) {
			throw new IllegalArgumentException("At least one of the two Platforms must not be null.");
		}
		if(p1 == null) {
			c = new Circle(p2.getStartX(), p2.getStartY(), DEFAULT_RADIUS);
			this.p2 = p2;
		} else {
			c = new Circle(p1.getEndX(), p1.getEndY(), DEFAULT_RADIUS);
			this.p1 = p1;
			if(p2 != null) {
				this.p2 = p2;
			}
		}
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
				if(p1 != null) {
					//System.out.println("setting p1 endpoint");
					p1.setEnd(x, y);
				} else {
					//System.out.println("p1 == null");
				}
				if(p2 != null) {
					//System.out.println("setting p2 startpoint");
					p2.setStart(x, y);
				} else {
					//System.out.println("p2 == null");
				}
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
	
	public RadialGradient getGrad() {
		return new RadialGradient(0, 0, c.getCenterX(), c.getCenterY(), c.getRadius(), false, 
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.rgb(255, 255, 255, 0.75)), new Stop(1.0, Color.rgb(150, 150, 150, 0.75)));
	}
	
	public void addSelfTo(Group parent) {
		parent.getChildren().add(c);
		c.toFront();
	}

	@Override
	public void update(Translation t) {
		// TODO Auto-generated method stub
		
	}
}
