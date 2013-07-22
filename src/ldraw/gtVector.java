//package ldraw;


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
	
	public gtVector(float x, float y, float z) {
		this(x,y,z,1);
	}
	
	public gtVector(float x, float y, float z, float w) {
		data = new float[4];
		data[0] = x;
		data[1] = y;
		data[2] = z;
		data[3] = w;
	}
	
	public void x(float _x) {
		data[0] = _x;
	}
	
	public void y(float _y) {
		data[1] = _y;
	}
	
	public void z(float _z) {
		data[2] = _z;
	}
	
	public void w(float _w) {
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

	public void hom_scale(float w) {
		for(int i=0; i < hom_size(); i++) {
			this.data[i] /= w;
		}
	}
	
	public float x() {
		return data[0];
	}
	
	public float y() {
		return data[1];
	}
	
	public float z() {
		return data[2];
	}
	
	public float w() {
		return data[3];
	}
	
	public float[] data() {
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
	
	public boolean near(gtVector other, float tol) {
		for(int i=0; i < size(); i++) {
			if(Math.abs(data[i]-other.data[i]) > tol)
				return false;
		}
		return true;
	}
	
	public void scale(float a) {
		for(int i=0; i < hom_size(); i++) {
			data[i] *= a;
		}
	}
	
	public gtVector scaled(float a) {
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
	
	public float dot(gtVector b) {
		float dot_prod = 0;
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
	
	public float norm() {
		float sqr_sum = 0;
		for(int i=0; i < hom_size(); i++) {
			sqr_sum += data[i]*data[i];
		}
		return (float) Math.sqrt(sqr_sum);
	}
	
	public void normalize() {
		float norm = norm();
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
	
	public void rotate(float angle, gtVector axis) {
		axis.normalize();
		gtVector a = axis.scaled(axis.dot(this));
		gtVector b = this.sub(a);
		float p = b.norm();
		b.normalize();
		gtVector c = axis.cross(b);
		float x = (float) Math.cos(angle);
		float y = (float) Math.sin(angle);
		gtVector d = b.scaled(x).add(c.scaled(y)).scaled(p);
		gtVector e = d.add(a);
		this.xyz(e);
	}
	
	public gtVector rotated(float angle, gtVector axis) {
		gtVector r = new gtVector(this);
		r.rotate(angle, axis);
		return r;
	}
	
	public String toString() {
		String s = String.format("[% .5f, % .5f, % .5f, % .5f]", x(), y(), z(), w());
		return s;
	}
	
	float data[];
}
