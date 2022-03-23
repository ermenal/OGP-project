package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This class stores the current state of the breakout game
 * 
 * @invar This object's balls array is not {@code null} 
 *     | getBalls() != null
 * @invar This object's balls array has no elements that are {@code null}
 *     | Arrays.stream(getBalls()).allMatch(e -> e != null)
 * @invar This object's balls array's elements are entirely inside the field 
 *     | Arrays.stream(getBalls()).allMatch(e -> e.getCenter().getX() - e.getDiameter()/2 >= 0 && 
 *     |     e.getCenter().getX() + e.getDiameter()/2 <= getBottomRight().getX() && e.getCenter().getY() - e.getDiameter()/2 >= 0 && 
 *     |     e.getCenter().getY() + e.getDiameter()/2 <= getBottomRight().getY())
 *     
 * @invar This object's blocks array is not {@code null} 
 *     | getBlocks() != null
 * @invar This object's blocks array has no elements that are {@code null}
 *     | Arrays.stream(getBlocks()).allMatch(e -> e != null)
 * @invar This object's blocks array's elements are entirely inside the field 
 *     | Arrays.stream(getBlocks()).allMatch(e -> e.getTopLeft().getX() >= 0 && e.getBottomRight().getX() <= getBottomRight().getX() &&
 *     |  e.getTopLeft().getY() >= 0 && e.getBottomRight().getY() <= getBottomRight().getY())
 *     
 * @invar This object's bottomRight point is not {@code null}
 *     | getBottomRight() != null
 *     
 * @invar This object's paddle is entirely inside the field
 *     | getPaddle().getTopLeft().getX() >= 0 && getPaddle().getBottomRight().getX() <= getBottomRight().getX() && 
 *     |     getPaddle().getTopLeft().getY() >= 0 && getPaddle().getBottomRight().getY() <= getBottomRight().getY()
 */

