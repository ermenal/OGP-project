package breakout;

import java.awt.Color;

/**
 * This class represents a block on a 2D-grid
 * 
 * @immutable
 * 
 * @invar | getBottomRight() != null
 * @invar | getTopLeft() != null
 * @invar | getHealth() == -1 || getHealth() > 0 && getHealth() <= 3
 * 
 * @invar The bottom right coordinates of the block are below and to the right of top left coordinates of the block
 *     | getTopLeft().getX() < getBottomRight().getX() &&
 *     | getTopLeft().getY() < getBottomRight().getY()
 */

public abstract class BlockState {
	
	/**
	 * @invar | bottomRight != null
	 * @invar | topLeft != null
	 * 
	 * @invar {@code bottomRight} coordinates of the block are below and to the right of {@code topLeft} coordinates of the block
	 *     | topLeft.getX() < bottomRight.getX() &&
	 *     | topLeft.getY() < bottomRight.getY()
	 */
	
	private final Point topLeft;
	private final Point bottomRight;

	/**
	 * Initializes this object so that it stores the given topLeft and bottomRight coordinates
	 * 
	 * @pre {@code topLeft} is not {@code null}
	 *     | topLeft != null
	 * @pre {@code bottomRight} is not {@code null}
	 *     | bottomRight != null
	 * 
	 * @pre {@code bottomRight} coordinates of the block are below and to the right of its {@code topLeft} coordinates
	 *     | bottomRight.getX() > topLeft.getX() && 
	 *     | bottomRight.getY() > topLeft.getY()
	 * 
	 * @post | getTopLeft() == topLeft
	 * @post | getBottomRight() == bottomRight
	 */
	
	public BlockState(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	
	/** Return this block's topLeft coordinate. 
	 * @post | result != null
	 */
	public Point getTopLeft() {
		return topLeft;
	}
	
	/** Return this block's bottomRight coordinate. 
	 * @post result != null
	 */
	public Point getBottomRight() {
		return bottomRight;
	}
	
	/**
	 * Returns true if the block gets destroyed when a ball collides with it, otherwise it returns false.
	 * 
	 * @post | result == true || result == false 
	 */
	
	public boolean getsDestroyedOnCollision() {
		return true;
	}
	
	/**
	 * Returns the block's color, depending on what kind of block it is.
	 * 
	 * @post 
	 * 		| result.equals(Color.WHITE) || result.equals(Color.RED) || result.equals(Color.ORANGE) || 
	 * 		| result.equals(Color.YELLOW) || result.equals(Color.GREEN) || result.equals(Color.BLUE)
	 */
	
	public abstract Color getColor();
	
	/**
	 * Returns the amount of health the block has or 1 if the block is not a sturdy block.
	 */
	
	public int getHealth() {
		return -1;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		return getClass().equals(obj.getClass());
	}
	
	/**
	 * Returns {@code ball}, unless {@code this} is a powerup block.
	 * 
	 * @pre Argument {@code ball} is not {@code null}.
	 * 		| ball != null
	 * 
	 * @inspects | ball
	 * 
	 * @post | result == ball || result.getClass().equals(SuperchargedBall.class)
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged.
	 * 		| result.getCenter() == ball.getCenter() && 
	 * 		| result.getDiameter() == ball.getDiameter() && 
	 * 		| result.getVelocity() == ball.getVelocity()
	 */
	
	public Ball specialBlockHandler(Ball ball) {
		return ball;
	}
	
	/**
	 * Returns {@code paddle}, unless {@code this} is a replicator block.
	 * 
	 * @pre Argument {@code paddle} is not {@code null}.
	 * 		| paddle != null
	 * 
	 * @post | result == paddle || getClass().equals(ReplicatorBlockState.class)
	 * 
	 * @post The resulting paddle's center has remained unchanged.
	 * 		| result.getCenter() == paddle.getCenter() 
	 */
	
	public PaddleState specialBlockHandler(PaddleState paddle) {
		return paddle;
	}
	
	/**
	 * Returns {@code null}, unless {@code this} is a sturdy block with more than 1 health.
	 * 
	 * @post | result == null || result.getClass().equals(SturdyBlockState.class)
	 */
	
	public BlockState specialBlockHandler() {
		return null;
	}
	
}
