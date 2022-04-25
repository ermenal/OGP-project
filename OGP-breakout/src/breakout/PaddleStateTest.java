package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

class PaddleStateTest {

	private Point center1 = new Point(1000, 1000);
	
	private Point ballCenter1 = new Point(2000, 1500);
	private Vector ballVelocity1 = new Vector(5, 5);
	private int ballDiameter1 = 5;
	
	private Point ballCenter2 = new Point(30000, 30000);
	private Vector ballVelocity2 = new Vector(2, -10);
	private int ballDiameter2 = 5;
	
	private Point ballCenter3 = new Point(10000, 15000);
	private Vector ballVelocity3 = new Vector(-13, 1);
	private int ballDiameter3 = 5;
	
	private PaddleState paddle1;
	private Ball ball1;
	private Ball ball2;
	private Ball ball3;
	
	
	
	@BeforeEach
	void setUp() {
		paddle1 = new PaddleState(center1, 2);
		ball1 = new NormalBall(ballCenter1, ballDiameter1, ballVelocity1);
		ball2 = new NormalBall(ballCenter2, ballDiameter2, ballVelocity2);
		ball3 = new NormalBall(ballCenter3, ballDiameter3, ballVelocity3);
		
		
	}
	
	
	@Test
	void ballHitPaddleTest() {
		assertThrows(AssertionError.class, () -> paddle1.hitPaddle(null, ball1));
		
		Ball[] balls = {ball1, ball2, ball3};
		
		assertEquals(5, paddle1.hitPaddle(balls, ball1).length);
		
	
	}

}
