package breakout;

import java.awt.Color;

/**
 * This class represents a powerup block on a 2D-grid
 * 
 * @immutable
 * 
 * @invar | getBottomRight() != null
 * @invar | getTopLeft() != null
 * @invar | getHealth() == -1
 * 
 * @invar The bottom right coordinates of the block are below and to the right of top left coordinates of the block
 *     | getTopLeft().getX() < getBottomRight().getX() &&
 *     | getTopLeft().getY() < getBottomRight().getY()
 */

public class PowerupBlockState extends BlockState {

	/**
	 * Initializes this object so that it stores the given topLeft and bottomRight coordinates of the powerup block
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
	
	PowerupBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	/**
	 * Returns the block's color, which for a powerup block is blue.
	 * 
	 * @post | result == Color.BLUE
	 */
	
	@Override
	
	public Color getColor() {
		return Color.BLUE;
	}
	
	/**
	 * Returns whether or not {@code obj} is equal to {@code this}
	 * 
	 * @post The result is {@code true} if {@code obj} is a powerup block with the same properties as this block. 
	 * 		 The result is {@code false} if this is not the case or {@code obj} is {@code null} 
	 * 		| result == ( (obj != null) && ( getClass().equals(obj.getClass()) &&
	 * 		|	((PowerupBlockState)obj).getTopLeft().equals(getTopLeft()) &&
	 * 		|	((PowerupBlockState)obj).getBottomRight().equals(getBottomRight()) ) )
	 */
	
	@Override
	
	public boolean equals(Object obj) {
		if (!(super.equals(obj)))
			return false;
		
		PowerupBlockState other = (PowerupBlockState) obj;
		return this == other || this.getTopLeft().equals(other.getTopLeft()) && 
								this.getBottomRight().equals(other.getBottomRight());
	}
	
	/**
	 * Returns a new supercharged ball.
	 * 
	 * @pre Argument {@code ball} is not {@code null}.
	 * 		| ball != null
	 * 
	 * @inspects | ball
	 * 
	 * @creates | result
	 * 
	 * @post The result is a supercharged ball 
	 * 		| result.getClass().equals(SuperchargedBall.class)
	 * 
	 * @post The resulting supercharged ball's center, diameter and velocity have remained unchanged. Its time has been initialized as 0.
	 * 		| result.getCenter() == ball.getCenter() && 
	 * 		| result.getDiameter() == ball.getDiameter() && 
	 * 		| result.getVelocity() == ball.getVelocity() && 
	 * 		| result.getTime() == 0
	 */
	
	@Override
	
	public Ball specialBlockHandler(Ball ball) {
		return new SuperchargedBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity(), 0);
	}
}
