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
 *     | e.getCenter().getX() + e.getDiameter()/2 <= getBottomRight().getX() && 
 *     | e.getCenter().getY() - e.getDiameter()/2 >= 0 && 
 *     | e.getCenter().getY() + e.getDiameter()/2 <= getBottomRight().getY())
 *     
 * @invar This object's blocks array is not {@code null} 
 *     | getBlocks() != null
 * @invar This object's blocks array has no elements that are {@code null}
 *     | Arrays.stream(getBlocks()).allMatch(e -> e != null)
 * @invar This object's blocks array's elements are entirely inside the field 
 *     | Arrays.stream(getBlocks()).allMatch(e -> e.getTopLeft().getX() >= 0 && 
 *     | e.getBottomRight().getX() <= getBottomRight().getX() &&
 *     | e.getTopLeft().getY() >= 0 && 
 *     | e.getBottomRight().getY() <= getBottomRight().getY())
 *     
 * @invar This object's bottomRight point is not {@code null}
 *     | getBottomRight() != null
 *     
 * @invar This object's paddle is entirely inside the field
 *     | getPaddle().getTopLeft().getX() >= 0 && 
 *     | getPaddle().getBottomRight().getX() <= getBottomRight().getX() && 
 *     | getPaddle().getTopLeft().getY() >= 0 && 
 *     | getPaddle().getBottomRight().getY() <= getBottomRight().getY()
 */

