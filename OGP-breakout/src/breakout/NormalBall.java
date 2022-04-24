package breakout;

import java.awt.Color;

/**
 * This class represents a normal ball on a 2D-grid.
 *
 * @invar | getCenter() != null
 * @invar | getDiameter() >= 0
 * @invar | getVelocity() != null
 */

public class NormalBall extends Ball{
	
	/**
	 * @pre | center != null
	 * @pre | velocity != null
	 * 
	 * @post | getCenter() == center
	 * @post | getDiameter() == Math.abs(diameter)
	 * @post | getVelocity() == velocity
	 */
	
	public NormalBall(Point center, int diameter, Vector velocity) {
		super(center, diameter, velocity);
	}

	/**
	 * Returns the ball's color, which for a normal ball is white.
	 * 
	 * @post | result.equals(Color.WHITE)
	 */
	
	@Override
	
	public Color getColor() {
		return Color.WHITE;
	}
	
	/**
	 * Returns a normal ball, that has an altered velocity in accordance with {@code addedVelocity}
	 * 
	 * @pre Argument {@code addedVelocity} is not {@code null}
	 * 		| addedVelocity != null
	 * 
	 * @inspects | this 
	 * 
	 * @creates | result
	 * 
	 * @post
	 * 		| result.getClass().equals(getClass())
	 * 
	 * @post The ball's velocity is the result of adding {@code addedVelocity} to its old velocity
	 * 		| result.getVelocity().equals(getVelocity().plus(addedVelocity))
	 * 
	 * @post The ball's center and diameter have remained unchanged
	 * 		| result.getCenter().equals(getCenter()) &&
	 * 		| result.getDiameter() == getDiameter()
	 */
	
	@Override
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new NormalBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity));
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (! (super.equals(obj)))
			return false;
		
		NormalBall other = (NormalBall) obj;
		return this == other || 
				other.getCenter().equals(this.getCenter()) && other.getDiameter() == this.getDiameter() && 
				other.getVelocity().equals(this.getVelocity());
	
	}
	
}
