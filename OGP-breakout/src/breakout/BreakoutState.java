package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This class stores the current state of the breakout game
 * 
 * @invar This object's balls array is not {@code null} 
 *     	| getBalls() != null
 * @invar This object's balls array has no elements that are {@code null}
 *     	| Arrays.stream(getBalls()).noneMatch(e -> e == null)
 * @invar This object's balls array's elements are entirely inside the field 
 *     	| Arrays.stream(getBalls()).allMatch(e ->
 *     	| e.getCenter().getX() - e.getDiameter()/2 >= 0 && 
 *     	| e.getCenter().getX() + e.getDiameter()/2 <= getBottomRight().getX() && 
 *     	| e.getCenter().getY() - e.getDiameter()/2 >= 0 && 
 *     	| e.getCenter().getY() + e.getDiameter()/2 <= getBottomRight().getY())
 *     
 * @invar This object's blocks array is not {@code null} 
 *     	| getBlocks() != null
 * @invar This object's blocks array has no elements that are {@code null}
 *     	| Arrays.stream(getBlocks()).noneMatch(e -> e == null)
 * @invar This object's blocks array's elements are entirely inside the field 
 *     	| Arrays.stream(getBlocks()).allMatch(e -> 
 *     	| e.getTopLeft().getX() >= 0 && 
 *     	| e.getBottomRight().getX() <= getBottomRight().getX() &&
 *     	| e.getTopLeft().getY() >= 0 && 
 *     	| e.getBottomRight().getY() <= getBottomRight().getY())
 *     
 * @invar This object's bottomRight point is not {@code null}
 *     	| getBottomRight() != null
 *     
 * @invar This object's paddle is not {@code null}
 * 		| getPaddle() != null
 * @invar This object's paddle is entirely inside the field
 *     	| getPaddle().getTopLeft().getX() >= 0 && 
 *     	| getPaddle().getBottomRight().getX() <= getBottomRight().getX() && 
 *     	| getPaddle().getTopLeft().getY() >= 0 && 
 *     	| getPaddle().getBottomRight().getY() <= getBottomRight().getY()
 *     
 * @invar The maximum amount of time a ball may be supercharged for after hitting a powerup block is greater than or equal to 0 milliseconds.
 * 		| getMaxSuperchargedTime() >= 0
 */

