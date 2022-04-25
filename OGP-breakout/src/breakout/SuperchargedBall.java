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
	 * 
	 * @post | getCenter() == center
	 * @post | getDiameter() == Math.abs(diameter)
	 * @post | getVelocity() == velocity
	 * @post | getTime() == time
	 */
	
	SuperchargedBall(Point center, int diameter, Vector velocity, int time){
		super(center, diameter, velocity);
		this.time = Math.abs(time);
	}
	
	/**
	 * Returns the ball's color, which for a supercharged ball is green.
	 * 
	 * @post | result.equals(Color.GREEN)
	 */
	
	@Override
	
	public Color getColor() {
		return Color.GREEN;
	}
	
	/**
	 * Changes the ball's velocity after it bounced against a non-destroyable block that is presented as {@code rect}.
	 * If the block is destroyable, the ball's velocity remains unchanged.
	 * 
	 * @pre {@code rect} is not {@code null}
	 * 		| rect != null
	 * @pre The ball hit the block on one of its sides.
	 * 		| raaktRechthoek(rect, 1) || 
	 * 		| raaktRechthoek(rect, 2) || 
	 * 		| raaktRechthoek(rect, 3) || 
	 * 		| raaktRechthoek(rect, 4)
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's center and diameter remained unchanged.
	 * 		| getCenter() == old(getCenter()) &&
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post For a non-destroyable block, the ball's velocity got changed according with the side of the block it hit.
	 * 		 If the supercharged ball hit the non-destroyable block on one of its corners, the ball's velocity got mirrored over 2 vertices.
	 * 		 For a destroyable block, the ball's velocity remained unchanged.
	 * 		| !destroyed && (
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) ||
	 * 		|		getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1)).mirrorOver(new Vector(-1, 0))) || 
	 * 		|		getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1)).mirrorOver(new Vector(1, 0))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1))) ||
	 * 		|		getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1)).mirrorOver(new Vector(-1, 0))) ||
	 * 		|		getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1)).mirrorOver(new Vector(1, 0))) ||
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
	 * 
	 * @post | result >= 0
	 */
	
	@Override
	
	public int getTime() {
		return time;
	}
	
	/**
	 * Returns {@code this} or a new normal ball, depending on how long the ball has been supercharged for.
	 * 
	 * @pre Argument {@code maxTime} should be greater than 0.
	 * 		| maxTime > 0
	 * @pre Argument {@code elapsedTime} should be greater than 0.
	 * 		| elapsedTime > 0
	 * 
	 * @mutates | this
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged.
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getVelocity() == getVelocity()
	 * 
	 * @post A new normal ball is returned if adding the elapsed time to the ball's time it has been supercharged for would result in a value larger than {@code maxTime}. 
	 * 		 If this doesn't result in a value larger than or equal to {@code maxTime}, the elapsed time is added onto the ball's current time and {@code this} is returned.
	 * 		| result == this && this.getTime() < maxTime  ||
	 * 		| result.getClass().equals(NormalBall.class) && this.getTime() + elapsedTime >= maxTime
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
	 * @post The result is also a supercharged ball
	 * 		| result.getClass().equals(getClass())
	 * 
	 * @post The ball's velocity is the result of adding {@code addedVelocity} to its old velocity.
	 * 		| result.getVelocity().equals(getVelocity().plus(addedVelocity))
	 * 
	 * @post The ball's center, diameter and time it's been supercharged for have remained unchanged.
	 * 		| result.getCenter().equals(getCenter()) &&
	 * 		| result.getDiameter() == getDiameter() &&
	 * 		| result.getTime() == getTime()
	 */
	
	@Override
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new SuperchargedBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity), time);
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (! (super.equals(obj)))
			return false;
		
		SuperchargedBall other = (SuperchargedBall) obj;
		return this == other || 
				other.getCenter().equals(this.getCenter()) && other.getDiameter() == this.getDiameter() && 
				other.getVelocity().equals(this.getVelocity()) && other.getTime() == this.getTime();
	}
}
