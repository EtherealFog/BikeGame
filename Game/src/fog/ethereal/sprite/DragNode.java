package fog.ethereal.sprite;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import fog.ethereal.world.Platform;

public class DragNode {
	public static final double DEFAULT_RADIUS = 10;
	private Platform p1;
	private Platform p2;
	private Circle c;
	
	public DragNode(Platform p1, Platform p2) {
		if(p1 != null && p2 != null && (p1.getEndX() != p2.getStartX() || p1.getEndY() != p2.getStartY())) {
			throw new IllegalArgumentException("The endpoint of p1 must coincide with the startpoint of p2.");
		} else if(p1 == null && p2 == null) {
			throw new IllegalArgumentException("At least one of the two Platforms must not be null.");
		}
		if(p1 == null) {
			c = new Circle(p2.getStartX(), p2.getStartY(), DEFAULT_RADIUS);
		} else {
			c = new Circle(p1.getEndX(), p1.getEndY(), DEFAULT_RADIUS);
		}
		setupHandlers();
		c.setFill(getGrad());
	}
	
	public void setupHandlers() {
		c.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getSceneX();
				double y = e.getSceneY();
				c.setCenterX(x);
				c.setCenterY(y);
				if(p1 != null) {
					p1.setEndX(x);
					p1.setEndY(y);
				}
				if(p2 != null) {
					p2.setStartX(x);
					p2.setStartY(y);
				}
			}
		});
		
		c.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.setRadius(DEFAULT_RADIUS * 2);
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
				CycleMethod.NO_CYCLE, new Stop(0.0, Color.WHITE), new Stop(1.0, Color.GRAY));
	}
}