public class BreakoutState {
	
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).noneMatch(e -> e == null)
	 * @invar | Arrays.stream(balls).noneMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX())
	 * @invar | Arrays.stream(balls).noneMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || e.getCenter().getY() - e.getDiameter()/2 < 0)
	 * 
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e == null)
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e.getTopLeft().getX() < 0 || e.getBottomRight().getX() > bottomRight.getX())
	 * @invar | Arrays.stream(blocks).noneMatch(e -> e.getTopLeft().getY() < 0 || e.getBottomRight().getY() > bottomRight.getY())
	 * 
	 * @invar | bottomRight != null
	 * 
	 * @invar | paddle != null
	 * @invar | paddle.getBottomRight().getX() <= bottomRight.getX() && paddle.getBottomRight().getY() <= bottomRight.getY() && paddle.getTopLeft().getX() >= 0 && paddle.getTopLeft().getY() >= 0 
	 * 
	 * @representationObject
	 */
	
	private BallState[] balls;
	private BlockState[] blocks;
	private final Point bottomRight;
	private PaddleState paddle;
	
	/**
	 * Initializes this object so that it stores the given balls, blocks, bottomRight point and paddle.
	 * 
	 * @throws IllegalArgumentException if the given balls array is null
	 * 	   | balls == null
	 * @throws IllegalArgumentException if any of the given balls array's elements are null
	 *     | Arrays.stream(balls).anyMatch(b -> b == null)
	 * @throws IllegalArgumentException if any of the given balls array's elements are outside of the field
	 * 	   | Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || e.getCenter().getY() - e.getDiameter()/2 < 0)
	 *     
	 * @throws IllegalArgumentException if the given blocks array is null
	 * 	   | blocks == null
	 * @throws IllegalArgumentException if any of the given blocks array's elements are null
	 *     | Arrays.stream(blocks).anyMatch(b -> b == null)
	 * @throws IllegalArgumentException if any of the given block array's elements are outside of the field    
	 *     | Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || e.getBottomRight().getX() > bottomRight.getX()) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || e.getBottomRight().getY() > bottomRight.getY())
	 *     
	 * @throws IllegalArgumentException if the given bottomRight is null
	 * 	   | blocks == null
	 * 
	 * @throws IllegalArgumentException if the given paddle is null
	 * 	   | paddle == null
	 * @throws IllegalArgumentException if the given paddle is outside of the field
	 *     | paddle.getBottomRight().getX() > bottomRight.getX() || paddle.getBottomRight().getY() > bottomRight.getY() || paddle.getTopLeft().getX() < 0 || paddle.getTopLeft().getY() < 0 
	 * 
	 * @inspects | balls, blocks
	 * 
	 * @post This object's BallState array's elements are equal to and in the same order as the given array of balls' elements
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].equals(balls[i]))
	 * 
	 * @post This object's BlockState array's elements are equal to and in the same order as the given array of blocks' elements
	 * 	   | IntStream.range(0, getBlocks().length).allMatch(i -> getBlocks()[i].equals(blocks[i]))
	 * 
	 * @post This object's bottom right Point has the same coordinates as the given bottomRight point
	 * 	   | bottomRight.equals(getBottomRight())
	 * 
	 * @post This object's paddle has the same center and size as the given paddle
	 *     | paddle.getCenter().equals(getPaddle().getCenter()) && getPaddle().getSize().equals(getPaddle().getSize())
	 */
	
	public BreakoutState(BallState[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		
		if (balls == null || blocks == null || bottomRight == null || paddle == null) {
			throw new IllegalArgumentException("BreakoutState argument can't be null");
		}
		if (Arrays.stream(balls).anyMatch(b -> b == null) || Arrays.stream(blocks).anyMatch(b -> b == null)) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are null");
		}
		
		if (Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || e.getCenter().getY() - e.getDiameter()/2 < 0) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || e.getBottomRight().getX() > bottomRight.getX()) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || e.getBottomRight().getY() > bottomRight.getY())) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are outside of the field");
		}
		
		if (paddle.getBottomRight().getX() > bottomRight.getX() || paddle.getBottomRight().getY() > bottomRight.getY() || paddle.getTopLeft().getX() < 0 || paddle.getTopLeft().getY() < 0) {
			throw new IllegalArgumentException("paddle should be inside of the field");
		}
		
		this.balls = balls.clone();
		this.blocks = blocks.clone();
		this.bottomRight = new Point(bottomRight.getX(), bottomRight.getY());
		this.paddle = new PaddleState(paddle.getCenter(), paddle.getSize());
	}
	
	
	/**
	 * returns a new array containing all the balls
	 * @creates | result
	 * @post the result is not {@code null} 
	 *    | result != null
	 * @post the result's elements are not {@code null}
	 *    | Arrays.stream(result).noneMatch(e -> e == null)
	 */
	public BallState[] getBalls() {
		return balls.clone();
	}

	/**
	 * returns a new array containing all the blocks 
	 * 
	 * @creates | result
	 * @post the result is not {@code null} 
	 *    | result != null
	 * @post the result's elements are not {@code null}
	 *    | Arrays.stream(result).noneMatch(e -> e == null)
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	
	/**
	 * returns the paddle 
	 * 
	 * @creates | result
	 * @post | result != null
	 */
	public PaddleState getPaddle() {
		return new PaddleState(paddle.getCenter(), paddle.getSize());
	}

	/**
	 * returns the coordinates of the bottom right of the field
	 * 
	 * @creates | result
	 * @post | result != null
	 */
	
	public Point getBottomRight() {
		return new Point(bottomRight.getX(), bottomRight.getY());
	}

	public void tick(int paddleDir) {
		moveAllBalls();
		
		raaktWallLinks();
		raaktWallBoven();
		raaktWallRechts();
		raaktOnder();
		
		raaktBlockOnder();
		raaktBlockLinks();
		raaktBlockBoven();
		raaktBlockRechts();
		
		raaktPaddleLinks(paddleDir);
		raaktPaddleBoven(paddleDir);
		raaktPaddleRechts(paddleDir);
	}
	
	/**
	 * moves all the balls currently in the game according to their current velocity
	 *    
	 * @mutates | this, getBalls()
	 * @inspects | getBottomRight()
	 * 
	 * @post This object's balls array's number of elements equals its old number of elements
	 *     | getBalls().length == old(getBalls()).length
	 * @post All balls in this object's balls array have been moved according to their current velocity, and none have gone outside of the field
	 *     | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter().plus(old(getBalls())[i].getVelocity())) || 
	 *     | getBalls()[i].getCenter().getX() + getBalls()[i].getDiameter()/2 == getBottomRight().getX() || 
	 *     | getBalls()[i].getCenter().getX() - getBalls()[i].getDiameter()/2 == 0 || 
	 *     | getBalls()[i].getCenter().getY() + getBalls()[i].getDiameter()/2 == getBottomRight().getY() ||
	 *     | getBalls()[i].getCenter().getY() - getBalls()[i].getDiameter()/2 == 0)
	 * 
	 */
	
	public void moveAllBalls() {
		ArrayList<BallState> newBalls = new ArrayList<BallState>();
		for (BallState ball: balls) {
			newBalls.add(ball.moveBall(getBottomRight()));
		}
		balls = newBalls.toArray(new BallState[] {});
	}
	
	/**
	 * Checks for a collision between a ball and the left wall, and if there is a collision, bounces the ball off the wall
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getBottomRight()
	 * 
	 * @post This object's balls array's number of elements equals its old number of elements
	 *     | getBalls().length == old(getBalls()).length
	 *     
	 * @post If a ball did not touch the left wall, it remained the exact same object at the exact same index they were before in the balls array. 
	 * 		 If a ball did touch the left wall, its velocity gets mirrored according to the wall by calling bounceWall(1) on the ball , while its center remains unchanged
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> (getBalls()[i].equals(old(getBalls())[i])) || 
	 *     |     (getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(1).getVelocity())))
	 * 
	 */
	public void raaktWallLinks() {
		for (BallState ball: balls) {
			if (ball.raaktLinks()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(1));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
		}
	}
	
	/**
	 * Checks for a collision beween a ball and the top wall, and if there is a collision, bounces the ball off the wall
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getBottomRight()
	 * 
	 * @post This object's balls array's number of elements equals its old number of elements
	 *     | getBalls().length == old(getBalls()).length
	 * @post If a ball did not touch the top wall, it remained the exact same object at the exact same index they were before in the balls array. 
	 * 		 If a ball did touch the top wall, its velocity gets mirrored according to the wall by calling bounceWall(2) on the ball , while its center remains unchanged
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> (getBalls()[i].equals(old(getBalls())[i])) || 
	 *     |     (getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(2).getVelocity())))
	 * 
	 */
	
	public void raaktWallBoven() {
		for (BallState ball: balls) {
			if (ball.raaktBoven()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(2));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
		}
	}
	
	/**
	 * Checks for a collision beween a ball and the top wall, and if there is a collision, bounces the ball off the wall
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getBottomRight()
	 * 
	 * @post This object's balls array's number of elements equals its old number of elements
	 *     | getBalls().length == old(getBalls()).length
	 * @post If a ball did not touch the right wall, it remained the exact same object at the exact same index they were before in the balls array. 
	 * 		 If a ball did touch the right wall, its velocity gets mirrored according to the wall by calling bounceWall(3) on the ball , while its center remains unchanged
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> (getBalls()[i].equals(old(getBalls())[i])) || 
	 *     |     (getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(3).getVelocity())))
	 * 
	 */
	
	public void raaktWallRechts() {
		for (BallState ball: balls) {
			if (ball.raaktRechts(bottomRight)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(3));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
		}
	}
	
	/**
	 * Checks for a collision between a ball and the bottom of the field, and if there is a collision, removes the ball from the game
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getBottomRight()
	 * 
	 * @post This object's balls array's number of elements is equal to or less than its old number of elements
	 * 	   | getBalls().length <= old(getBalls()).length
	 * @post Any ball that collided with the bottom of the field, got removed from the object's balls array. 
	 *       If a ball did not collide with the bottom of the field, it stays in the object's balls array.
	 *     |  IntStream.range(0, getBalls().length).noneMatch(i -> getBalls()[i].raaktOnder(getBottomRight()))
	 *     
	 */
	
	public void raaktOnder() {
		balls = Arrays.stream(balls).filter(e -> !(e.raaktOnder(getBottomRight()))).toArray(BallState[]::new);
	}
	
	/**
	 * Checks for a collision between a ball and a block's bottom side. If there is a collision, the block gets removed from the game and the ball gets bounced off the block's bottom side.
	 * 
	 * @mutates | this, getBalls(), getBlocks()
	 * 
	 * @post This object's balls array's number of elements is equal to or less than its old number of elements. 
	 * 	   | getBalls().length <= old(getBalls()).length
	 * @post This object's blocks array's  number of elements is equal to or less than its old number of elements
	 * 	   | getBlocks().length <= old(getBlocks()).length
	 * 
	 */
	
	public void raaktBlockOnder() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktBlockOnder(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 1));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	public void raaktBlockLinks() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktBlockLeft(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 2));
						}
					}
					balls = newBalls.toArray(new BallState[] {});		
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	public void raaktBlockBoven() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktBlockBoven(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 3));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	public void raaktBlockRechts() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktBlockRight(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 4));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	public void raaktPaddleLinks(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktPaddleLinks(paddle)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 1));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
		}
	}
	
	public void raaktPaddleBoven(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktPaddleBoven(paddle)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 2));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
		}
	}
	
	public void raaktPaddleRechts(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktPaddleRechts(paddle)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 3));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			
		}
	}
	
	
	
	

	public void movePaddleRight() {
		paddle = paddle.movePaddleRightBy(getBottomRight());
	}

	public void movePaddleLeft() {
		paddle = paddle.movePaddleLeftBy();
	}
	
	public boolean isWon() {
		if (blocks.length == 0 && balls.length > 0) {
		return true;
		}
		return false;
	}

	public boolean isDead() {
		if (balls.length == 0) {
			return true;
		}
		return false;
	}
}
