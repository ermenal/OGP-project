package breakout;

/**
 * An object of this class represents a rectangle on a 2D-grid
 * 
 * @immutable
 * 
 * @invar The bottom right point is not {@code null}
 *    | getBottomRight() != null
 * @invar The top left point is not {@code null}
 *    | getTopLeft() != null
 *    
 * @invar The bottom right point of the rectangle is not positioned above or to the left of the top left point
 *    | getBottomRight().getX() >= getTopLeft().getX() &&
 *    | getBottomRight().getY() >= getTopLeft().getY()
 */

public class Rect {
	
	/**
	 * @invar {@code topLeft} is not {@code null}
	 *    | topLeft != null
	 * @invar {@code bottomRight} is not {@code null} 
	 *    | bottomRight != null
	 *
	 * @invar The {@code bottomRight} point is not positioned above or to the left of the {@code topLeft} point
	 *     | bottomRight.getX() >= topLeft.getX() && 
	 *	   | bottomRight.getY() >= topLeft.getY()
	 */
	
	private final Point topLeft;
	private final Point bottomRight;
	
	/**
	 * Initializes the object with the given topLeft and bottomRight points 
	 * 
	 * @pre Parameter {@code topLeft} is not {@code null}
	 *     | topLeft != null
	 * @pre Parameter {@code bottomRight} is not {@code null}
	 *     | bottomRight != null
	 *     
	 * @pre {@code bottomRight} is not positioned above or to the left of {@code topLeft}
	 *     | bottomRight.getX() >= topLeft.getX() && 
	 *     | bottomRight.getY() >= topLeft.getY()
	 *     
	 * @post | getBottomRight() == bottomRight
	 * @post | getTopLeft() == topLeft
	 */
	
	public Rect(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	/**
	 * Returns the top left point of the rectangle
	 */
	
	public Point getTopLeft() {
		return topLeft;
	}
	
	/**
	 * Returns the bottom right point of the rectangle
	 */
	
	public Point getBottomRight() {
		return bottomRight;
	}
}
