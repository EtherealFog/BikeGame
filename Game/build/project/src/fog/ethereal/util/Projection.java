package fog.ethereal.util;

public class Projection {
	private double min;
	private double max;
	
	public Projection(double min, double max) {
		if(min <= max) {	
			this.min = min;
			this.max = max;
		} else {
			this.max = min;
			this.min = max;
		}
	}
	
	public boolean overlaps(Projection other) {
		return (!(other.getMax() < min && other.getMax() < max)) && (!(max < other.getMin() && max < other.getMax()));
	}
	
	/*
	public double overlap(Projection other) {
		
	}
	*/
	public double getOverlap(Projection other) {
		double overlap1 = max - other.getMin();
		double overlap2 = other.getMax() - min;
		return overlap1 < overlap2 ? overlap1 : overlap2;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
}
