package breakout;

import java.awt.Color;

public class NormalPaddleState extends PaddleState{

	public NormalPaddleState(Point center) {
		super(center);
	}
	
	/**
	 * Returns the paddle's color, which for a normal paddle is cyan.
	 * 
	 * @post | result == Color.CYAN
	 */
	
	public Color getColor() {
		return Color.CYAN;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		
		NormalPaddleState other = (NormalPaddleState)obj;
		return other.getCenter().equals(getCenter());
	}
	
	public PaddleState ballHitPaddle() {
		return new NormalPaddleState(getCenter());
	}
	public Ball[] hitPaddleReplicationHandler(Ball[] balls, Ball ball) {
		return balls.clone();
	}
	
	/**
	 * Returns a new normal paddle that has moved {@code 10 * elapsedTime} units to the right, keeping in mind that it can't go outside of the field
	 * 
	 * @pre Argument {@code br} is not {@code null}
	 * 		| br != null
	 * @pre Argument {@code elapsedTime} is greater than 0
	 * 		| elapsedTime > 0
	 * 
	 * @creates | result
	 * 
	 * @post The result is not {@code null}
	 * 	    | result != null
	 * @post The resulting paddle is the same kind of paddle as {@code this}, which in this case is a normal paddle
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
		return new NormalPaddleState(newCenter);
	}
	
	/**
	 * Returns a new normal paddle that has moved {@code 10 * elapsedTime} units to the left, keeping in mind that it can't go outside of the field
	 * 
	 * @pre Argument {@code elapsedTime} is greater than 0
	 * 		| elapsedTime > 0
	 * 
	 * @creates | result
	 * 
	 * @post The result is not {@code null}
	 * 		|  result != null
	 * @post The resulting paddle is the same kind of paddle as {@code this}, which in this case is a normal paddle
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
		return new NormalPaddleState(newCenter);
	}
	
}
