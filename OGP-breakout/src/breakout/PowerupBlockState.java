package breakout;

import java.awt.Color;

/**
 * This class represents a powerup block on a 2D-grid
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

public class PowerupBlockState extends BlockState {

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
	
	PowerupBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	/**
	 * Returns the block's color, which for a powerup block is blue.
	 * 
	 * @post | result.equals(Color.BLUE)
	 */
	
	@Override
	
	public Color getColor() {
		return Color.BLUE;
	}
	
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
	 * @post | result.getClass().equals(SuperchargedBall.class)
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged. Its time has been initialized as 0.
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
