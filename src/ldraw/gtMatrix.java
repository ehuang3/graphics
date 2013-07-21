
public class gtMatrix {
	
	public static gtMatrix Identity() {
		gtMatrix I = new gtMatrix();
		for(int i=0; i < I.rows(); i++) {
			I.data[i][i] = 1;
		}
		return I;
	}
	
	public gtMatrix() {
		data = new double[4][4];
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
	
	public void translate(double x, double y, double z) {
		translate(new gtVector(x,y,z,1));
	}
	
	public gtMatrix translated(double x, double y, double z) {
		return translated(new gtVector(x,y,z,1));
	}
	
	public void rotate(double angle, gtVector axis) {
		gtMatrix R = Identity();
		for(int i=0; i < cols()-1; i++) {
			R.col(i, R.col(i).rotated(angle, axis));
		}
		this.copyOver(this.mult(R));
	}
	
	public gtMatrix rotated(double angle, gtVector axis) {
		gtMatrix A = new gtMatrix(this);
		A.rotate(angle, axis);
		return A;
	}
	
	public void rotate(double angle, double x, double y, double z) {
		rotate(angle, new gtVector(x,y,z,1));
	}
	
	public gtMatrix rotated(double angle, double x, double y, double z) {
		return rotated(angle, new gtVector(x,y,z,1));
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
	
	double data[][];
}
