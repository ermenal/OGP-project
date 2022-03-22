package breakout;


/**
 * This class represents a block on a 2D-grid
 * 
 * @immutable
 */
public class BlockState {
	// DONE: implement
	
	
	private final Point topLeft;
	private final Point bottomRight;

	
	/**
	 * Return a new BlockState with given topLeft and bottomRight coordinates
	 * 
	 * @post getTopLeft() == topLeft
	 * @post getBottomRight == bottomRight
	 */
	public BlockState(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	
	/** Return this point's topLeft coordinate. */
	public Point getTopLeft() {
		return this.topLeft;
	}
	
	/** Return this point's bottomRight coordinate. */
	public Point getBottomRight() {
		return this.bottomRight;
	}
}
