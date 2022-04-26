package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BallTest {
	
	Ball normalBall1;
	Ball superchargedBall1;
	
	Ball normalBall2;
	Ball superchargedBall2;
	
	Ball normalBall3;
	Ball superchargedBall3;
	
	Point center1;
	int diameter1;
	Vector velocity1;
	
	Point center2;
	int diameter2;
	Vector velocity2;
	
	Point center3;
	int diameter3;
	Vector velocity3;
	
	
	@BeforeEach
	void setUp() {
		BreakoutFacade facade = new BreakoutFacade();
		
		center1 = new Point(25000, 25000);
		diameter1 = 1000;
		velocity1 = new Vector(10, 5);
		
		normalBall1 = facade.createNormalBall(center1, diameter1, velocity1);
		superchargedBall1 = facade.createSuperchargedBall(center1, diameter1, velocity1, 0);
	
	}	
	
	@Test
	void testBalls1() {
		assertTrue(normalBall1.getCenter().equals(center1));
		assertTrue(normalBall1.getDiameter() == diameter1);
		assertTrue(normalBall1.getVelocity().equals(velocity1));
		
		assertTrue(superchargedBall1.getCenter().equals(center1));
		assertTrue(superchargedBall1.getDiameter() == diameter1);
		assertTrue(superchargedBall1.getVelocity().equals(velocity1));
		
		assertTrue(normalBall1.equals(new NormalBall(center1, diameter1, velocity1)));
		assertFalse(normalBall1.equals(superchargedBall1));
		
		assertEquals(Color.WHITE, normalBall1.getColor());
		assertEquals(Color.GREEN, superchargedBall1.getColor());
		
		assertFalse(normalBall1.equals(null));
		
	}
	
	
}
