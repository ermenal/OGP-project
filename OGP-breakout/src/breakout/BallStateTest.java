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
	
	PaddleState paddle1;
	PaddleState paddle2;
	PaddleState paddle3;
	
	Point centerPaddle1;
	Point centerPaddle2;
	Point centerPaddle3;
	
	Vector PaddleSize;
	
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
		
		PaddleSize = new Vector(1,1);
		
		centerPaddle1 = new Point(0,1);
		paddle1 = new PaddleState(centerPaddle1, PaddleSize);
		
		centerPaddle2 = new Point(1000,0);
		paddle2 = new PaddleState(centerPaddle2, PaddleSize);
		
		centerPaddle3 = new Point(25500,15000);
		paddle3 = new PaddleState(centerPaddle3, PaddleSize);
		
	}
	
	@Test
	void testGetDiameter() {
		assertEquals(1,bal1.getDiameter());
		assertEquals(33,bal2.getDiameter());
		assertEquals(1000,bal3.getDiameter());
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

		Point br1 = new Point(50000, 30000);
		BallState newball1 = new BallState(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.moveBall(br1).getCenter());
		assertEquals(newball1.getVelocity(), bal1.moveBall(br1).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.moveBall(br1).getDiameter());
		
		Point br2 = new Point(100000, 100000);
		BallState newball2 = new BallState(new Point(1007,16),33, new Vector(7,-3));
		assertEquals(newball2.getCenter(), bal2.moveBall(br2).getCenter());
		assertEquals(newball2.getVelocity(), bal2.moveBall(br2).getVelocity());
		assertEquals(newball2.getDiameter(), bal2.moveBall(br2).getDiameter());
		
		Point br3 = new Point(33333, 22222);
		BallState newball3 = new BallState(new Point(25001, 15003), 1000, new Vector(1,3));
		assertEquals(newball3.getCenter(), bal3.moveBall(br3).getCenter());
		assertEquals(newball3.getVelocity(), bal3.moveBall(br3).getVelocity());
		assertEquals(newball3.getDiameter(), bal3.moveBall(br3).getDiameter());

	}
	
	@Test
	void testRaakt() {
		Point br = new Point(50000,30000);
		 
		 
		assertEquals(false, bal1.raaktOnder(br));
		assertEquals(false, bal1.raaktRechts(br));
		assertEquals(true, bal1.raaktLinks());
		assertEquals(true, bal1.raaktBoven());
		
		assertEquals(false, bal2.raaktOnder(br));
		assertEquals(false, bal2.raaktRechts(br));
		assertEquals(false, bal2.raaktLinks());
		assertEquals(true, bal2.raaktBoven());
		
		assertEquals(false, bal3.raaktOnder(br));
		assertEquals(false, bal3.raaktRechts(br));
		assertEquals(false, bal3.raaktLinks());
		assertEquals(false, bal3.raaktBoven());
		
		assertNotEquals(null, bal1.raaktBoven());
		
	}
	
	@Test
	void testRaaktPaddle() {
		
		assertEquals(true, bal1.raaktPaddleBoven(paddle1));
		//assertEquals(false, bal1.raaktPaddleLinks(paddle1));
		//assertEquals(false, bal1.raaktPaddleRechts(paddle1));
		
		//assertEquals(true, bal2.raaktPaddleRechts(paddle2));
		
		assertEquals(false, bal3.raaktPaddleBoven(paddle3));
		assertEquals(true, bal3.raaktPaddleLinks(paddle3));
		assertEquals(false, bal1.raaktPaddleRechts(paddle3));
		
	}
	
	@Test
	void testbouncewall() {
		//randgeval met velocity 0,0 dus bounce tegen elke muur is "gelijk"
		BallState newball1 = new BallState(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.bounceWall(1).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(1).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(1).getDiameter());
		assertEquals(newball1.getCenter(), bal1.bounceWall(2).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(2).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(2).getDiameter());
		assertEquals(newball1.getCenter(), bal1.bounceWall(3).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(3).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(3).getDiameter());
		assertEquals(newball1.getCenter(), bal1.bounceWall(4).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(4).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(4).getDiameter());
		
		//ball2 tegen elke wall
		BallState newball21 = new BallState(new Point(1000,0), 33, new Vector(-7,-3));
		assertEquals(newball21.getCenter(), bal2.bounceWall(1).getCenter());
		assertEquals(newball21.getVelocity(), bal2.bounceWall(1).getVelocity());
		assertEquals(newball21.getDiameter(), bal2.bounceWall(1).getDiameter());
		BallState newball22 = new BallState(new Point(1000,0), 33, new Vector(7,3));
		assertEquals(newball22.getCenter(), bal2.bounceWall(2).getCenter());
		assertEquals(newball22.getVelocity(), bal2.bounceWall(2).getVelocity());
		assertEquals(newball22.getDiameter(), bal2.bounceWall(2).getDiameter());
		BallState newball23 = new BallState(new Point(1000,0), 33, new Vector(-7,-3));
		assertEquals(newball23.getCenter(), bal2.bounceWall(3).getCenter());
		assertEquals(newball23.getVelocity(), bal2.bounceWall(3).getVelocity());
		assertEquals(newball23.getDiameter(), bal2.bounceWall(3).getDiameter());
		
		//ball3 tegen elke wall
		BallState newball31 = new BallState(new Point(25000,15000), 1000, new Vector(-1,3));
		assertEquals(newball31.getCenter(), bal3.bounceWall(1).getCenter());
		assertEquals(newball31.getVelocity(), bal3.bounceWall(1).getVelocity());
		assertEquals(newball31.getDiameter(), bal3.bounceWall(1).getDiameter());
		BallState newball32 = new BallState(new Point(25000,15000), 1000, new Vector(1,-3));
		assertEquals(newball32.getCenter(), bal3.bounceWall(2).getCenter());
		assertEquals(newball32.getVelocity(), bal3.bounceWall(2).getVelocity());
		assertEquals(newball32.getDiameter(), bal3.bounceWall(2).getDiameter());
		BallState newball33 = new BallState(new Point(25000,15000), 1000, new Vector(-1,3));
		assertEquals(newball33.getCenter(), bal3.bounceWall(3).getCenter());
		assertEquals(newball33.getVelocity(), bal3.bounceWall(3).getVelocity());
		assertEquals(newball33.getDiameter(), bal3.bounceWall(3).getDiameter());
	}
	
	@Test
	void testBreakoutState() {
		BreakoutState bs = new BreakoutState(new BallState[0], new BlockState[0], new Point(0, 0), new PaddleState(new Point(0, 0), new Vector(0, 0)));
	}
}