public class BreakoutState {
	
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).noneMatch(e -> e == null)
	 * @invar | Arrays.stream(balls).noneMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 ||
	 *        | e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX())
	 * @invar | Arrays.stream(balls).noneMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() ||
	 *        | e.getCenter().getY() - e.getDiameter()/2 < 0)
	 * 
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
	 * @representationObject
	 */
	
	private BallState[] balls;
	/** @representationObject */
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
	 * 	   | Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || 
	 *     | e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || 
	 *     | Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || 
	 *     | e.getCenter().getY() - e.getDiameter()/2 < 0)
	 *     
	 * @throws IllegalArgumentException if the given blocks array is null
	 * 	   | blocks == null
	 * @throws IllegalArgumentException if any of the given blocks array's elements are null
	 *     | Arrays.stream(blocks).anyMatch(b -> b == null)
	 * @throws IllegalArgumentException if any of the given block array's elements are outside of the field    
	 *     | Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || 
	 *     | e.getBottomRight().getX() > bottomRight.getX()) || 
	 *     | Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || 
	 *     | e.getBottomRight().getY() > bottomRight.getY())
	 *     
	 * @throws IllegalArgumentException if the given bottomRight is null
	 * 	   | blocks == null
	 * 
	 * @throws IllegalArgumentException if the given paddle is null
	 * 	   | paddle == null
	 * @throws IllegalArgumentException if the given paddle is outside of the field
	 *     | paddle.getBottomRight().getX() > bottomRight.getX() || 
	 *     | paddle.getBottomRight().getY() > bottomRight.getY() || 
	 *     | paddle.getTopLeft().getX() < 0 || 
	 *     | paddle.getTopLeft().getY() < 0 
	 * 
	 * @inspects | balls, blocks
	 * 
	 * @post This object's BallState array's elements are equal to and in the same order as the given array of balls' elements
	 * 	   | IntStream.range(0, getBalls().length).
	 * 	   | 	allMatch(i -> getBalls()[i].equals(balls[i]))
	 * 
	 * @post This object's BlockState array's elements are equal to and in the same order as the given array of blocks' elements
	 * 	   | IntStream.range(0, getBlocks().length).
	 * 	   |	allMatch(i -> getBlocks()[i].equals(blocks[i]))
	 * 
	 * @post This object's bottom right Point has the same coordinates as the given bottomRight point
	 * 	   | bottomRight.equals(getBottomRight())
	 * 
	 * @post This object's paddle has the same center and size as the given paddle
	 *     | paddle.getCenter().equals(getPaddle().getCenter()) && 
	 *     | getPaddle().getSize().equals(getPaddle().getSize())
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
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	
	/**
	 * Returns a new array containing all the balls
	 * 
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
	 * Returns a new array containing all the blocks 
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
	 * Returns the paddle 
	 * 
	 * @post the result is not {@code null}
	 *     | result != null
	 */
	public PaddleState getPaddle() {
		return paddle;
	}

	/**
	 * Returns the coordinates of the bottom right of the field
	 * 
	 * @post the result is not {@code null}
	 *     | result != null
	 * 
	 * @immutable This object is associated with the same bottom right point throughout its lifetime
	 */
	
	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * Calls all methods nescessary for moving the balls and collisions
	 * 
	 * @mutates | this, getBalls(), getBlocks()
	 */
	
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
	 * Moves all the balls currently in the game according to their current velocity
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
	 *     | 		(getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(1).getVelocity())))
	 * 
	 */
	public void raaktWallLinks() {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(new Point(-1, 0), new Point(0, bottomRight.getY())), 4)) {
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
	 *     |		(getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(2).getVelocity())))
	 * 
	 */
	
	public void raaktWallBoven() {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(new Point(0, -1), new Point(getBottomRight().getX(), 0)), 1)) {
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
	 *     |		(getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceWall(3).getVelocity())))
	 * 
	 */
	
	public void raaktWallRechts() {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(new Point(bottomRight.getX(), -1), new Point(bottomRight.getX() + 1, bottomRight.getY())), 2)) {
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
	 *     | IntStream.range(0, getBalls().length).noneMatch(i -> getBalls()[i].
	 *     |	raaktRechthoek(new Rechthoek(new Point(0, getBottomRight().getY()), new Point(getBottomRight().getX(), getBottomRight().getY()+1)), 3))
	 *     
	 */
	
	public void raaktOnder() {
		balls = Arrays.stream(balls).filter(e -> !(e.raaktRechthoek(new Rechthoek(new Point(0, bottomRight.getY()), new Point(bottomRight.getX(), bottomRight.getY()+1)), 3))).toArray(BallState[]::new);
	}
	
	/**
	 * Checks for a collision between a ball and a block's bottom side. If there is a collision, the block gets removed from the game and the ball gets bounced off the block's bottom side.
	 * 
	 * @mutates | this, getBalls(), getBlocks()
	 * 
	 * @post This object's balls array's number of elements is equal to its old number of elements. 
	 * 	   | getBalls().length == old(getBalls()).length 
	 * @post This object's blocks array's  number of elements is equal to or less than its old number of elements
	 * 	   | getBlocks().length <= old(getBlocks()).length 
	 * @post All blocks that were hit by a ball on their bottom side get removed from the game
	 * 	   | Arrays.stream(getBlocks()).noneMatch(e -> Arrays.stream(getBalls()).anyMatch(b -> b.raaktRechthoek(new Rechthoek(e.getTopLeft(), e.getBottomRight()), 1)))
	 * @post All balls remained unchanged, unless the ball bounced off a block's bottom side. 
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() &&
	 * 	   |    getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 * 	   |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) ||
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceBlock(1).getVelocity())))
	 */
	
	public void raaktBlockOnder() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktRechthoek(new Rechthoek(block.getTopLeft(), block.getBottomRight()), 1)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(1));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	/**
	 * Checks for a collision between a ball and a block's left side. If there is a collision, the block gets removed from the game and the ball gets bounced off the block's left side.
	 * 
	 * @mutates | this, getBalls(), getBlocks()
	 * 
	 * @post This object's balls array's number of elements is equal to its old number of elements. 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's blocks array's  number of elements is equal to or less than its old number of elements
	 * 	   | getBlocks().length <= old(getBlocks()).length
	 * @post All blocks that were hit by a ball on their left side get removed from the game
	 * 	   | Arrays.stream(getBlocks()).noneMatch(e -> Arrays.stream(getBalls()).anyMatch(b -> b.raaktRechthoek(new Rechthoek(e.getTopLeft(), e.getBottomRight()), 2)))
	 * @post All balls remained unchanged, unless the ball bounced off a block's left side. 
	 * 	   | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() &&
	 * 	   |    getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 * 	   |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) ||
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceBlock(2).getVelocity())))
	 */
	
	public void raaktBlockLinks() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktRechthoek(new Rechthoek(block.getTopLeft(), block.getBottomRight()), 2)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(2));
						}
					}
					balls = newBalls.toArray(new BallState[] {});		
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	/**
	 * Checks for a collision between a ball and a block's top side. If there is a collision, the block gets removed from the game and the ball gets bounced off the block's top side.
	 * 
	 * @mutates | this, getBlocks(), getBalls()
	 * 
	 * @post This object's balls array's number of elements is equal to its old number of elements. 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's blocks array's  number of elements is equal to or less than its old number of elements
	 * 	   | getBlocks().length <= old(getBlocks()).length
	 * @post Any block that was hit by a ball on its top side got removed from the game
	 * 	   | Arrays.stream(getBlocks()).noneMatch(e -> Arrays.stream(getBalls()).anyMatch(b -> b.raaktRechthoek(new Rechthoek(e.getTopLeft(), e.getBottomRight()), 3)))
	 * @post All balls remained unchanged, unless the ball bounced off a block's top side. 
	 *     |IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() &&
	 * 	   |    getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 * 	   |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) ||
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceBlock(3).getVelocity())))
	 */
	
	public void raaktBlockBoven() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktRechthoek(new Rechthoek(block.getTopLeft(), block.getBottomRight()), 3)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(3));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	/**
	 * Checks for a collision between a ball and a block's right side. If there is a collision, the block gets removed from the game and the ball gets bounced off the block's right side.
	 * 
	 * @mutates | this, getBlocks(), getBalls()
	 * 
	 * @post This object's balls array's number of elements is equal to its old number of elements. 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's blocks array's  number of elements is equal to or less than its old number of elements
	 * 	   | getBlocks().length <= old(getBlocks()).length
	 * @post Any block that was hit by a ball on its right side got removed from the game
	 * 	   | Arrays.stream(getBlocks()).noneMatch(e -> Arrays.stream(getBalls()).anyMatch(b -> b.raaktRechthoek(new Rechthoek(e.getTopLeft(), e.getBottomRight()), 4)))
	 * @post All balls remained unchanged, unless the ball bounced off a block's right side. 
	 *     |IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() &&
	 * 	   |    getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) && 
	 * 	   |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) ||
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bounceBlock(4).getVelocity())))
	 */
	
	public void raaktBlockRechts() {
		for (BallState ball: balls) {
			for (BlockState block: blocks) {
				if (ball.raaktRechthoek(new Rechthoek(block.getTopLeft(), block.getBottomRight()), 4)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(4));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					blocks = Arrays.stream(blocks).filter(b -> b != block).toArray(BlockState[]::new);
				}
			}
		}
	}
	
	/**
	 * Checks for a collision between a ball and the paddle's left side. If there is a collision, the ball gets bounced off the paddle's left side
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getPaddle()
	 * 
	 * @post This object's balls array's number of elements has remained the same 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's paddle has remained the exact same instance
	 *     | getPaddle().equals(old(getPaddle()))
	 * @post This object's balls have remained unchanged, unless they bounced off the left side of the paddle, in which case the velocity has changed
	 *     | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) &&
	 *     |    getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() && 
	 *     |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) || 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bouncePaddle(getPaddle(), paddleDir, 1).getVelocity())))
	 */
	
	public void raaktPaddleLinks(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(paddle.getTopLeft(), paddle.getBottomRight()), 2)) {
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
	
	/**
	 * Checks for a collision between a ball and the paddle's top side. If there is a collision, the ball gets bounced off the paddle's top side
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getPaddle()
	 * 
	 * @post This object's balls array's number of elements has remained the same 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's paddle has remained the exact same instance
	 *     | getPaddle().equals(old(getPaddle())) 
	 * @post This object's balls have remained unchanged, unless they bounced off the top side of the paddle, in which case the velocity has changed
	 *     | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) &&
	 *     |    getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() && 
	 *     |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) || 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bouncePaddle(getPaddle(), paddleDir, 2).getVelocity())))
	 */
	
	public void raaktPaddleBoven(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(paddle.getTopLeft(), paddle.getBottomRight()), 3)) {
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
	
	/**
	 * Checks for a collision between any ball and the paddle's right side. If a collision is detected, the ball gets bounced off the paddle's right side
	 * 
	 * @mutates | this, getBalls()
	 * @inspects | getPaddle()
	 * 
	 * @post This object's balls array's number of elements has remained the same 
	 * 	   | getBalls().length == old(getBalls()).length
	 * @post This object's paddle has remained the exact same instance
	 *     | getPaddle().equals(old(getPaddle())) 
	 * @post This object's balls have remained unchanged, unless they bounced off the right side of the paddle, in which case the velocity has changed
	 *     | IntStream.range(0, getBalls().length).allMatch(i -> getBalls()[i].getCenter().equals(old(getBalls())[i].getCenter()) &&
	 *     |    getBalls()[i].getDiameter() == old(getBalls())[i].getDiameter() && 
	 *     |		(getBalls()[i].getVelocity().equals(old(getBalls())[i].getVelocity()) || 
	 *     |		getBalls()[i].getVelocity().equals(old(getBalls())[i].bouncePaddle(getPaddle(), paddleDir, 3).getVelocity())))
	 */
	
	public void raaktPaddleRechts(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktRechthoek(new Rechthoek(paddle.getTopLeft(), paddle.getBottomRight()), 4)) {
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
	
	/**
	 * Moves the paddle to the right by 10 units, keeping in mind it can't go outside of the field
	 * 
	 * @mutates | this, getPaddle()
	 * @inspects | getBottomRight()
	 * 
	 * @post the paddle has moved to the right by 10 units, unless it would have gone outside of the field, in which case it does not go any further than the right of the field
	 * 	   | getPaddle().getCenter().getX() == old(getPaddle()).getCenter().getX() + 10 || 
	 * 	   | getPaddle().getCenter().getX() == getBottomRight().getX() - getPaddle().getSize().getX()
	 */
	
	public void movePaddleRight() {
		paddle = paddle.movePaddleRight(getBottomRight());
	}
	
	/**
	 * Moves the paddle to the left by 10 units, keeping in mind it can't go outside of the field
	 * 
	 * @mutates | this, getPaddle()
	 * @inspects | getBottomRight()
	 * 
	 * @post the paddle has moved to the left by 10 units, unless it would have gone outside of the fiels, in which case it did not go any further than the left of the field
	 *     | getPaddle().getCenter().getX() == old(getPaddle()).getCenter().getX() - 10 || 
	 *     | getPaddle().getCenter().getX() == getPaddle().getSize().getX()
	 */

	public void movePaddleLeft() {
		paddle = paddle.movePaddleLeft();
	}
	
	/**
	 * Returns whether the game is won or not 
	 * 
	 * @inspects | getBlocks(), getBalls()
	 * 
	 * @post  The result is {@code true} if there are no more blocks on the field, and at least one ball is still in the game
	 *     | result == (getBlocks().length == 0 && getBalls().length > 0)
	 */
	
	public boolean isWon() {
		return blocks.length == 0 && balls.length > 0;
	}

	/**
	 * Returns whether the game is lost or not
	 * 
	 * @inspects | getBalls()
	 * 
	 * @post  The result is {@code true} if there are no more balls left in the game
	 *    | result == (getBalls().length == 0)
	 */
	
	public boolean isDead() {
		return balls.length == 0;
	}
}
