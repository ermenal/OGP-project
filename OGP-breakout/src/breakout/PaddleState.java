package breakout;

import java.awt.Color;

/**
 * This class represents a paddle on a 2D-grid
 * 
 * @immutable
 *
 * @invar | getCenter() != null
 * @invar | getSize().equals(new Vector(1500, 250))
 */

public class PaddleState {
	
	/**
	 * @invar | center != null
	 * @invar | size.equals(new Vector(1500, 250))
	 */
	
	private final Point center;
	private static final Vector size = new Vector(1500, 250);
	
	private final int amountOfReplications;
	
	/**
	 * Initializes this object so that it stores the given center and size.
	 * 
	 * @pre The argument {@code center} is not {@code null}
	 *     | center != null
	 * 
	 * @post | getCenter() == center
	 * @post | getAmountOfReplications() == amountOfReplications
	 */
	
	public PaddleState(Point center, int amountOfReplications){
		this.center = center;
		this.amountOfReplications = amountOfReplications;
	}
	
	
	/**
	 * Returns the top left coordinates of the paddle
	 * 
	 * @post The result is not {@code null}
	 *     | result != null
	 * @post The result is a Point object representing the top left point of the paddle
	 *      | result.getX() == getCenter().getX() - getSize().getX() &&
	 *      | result.getY() == getCenter().getY() - getSize().getY()
	 */
	
	public Point getTopLeft() {
		Point tl = center.minus(size);
		return tl;
	}
	
	/**
	 * Returns the bottom right coordinates of the paddle
	 * 
	 * @post The result is not {@code null} 
	 * 	   | result != null
	 * @post The result is a Point object representing the bottom right point of the paddle
	 *     | result.getX() == getCenter().getX() + getSize().getX() &&
	 *     | result.getY() == getCenter().getY() + getSize().getY()
	 */
	
	public Point getBottomRight() {
		Point br = center.plus(size);
		return br;
	}
	
	/**
	 * Returns the paddle's center
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	* Returns the paddle's size
	*/
	
	public Vector getSize() {
		return size;
	}
	
	public Color getColor() {
		if (amountOfReplications > 0)
			return Color.GREEN;
		return Color.CYAN;
	}
	
	public int getAmountOfReplications() {
		return amountOfReplications;
	}
	
	public Ball[] hitPaddle(Ball[] balls, Ball ball) {
		Ball[] newBalls = new Ball[balls.length + amountOfReplications];
		
		int i = 0;
		for (;i<balls.length; i++) {
			newBalls[i] = balls[i];
		}
		
		Ball[] clonesToBeAdded = new Ball[amountOfReplications];
		
		Vector[] addedVelocities = {new Vector(2, -2), new Vector(2, 2), new Vector(-2, 2)};
		for (int idx=0; idx<amountOfReplications;idx++) {
			clonesToBeAdded[idx] = ball.cloneBallWithChangedVelocity(addedVelocities[idx]);
		}
		
		for (int j=0;j<clonesToBeAdded.length;j++) {
			newBalls[i] = clonesToBeAdded[j];
			i++;
		}
		
		return newBalls;
	}
	
	/**
	 * Returns a new paddle object that has moved 10 units to the right, keeping in mind that it can't go outside of the field
	 * @param elapsedTime 
	 * 
	 * @pre Argument {@code br} is not {@code null}
	 * 		| br != null
	 * 
	 * @post The result is not {@code null}
	 * 	    | result != null
	 * @post The result's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post The result's X coordinate has moved right by 10 units, unless it would have gone outside of the field,
	 * 		 in which case its center has been adjusted keeping the paddle's size in mind 
	 * 		| result.getCenter().getX() == old(getCenter()).getX() + 10*elapsedTime ||
	 * 		| result.getCenter().getX() == br.getX() - getSize().getX()
	 */
	
	public PaddleState movePaddleRight(Point br, int elapsedTime) {
		int moveBy = 10 * elapsedTime;
		Point newCenter = new Point(center.getX() + moveBy, center.getY());
		if (newCenter.getX() + size.getX() > br.getX()){
			newCenter = new Point(br.getX() - size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, amountOfReplications);
	}
	
	/**
	 * Returns a new paddle object that has moved 10 units to the left, keeping in mind that it can't go outside of the field
	 * 
	 * @post The result is not {@code null}
	 * 		|  result != null
	 * @post The result's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post The result's X coordinate has moved left by 10 units, unless it would have gone outside of the field, 
	 * 		 in which case its center has been adjusted keeping the paddle's size in mind
	 * 		| result.getCenter().getX() == old(getCenter()).getX() - 10*elapsedTime || 
	 * 		| result.getCenter().getX() == getSize().getX()
	 */
	
	public PaddleState movePaddleLeft(int elapsedTime) {
		int moveBy = 10 * elapsedTime;
		Point newCenter = new Point(center.getX() - moveBy, center.getY());
		if (newCenter.getX() - size.getX() < 0){
			newCenter = new Point(size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, amountOfReplications);
	}
	
	
}
