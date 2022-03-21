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
		center2 = new Point(1000,20);
		diameter2 = 33;
		bal2 = new BallState(center2, diameter2, velocity2);
		
		velocity3 = new Vector(1,3);
		center3 = new Point(25000,15000);
		diameter3 = 1000;
		bal3 = new BallState(center3, diameter3, velocity3);
		
		
	}

	@Test
	void testBallSate() {
		assertEquals(new Point(0,0), bal1.getCenter());
		assertNotEquals(new Point(0,1), bal1.getCenter());
		
	}

}
