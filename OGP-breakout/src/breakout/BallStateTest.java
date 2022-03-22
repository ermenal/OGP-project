package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BallStateTest {
	
	BallState bal1;
	BallState bal2;
	BallState bal3;
	
	Vector velocity1;
	Vector velocity2;
	Vector velocity3;
	
	Point center1;
	Point center2;
	Point center3;
	
	int diameter1;
	int diameter2;
	int diameter3;
	
	@BeforeEach
	void setUp() {
		velocity1 = new Vector(0,0);
		center1 = new Point(0,0);
		diameter1 = 1;
		bal1 = new BallState(center1, diameter1, velocity1);
		
		velocity2 = new Vector(7,-3);
		center2 = new Point(1000,0);
		diameter2 = 33;
		bal2 = new BallState(center2, diameter2, velocity2);
		
		velocity3 = new Vector(1,3);
		center3 = new Point(25000,15000);
		diameter3 = 1000;
		bal3 = new BallState(center3, diameter3, velocity3);
		
		
	}

	@Test
	void testGetCenter() {
		assertEquals(new Point(0,0), bal1.getCenter());
		assertEquals(new Point(1000,0), bal2.getCenter());
		assertEquals(new Point(25000,15000), bal3.getCenter());
		assertNotEquals(new Vector(0,0), bal1.getCenter());
		assertNotEquals(null, bal2.getCenter());
	}
	
	@Test
	void testGetVelocity() {
		assertEquals(new Vector(0,0), bal1.getVelocity());
		assertEquals(new Vector(7,-3), bal2.getVelocity());
		assertEquals(new Vector(1,3),bal3.getVelocity());
		assertNotEquals(null, bal3.getVelocity());
	}
	
	@Test
	void testGetTopLeft() {
		assertEquals(new Point( -1/2, -1/2), bal1.getTopLeftOfSurroundingRectangle());
		assertEquals(new Point(1000 - 33/2, -33/2),bal2.getTopLeftOfSurroundingRectangle());
		assertEquals(new Point(24500, 14500), bal3.getTopLeftOfSurroundingRectangle());
		assertNotEquals(null, bal1.getTopLeftOfSurroundingRectangle());
	}
	
	@Test
	void testBottomRight() {
		assertEquals(new Point(1/2, 1/2), bal1.getBottomRightOfSurroundingRectangle());
		assertEquals(new Point(1000 + 33/2, 33/2), bal2.getBottomRightOfSurroundingRectangle());
		assertEquals(new Point(25500, 15500), bal3.getBottomRightOfSurroundingRectangle());
		assertNotEquals(null, bal1.getBottomRightOfSurroundingRectangle());
	}
	
	@Test
	void testmoveBall() {
		BallState newBall = new BallState(new Point(0,0), 1, new Vector(0,0));
		BallState bewogenBall = bal1.moveBall();
		assertEquals(newBall.getCenter(), bewogenBall.getCenter());
		assertEquals(newBall.getVelocity(), bewogenBall.getVelocity());
		
	}
	
	@Test
	void testRaakt() {
		assertEquals(false, bal1.raaktOnder());
		assertEquals(false, bal1.raaktRechts());
		assertEquals(true, bal1.raaktLinks());
		assertEquals(true, bal1.raaktBoven());
		
		assertEquals(false, bal2.raaktOnder());
		assertEquals(false, bal2.raaktRechts());
		assertEquals(false, bal2.raaktLinks());
		assertEquals(true, bal2.raaktBoven());
		
		assertEquals(false, bal3.raaktOnder());
		assertEquals(false, bal3.raaktRechts());
		assertEquals(false, bal3.raaktLinks());
		assertEquals(false, bal3.raaktBoven());
		
		assertNotEquals(null, bal1.raaktBoven());
	}
	
	@Test
	void testRaaktPaddle() {
	}
}
