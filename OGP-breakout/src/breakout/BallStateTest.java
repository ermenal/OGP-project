package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BallStateTest {
	
	Ball bal1;
	Ball bal2;
	Ball bal3;
	
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
	
	Vector PaddleSize1;
	Vector PaddleSize2;
	
	BlockState blok1;
	BlockState blok2;
	BlockState blok3;
	BlockState blok4;
	
	Point topleftBlock1;
	Point topleftBlock2;
	Point topleftBlock3;
	Point topleftBlock4;
	
	Point bottomrightBlock1;
	Point bottomrightBlock2;
	Point bottomrightBlock3;
	Point bottomrightBlock4;

	@BeforeEach
	void setUp() {
		velocity1 = new Vector(0,0);
		center1 = new Point(0,0);
		diameter1 = 1;
		bal1 = new Ball(center1, diameter1, velocity1);
		
		velocity2 = new Vector(7,-3);
		center2 = new Point(1000,0);
		diameter2 = 33;
		bal2 = new Ball(center2, diameter2, velocity2);
		
		velocity3 = new Vector(1,3);
		center3 = new Point(25000,15000);
		diameter3 = 1000;
		bal3 = new Ball(center3, diameter3, velocity3);
		
		PaddleSize1 = new Vector(10,10);
		centerPaddle1 = new Point(0,10);
		paddle1 = new PaddleState(centerPaddle1, PaddleSize1);
		
		PaddleSize2 = new Vector(100,100);
		centerPaddle2 = new Point(900,100);
		paddle2 = new PaddleState(centerPaddle2, PaddleSize2);
		
		centerPaddle3 = new Point(25500,15000);
		paddle3 = new PaddleState(centerPaddle3, PaddleSize2);
		
		topleftBlock1 = new Point(-1,0);
		bottomrightBlock1 = new Point(1,1);
		blok1 = new BlockState(topleftBlock1, bottomrightBlock1);
		
		topleftBlock2 = new Point(900,-10);
		bottomrightBlock2 = new Point(1100,0);
		blok2 = new BlockState(topleftBlock2, bottomrightBlock2);
		
		topleftBlock3 = new Point(25500,14500);
		bottomrightBlock3 = new Point(27000,17000);
		blok3 = new BlockState(topleftBlock3, bottomrightBlock3);
		
		topleftBlock4 = new Point(-10,-10);
		bottomrightBlock4 = new Point(0,10);
		blok4 = new BlockState(topleftBlock4, bottomrightBlock4);
		
	}
	
	@Test
	void testGetDiameterbal() {
		assertEquals(1,bal1.getDiameter());
		assertEquals(33,bal2.getDiameter());
		assertEquals(1000,bal3.getDiameter());
	}

	@Test
	void testGetCenterbal() {
		assertEquals(new Point(0,0), bal1.getCenter());
		assertEquals(new Point(1000,0), bal2.getCenter());
		assertEquals(new Point(25000,15000), bal3.getCenter());
		assertNotEquals(new Vector(0,0), bal1.getCenter());
		assertNotEquals(null, bal2.getCenter());
	}
	
	@Test
	void testGetVelocitybal() {
		assertEquals(new Vector(0,0), bal1.getVelocity());
		assertEquals(new Vector(7,-3), bal2.getVelocity());
		assertEquals(new Vector(1,3),bal3.getVelocity());
		assertNotEquals(null, bal3.getVelocity());
	}
	
	@Test
	void testGetTopLeftbal() {
		assertEquals(new Point( -1/2, -1/2), bal1.getTopLeftOfSurroundingRectangle());
		assertEquals(new Point(1000 - 33/2, -33/2),bal2.getTopLeftOfSurroundingRectangle());
		assertEquals(new Point(24500, 14500), bal3.getTopLeftOfSurroundingRectangle());
		assertNotEquals(null, bal1.getTopLeftOfSurroundingRectangle());
	}
	
	@Test
	void testBottomRightbal() {
		assertEquals(new Point(1/2, 1/2), bal1.getBottomRightOfSurroundingRectangle());
		assertEquals(new Point(1000 + 33/2, 33/2), bal2.getBottomRightOfSurroundingRectangle());
		assertEquals(new Point(25500, 15500), bal3.getBottomRightOfSurroundingRectangle());
		assertNotEquals(null, bal1.getBottomRightOfSurroundingRectangle());
	}
	
	@Test
	void testmoveBall() {

		Point br1 = new Point(50000, 30000);
		Ball newball1 = new Ball(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.moveBall(br1).getCenter());
		assertEquals(newball1.getVelocity(), bal1.moveBall(br1).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.moveBall(br1).getDiameter());
		
		Point br2 = new Point(100000, 100000);
		Ball newball2 = new Ball(new Point(1007,16),33, new Vector(7,-3));
		assertEquals(newball2.getCenter(), bal2.moveBall(br2).getCenter());
		assertEquals(newball2.getVelocity(), bal2.moveBall(br2).getVelocity());
		assertEquals(newball2.getDiameter(), bal2.moveBall(br2).getDiameter());
		
		Point br3 = new Point(33333, 22222);
		Ball newball3 = new Ball(new Point(25001, 15003), 1000, new Vector(1,3));
		assertEquals(newball3.getCenter(), bal3.moveBall(br3).getCenter());
		assertEquals(newball3.getVelocity(), bal3.moveBall(br3).getVelocity());
		assertEquals(newball3.getDiameter(), bal3.moveBall(br3).getDiameter());

	}
		
	@Test
	void testbouncewall() {
		//botst links en boven
		Ball newball1 = new Ball(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.bounceWall(1).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(1).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(1).getDiameter());
		assertEquals(newball1.getCenter(), bal1.bounceWall(2).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceWall(2).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceWall(2).getDiameter());
		
		//ball2 botst tegen bovenmuur

		Ball newball2 = new Ball(new Point(1000,0), 33, new Vector(7,3));
		assertEquals(newball2.getCenter(), bal2.bounceWall(2).getCenter());
		assertEquals(newball2.getVelocity(), bal2.bounceWall(2).getVelocity());
		assertEquals(newball2.getDiameter(), bal2.bounceWall(2).getDiameter());

		
		//ball3 raakt geen muur
		
	}
	
	
	void testbouncePaddle() {
		//bal1 raakt bovenkant paddle 1
		int paddledir1 = 0;
		Ball newball1 = new Ball(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.bouncePaddle(paddledir1, 2).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bouncePaddle(paddledir1, 2).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bouncePaddle(paddledir1, 2).getDiameter());
		
		//bal2 botst niet tegen een paddle
		
		//bal 3 botst linkerkant paddle 3
		int paddledir3 = 2;
		Ball newball3 = new Ball(new Point(25000,15000), 1000, new Vector(3,3));
		assertEquals(newball3.getCenter(), bal3.bouncePaddle(paddledir3, 1).getCenter());
		assertEquals(newball3.getVelocity(), bal3.bouncePaddle(paddledir3, 1).getVelocity());
		assertEquals(newball3.getDiameter(), bal3.bouncePaddle(paddledir3, 1).getDiameter());
	}
	
	@Test
	void testbounceBlock() {
		//bal1 raakt blok1 vanboven en blok4 langs rechts
		BallState newball1 = new BallState(new Point(0,0), 1, new Vector(0,0));
		assertEquals(newball1.getCenter(), bal1.bounceBlock(3).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceBlock(3).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceBlock(3).getDiameter());
		
		assertEquals(newball1.getCenter(), bal1.bounceBlock(4).getCenter());
		assertEquals(newball1.getVelocity(), bal1.bounceBlock(4).getVelocity());
		assertEquals(newball1.getDiameter(), bal1.bounceBlock(4).getDiameter());
		
		//bal2 raakt blok2 langs onder
		BallState newball2 = new BallState(new Point(1000,0), 33, new Vector(7,3));
		assertEquals(newball2.getCenter(), bal2.bounceBlock(1).getCenter());
		assertEquals(newball2.getVelocity(), bal2.bounceBlock(1).getVelocity());
		assertEquals(newball2.getDiameter(), bal2.bounceBlock(1).getDiameter());
		
		//bal3 raakt blok3 langs links
		BallState newball3 = new BallState(new Point(25000,15000), 1000, new Vector(-1,3));
		assertEquals(newball3.getCenter(), bal3.bounceBlock(2).getCenter());
		assertEquals(newball3.getVelocity(), bal3.bounceBlock(2).getVelocity());
		assertEquals(newball3.getDiameter(), bal3.bounceBlock(2).getDiameter());
	}
	
	@Test
	void testraaktrechthoek() {
		
		//walls
		
		Rechthoek bovenmuur = new Rechthoek(new Point(0,-1), new Point(50000,0));
		Rechthoek rechtsemuur = new Rechthoek(new Point(50000,0), new Point(50001,30000));
		Rechthoek ondermuur = new Rechthoek(new Point(0,30000), new Point(50000,30001));
		Rechthoek linksemuur = new Rechthoek(new Point(-1,0), new Point(0,30000));
		
		assertEquals(true, bal1.raaktRechthoek(bovenmuur, 1));
		assertEquals(false, bal1.raaktRechthoek(rechtsemuur, 2));
		assertEquals(false, bal1.raaktRechthoek(ondermuur, 3));
		assertEquals(true, bal1.raaktRechthoek(linksemuur, 4));
		
		assertEquals(true, bal2.raaktRechthoek(bovenmuur, 1));
		assertEquals(false, bal2.raaktRechthoek(rechtsemuur, 2));
		assertEquals(false, bal2.raaktRechthoek(ondermuur, 3));
		assertEquals(false, bal2.raaktRechthoek(linksemuur, 4));
		
		assertEquals(false, bal3.raaktRechthoek(bovenmuur, 1));
		assertEquals(false, bal3.raaktRechthoek(rechtsemuur, 2));
		assertEquals(false, bal3.raaktRechthoek(ondermuur, 3));
		assertEquals(false, bal3.raaktRechthoek(linksemuur, 4));
						
		assertNotEquals(null, bal1.raaktRechthoek(bovenmuur,1));
		
		//blocks

		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok1.getTopLeft(),blok1.getBottomRight()),1));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok1.getTopLeft(),blok1.getBottomRight()),2));
		assertEquals(true, bal1.raaktRechthoek(new Rechthoek(blok1.getTopLeft(),blok1.getBottomRight()),3));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok1.getTopLeft(),blok1.getBottomRight()),4));
		
		assertEquals(true, bal2.raaktRechthoek(new Rechthoek(blok2.getTopLeft(),blok2.getBottomRight()),1));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(blok2.getTopLeft(),blok2.getBottomRight()),2));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(blok2.getTopLeft(),blok2.getBottomRight()),3));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(blok2.getTopLeft(),blok2.getBottomRight()),4));
		
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(blok3.getTopLeft(),blok3.getBottomRight()),1));
		assertEquals(true, bal3.raaktRechthoek(new Rechthoek(blok3.getTopLeft(),blok3.getBottomRight()),2));
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(blok3.getTopLeft(),blok3.getBottomRight()),3));
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(blok3.getTopLeft(),blok3.getBottomRight()),4));
		
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok4.getTopLeft(),blok4.getBottomRight()),1));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok4.getTopLeft(),blok4.getBottomRight()),2));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(blok4.getTopLeft(),blok4.getBottomRight()),3));
		assertEquals(true, bal1.raaktRechthoek(new Rechthoek(blok4.getTopLeft(),blok4.getBottomRight()),4));
		
		//paddle
		
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(paddle1.getTopLeft(),paddle1.getBottomRight()), 1));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(paddle1.getTopLeft(),paddle1.getBottomRight()), 2));
		assertEquals(true, bal1.raaktRechthoek(new Rechthoek(paddle1.getTopLeft(),paddle1.getBottomRight()), 3));
		assertEquals(false, bal1.raaktRechthoek(new Rechthoek(paddle1.getTopLeft(),paddle1.getBottomRight()), 4));
		
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(paddle2.getTopLeft(),paddle2.getBottomRight()), 1));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(paddle2.getTopLeft(),paddle2.getBottomRight()), 2));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(paddle2.getTopLeft(),paddle2.getBottomRight()), 3));
		assertEquals(false, bal2.raaktRechthoek(new Rechthoek(paddle2.getTopLeft(),paddle2.getBottomRight()), 4));
		
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(paddle3.getTopLeft(),paddle3.getBottomRight()), 1));
		assertEquals(true, bal3.raaktRechthoek(new Rechthoek(paddle3.getTopLeft(),paddle3.getBottomRight()), 2));
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(paddle3.getTopLeft(),paddle3.getBottomRight()), 3));
		assertEquals(false, bal3.raaktRechthoek(new Rechthoek(paddle3.getTopLeft(),paddle3.getBottomRight()), 4));
		
	}
	
	@Test
	void testGetTopLeftBlock() {
		assertEquals(new Point(-1,0), blok1.getTopLeft());
		assertEquals(new Point(900,-10), blok2.getTopLeft());
		assertEquals(new Point(25500,14500), blok3.getTopLeft());
		assertEquals(new Point(-10,-10), blok4.getTopLeft());
	}
	
	@Test
	void testGetBottomRightBlock() {
		assertEquals(new Point(1,1),blok1.getBottomRight());
		assertEquals(new Point(1100,0),blok2.getBottomRight());
		assertEquals(new Point(27000,17000),blok3.getBottomRight());
		assertEquals(new Point(0,10),blok4.getBottomRight());
	}
	
	@Test
	void testGetTopLeftPaddle() {
		
	}
	
	@Test
	void testBreakoutState() {
		BreakoutState bs = new BreakoutState(new Ball[0], new BlockState[0], new Point(0, 0), new PaddleState(new Point(0, 0), new Vector(0, 0)));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(null,null,null,null) );
		
	}
	
	@Test 
	void nieuweRaakTest() {
		Ball testBall = new Ball(new Point(5000, 5000), 1000, new Vector(100, -100));
		Rect testRechthoek = new Rect(new Point(3000, 3000), new Point(7000, 4700));
		
		assertEquals(true, testBall.raaktRechthoek(testRechthoek, 1));
		
		Ball testHoekBall = new Ball(new Point(5000, 5000), 482, new Vector(-120, -209));
		Rect testHoekRechthoek = new Rect(new Point(3000, 3000), new Point(4900, 4900));
		assertTrue(testHoekBall.raaktRechthoek(testHoekRechthoek, 1));
	}
	
	
}
