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
	 * Returns the amount of health the block has.
	 */
	
	public int getHealth() {
		return -1;
	}
	
	@Override
	
	public abstract boolean equals(Object obj);
	
	public Ball specialBlockHandler(Ball ball) {
		return ball;
	}
	
	public PaddleState specialBlockHandler(PaddleState paddle) {
		return paddle;
	}
	
	public BlockState specialBlockHandler() {
		return null;
	}
	
}