public class BreakoutState {
	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e == null)
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e.getTopLeft().getX() < 0 || 
	 *        | e.getBottomRight().getX() > bottomRight.getX())
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e.getTopLeft().getY() < 0 || 
	 *        | e.getBottomRight().getY() > bottomRight.getY())
	 * 
	 * @invar | bottomRight != null
	 * 
	 * @invar | paddle != null
	 * @invar | paddle.getBottomRight().getX() <= bottomRight.getX() && 
	 *        | paddle.getBottomRight().getY() <= bottomRight.getY() && 
	 *        | paddle.getTopLeft().getX() >= 0 && 
	 *        | paddle.getTopLeft().getY() >= 0 
	 *        
	 * @invar | maxSuperchargedTime >= 0
	 * 
	 * @representationObject
	 */
	
	private Ball[] balls;
	/** @representationObject */
	private BlockState[] blocks;
	private final Point bottomRight;
	private PaddleState paddle;
	private final int maxSuperchargedTime;
	
	public final static int MAX_ELAPSED_TIME = 20;
	
	/**
	 * Initializes this object so that it stores the given balls, blocks, bottomRight point and paddle.
	 * 
	 * @throws IllegalArgumentException if the given balls array is null
	 * 	    | balls == null
	 * @throws IllegalArgumentException if any of the given balls array's elements are null
	 *      | Arrays.stream(balls).anyMatch(b -> b == null)
	 * @throws IllegalArgumentException if any of the given balls array's elements are outside of the field
	 * 	    | Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || 
	 *      | e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || 
	 *      | Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || 
	 *      | e.getCenter().getY() - e.getDiameter()/2 < 0)
	 *     
	 * @throws IllegalArgumentException if the given blocks array is null
	 * 	    | blocks == null
	 * @throws IllegalArgumentException if any of the given blocks array's elements are null
	 *      | Arrays.stream(blocks).anyMatch(b -> b == null)
	 * @throws IllegalArgumentException if any of the given block array's elements are outside of the field    
	 *      | Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || 
	 *      | e.getBottomRight().getX() > bottomRight.getX()) || 
	 *      | Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || 
	 *      | e.getBottomRight().getY() > bottomRight.getY())
	 *     
	 * @throws IllegalArgumentException if the given bottomRight is null
	 * 	    | blocks == null
	 * 
	 * @throws IllegalArgumentException if the given paddle is null
	 * 	    | paddle == null
	 * @throws IllegalArgumentException if the given paddle is outside of the field
	 *      | paddle.getBottomRight().getX() > bottomRight.getX() || 
	 *      | paddle.getBottomRight().getY() > bottomRight.getY() || 
	 *      | paddle.getTopLeft().getX() < 0 || 
	 *      | paddle.getTopLeft().getY() < 0 
	 *     
	 * @throws IllegalArgumentException if the given maxSuperchargedTime is smaller than 0
	 * 		| maxSuperchargedTime < 0
	 * 
	 * @inspects | balls, blocks
	 * 
	 * @post This object's {@code Ball} array's elements are equal to and in the same order as the given array of balls' elements
	 * 	    | IntStream.range(0, getBalls().length).
	 * 	    | 	allMatch(i -> getBalls()[i].equals(balls[i]))
	 * 
	 * @post This object's BlockState array's elements are equal to and in the same order as the given array of blocks' elements
	 * 	    | IntStream.range(0, getBlocks().length).
	 * 	    |	allMatch(i -> getBlocks()[i].equals(blocks[i]))
	 * 
	 * @post This object's bottom right Point has the same coordinates as the given bottomRight point
	 * 	    | bottomRight.equals(getBottomRight())
	 * 
	 * @post This object's paddle has the same center and size as the given paddle
	 *      | paddle.getCenter().equals(getPaddle().getCenter()) && 
	 *      | getPaddle().getSize().equals(getPaddle().getSize())
	 * @post the maximum amount of time a ball may be supercharged for after hitting a powerup block is {@code maxSuperchargedTime}.
	 * 		| getMaxSuperchargedTime() == maxSuperchargedTime
	 */
	
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle, int maxSuperchargedTime) {
		
		if (balls == null || blocks == null || bottomRight == null || paddle == null) {
			throw new IllegalArgumentException("BreakoutState argument can't be null");
		}
		if (Arrays.stream(balls).anyMatch(b -> b == null) || Arrays.stream(blocks).anyMatch(b -> b == null)) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are null");
		}
		
		if (Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || e.getCenter().getY() - e.getDiameter()/2 < 0) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || e.getBottomRight().getX() > bottomRight.getX()) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || e.getBottomRight().getY() > bottomRight.getY())) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are outside of the field");
		}
		
		if (paddle.getBottomRight().getX() > bottomRight.getX() || paddle.getBottomRight().getY() > bottomRight.getY() || paddle.getTopLeft().getX() < 0 || paddle.getTopLeft().getY() < 0)
			throw new IllegalArgumentException("paddle should be inside of the field");
		
		if (maxSuperchargedTime < 0)
			throw new IllegalArgumentException("The maximum time a ball is allowed to be supercharged for after hitting a powerup block should be greater than or equal to 0");
		
		this.balls = balls.clone();
		this.blocks = blocks.clone();
		this.bottomRight = bottomRight;
		this.paddle = paddle;
		this.maxSuperchargedTime = maxSuperchargedTime;
		
	}
	
	/**
	 * Returns a new array containing all the balls
	 * 
	 * @creates | result
	 */
	
	public Ball[] getBalls() {
		return balls.clone();
	}
	
	/**
	 * Returns a new array containing all the blocks 
	 * 
	 * @creates | result
	 */

	public BlockState[] getBlocks() {
		return blocks.clone();
	}
	
	/**
	 * Returns the paddle 
	 */
	
	public PaddleState getPaddle() {
		return paddle;
	}
	
	/**
	 * Returns the coordinates of the bottom right of the field
	 * 
	 * @immutable This object is associated with the same bottom right point throughout its lifetime
	 */

	public Point getBottomRight() {
		return bottomRight;
	}
	
	/**
	 * Returns the maximum amount of time a ball can be supercharged for after hitting a powerup block. 
	 * 
	 * @immutable This value is the same throughout its lifetime
	 */
	
	public int getMaxSuperchargedTime() {
		return maxSuperchargedTime;
	}

	/**
	 * Calls all methods nescessary for moving the balls and handling collisions.
	 * 
	 * @mutates | this
	 */
	
	public void tick(int paddleDir, int elapsedTime) {
		
		if (elapsedTime > MAX_ELAPSED_TIME) {
			elapsedTime = MAX_ELAPSED_TIME;
		}
		
			moveAllBalls(elapsedTime);
			
			superchargedTimeHandler(elapsedTime);
		
			wallCollisionHandler();
			
			lowerWallCollisionHandler();
			
			blockCollisionHandler();
			
			paddleCollisionHandler(paddleDir);
	}
	
	private void moveAllBalls(int elapsedTime) {		
		for (int i=0; i<balls.length; i++) {
			balls[i].moveBall(getBottomRight(), elapsedTime);
		}
	}
	
	private void superchargedTimeHandler(int elapsedTime) {
		for (int i=0;i<balls.length;i++) {
			balls[i] = balls[i].superchargedTimeHandler(elapsedTime, maxSuperchargedTime);
		}
	}
	
	private void wallCollisionHandler() {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(new Point(-1, 0), new Point(0, bottomRight.getY())), 4)) {
				ball.bounceWall(1);
				continue;
			}
			if (ball.raaktRechthoek(new Rect(new Point(0, -1), new Point(getBottomRight().getX(), 0)), 1)) {
				ball.bounceWall(2);
				continue;
			}
			if (ball.raaktRechthoek(new Rect(new Point(bottomRight.getX(), 0), new Point(bottomRight.getX() + 1, bottomRight.getY())), 2)) {
				ball.bounceWall(3);
				continue;
			}
		}
	}  
	
	private void lowerWallCollisionHandler() {
		balls = Arrays.stream(balls).filter(e -> !(e.raaktRechthoek(new Rect(new Point(0, bottomRight.getY()), new Point(bottomRight.getX(), bottomRight.getY()+1)), 3))).toArray(Ball[]::new);
	}
	
	private void blockCollisionHandler() {
		for (int j=0; j < balls.length;j++) {
			BlockState[] tempBlocks = getBlocks();
			for (int i=0; i<tempBlocks.length; i++) {
				boolean geraakt = false;
				Rect blockRechthoek = new Rect(tempBlocks[i].getTopLeft(), tempBlocks[i].getBottomRight());
				if (balls[j].raaktRechthoek(blockRechthoek, 1)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 2)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 3)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 4)) {
					geraakt = true;
				}
				if (geraakt) {
				balls[j].hitBlock(blockRechthoek, tempBlocks[i].getsDestroyedOnCollision());
				
				paddle = tempBlocks[i].specialBlockHandler(paddle);
				balls[j] = tempBlocks[i].specialBlockHandler(balls[j]);
				tempBlocks[i] = tempBlocks[i].specialBlockHandler();
				}
			}
			blocks = Arrays.stream(tempBlocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	private void paddleCollisionHandler(int paddleDir) {
		Rect paddleRect = new Rect(paddle.getTopLeft(), paddle.getBottomRight());
		for (Ball ball: getBalls()) {
			boolean geraakt = false;
			if (ball.raaktRechthoek(paddleRect, 3)) {
				ball.bouncePaddle(paddleDir, 2);
				geraakt = true;
			}
			if (ball.raaktRechthoek(paddleRect, 2)) {
				ball.bouncePaddle(paddleDir, 1);
				geraakt = true;
			}
			if (ball.raaktRechthoek(paddleRect, 4)) {
				ball.bouncePaddle(paddleDir, 3);
				geraakt = true;
			}
			if (geraakt) {
				balls = Arrays.stream(paddle.hitPaddle(getBalls(), ball)).filter(b -> b != null).toArray(Ball[]::new);
				paddle = new PaddleState(paddle.getCenter(), Math.max(0, paddle.getAmountOfReplications()-1));
			}
		}
	} 
	
	public void movePaddleRight(int elapsedTime) {
		paddle = paddle.movePaddleRight(getBottomRight(), elapsedTime);
	}
	
	public void movePaddleLeft(int elapsedTime) {
		paddle = paddle.movePaddleLeft(elapsedTime);
	}
	
	public boolean isWon() {
		return blocks.length == 0 && balls.length > 0;
	}

	public boolean isDead() {
		return balls.length == 0;
	}
}
