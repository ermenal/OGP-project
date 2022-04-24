package breakout;

import java.awt.Color;

/**
 * This class represents a replicator block on a 2D-grid
 * 
 * @immutable
 * 
 * @invar | getBottomRight() != null
 * @invar | getTopLeft() != null
 * @invar | getHealth() == -1
 * 
 * @invar The bottom right coordinates of the block are below and to the right of top left coordinates of the block
 *     | getTopLeft().getX() < getBottomRight().getX() &&
 *     | getTopLeft().getY() < getBottomRight().getY()
 */

public class ReplicatorBlockState extends BlockState {

	ReplicatorBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	/**
	 * Returns the block's color, which is green.
	 * 
	 * @post | result == Color.GREEN
	 */
	
	@Override
	
	public Color getColor() {
		return Color.GREEN;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		SturdyBlockState other = (SturdyBlockState) obj;
		if (!(other.getColor().equals(this.getColor())))
			return false;
		if (!(other.getTopLeft().equals(this.getTopLeft())))
			return false;
		if (!(other.getBottomRight().equals(this.getBottomRight())))
			return false;
		return true;
	}
	
	public PaddleState specialBlockHandler(PaddleState paddle) {
		return new PaddleState(paddle.getCenter(), 3);
	}
	
}
