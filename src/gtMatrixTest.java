import static org.junit.Assert.*;
import ldraw.gtMatrix;
import ldraw.gtVector;

import org.junit.Test;


public class gtMatrixTest {

	@Test
	public void testRotation() {
		gtMatrix A = gtMatrix.Identity();
		
		A.rotate(Math.PI/3, gtVector.UnitX());
		
		// System.out.println(A);
		
		A.translate(new gtVector(0.5, 0.5, 0));
		
		// System.out.println(A);
		
		A.rotate(Math.PI/3, gtVector.UnitZ());
		
		// System.out.println(A);
		
		// System.out.println();
		
		A = gtMatrix.Identity();
		
		A.rotate(Math.PI/3, gtVector.UnitZ());
		// 
		// System.out.println(A);
		// System.out.println();
		
		A.rotate(Math.PI/3, new gtVector(0.1,0.123,1.34));
		
		// System.out.println(A);
	}

	@Test
	public void testOrthProjection() {
		gtMatrix ortho = gtMatrix.Ortho(-2, 2, -2, 2, -1, 3);

		gtVector a = new gtVector(1, 1, 1);

		System.out.println(ortho.mult(a));
	}


}
