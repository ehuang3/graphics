//package ldraw;


public class gtMatrix {
	
	public static gtMatrix Identity() {
		gtMatrix I = new gtMatrix();
		for(int i=0; i < I.rows(); i++) {
			I.data[i][i] = 1;
		}
		return I;
	}
	
	public static gtMatrix Scale(float x, float y, float z) {
		gtMatrix S = Identity();
		S.data[0][0] *= x;
		S.data[1][1] *= y;
		S.data[2][2] *= z;
		return S;
	}
	
	public static gtMatrix Ortho(float l, float r, float b, float t, float n, float f) {
		gtMatrix O = Identity();
		O.data[0][0] = 2/(r - l);
		O.data[1][1] = 2/(t - b);
		O.data[2][2] = -2/(f - n);
		O.data[0][3] = (r + l)/(r - l);
		O.data[1][3] = (t + b)/(t - b);
		O.data[2][3] = (f + n)/(f - n);
		return O;
	}
	
	public static gtMatrix Frustum(float l, float r, float b, float t, float n, float f) {
		gtMatrix F = Identity();
		F.data[0][0] = 2*n/(r - l);
		F.data[1][1] = 2*n/(t - b);
		F.data[2][2] = -(f + n)/(f - n);
		F.data[3][3] = 0;
		F.data[0][2] = (r + l)/(r - l);
		F.data[1][2] = (t + b)/(t - b);
		F.data[3][2] = -1;
		F.data[2][3] = -2*f*n/(f - n);
		return F;
	}
	
	public static gtMatrix Frustum(float fov, float n, float f) {
		float h = (float) (n/Math.cos(fov/2));
		return Frustum(-h, h, -h, h, n, f);
	}
	
	public gtMatrix() {
		data = new float[4][4];
		data[3][3] = 1;
	}
	
	public gtMatrix(gtMatrix other) {
		this();
		for(int i=0; i < rows(); i++) {
			for(int j=0; j < cols(); j++) {
				this.data[i][j] = other.data[i][j];
			}
		}
	}
	
	public int rows() { 
		return data.length;
	}
	
	public int cols() {
		return data[0].length;
	}
	
	public gtVector row(int i) {
		return new gtVector(data[i][0], data[i][1], data[i][2], data[i][3]);
	}
	
	public gtVector col(int i) {
		return new gtVector(data[0][i], data[1][i], data[2][i], data[3][i]);
	}

	public void row(int i, gtVector r) {
		for(int j=0; j < cols(); j++) {
			data[i][j] = r.data()[j];
		}
	}
	
	public void col(int j, gtVector c) {
		for(int i=0; i < cols(); i++) {
			data[i][j] = c.data()[i];
		}
	}
	
	public void copyOver(gtMatrix other) {
		for(int i=0; i < rows(); i++) {
			for(int j=0; j < cols(); j++) {
				data[i][j] = other.data[i][j];
			}
		}
	}
	
	public void translate(gtVector t) {
		assert(t.w() == 1);
		this.col(3, this.mult(t));
	}
	
	public gtMatrix translated(gtVector t) {
		gtMatrix A = new gtMatrix(this);
		A.translate(t);
		return A;
	}
	
	public void translate(float x, float y, float z) {
		translate(new gtVector(x,y,z,1));
	}
	
	public gtMatrix translated(float x, float y, float z) {
		return translated(new gtVector(x,y,z,1));
	}
	
	public void rotate(float angle, gtVector axis) {
		gtMatrix R = Identity();
		for(int i=0; i < cols()-1; i++) {
			R.col(i, R.col(i).rotated(angle, axis));
		}
		this.copyOver(this.mult(R));
	}
	
	public gtMatrix rotated(float angle, gtVector axis) {
		gtMatrix A = new gtMatrix(this);
		A.rotate(angle, axis);
		return A;
	}
	
	public void rotate(float angle, float x, float y, float z) {
		rotate(angle, new gtVector(x,y,z,1));
	}
	
	public gtMatrix rotated(float angle, float x, float y, float z) {
		return rotated(angle, new gtVector(x,y,z,1));
	}
	
	public void scale(float sx, float sy, float sz) {
		gtMatrix S = Scale(sx, sy, sz);
		gtMatrix mS = this.mult(S);
		this.copyOver(mS);
	}
	
	public gtMatrix mult(gtMatrix B) {
		gtMatrix X = new gtMatrix(this);
		for(int i=0; i < rows(); i++) {
			for(int j=0; j < cols(); j++) {
				X.data[i][j] = this.row(i).dot(B.col(j));
			}
		}
		return X;
	}
	
	public gtVector mult(gtVector v) {
		gtVector x = new gtVector();
		for(int i=0; i < rows(); i++) {
			x.data()[i] = row(i).dot(v);
		}
		return x;
	}
	
	public String toString() {
		String out = "";
		for(int i=0; i < rows(); i++) {
			out += row(i);
			if(i < rows()-1)
				out += "\n";
		}
		return out;
	}
	
	float data[][];
}
