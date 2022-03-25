package breakout;

/**
 * This class represents a ball on a 2D-grid
 * 
 * @immutable
 *
 * @invar | getCenter() != null
 * @invar | getDiameter() >= 0
 * @invar | getVelocity() != null
 */

public class BallState {
	
	/**
	 * @invar | center != null
	 * @invar | diameter >= 0
	 * @invar | velocity != null
	 */
	
	private final Point center;
	private final int diameter;
	private final Vector velocity;
	
	/**
	 * @pre | center != null
	 * @pre | velocity != null
	 * 
	 * @post | getCenter() == center
	 * @post | getDiameter() == Math.abs(diameter)
	 * @post | getVelocity() == velocity
	 */
	
	public BallState(Point center, int diameter, Vector velocity) {
		this.center = center;
		this.diameter = Math.abs(diameter);
		this.velocity = velocity;
	}
	
	/**
	 * Returns the object's diameter
	 */
	
	public int getDiameter() {
		return diameter;
	}
	
	/**
	 * Returns the object's center
	 */
	
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Returns the object's velocity
	 */
	
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * Returns the top left point of the rectangle that surrounds the ball
	 * 
	 * @post | result.getX() == getCenter().getX() - getDiameter()/2 &&
	 * 		 | result.getY() == getCenter().getY() - getDiameter()/2
	 */
	
	public Point getTopLeftOfSurroundingRectangle() {
		Point topLeft = new Point(center.getX() - diameter/2, center.getY() - diameter/2);
		return topLeft;
	}
	
	/**
	 * Returns the bottom right point of the rectangle that surrounds the ball 
	 * 
	 * @post | result.getX() == getCenter().getX() + getDiameter()/2 && 
	 * 		 | result.getY() == getCenter().getY() + getDiameter()/2
	 */
	
	public Point getBottomRightOfSurroundingRectangle() {
		Point bottomRight = new Point(center.getX() + diameter/2, center.getY() + diameter/2);
		return bottomRight;
	}
	
	/** 
	 * Returns a new ball that moved according to the ball's current velocity
	 * 
	 * @pre argument {@code br} is not {@code null} 
	 * 		| br != null
	 * 
	 * @post | result != null
	 * @post The ball has moved according to its velocity, keeping in mind that it can't go outside of the field
	 * 		| result.getCenter().getX() == getCenter().plus(getVelocity()).getX()  || 
	 * 		| result.getCenter().getX() == getDiameter()/2  || 
	 * 		| result.getCenter().getX() == br.getX() - getDiameter()/2 ||
	 * 		| result.getCenter().getY() == getDiameter()/2  ||
	 * 		| result.getCenter().getY() == br.getY() - getDiameter()/2
	 * @post The resulting ball's velocity and diameter remained unchanged
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getVelocity() == getVelocity()
	 */
	
	public BallState moveBall(Point br) {
		Point newCenter = center.plus(velocity);
		if (newCenter.getX() - diameter/2 < 0) {
			newCenter = new Point(diameter/2, newCenter.getY());
		}
		if (newCenter.getX() + diameter/2 > br.getX()) {
			newCenter = new Point(br.getX() - diameter/2, newCenter.getY());
		}
		
		if (newCenter.getY() + diameter/2 > br.getY()) {
			newCenter = new Point(newCenter.getX(), br.getY() - diameter/2);
		}
		
		if (newCenter.getY() - diameter/2 < 0) {
			newCenter = new Point(newCenter.getX(), diameter/2);
		}
		return new BallState(newCenter, diameter, velocity);
	}
	
	/**
	 * Returns a new ball that bounced against a given wall, indicated by the {@code wallNumber} parameter
	 * 			(1 indicates the left wall, 2 indicates the top wall and 3 indicates the right wall)
	 * 
	 * @pre Argument {@code wallNumber} should be 1, 2 or 3, indicating which wall the ball made contact with.
	 * 		| wallNumber == 1 || wallNumber == 2 || wallNumber == 3
	 * 
	 * @post The result is not {@code null}
	 * 		| result != null
	 * 
	 * @post The resulting ball's center and diameter remained the same
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter()
	 * 
	 * @post The resulting ball's velocity has been mirrored in accordance with the given {@code wallNumber}
	 * 		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(1, 0))) && wallNumber == 1|| 
	 * 		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(0, 1))) && wallNumber == 2 || 
	 * 		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(-1, 0))) && wallNumber == 3
	 */
	
