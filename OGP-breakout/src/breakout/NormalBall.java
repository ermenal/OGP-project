package breakout;

import java.awt.Color;

/**
 * This class represents a normal ball on a 2D-grid.
 *
 * @invar | getCenter() != null
 * @invar | getDiameter() >= 0
 * @invar | getVelocity() != null
 * @invar | getTime() == -1
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
	 * @post | result == Color.WHITE
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
	 * @post The resulting ball is a normal ball
	 * 		| result.getClass().equals(getClass())
	 * 
	 * @post The resulting ball's velocity is the result of adding {@code addedVelocity} to this ball's velocity
	 * 		| result.getVelocity().equals(getVelocity().plus(addedVelocity))
	 * 
	 * @post The resulting ball's center, time it has been supercharged for and diameter are the same as {@code this}.
	 * 		| result.getCenter().equals(getCenter()) &&
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getTime() == getTime()
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
