package fog.ethereal.util;

public class VectorMD {
	/**
	 * double representing magnitude
	 */
	double m;
	/**
	 * double representing standard rotation (starting on positive x-axis and going counter-clockwise)
	 * in radians.
	 */
	double d;
	
	public VectorMD(double m, double d) {
		this.m = m;
		this.d = d;
	}
	
	public double getMagnitude() {
		return m;
	}
	
	public double getDirection() {
		return d;
	}
	
	public static VectorCT toCT(VectorMD md) {
		double x = Math.sin(md.getDirection()) * md.getMagnitude();
		double y = Math.cos(md.getDirection()) * md.getMagnitude();
		return new VectorCT(x, y);
	}
	
}
