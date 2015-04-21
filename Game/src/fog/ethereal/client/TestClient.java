package fog.ethereal.client;

import java.awt.Rectangle;

import fog.ethereal.world.Platform;

public class TestClient {

	public static void main(String[] args) {
		Rectangle rect = new Rectangle(0, 0, 100, 100);
		Platform p = new Platform(25, 125, 99, 101);
		System.out.println(p.intersects(rect));
	}
}
