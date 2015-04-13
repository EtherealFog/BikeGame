package fog.ethereal.util;

public class Projection {
	private double min;
	private double max;
	
	public Projection(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean overlaps(Projection other) {
		return (!(other.getMax() < min && other.getMax() < max)) || (!(max < other.getMin() && max < other.getMax()));
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
}
