package breakout;

import java.awt.Color;

/**
 * This class represents a supercharged ball on a 2D-grid
 *
 * @invar | getCenter() != null
 * @invar | getDiameter() >= 0
 * @invar | getVelocity() != null
 * @invar | getTime() >= 0 
 */

public class SuperchargedBall extends Ball {
	/**
	 * @invar | time >= 0
	 */
	
	private int time;
	
	/**
	 * @pre | center != null
	 * @pre | velocity != null
	 * @pre | time >= 0
	 * 
	 * @post | getCenter() == center
	 * @post | getDiameter() == Math.abs(diameter)
	 * @post | getVelocity() == velocity
	 * @post | getTime() == time
	 */
	
	SuperchargedBall(Point center, int diameter, Vector velocity, int time){
		super(center, diameter, velocity);
		this.time = time;
	}
	
	/**
	 * Returns the ball's color, which for a supercharged ball is green.
	 * 
	 * @post | result == Color.GREEN
	 */
	
	@Override
	
	public Color getColor() {
		return Color.GREEN;
	}
	
	/**
	 * Changes this supercharged ball's velocity after it bounced against a non-destroyable block that is presented as {@code rect}.
	 * If the block is destroyable, the ball's velocity remains unchanged.
	 * 
	 * @pre {@code rect} is not {@code null}
	 * 		| rect != null
	 * @pre This ball hit the block on one of its sides.
	 * 		| raaktRechthoek(rect, 1) || 
	 * 		| raaktRechthoek(rect, 2) || 
	 * 		| raaktRechthoek(rect, 3) || 
	 * 		| raaktRechthoek(rect, 4)
	 * 
	 * @mutates | this
	 * 
	 * @post This ball's center and diameter remained unchanged.
	 * 		| getCenter() == old(getCenter()) &&
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post For a non-destroyable block, this supercharged ball's velocity got changed according with the side of the block it hit.
	 * 		 For a destroyable block, this supercharged ball's velocity remained unchanged.
	 * 		| !destroyed && (
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(1, 0))) ) || 
	 * 		| destroyed &&
	 * 		| 	getVelocity().equals(old(getVelocity()))
	 */
	
	@Override
	
	public void hitBlock(Rect rect, boolean destroyed) {
		if (destroyed == false) {
			super.hitBlock(rect, destroyed);
		}else {
			return;
		}
	}
	
	/**
	 * Returns the amount of time the ball has been supercharged for in milliseconds. 
	 */
	
	@Override
	
	public int getTime() {
		return time;
	}
	
	/**
	 * Returns {@code this} or a new normal ball, depending on how long the ball has been supercharged for.
	 * 
	 * @pre Argument {@code maxTime} should not be less than 0.
	 * 		| maxTime >= 0
	 * @pre Argument {@code elapsedTime} should be greater than 0.
	 * 		| elapsedTime > 0
	 * 
	 * @mutates | this
	 * 
	 * @creates | result 
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged.
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getVelocity() == getVelocity()
	 * @post A new normal ball is returned if adding the elapsed time to this ball's time it has been supercharged for would result in a value larger than or equal to {@code maxTime}. 
	 * 		 If this doesn't result in a value larger than or equal to {@code maxTime}, the elapsed time is added onto the ball's current time and {@code this} is returned.
	 * 		| result == this && getTime() < maxTime  ||
	 * 		| result.getClass().equals(NormalBall.class) && getTime() + elapsedTime >= maxTime
	 */
	
	@Override
	
	public Ball superchargedTimeHandler(int elapsedTime, int maxTime) {
		if (time + elapsedTime >= maxTime)
			return new NormalBall(getCenter(), getDiameter(), getVelocity());
		time += elapsedTime;
		return this;
	}
	
	/**
	 * Returns a supercharged ball, that has an altered velocity in accordance with {@code addedVelocity}.
	 * 
	 * @pre Argument {@code addedVelocity} is not {@code null}
	 * 		| addedVelocity != null
	 * 
	 * @inspects | this 
	 * 
	 * @creates | result
	 * 
	 * @post The result is a supercharged ball
	 * 		| result.getClass().equals(getClass())
	 * 
	 * @post The resulting ball's velocity is the result of adding {@code addedVelocity} to this ball's velocity.
	 * 		| result.getVelocity().equals(getVelocity().plus(addedVelocity))
	 * 
	 * @post The resulting ball's center, time it has been supercharged for and diameter are the same as {@code this}.
	 * 		| result.getCenter().equals(getCenter()) &&
	 * 		| result.getDiameter() == getDiameter() &&
	 * 		| result.getTime() == getTime()
	 */
	
	@Override
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new SuperchargedBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity), time);
	}
	
	@Override
	
	/**
	 * Checks if {@code obj} is the same class with the same properties as {@code this}
	 * 
	 * @post The result is {@code true} if {@code obj} is a supercharged ball with the same properties as this ball. 
	 * 		 The result is {@code false} if this is not the case or {@code obj} is {@code null} 
	 * | result == ( (obj != null) && getClass().equals(obj.getClass()) && 
	 * |	((SuperchargedBall)obj).getCenter().equals(getCenter()) && 
	 * |	((SuperchargedBall)obj).getDiameter() == getDiameter() && 
	 * |	((SuperchargedBall)obj).getVelocity().equals(getVelocity()) && 
	 * |	((SuperchargedBall)obj).getTime() == getTime() )
	 */
	
	public boolean equals(Object obj) {
		if (! (super.equals(obj)))
			return false;
		
		SuperchargedBall other = (SuperchargedBall) obj;
		return this == other || 
				other.getCenter().equals(this.getCenter()) && other.getDiameter() == this.getDiameter() && 
				other.getVelocity().equals(this.getVelocity()) && other.getTime() == this.getTime();
	}
	
}

