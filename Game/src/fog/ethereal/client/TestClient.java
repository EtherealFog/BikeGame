package fog.ethereal.client;

import fog.ethereal.util.Projection;

public class TestClient {

	public static void main(String[] args) {
		Projection p1 = new Projection(-4, 1);
		Projection p2 = new Projection(2.75, 4);
		
		System.out.println(p1.getOverlap(p2));
		System.out.println(p2.getOverlap(p1));

	}

}
