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
	
}
