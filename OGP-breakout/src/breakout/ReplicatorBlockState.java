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
	 * Returns the block's color, which for a replicator block is green.
	 * 
	 * @post | result == Color.GREEN
	 */
	
	@Override
	
	public Color getColor() {
		return Color.GREEN;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (!(super.equals(obj)))
			return false;
		
		ReplicatorBlockState other = (ReplicatorBlockState) obj;
		return this == other || this.getTopLeft().equals(other.getTopLeft()) && 
								this.getBottomRight().equals(other.getBottomRight());
	}
	
	/**
	 * Returns a new paddle with 3 replications.
	 * 
	 * @pre Argument {@code paddle} is not {@code null}.
	 * 		| paddle != null
	 * 
	 * @post | result.equals(new ReplicatorPaddleState(paddle.getCenter(), 3)) && getClass().equals(ReplicatorBlockState.class)
	 * 
	 * @post The resulting paddle's center has remained unchanged and it has 3 replications remaining.
	 * 		| result.getCenter() == paddle.getCenter() &&
	 * 		| result.getAmountOfReplications() == 3
	 */
	
	@Override
	
	public PaddleState specialBlockHandler(PaddleState paddle) {
		return new ReplicatorPaddleState(paddle.getCenter(), 3);
	}
	
}
