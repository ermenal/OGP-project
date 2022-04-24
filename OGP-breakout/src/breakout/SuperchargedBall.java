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
	 * Changes the ball's center according with its velocity and the amount of milliseconds since the last time it moved.
	 * Also changes the amount of time the ball has been supercharged for.
	 * 
	 * @pre Argument {@code br} is not {@code null} 
	 * 		| br != null
	 * @pre Argument {@timeElapsed} is greater than 0
	 * 		| timeElapsed > 0
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's velocity and diameter remained unchanged
	 * 		| getDiameter() == old(getDiameter()) && 
	 * 		| getVelocity() == old(getVelocity())
	 * 
	 * @post The ball has moved according to its velocity, keeping in mind that it can't go outside of the field
	 * 		| getCenter().getX() == old(getCenter()).plus(getVelocity().scaled(timeElapsed)).getX()  || 
	 * 		| getCenter().getX() == getDiameter()/2  || 
	 * 		| getCenter().getX() == br.getX() - getDiameter()/2 ||
	 * 		| getCenter().getY() == getDiameter()/2  ||
	 * 		| getCenter().getY() == br.getY() - getDiameter()/2
	 * 
	 * @post {@code timeElapsed} is added to the amount of time this ball has been supercharged for.
	 * 		| getTime() == old(getTime()) + timeElapsed
	 */
	
	@Override
	
	public void moveBall(Point br, int timeElapsed) {
		super.moveBall(br, timeElapsed);
		time += timeElapsed;
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
	 * 		 For a destroyable block, the ball's velocity remained unchanged.
	 * 		| (!destroyed && (
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1))) )||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(1, 0))) ) || 
	 * 		| (destroyed &&
	 * 		| 	getVelocity().equals(old(getVelocity())))
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
	 * Returns {@code this} if the amount of time the ball has been supercharged for does not exceed {@code maxTime} or a new normal ball if it does.
	 * 
	 * @pre Argument {@code maxTime} should be greater than 0
	 * 		| maxTime > 0
	 * 
	 * @inspects | this
	 * 
	 * @post If the ball is supercharged, a new normal ball is returned if the ball has been supercharged longer than {@code maxTime}. 
	 * 		 If the supercharged ball hasn't been supercharged for longer than {@code maxTime}, {@code this} is returned.
	 * 		| result == this && this.getTime() <= maxTime || 
	 * 		| result.getClass().equals(NormalBall.class) && this.getTime() > maxTime
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged.
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getVelocity() == getVelocity()
	 * 
	 * @post The resulting ball's time left supercharged has either remained the same, or it has become -1 because it is now a normal ball.
	 * 		| result.getTime() == getTime() || 
	 * 		| result.getTime() == -1 && result.getClass().equals(NormalBall.class)
	 */
	
	@Override
	
	public Ball superchargedTimeHandler(int maxTime) {
		if (time > maxTime) {
			return new NormalBall(getCenter(), getDiameter(), getVelocity());
	}
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
