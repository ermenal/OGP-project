package breakout;

import java.awt.Color;

/**
 * This class represents a normal block on a 2D-grid
 * 
 * @immutable
 * 
 * @invar | getBottomRight() != null
 * @invar | getTopLeft() != null
 * @invar | getHealth() == -1 || getHealth() > 0 && getHealth() <= 3
 * 
 * @invar The bottom right coordinates of the block are below and to the right of top left coordinates of the block
 *     | getTopLeft().getX() < getBottomRight().getX() &&
 *     | getTopLeft().getY() < getBottomRight().getY()
 */

public class NormalBlockState extends BlockState {
	
	/**
	 * Initializes this object so that it stores the given topLeft and bottomRight coordinates
	 * 
	 * @pre {@code topLeft} is not {@code null}
	 *     | topLeft != null
	 * @pre {@code bottomRight} is not {@code null}
	 *     | bottomRight != null
	 * 
	 * @pre {@code bottomRight} coordinates of the block are below and to the right of its {@code topLeft} coordinates
	 *     | bottomRight.getX() > topLeft.getX() && 
	 *     | bottomRight.getY() > topLeft.getY()
	 * 
	 * @post | getTopLeft() == topLeft
	 * @post | getBottomRight() == bottomRight
	 */
	
	NormalBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	/**
	 * Returns the block's color, which for a normal block is {@code white}
	 * 
	 * @post | result.equals(Color.WHITE)
	 */
	
	@Override
	
	public Color getColor() {
		return Color.WHITE;
	}

	@Override
	
	public boolean equals(Object obj) {
		if (!(super.equals(obj)))
			return false;
		
		NormalBlockState other = (NormalBlockState) obj;
		return this == other || this.getTopLeft().equals(other.getTopLeft()) && 
								this.getBottomRight().equals(other.getBottomRight());
	}
	
}
