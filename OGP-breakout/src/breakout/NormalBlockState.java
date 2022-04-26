package breakout;

import java.awt.Color;

/**
 * This class represents a normal block on a 2D-grid
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

public class NormalBlockState extends BlockState {
	
	/**
	 * Initializes this object so that it stores the given topLeft and bottomRight coordinates of the normal block
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
	 * Returns the block's color, which for a normal block is white
	 * 
	 * @post | result == Color.WHITE
	 */
	
	@Override
	
	public Color getColor() {
		return Color.WHITE;
	}
	
	/**
	 * Returns whether or not {@code obj} is equal to {@code this}
	 * 
	 * @post The result is {@code true} if {@code obj} is a normal block with the same properties as this block. 
	 * 		 The result is {@code false} if this is not the case or {@code obj} is {@code null} 
	 * 		| result == ( (obj != null) && ( getClass().equals(obj.getClass()) &&
	 * 		|	((NormalBlockState)obj).getTopLeft().equals(getTopLeft()) &&
	 * 		|	((NormalBlockState)obj).getBottomRight().equals(getBottomRight()) ) )
	 */

	@Override
	
	public boolean equals(Object obj) {
		if (!(super.equals(obj)))
			return false;
		
		NormalBlockState other = (NormalBlockState) obj;
		return this == other || this.getTopLeft().equals(other.getTopLeft()) && 
								this.getBottomRight().equals(other.getBottomRight());
	}
	
}
