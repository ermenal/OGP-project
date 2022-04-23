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
	 * Changes the ball's center according with its velocity and the amount of milliseconds since the last time it moved
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
	 * Returns the ball's 
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
	 * @pre The ball hit the block on one of its sides
	 * 		| raaktRechthoek(rect, 1) || 
	 * 		| raaktRechthoek(rect, 2) || 
	 * 		| raaktRechthoek(rect, 3) || 
	 * 		| raaktRechthoek(rect, 4)
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's center and diameter remained unchanged
	 * 		| getCenter() == old(getCenter()) &&
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post For a non-destroyable block, the ball's velocity got changed according with the side of the block it hit.
	 * 		 For a destroyable block, the ball's velocity remained unchanged.
	 * 		| (!destroyed && 
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) ||
	 * 		| 	getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1))) ||
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
	
	public int getTime() {
		return time;
	}
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new SuperchargedBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity), time);
	}
	
	public boolean equals(Object obj) {
		if (! (super.equals(obj)))
			return false;
		
		SuperchargedBall other = (SuperchargedBall) obj;
		return other.getCenter().equals(this.getCenter()) && other.getDiameter() == this.getDiameter() && other.getVelocity().equals(this.getVelocity());
	}
}
