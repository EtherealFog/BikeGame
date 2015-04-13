package fog.ethereal.util;
/**
 * Vector for cartesian coordinate system; 
 * origin as 'tail' of vector with (x, y) coordinates of head.
 */
public class VectorCT {
	private double x;
	private double y;
	
	public VectorCT(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static VectorMD toMD(VectorCT ct) {
		double m;
		double d;
		m = Math.sqrt(ct.getX() * ct.getX() + ct.getY() * ct.getY());
		d = Math.toRadians(Math.atan2(ct.getX(), ct.getY()));
		VectorMD md = new VectorMD(m, d);
		return md;
	}
	
	public VectorCT subtract(VectorCT other) {
		return new VectorCT(other.getX() - x, other.getY() - y);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
