package fog.ethereal.util;
/**
 * Vector for cartesian coordinate system; 
 * origin(0, 0) as 'tail' of vector with (x, y) coordinates of head.
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
	
	public VectorCT unit() {
		double scal = length();
		double newx = x / scal;
		double newy = y / scal;
		return new VectorCT(newx, newy);
	}
	
	public void toUnit() {
		double scal = length();
		x /= scal;
		y /= scal;
	}
	
	public VectorCT avg(VectorCT other) {
		return new VectorCT((other.getX() + x) / 2, (other.getY() + y) / 2);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double dot(VectorCT other) {
		return (x * other.getX() + y * other.getY()) / other.length();
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	public VectorCT dotVector(VectorCT other) {//returns 'other' in the length of the dot product.
		VectorCT newVector = other.unit();
		newVector.mult(dot(other));
		return newVector;
	}
	
	public VectorCT clone() {
		return new VectorCT(x, y);
	}
	
	public void mult(double scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public void add(VectorCT v) {
		x += v.getX();
		y += v.getY();
	}
	
	public void sub(VectorCT v) {
		x -= v.getX();
		y -= v.getY();
	}
	
	public void shorten(double amt) {//subtracts a certain amount along the angle of the vector
		double scal = length();
		scal -= amt;
		toUnit();
		mult(scal);
	}
}
