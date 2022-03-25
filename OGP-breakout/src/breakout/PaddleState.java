package breakout;

/**
 * This class represents a paddle on a 2D-grid
 * 
 * @immutable
 *
 * @invar | getCenter() != null
 * @invar | getSize() != null
 * 
 * @invar the paddle's size 
 *     | getSize().getX() >= 0 && getSize().getY() >= 0
 */

public class PaddleState {
	// TODO: VOLLEDIGE DOCUMENTATIE, nog niks is af
	
	/**
	 * @invar | center != null
	 * @invar | size != null
	 * 
	 * @invar 
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
	 *      | result.getX() == getCenter().minus(getSize()).getX() &&
	 *      | result.getY() == getCenter().minus(getSize()).getY()
	 */
	
	public Point getTopLeft() {
		Point tl = center.minus(size);
		return tl;
	}
	
	public Point getBottomRight() {
		Point br = center.plus(size);
		return br;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public PaddleState movePaddleRightBy(Point br) {
		int moveBy = 10;
		Point newCenter = new Point(center.getX() + moveBy, center.getY());
		if (newCenter.getX() + size.getX() > br.getX()){
			newCenter = new Point(br.getX() - size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	public PaddleState movePaddleLeftBy() {
		int moveBy = 10;
		Point newCenter = new Point(center.getX() - moveBy, center.getY());
		if (newCenter.getX() - size.getX() < 0){
			newCenter = new Point(size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	
}
