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
	 * @creates | result
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
	 * @creates | result
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
	 * @creates | result
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
	 * 
	 * @creates | result
	 * 
	 * @pre Argument {@code wallNumber} should be 1, 2 or 3, indicating which wall the ball made contact with 
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
	 * and speeding the ball up or down by 2 units according to the paddle's current direction {@code paddleDir}
	 * 
	 * @creates | result
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
	
	public boolean raaktRechthoek(Rechthoek rechthoek, int sideNumber) {
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		// bottomSide
		if (sideNumber == 1) {
			Point rechthoekLinksOnderPunt = new Point(rechthoek.getTopLeft().getX(), rechthoek.getBottomRight().getY());
			if (ballBovenstePunt.getX() >= rechthoek.getTopLeft().getX() && ballBovenstePunt.getX() <= rechthoek.getBottomRight().getX() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && ballBovenstePunt.getY() >= rechthoek.getBottomRight().getY() - diameter/2) {
				return raakDottedProduct(velocity, new Vector(0, 1));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoekLinksOnderPunt) && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && center.getY() >= rechthoek.getBottomRight().getY() && center.getX() <= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX()) {
				return raakDottedProduct(velocity, new Vector(0, 1));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoek.getBottomRight()) && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && center.getY() >= rechthoek.getBottomRight().getY() && center.getX() >= rechthoek.getBottomRight().getX() && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX()) {
				return raakDottedProduct(velocity, new Vector(0, 1));
			}
			
		}
		// leftSide
		if (sideNumber == 2) {
			Point rechthoekLinksOnderPunt = new Point(rechthoek.getTopLeft().getX(), rechthoek.getBottomRight().getY());
			if (ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() <= rechthoek.getTopLeft().getX() + diameter/2 && ballRechtsePunt.getY() <= rechthoek.getBottomRight().getY() && ballRechtsePunt.getY() >= rechthoek.getTopLeft().getY()) {
				return raakDottedProduct(velocity, new Vector(-1, 0));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoek.getTopLeft()) && center.getY() <= rechthoek.getTopLeft().getY() && center.getX() <= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()) {
				return raakDottedProduct(velocity, new Vector(-1, 0));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoekLinksOnderPunt) && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && center.getY() >= rechthoek.getBottomRight().getY() && center.getX() <= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX()) {
				return raakDottedProduct(velocity, new Vector(-1, 0));
			}
			
		}
		//topSide
		if (sideNumber == 3) {
			Point rechthoekRechtsBovenPunt = new Point(rechthoek.getBottomRight().getX(), rechthoek.getTopLeft().getY());
			if (ballOnderstePunt.getX() >= rechthoek.getTopLeft().getX() && ballOnderstePunt.getX() <= rechthoek.getBottomRight().getX() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() <= rechthoek.getTopLeft().getY() + diameter/2) {
				return raakDottedProduct(velocity, new Vector(0, -1));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoek.getTopLeft()) && center.getX() <= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && center.getY() <= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()){
				return raakDottedProduct(velocity, new Vector(0, -1));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoekRechtsBovenPunt) && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getX() >= rechthoek.getBottomRight().getX() && center.getY() <= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()) {
				return raakDottedProduct(velocity, new Vector(0, -1));
			}
		}
		//rightSide
		if (sideNumber == 4) {
			Point rechthoekRechtsBovenPunt = new Point(rechthoek.getBottomRight().getX(), rechthoek.getTopLeft().getY());
			if(ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && ballLinksePunt.getX() >= rechthoek.getBottomRight().getX()-diameter/2 && ballLinksePunt.getY() >= rechthoek.getTopLeft().getY() && ballLinksePunt.getY() <= rechthoek.getBottomRight().getY()) {
				return raakDottedProduct(velocity, new Vector(1, 0));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoekRechtsBovenPunt) && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getX() >= rechthoek.getBottomRight().getX() && center.getY() <= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY()){
				return raakDottedProduct(velocity, new Vector(1, 0));
			}
			if ((getDiameter()/2) * (getDiameter()/2) >= distanceCenterToPointPowerOf2(rechthoek.getBottomRight()) && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getX() >= rechthoek.getBottomRight().getX() && center.getY() >= rechthoek.getBottomRight().getY() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY()) {
				return raakDottedProduct(velocity, new Vector(1, 0));
			}
		}
		return false;
	}
	
	private int distanceCenterToPointPowerOf2(Point punt) {
		return (punt.getX() - center.getX()) * (punt.getX() - center.getX()) + (punt.getY() - center.getY()) * (punt.getY() - center.getY());
	}
	
}
