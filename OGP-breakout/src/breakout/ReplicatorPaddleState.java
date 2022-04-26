package breakout;

import java.awt.Color;
import java.util.stream.IntStream;
import java.util.Arrays;

	/**
	 * This class represents a replicator paddle on a 2D-grid
	 * 
 	* @invar | getCenter() != null
 	* @invar | getSize().equals(new Vector(1500, 250))
 	* @invar The amount of replications this paddle will spawn when a ball collides with it is at least 1 and at most 3 
 	* 		| getAmountOfReplications() >= 1 && getAmountOfReplications() <= 3
 	* @invar | getAddedVelocities().length == 3
 	* @invar | IntStream.range(0, getAddedVelocities().length).allMatch(i -> 
 	* 		  | 	i == 0 && getAddedVelocities()[i].equals(new Vector(2, -2)) ||
 	* 		  |		i == 1 && getAddedVelocities()[i].equals(new Vector(2, 2)) ||
 	* 		  |		i == 2 && getAddedVelocities()[i].equals(new Vector(-2, 2)))
 	*/
	
	
public class ReplicatorPaddleState extends PaddleState{
	
	private final int amountOfReplications;
	
	/**
	 * Initializes this object so that it stores the given center and amount of replications the replicator paddle has left.
	 * 
	 * @pre The argument {@code center} is not {@code null}
	 *     	| center != null
	 * @pre The value of {@code amountOfReplications} is a value between 1 and 3, including 1 and 3.
	 * 		This number indicates the amount of clones that will be spawned when a ball makes contact with the paddle.
	 * 		| amountOfReplications >= 0 && amountOfReplications <= 3
	 * 
	 * @post | getCenter() == center
	 * @post | getAmountOfReplications() == amountOfReplications
	 */
	
	public ReplicatorPaddleState(Point center, int amountOfReplications) {
		super(center);
		this.amountOfReplications = amountOfReplications;
	}
	
	/**
	 * Returns the paddle's color, which for a replicator paddle is pink.
	 * 
	 * @post | result == Color.PINK
	 */
	
	@Override
	
	public Color getColor() {
		return Color.PINK;
	}
	
	/**
	 * Returns the amount of balls that will be cloned on contact with this paddle.
	 * 
	 * @post The amount of replications is at least 1 and at most 3 for this replication paddle
	 * 		| result >= 1 && result <= 3 && getClass().equals(ReplicatorPaddleState.class)
	 */
	
	@Override
	
	public int getAmountOfReplications() {
		return amountOfReplications;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		
		ReplicatorPaddleState other = (ReplicatorPaddleState)obj;
		return other.getCenter().equals(getCenter()) && other.getAmountOfReplications() == amountOfReplications;
	}
	
	public PaddleState ballHitPaddle() {
		if (amountOfReplications - 1 <= 0)
			return new NormalPaddleState(getCenter());
		return new ReplicatorPaddleState(getCenter(), amountOfReplications-1);
	}
	
	public Ball[] hitPaddleReplicationHandler(Ball[] balls, Ball ball) {
		Ball[] newBalls = new Ball[balls.length + amountOfReplications];
		
		int i = 0;
		for (;i<balls.length; i++) {
			newBalls[i] = balls[i];
		}
		
		Ball[] clonesToBeAdded = new Ball[amountOfReplications];
		
		for (int idx=0; idx<amountOfReplications;idx++) {
			clonesToBeAdded[idx] = ball.cloneBallWithChangedVelocity(getAddedVelocities()[idx]);
		}
		
		for (int j=0;j<clonesToBeAdded.length;j++) {
			newBalls[i] = clonesToBeAdded[j];
			i++;
		}
		
		return newBalls;
	}
	
	/**
	 * Returns a new replicator paddle that has moved {@code 10 * elapsedTime} units to the right, keeping in mind that it can't go outside of the field
	 * 
	 * @pre Argument {@code br} is not {@code null}
	 * 		| br != null
	 * @pre Argument {@code elapsedTime} should be greater than 0
	 * 		| elapsedTime > 0
	 * 
	 * @creates | result
	 * 
	 * @post The result is not {@code null}
	 * 	    | result != null
	 * @post The resulting paddle is the same kind of paddle as {@code this}, which in this case is a replicator paddle
	 * 		| result.getClass().equals(getClass())
	 * @post The resulting paddle's center's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post The resulting paddle has moved right by {@code 10 * elapsedTime} units, unless it would have gone outside of the field,
	 * 		 in which case its center's x-coordinate has been adjusted keeping the paddle's size in mind 
	 * 		| result.getCenter().getX() == old(getCenter()).getX() + 10*elapsedTime ||
	 * 		| result.getCenter().getX() == br.getX() - getSize().getX()
	 */
	
	public PaddleState movePaddleRight(Point br, int elapsedTime) {
		int moveBy = 10 * elapsedTime;
		Point newCenter = new Point(getCenter().getX() + moveBy, getCenter().getY());
		if (newCenter.getX() + getSize().getX() > br.getX()){
			newCenter = new Point(br.getX() - getSize().getX(), newCenter.getY());
		}
		return new ReplicatorPaddleState(newCenter, amountOfReplications);
	}
	
	/**
	 * Returns a new replicator paddle  that has moved {@code 10 * elapsedTime} units to the left, keeping in mind that it can't go outside of the field
	 * 
	 * @pre Argument {@code elapsedTime} is greater than 0
	 * 		| elapsedTime > 0
	 * 
	 * @creates | result
	 * 
	 * @post The result is not {@code null}
	 * 		|  result != null
	 * @post The resulting paddle is the same kind of paddle as {@code this}, which in this case is a replicator paddle
	 * 		| result.getClass().equals(getClass())
	 * @post The resulting paddle's center's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post The resulting paddle has moved left by {@code 10 * elapsedTime} units, unless it would have gone outside of the field, 
	 * 		 in which case its center's x-coordinate has been adjusted keeping the paddle's size in mind
	 * 		| result.getCenter().getX() == old(getCenter()).getX() - 10*elapsedTime || 
	 * 		| result.getCenter().getX() == getSize().getX()
	 */
	
	public PaddleState movePaddleLeft(int elapsedTime) {
		int moveBy = 10 * elapsedTime;
		Point newCenter = new Point(getCenter().getX() - moveBy, getCenter().getY());
		if (newCenter.getX() - getSize().getX() < 0){
			newCenter = new Point(getSize().getX(), newCenter.getY());
		}
		return new ReplicatorPaddleState(newCenter, amountOfReplications);
	}
	
}