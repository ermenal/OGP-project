package breakout;

/**
 * This class represents a paddle on a 2D-grid
 * 
 * @immutable
 *
 * @invar | getCenter() != null
 * @invar | getSize() != null
 * 
 * @invar the paddle's size vector should not be pointing to the left or up 
 *     | getSize().getX() >= 0 && getSize().getY() >= 0
 */

public class PaddleState {
	
	/**
	 * @invar | center != null
	 * @invar | size != null
	 * 
	 * @invar The size vector should not be pointing to the left or up
	 *     | size.getX() >= 0 &&
	 *     | size.getY() >= 0
	 */
	
	private final Point center;
	private final Vector size;
	
	/**
	 * @pre The argument {@code center} is not {@code null}
	 *     | center != null
	 * @pre The argument {@code size} is not {@code null}
	 * 	   | size != null
	 * 
	 * @post | getCenter() == center
	 * @post | getSize() == size
	 */
	
	public PaddleState(Point center, Vector size){
		this.center = center;
		this.size = size;
	}
	
	
	/**
	 * Returns the top left coordinates of the paddle
	 * 
	 * @creates | result
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
	 * @creates | result
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
	
	/**
	 * Returns a new paddle object that has moved 10 units to the right, keeping in mind that it can't go outside of the field
	 * 
	 * @creates | result
	 * 
	 * @pre argument {@code br} is not {@code null}
	 * 		| br != null
	 * 
	 * @post the result is not {@code null}
	 * 	    | result != null
	 * @post the result's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post the result's X coordinate has moved right by 10 units, unless it would have gone outside of the field,
	 * 		 in which case its center has been adjusted keeping the paddle's size in mind 
	 * 		| result.getCenter().getX() == old(getCenter()).getX() + 10 ||
	 * 		| result.getCenter().getX() == br.getX() - getSize().getX()
	 */
	
	public PaddleState movePaddleRight(Point br) {
		int moveBy = 10;
		Point newCenter = new Point(center.getX() + moveBy, center.getY());
		if (newCenter.getX() + size.getX() > br.getX()){
			newCenter = new Point(br.getX() - size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	/**
	 * Returns a new paddle object that has moved 10 units to the left, keeping in mind that it can't go outside of the field
	 * 
	 * @creates | result
	 * 
	 * @post the result is not {@code null}
	 * 		|  result != null
	 * @post the result's Y coordinate has remained the same
	 * 		| result.getCenter().getY() == old(getCenter()).getY()
	 * @post the result's X coordinate has moved left by 10 units, unless it would have gone outside of the field, 
	 * 		 in which case its center has been adjusted keeping the paddle's size in mind
	 * 		| result.getCenter().getX() == old(getCenter()).getX() - 10 || 
	 * 		| result.getCenter().getX() == getSize().getX()
	 */
	
	public PaddleState movePaddleLeft() {
		int moveBy = 10;
		Point newCenter = new Point(center.getX() - moveBy, center.getY());
		if (newCenter.getX() - size.getX() < 0){
			newCenter = new Point(size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	
}
