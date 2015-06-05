package fog.ethereal.client;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

public class TestClient {
	public static void main(String[] args) {
		Point2D test = new Point2D(50, 50);
		System.out.println(test);
		System.out.println(new Rotate(25).deltaTransform(test));
	}
}
