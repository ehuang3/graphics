
public class gtVector {
	
	public static gtVector Zero() {
		return new gtVector();
	}
	
	public static gtVector UnitX() {
		return new gtVector(1,0,0,1);
	}
	
	public static gtVector UnitY() {
		return new gtVector(0,1,0,1);
	}
	
	public static gtVector UnitZ() {
		return new gtVector(0,0,1,1);
	}
	
	public gtVector() {
		this(0,0,0,1);
	}
	
	public gtVector(gtVector other) {
		this(other.x(), other.y(), other.z(), other.w());
	}
	
	public gtVector(double x, double y, double z) {
		this(x,y,z,1);
	}
	
	public gtVector(double x, double y, double z, double w) {
		data = new double[4];
		data[0] = x;
		data[1] = y;
		data[2] = z;
		data[3] = w;
	}
	
	public void x(double _x) {
		data[0] = _x;
	}
	
	public void y(double _y) {
		data[1] = _y;
	}
	
	public void z(double _z) {
		data[2] = _z;
	}
	
	public void w(double _w) {
		data[3] = _w;
	}
	
	public void xyz(gtVector a) {
		for(int i=0; i < hom_size(); i++) {
			this.data[i] = a.data[i];
		}
	}
	
	public void xyzw(gtVector a) {
		for(int i=0; i < size(); i++) {
			this.data[i] = a.data[i];
		}
	}
	
	public double x() {
		return data[0];
	}
	
	public double y() {
		return data[1];
	}
	
	public double z() {
		return data[2];
	}
	
	public double w() {
		return data[3];
	}
	
	public double[] data() {
		return data;
	}
	
	public int hom_size() {
		return data.length-1;
	}
	
	public int size() {
		return data.length;
	}
	
	public boolean eq(gtVector other) {
		for(int i=0; i < size(); i++) {
			if(this.data[i] != other.data[i])
				return false;
		}
		return true;
	}
	
	public boolean near(gtVector other, double tol) {
		for(int i=0; i < size(); i++) {
			if(Math.abs(data[i]-other.data[i]) > tol)
				return false;
		}
		return true;
	}
	
	public void scale(double a) {
		for(int i=0; i < hom_size(); i++) {
			data[i] *= a;
		}
	}
	
	public gtVector scaled(double a) {
		gtVector b = new gtVector(this);
		b.scale(a);
		return b;
	}
	
	public gtVector add(gtVector b) {
		gtVector c = new gtVector(this);
		for(int i=0; i < hom_size(); i++) {
			c.data[i] += b.data[i];
		}
		return c;
	}
	
	public gtVector sub(gtVector b) {
		return add(b.scaled(-1));
	}
	
	public double dot(gtVector b) {
		double dot_prod = 0;
		for(int i=0; i < data.length; i++) {
			dot_prod += data[i]*b.data[i];
		}
		return dot_prod;
	}
	
	public gtVector cross(gtVector b) {
		gtVector c = new gtVector();
		c.x( y()*b.z()-z()*b.y() );
		c.y( z()*b.x()-x()*b.z() );
		c.z( x()*b.y()-y()*b.x() );
		return c;
	}
	
	public double norm() {
		double sqr_sum = 0;
		for(int i=0; i < hom_size(); i++) {
			sqr_sum += data[i]*data[i];
		}
		return Math.sqrt(sqr_sum);
	}
	
	public void normalize() {
		double norm = norm();
		if(norm == 0)
			return;
		for(int i=0; i < hom_size(); i++) {
			data[i] /= norm;
		}
	}
	
	public gtVector normalized() {
		gtVector unit = new gtVector(this);
		unit.normalize();
		return unit;
	}
	
	public void rotate(double angle, gtVector axis) {
		axis.normalize();
		gtVector a = axis.scaled(axis.dot(this));
		gtVector b = this.sub(a);
		double p = b.norm();
		b.normalize();
		gtVector c = axis.cross(b);
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		gtVector d = b.scaled(x).add(c.scaled(y)).scaled(p);
		gtVector e = d.add(a);
		this.xyz(e);
	}
	
	public gtVector rotated(double angle, gtVector axis) {
		gtVector r = new gtVector(this);
		r.rotate(angle, axis);
		return r;
	}
	
	public String toString() {
		String out = "";
		out += "[" + x() + ", " + y() + ", " + z() + ", " + w() + "]";
		
		String s = String.format("[% .5f, % .5f, % .5f, % .5f]", x(), y(), z(), w());
		
		return s;
	}
	
	double data[];
}
