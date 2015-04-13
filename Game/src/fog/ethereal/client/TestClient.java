package fog.ethereal.client;

import fog.ethereal.util.Projection;

public class TestClient {

	public static void main(String[] args) {
		Projection p1 = new Projection(-6, -2);
		Projection p2 = new Projection(0, 4);
		
		System.out.println(p1.overlaps(p2));
		System.out.println(p2.overlaps(p1));

	}

}
