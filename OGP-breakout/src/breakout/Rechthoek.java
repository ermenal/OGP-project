package breakout;

/**
 * An object of this class represents a rectangle on a 2D-grid
 * 
 *@invar the bottom right point is not {@code null}
 *    | getBottomRight() != null
 *@invar the top left point is not {@code null}
 *    | getTopLeft() != null
 *    
 *@invar the bottom right point of the rectangle is not positioned above or to the left of the top left point
 *    | getBottomRight().getX() >= getTopLeft().getX() &&
 *    | getBottomRight().getY() >= getTopLeft().getY()
 */

public class Rechthoek {
	private final Point topLeft;
	private final Point bottomRight;
	
	public Rechthoek(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Point getTopLeft() {
		return topLeft;
	}
	
	public Point getBottomRight() {
		return bottomRight;
	}
}
