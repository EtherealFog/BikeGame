package fog.ethereal.util;

public class Translation {
	private int x;
	private int y;
	
	public Translation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Translation set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Translation move(int dx, int dy) {
		x += dx;
		y += dy;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
