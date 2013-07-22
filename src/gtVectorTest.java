import static org.junit.Assert.*;
import ldraw.gtVector;

import org.junit.Test;


public class gtVectorTest {

	@Test
	public void testCross() {
		assertTrue(gtVector.UnitX().cross(gtVector.UnitY()).eq(gtVector.UnitZ()));
	}

	@Test
	public void testRotate() {
		gtVector axis = gtVector.UnitX().add(gtVector.UnitY());
		gtVector v = gtVector.UnitZ();
		v.rotate(Math.PI/2, axis);
		System.out.println(v);
	}
	
}
