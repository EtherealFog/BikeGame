package fog.ethereal.client;

import javafx.beans.property.SimpleLongProperty;

public class TestClient {
	public static void main(String[] args) {
		SimpleLongProperty p = new SimpleLongProperty(null, "test");
		System.out.println(p.get());
	}
}