	public BallState bounceWall(int wallNumber) {
		
		if (wallNumber == 1) {
			// leftWall
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0));
			return new BallState(center, diameter, newVelocity);
		}
		if (wallNumber == 2) {
			// topWall
			Vector newVelocity = velocity.mirrorOver(new Vector(0, 1));
			return new BallState(center, diameter, newVelocity);
		}
		if (wallNumber == 3) {
			// righttWall
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0));
			return new BallState(center, diameter, newVelocity);
		}
		return null;
	}
	
	/**
	 * Returns a new ball that bounced against a given side of the paddle, indicated by the {@code paddleSideNumber}
	 * 			(1 indicates left side, 2 indicates top side and 3 indicates right side)
	 * and speeding the ball up or down by 2 units according to the paddle's current direction {@code paddleDir}:
	 * 			(-1 indicates the paddle is moving left, 0 indicates it's standing still and 1 indicates it's moving right)
	 * 
	 * @pre Argument {@code paddleDir} should be -1, 0 or 1
	 * 		| paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 * @pre Argument {@code paddleSideNumber} should be 1, 2 or 3
	 * 		| paddleSideNumber == 1 || paddleSideNumber == 2 || paddleSideNumber == 3
	 * 
	 * @post The resulting ball's center and diameter have remained unchanged
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter()
	 * 
	 * @post The resulting ball's velocity has been mirrored in accordance with the given {@code paddleSideNumber} 
	 *  	 and its X-component has been changed in accordance with the paddle's given direction {@code paddleDir}
	 *  	| result.getVelocity()
	 *  	|	.equals(new Vector(getVelocity().mirrorOver(new Vector(-1, 0)).getX() + paddleDir * 2, getVelocity().mirrorOver(new Vector(-1, 0)).getY())) && 
	 *  	| 	paddleSideNumber == 1 ||
	 *  	| result.getVelocity()
	 *  	|	.equals(new Vector(getVelocity().mirrorOver(new Vector(0, -1)).getX() + paddleDir * 2, getVelocity().mirrorOver(new Vector(0, -1)).getY())) && 
	 *  	|	paddleSideNumber == 2 ||
	 *  	| result.getVelocity()
	 *  	|	.equals(new Vector(getVelocity().mirrorOver(new Vector(1, 0)).getX() + paddleDir * 2, getVelocity().mirrorOver(new Vector(1, 0)).getY())) && 
	 *  	| 	paddleSideNumber == 3
	 */
	
	public BallState bouncePaddle(int paddleDir, int paddleSideNumber) {
		int addedVelocity = paddleDir * 2;
		if (paddleSideNumber == 1) {
			// leftSide
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0)).plus(new Vector(addedVelocity, 0));
			return new BallState(center, diameter, newVelocity);
		}
		if (paddleSideNumber == 2) {
			// topSide
			Vector newVelocity = velocity.mirrorOver(new Vector(0, -1)).plus(new Vector(addedVelocity, 0));
			return new BallState(center, diameter, newVelocity);
		}
		if (paddleSideNumber == 3) {
			// rightSide
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0)).plus(new Vector(addedVelocity, 0));
			return new BallState(center, diameter, newVelocity);
		}
		return null;
	}
	
	private boolean raakDottedProduct(Vector velocity, Vector n) {
		//avoiding weird effects
		Vector v = new Vector(-velocity.getX(), -velocity.getY());
		return v.product(n) >= 0;
	}
	
	/**
	 * Returns a new ball that bounced against a given side of a block, indicated by the {@code blockSideNumber}.
	 * 			(1 indicates the bottom side, 2 indicates the left side, 3 indicates the top side and 4 indicates the right side)
	 * 
	 * @pre Argument {@code blockSideNumber} should be 1, 2, 3 or 4
	 * 		| blockSideNumber == 1 || blockSideNumber == 2 || blockSideNumber == 3 || blockSideNumber == 4
	 * @post The resulting ball's center and diameter have not changed
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter()
	 * 
	 * @post The resulting ball's velocity has been mirrored in accordance with the given {@code blockSideNumber}
	 * 		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(0, 1))) && blockSideNumber == 1 ||
	 * 		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(-1, 0))) && blockSideNumber == 2 ||
	 *		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(0, -1))) && blockSideNumber == 3 ||
	 *		| result.getVelocity().equals(getVelocity().mirrorOver(new Vector(1, 0))) && blockSideNumber == 4
	 * 
	 */
	
	public BallState bounceBlock(int blockSideNumber) {
		if (blockSideNumber == 1) {
			//bottomSide
			Vector newVelocity = velocity.mirrorOver(new Vector(0, 1));
			return new BallState(center, diameter, newVelocity);
		}
		if (blockSideNumber == 2) {
			//leftSide
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0));
			return new BallState(center, diameter, newVelocity);
		}
		if (blockSideNumber == 3) {
			//topSide
			Vector newVelocity = velocity.mirrorOver(new Vector(0, -1));
			return new BallState(center, diameter, newVelocity);
		}
		if (blockSideNumber == 4) {
			//rightSide
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0));
			return new BallState(center, diameter, newVelocity);
		}
		return null;
	}
	
	/**
	 * Returns true if the ball has hit {@code rechthoek} on the given side, indicated by {@code sideNumber} 
	 * 			(1 indicates the bottom side, 2 indicates the left side, 3 indicates the top side and 4 indicates the right side)
	 * 
	 * @pre Argument {@code sideNumber} should be 1, 2, 3 or 4
	 * 		| sideNumber == 1 || sideNumber == 2 || sideNumber == 3 || sideNumber == 4
	 * @pre Argument {@code rechthoek} should not be {@code null}
	 * 		| rechthoek != null
	 * 
	 * @post the ball's properties have remained unchanged
	 * 		| getCenter() == old(getCenter()) &&
	 * 		| getDiameter() == old(getDiameter()) &&
	 * 		| getVelocity() == old(getVelocity())
	 * 
	 * @post The result is true if a collision happened between the ball and the rectangle's given side 
	 * 	
	 */
	
	public boolean raaktRechthoek(Rechthoek rechthoek, int sideNumber) {
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		// bottomSide
		if (sideNumber == 1) {
			if (ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getY() >= rechthoek.getBottomRight().getY() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY()) {
				Point linksOnder = new Point(rechthoek.getTopLeft().getX(), rechthoek.getBottomRight().getY());
				Point rechtsOnder = rechthoek.getBottomRight();
				if (getDiameter()/2 * getDiameter()/2 >= (((rechtsOnder.getX() - linksOnder.getX())*(linksOnder.getY() - center.getY()) - (linksOnder.getX() - center.getX())*(rechtsOnder.getY() - linksOnder.getY())) * ((rechtsOnder.getX() - linksOnder.getX())*(linksOnder.getY() - center.getY()) - (linksOnder.getX() - center.getX())*(rechtsOnder.getY() - linksOnder.getY()))) / ((rechtsOnder.getX() - linksOnder.getX())*(rechtsOnder.getX() - linksOnder.getX()) + (rechtsOnder.getY() - linksOnder.getY())*(rechtsOnder.getY() - linksOnder.getY()))) {
					return raakDottedProduct(velocity, new Vector(0, 1));
				}
			}
			
		}
		// leftSide
		if (sideNumber == 2) {
			if (center.getX() <= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()) {
				Point linksBoven = rechthoek.getTopLeft();
				Point linksOnder = new Point(rechthoek.getTopLeft().getX(), rechthoek.getBottomRight().getY());
				if (getDiameter()/2 * getDiameter()/2 >= distanceCenterTo2Points(linksBoven, linksOnder)) {
					return raakDottedProduct(velocity, new Vector(-1, 0));
				}
			}
			
		}
		//topSide
		if (sideNumber == 3) {
			if (ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getY() <= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()) {
				Point rechthoekRechtsBovenPunt = new Point(rechthoek.getBottomRight().getX(), rechthoek.getTopLeft().getY());
				if (getDiameter()/2 * getDiameter()/2 >= distanceCenterTo2Points(rechthoek.getTopLeft(), rechthoekRechtsBovenPunt)) {
					return raakDottedProduct(velocity, new Vector(0, -1));
				}
			}
		}
		//rightSide
		if (sideNumber == 4) {
			if (ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && center.getX() >= rechthoek.getBottomRight().getX() && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX()) {
				Point rechthoekRechtsBovenPunt = new Point(rechthoek.getBottomRight().getX(), rechthoek.getTopLeft().getY());
				if (getDiameter()/2 * getDiameter()/2 >= distanceCenterTo2Points(rechthoekRechtsBovenPunt, rechthoek.getBottomRight())) {
					return raakDottedProduct(velocity, new Vector(1, 0));
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the distance of the center of the ball to a line constructed by 2 points
	 * 
	 * @pre {@code punt1} is not {@code null}
	 * 		| punt1 != null
	 * @pre {@code punt2} is not {@code null}
	 * 		| punt2 != null
	 * @pre {@code punt1} must have either a different x-coordinate to {@code punt2}, a different y-coordinate or both
	 * 		| punt1.getX() != punt2.getX() || 
	 * 		|	punt1.getY() != punt2.getY()
	 * 
	 * @post the result is the distance between the center of the ball and the line constructed by the 2 given points
	 * 		| result == 
	 * 		|	((punt2.getX() - punt1.getX()) * (punt1.getY() - getCenter().getY()) - 
	 * 		| 			(punt1.getX() - getCenter().getX()) * (punt2.getY() - punt1.getY())) * 
	 * 		|		((punt2.getX() - punt1.getX()) * (punt1.getY() - getCenter().getY()) - 
	 * 		| 			(punt1.getX() - getCenter().getX()) * (punt2.getY() - punt1.getY())) / 
	 * 		|	((punt2.getX() - punt1.getX()) * (punt2.getX() - punt1.getX()) + 
	 * 		|		(punt2.getY() - punt1.getY()) * (punt2.getY() - punt1.getY()))
	 */
	public int distanceCenterTo2Points(Point punt1, Point punt2) {
		int x1 = punt1.getX();
		int x2 = punt2.getX();
		int y1 = punt1.getY();
		int y2 = punt2.getY();
		int x0 = center.getX();
		int y0 = center.getY();
		int bovenEquation = (x2 - x1)*(y1 - y0) - (x1 - x0)*(y2 - y1);
		int onderEquation = (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
		int equation = (bovenEquation)*(bovenEquation) / (onderEquation);
		
		return equation;
	}
	
}
