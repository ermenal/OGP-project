package breakout;

import java.awt.Color;
import java.util.stream.IntStream;
import java.util.Arrays;

/**
 * This class represents a paddle on a 2D-grid.
 * 
 * @immutable
 *
 * @invar | getCenter() != null
 * @invar | getSize().equals(new Vector(24999, 250))
 * @invar | getAmountOfReplications() >= 0 && getAmountOfReplications() <= 3
 * @invar | getAddedVelocities().length == 3
 * @invar | IntStream.range(0, getAddedVelocities().length).allMatch(i -> 
 * 		  | 	i == 0 && getAddedVelocities()[i].equals(new Vector(2, -2)) ||
 * 		  |		i == 1 && getAddedVelocities()[i].equals(new Vector(2, 2)) ||
 * 		  |		i == 2 && getAddedVelocities()[i].equals(new Vector(-2, 2)))
 */

public class PaddleState {
	
	/**
	 * @invar | center != null
	 * @invar | Size.equals(new Vector(24999, 250))
	 * @invar | amountOfReplications >= 0 && amountOfReplications <= 3
	 * @invar | AddedVelocities.length == 3
	 * @invar | IntStream.range(0, AddedVelocities.length).allMatch(i -> 
	 * 		  | 	i == 0 && AddedVelocities[i].equals(new Vector(2, -2)) ||
	 * 		  |		i == 1 && AddedVelocities[i].equals(new Vector(2, 2)) ||
	 * 		  |		i == 2 && AddedVelocities[i].equals(new Vector(-2, 2)))
	 */
	
	private final Point center;
	private final int amountOfReplications;
	private static final Vector Size = new Vector(24999, 250);
	private static final Vector[] AddedVelocities = {new Vector(2, -2), new Vector(2, 2), new Vector(-2, 2)};
	
	/**
	 * Initializes this object so that it stores the given center and amount of replications the paddle has left.
	 * 
	 * @pre The argument {@code center} is not {@code null}
	 *     	| center != null
	 * @pre The value of {@code amountOfReplications} is a value between 0 and 3, including 0 and 3.
	 * 		This number indicates the amount of clones that will be spawned when a ball makes contact with the paddle.
	 * 		| amountOfReplications >= 0 && amountOfReplications <= 3
	 * 
	 * @post | getCenter() == center
	 * @post | getAmountOfReplications() == amountOfReplications
	 */
	
	public PaddleState(Point center, int amountOfReplications){
		this.center = center;
		this.amountOfReplications = amountOfReplications;
	}
	
	
	/**
	 * Returns the top left coordinates of the paddle/
	 * 
	 * @post The result is not {@code null}
	 *     	| result != null
	 * @post The result is a Point object representing the top left point of the paddle.
	 *      | result.getX() == getCenter().getX() - getSize().getX() &&
	 *      | result.getY() == getCenter().getY() - getSize().getY()
	 */
	
	public Point getTopLeft() {
		Point tl = center.minus(Size);
		return tl;
	}
	
	/**
	 * Returns the bottom right coordinates of the paddle.
	 * 
	 * @post The result is not {@code null} 
	 * 	   	| result != null
	 * @post The result is a Point object representing the bottom right point of the paddle.
	 *     	| result.getX() == getCenter().getX() + getSize().getX() &&
	 *     	| result.getY() == getCenter().getY() + getSize().getY()
	 */
	
	public Point getBottomRight() {
		Point br = center.plus(Size);
		return br;
	}
	
	/**
	 * Returns the paddle's center.
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	* Returns the paddle's size.
	*/
	
	public Vector getSize() {
		return Size;
	}
	
	/**
	 * Returns the paddle's color.
	 * 
	 * @post The result is cyan if the paddle spawns 0 replications, otherwise it's green.
	 * 		| result.equals(Color.CYAN) && getAmountOfReplications() == 0 ||
	 * 		| result.equals(Color.GREEN) && getAmountOfReplications() > 0
	 */
	
	public Color getColor() {
		if (amountOfReplications > 0)
			return Color.GREEN;
		return Color.CYAN;
	}
	
	/**
	 * Returns the amount of balls that will be cloned on contact with this paddle.
	 */
	
	public int getAmountOfReplications() {
		return amountOfReplications;
	}
	
	/**
	 * Returns the velocities that will be added to any cloned balls.
	 * 
	 * @creates | result
	 */
	
	public Vector[] getAddedVelocities() {
		return AddedVelocities.clone();
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		PaddleState other = (PaddleState)obj;
		return this == other || other.getCenter().equals(getCenter()) && other.getAmountOfReplications() == this.getAmountOfReplications();
	}
	
	/**
	 * Returns an array, containing all balls in the given array {@code balls} as well as the balls that were cloned when 
	 * ball {@code ball} collided with the paddle.
	 * 
	 * @pre Argument {@code balls} should not be {@code null}.
	 * 		| balls != null
	 * @pre Argument {@code ball} should not be {@code null}.
	 * 		| ball != null
	 * 
	 * @inspects | balls, ball
	 * 
	 * @creates | result
	 * 
	 * @post The resulting array will have an amount of elements equal to the sum of the number of elements in {@code balls} and the amount of balls this paddle will clone.
	 * 		| result.length == balls.length + getAmountOfReplications()
	 * @post All elements from {@code balls} are in the resulting array, and all of them are on the same index as they were in {@code balls}.
	 * 		| IntStream.range(0, balls.length).
	 * 		|	allMatch(i -> balls[i] == result[i])
	 * @post None of the resulting array's elements are {@code null}, unless they were {@code null} in {@code balls}.
	 * 		| IntStream.range(0, result.length).
	 * 		|	allMatch(i -> result[i] != null || i <= balls.length)
	 * @post All of the cloned balls in the resulting array are behind the elements from {@code balls}. The cloned balls' classes, time left supercharged, centers and diameters 
	 * 		 are all the same as {@code ball} and their velocities are equal to the sum of one of the Vectors from {@code AddedVelocities} and the velocitiy from {@code ball}.
	 * 		| IntStream.range(balls.length, result.length).allMatch(i ->
	 * 		| 	result[i].equals(ball.cloneBallWithChangedVelocity(getAddedVelocities()[0])) ||
	 * 		|	result[i].equals(ball.cloneBallWithChangedVelocity(getAddedVelocities()[1])) ||
	 * 		|	result[i].equals(ball.cloneBallWithChangedVelocity(getAddedVelocities()[2])) )
	 */
	
	public Ball[] hitPaddle(Ball[] balls, Ball ball) {
		Ball[] newBalls = new Ball[balls.length + amountOfReplications];
		
		int i = 0;
		for (;i<balls.length; i++) {
			newBalls[i] = balls[i];
		}
		
		Ball[] clonesToBeAdded = new Ball[amountOfReplications];
		
		for (int idx=0; idx<amountOfReplications;idx++) {
			clonesToBeAdded[idx] = ball.cloneBallWithChangedVelocity(AddedVelocities[idx]);
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
		if (newCenter.getX() + Size.getX() > br.getX()){
			newCenter = new Point(br.getX() - Size.getX(), newCenter.getY());
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
		if (newCenter.getX() - Size.getX() < 0){
			newCenter = new Point(Size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, amountOfReplications);
	}
	
	
}
