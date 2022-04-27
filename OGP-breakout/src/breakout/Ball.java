package breakout;

import java.awt.Color;

/**
 * This class represents a ball on a 2D-grid
 *
 * @invar | getCenter() != null
 * @invar | getDiameter() >= 0
 * @invar | getVelocity() != null
 * @invar | getTime() == -1 || getTime() >= 0
 */

public abstract class Ball {
	
	/**
	 * @invar | center != null
	 * @invar | diameter >= 0
	 * @invar | velocity != null
	 */
	
	private Point center;
	private final int diameter;
	private Vector velocity;
	
	/**
	 * @pre | center != null
	 * @pre | velocity != null
	 * 
	 * @post | getCenter() == center
	 * @post | getDiameter() == Math.abs(diameter)
	 * @post | getVelocity() == velocity
	 */
	
	public Ball(Point center, int diameter, Vector velocity) {
		this.center = center;
		this.diameter = Math.abs(diameter);
		this.velocity = velocity;
	}
	
	/**
	 * Returns the object's diameter
	 * 
	 * @immutable This object is associated with the same diameter throughout its lifetime
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
	 * Returns the ball's color. Either white if it is a normal ball, or green if it is a supercharged ball.
	 * 
	 * @post The result is either green or white
	 * 		| result == Color.GREEN || result == Color.WHITE
	 */
	
	public abstract Color getColor();
	
	/**
	 * Returns {@code this} or a new normal ball, depending on whether or not the ball is currently supercharged and for how long.
	 * 
	 * @pre Argument {@code maxTime} should not be less than 0.
	 * 		| maxTime >= 0
	 * @pre Argument {@code elapsedTime} should be greater than 0.
	 * 		| elapsedTime > 0
	 * 
	 * @mutates | this
	 * 
	 * @creates | result
	 * 
	 * @post The resulting ball's center, diameter and velocity have remained unchanged.
	 * 		| result.getCenter() == getCenter() && 
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getVelocity() == getVelocity()
	 * @post If this ball isn't supercharged, {@code this} is returned. 
	 * 		 If this ball is supercharged, a new normal ball is returned if adding the elapsed time to this ball's time 
	 * 		 it has been supercharged for would result in a value larger than or equal to {@code maxTime}. 
	 * 		 If this doesn't result in a value larger than or equal to {@code maxTime}, the elapsed time is added onto the ball's current time and {@code this} is returned.
	 * 		| result == this && getTime() < maxTime ||
	 * 		| result.getClass().equals(NormalBall.class) && getTime() + elapsedTime >= maxTime
	 */
	
	public Ball superchargedTimeHandler(int elapsedTime, int maxTime) {
		return this;
	}
	
	/**
	 * Returns the amount of time the ball has been supercharged for in milliseconds. If the ball is not supercharged, the result will be {@code -1}.
	 * This method is used for formal documentation.
	 * 
	 * @post The result is -1 for a normal ball, or not smaller than 0 for a supercharged ball
	 * 		| result == -1 && getClass().equals(NormalBall.class) || 
	 * 		| result >= 0 && getClass().equals(SuperchargedBall.class)
	 */
	
	public int getTime() {
		return -1;
	}
	
	/**
	 * Returns either a normal ball or a supercharged ball, that has an altered velocity in accordance with {@code addedVelocity}.
	 * 
	 * @pre Argument {@code addedVelocity} is not {@code null}
	 * 		| addedVelocity != null
	 * 
	 * @inspects | this 
	 * 
	 * @creates | result
	 * 
	 * @post The result is the same kind of ball as {@code this}
	 * 		| result.getClass().equals(getClass())
	 * 
	 * @post The resulting ball's velocity is the result of adding {@code addedVelocity} to this ball's velocity.
	 * 		| result.getVelocity().equals(getVelocity().plus(addedVelocity))
	 * 
	 * @post The resulting ball's center, time it has been supercharged for and diameter are the same as {@code this}.
	 * 		| result.getCenter().equals(getCenter()) &&
	 * 		| result.getDiameter() == getDiameter() && 
	 * 		| result.getTime() == getTime()
	 */
	
	public abstract Ball cloneBallWithChangedVelocity(Vector addedVelocity);
	
	/**
	 * Checks if {@code this} is the same class as {@code obj}. 
	 * 
	 * @post The result is {@code true} if {@code obj} is a {@code SuperchargedBall} or a {@code NormalBall} with the same properties as {@code this}.
	 * 		 Returns {@code false} if this is not the case or if {@code obj} is {@code null}.
	 * 	| result == ( (obj != null) && ( (
	 * 	|		obj.getClass().equals(NormalBall.class) &&  
	 * 	|			getClass().equals(NormalBall.class) &&
	 * 	|			((NormalBall)obj).getCenter().equals(getCenter()) && 
	 * 	|			((NormalBall)obj).getDiameter() == getDiameter() && 
	 * 	|			((NormalBall)obj).getVelocity().equals(getVelocity()) ) || (
	 * 	| 		obj.getClass().equals(SuperchargedBall.class) && 
	 * 	|			getClass().equals(SuperchargedBall.class) &&
	 * 	|			((SuperchargedBall)obj).getCenter().equals(getCenter()) && 
	 * 	|			((SuperchargedBall)obj).getDiameter() == getDiameter() && 
	 * 	|			((SuperchargedBall)obj).getVelocity().equals(getVelocity()) && 
	 * 	|			((SuperchargedBall)obj).getTime() == getTime() ) ) )
	 */
	
	@Override
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return getClass() == obj.getClass();
	
	}
	
	/** 
	 * Changes the ball's center according with its velocity and the amount of milliseconds since the last time it moved.
	 * 
	 * @pre Argument {@code br} is not {@code null} 
	 * 		| br != null
	 * @pre Argument {@timeElapsed} is greater than 0
	 * 		| timeElapsed > 0
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's velocity and diameter remained unchanged.
	 * 		| getDiameter() == old(getDiameter()) && 
	 * 		| getVelocity() == old(getVelocity())
	 * 
	 * @post The ball has moved according to its velocity and the time since it last moved, keeping in mind that it can't go outside of the field.
	 * 		| getCenter().getX() == old(getCenter()).plus(getVelocity().scaled(timeElapsed)).getX()  || 
	 * 		| getCenter().getX() == getDiameter()/2  || 
	 * 		| getCenter().getX() == br.getX() - getDiameter()/2 ||
	 * 		| getCenter().getY() == getDiameter()/2  ||
	 * 		| getCenter().getY() == br.getY() - getDiameter()/2
	 * 
	 */
	
	public void moveBall(Point br, int timeElapsed) {
		Point newCenter = center.plus(velocity.scaled(timeElapsed));
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
		center = newCenter;
	}
	
	/**
	 * Changes the ball's velocity after it bounced against a certain wall, indicated by the {@code wallNumber} parameter.
	 * 			(1 indicates the left wall, 2 indicates the top wall and 3 indicates the right wall)
	 * 
	 * @pre Argument {@code wallNumber} should be 1, 2 or 3, indicating which wall the ball made contact with.
	 * 		| wallNumber == 1 || wallNumber == 2 || wallNumber == 3
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's center and diameter remained the same.
	 * 		| getCenter() == old(getCenter()) && 
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post The ball's velocity has been mirrored in accordance with the given {@code wallNumber}.
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(1, 0))) && wallNumber == 1|| 
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) && wallNumber == 2 || 
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) && wallNumber == 3
	 */
	
	public void bounceWall(int wallNumber) {
		
		if (wallNumber == 1) {
			// leftWall
			velocity = velocity.mirrorOver(new Vector(1, 0));
			return;
		}
		if (wallNumber == 2) {
			// topWall
			velocity = velocity.mirrorOver(new Vector(0, 1));
			return;
		}
		if (wallNumber == 3) {
			// righttWall
			velocity = velocity.mirrorOver(new Vector(-1, 0));
		}
	}
	
	/**
	 * Changes the ball's velocity after it bounced against a given side of the paddle, indicated by {@code paddleSideNumber}.
	 * 			(1 indicates left side, 2 indicates top side and 3 indicates right side)
	 * and speeding the ball up or down in the horizontal direction by 2 units according to the paddle's current direction {@code paddleDir}:
	 * 			(-1 indicates the paddle is moving left, 0 indicates it's standing still and 1 indicates it's moving right)
	 * 
	 * @pre Argument {@code paddleDir} should be -1, 0 or 1
	 * 		| paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 * @pre Argument {@code paddleSideNumber} should be 1, 2 or 3
	 * 		| paddleSideNumber == 1 || paddleSideNumber == 2 || paddleSideNumber == 3
	 * 
	 * @mutates | this
	 * 
	 * @post The ball's center and diameter have remained unchanged.
	 * 		| getCenter() == old(getCenter()) && 
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post The ball's velocity has been mirrored in accordance with the given {@code paddleSideNumber} 
	 *  	 and its X-component has been changed in accordance with the paddle's given direction {@code paddleDir}.
	 *  	| paddleSideNumber == 1 &&
	 *  	| 	getVelocity().equals
	 *  	|		(old(getVelocity()).mirrorOver(new Vector(-1, 0)).plus(new Vector(paddleDir*2, 0)) ) ||
	 *  	| paddleSideNumber == 2 &&
	 *  	| 	getVelocity().equals
	 *  	|		(old(getVelocity()).mirrorOver(new Vector(0, -1)).plus(new Vector(paddleDir*2, 0)) ) ||
	 *  	| paddleSideNumber == 3 &&
	 *  	| 	getVelocity().equals
	 *  	|		(old(getVelocity()).mirrorOver(new Vector(1, 0)).plus(new Vector(paddleDir*2, 0)) )
	 */
	
	public void bouncePaddle(int paddleDir, int paddleSideNumber) {
		int addedVelocity = paddleDir * 2;
		if (paddleSideNumber == 1) {
			// leftSide
			velocity = velocity.mirrorOver(new Vector(-1, 0)).plus(new Vector(addedVelocity, 0));
			return;
		}
		if (paddleSideNumber == 2) {
			// topSide
			velocity = velocity.mirrorOver(new Vector(0, -1)).plus(new Vector(addedVelocity, 0));
			return;
		}
		if (paddleSideNumber == 3) {
			// rightSide
			velocity = velocity.mirrorOver(new Vector(1, 0)).plus(new Vector(addedVelocity, 0));
		}
	}
	
	/**
	 * Returns the dot product of the vector {@code n} and the vector constructed by scaling {@code velocity} with -1. 
	 * 
	 * @pre {@code velocity} should not be null
	 * 		| velocity != null
	 * @pre {@code n} should not be null
	 * 		| n != null
	 * 
	 * @post The result is true if the angle between the velocity and vector v is sharp
	 * 		| result == velocity.scaled(-1).product(n) >= 0
	 */
	
	public boolean raakDottedProduct(Vector velocity, Vector n) {
		Vector v = velocity.scaled(-1);
		return v.product(n) >= 0;
	}
	
	/**
	 * Changes this ball's velocity after it bounced against a block that is presented as {@code rect}, depending on what kind of ball this is.
	 * 
	 * @pre {@code rect} is not {@code null}
	 * 		| rect != null
	 * @pre This ball hit the block on one of its sides
	 * 		| raaktRechthoek(rect, 1) || 
	 * 		| raaktRechthoek(rect, 2) || 
	 * 		| raaktRechthoek(rect, 3) || 
	 * 		| raaktRechthoek(rect, 4)
	 * 
	 * @mutates | this
	 * 
	 * @post This ball's center and diameter remained unchanged.
	 * 		| getCenter() == old(getCenter()) &&
	 * 		| getDiameter() == old(getDiameter())
	 * 
	 * @post Depending on which side this ball hit the block on, which kind of ball this is
	 * 		 and if the block is destroyable or not, this ball's velocity got changed accordingly.
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, 1))) ||
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(-1, 0))) ||
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(0, -1))) ||
	 * 		| getVelocity().equals(old(getVelocity()).mirrorOver(new Vector(1, 0))) ||
	 * 		| getVelocity().equals(old(getVelocity()))
	 */
	
	public void hitBlock(Rect rect, boolean destroyed) {
			if (raaktRechthoek(rect, 1)) {
				velocity = velocity.mirrorOver(new Vector(0, 1));
				return;
			}
			if (raaktRechthoek(rect, 2)) {
				velocity = velocity.mirrorOver(new Vector(-1, 0));
				return;
			}
			if (raaktRechthoek(rect, 3)) {
				velocity = velocity.mirrorOver(new Vector(0, -1));
				return;
			}
			if (raaktRechthoek(rect, 4))
				velocity = velocity.mirrorOver(new Vector(1, 0));
		
	}
	
	/**
	 * Returns true if the ball has hit {@code rechthoek} on the given side, indicated by {@code sideNumber}.
	 * 			(1 indicates the bottom side, 2 indicates the left side, 3 indicates the top side and 4 indicates the right side)
	 * 
	 * @pre Argument {@code sideNumber} should be 1, 2, 3 or 4
	 * 		| sideNumber == 1 || sideNumber == 2 || sideNumber == 3 || sideNumber == 4
	 * @pre Argument {@code rechthoek} should not be {@code null}
	 * 		| rechthoek != null
	 * 
	 * @inspects | this
	 * 
	 * @post The result is true if the distance between the circle's center to the given side is smaller than or equal to the circle's radius.
	 * 		| result == ((Math.max(rechthoek.getTopLeft().getX(), Math.min(getCenter().getX(), rechthoek.getBottomRight().getX())) - getCenter().getX()) -
	 * 		|			(Math.max(rechthoek.getTopLeft().getX(), Math.min(getCenter().getX(), rechthoek.getBottomRight().getX())) - getCenter().getX()) + 
	 * 		|		(Math.max(rechthoek.getTopLeft().getY(), Math.min(getCenter().getY(), rechthoek.getBottomRight().getY())) - getCenter().getY()) * 
	 * 		|			(Math.max(rechthoek.getTopLeft().getY(), Math.min(getCenter().getY(), rechthoek.getBottomRight().getY())) - getCenter().getY())) 
	 * 		|	<= getDiameter()/2 * getDiameter()/2 &&
	 * 		| 		(getVelocity().scaled(-1).product(new Vector (0, 1)) >= 0 || 
	 * 		|		getVelocity().scaled(-1).product(new Vector(-1, 0)) >= 0 ||
	 * 		|		getVelocity().scaled(-1).product(new Vector(0, -1)) >= 0 ||
	 * 		|		getVelocity().scaled(-1).product(new Vector(1, 0)) >= 0) ||
	 * 		| result == false
	 */
	
	public boolean raaktRechthoek(Rect rechthoek, int sideNumber) {
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		// bottomSide
		if (sideNumber == 1) {
			if (ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && center.getY() >= rechthoek.getBottomRight().getY() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY()) {
				Point linksOnder = new Point(rechthoek.getTopLeft().getX(), rechthoek.getBottomRight().getY());
				Point rechtsOnder = rechthoek.getBottomRight();
				if (getDiameter()/2 * getDiameter()/2 >= distanceCenterTo2Points(linksOnder, rechtsOnder)) {
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
	 * Returns the distance of the center of the ball to a line constructed by 2 points to the power of 2.
	 * 
	 * @pre {@code punt1} is not {@code null}
	 * 		| punt1 != null
	 * @pre {@code punt2} is not {@code null}
	 * 		| punt2 != null
	 * @pre {@code punt1} must have either a different x-coordinate to {@code punt2}, a different y-coordinate or both.
	 * 		| punt1.getX() != punt2.getX() || 
	 * 		|	punt1.getY() != punt2.getY()
	 * 
	 * @inspects | this
	 * 
	 * @post The result is the distance between the center of the ball and the line constructed by the 2 given points.
	 * 		| result == 
	 * 		|	((punt2.getX() - punt1.getX()) * (punt1.getY() - getCenter().getY()) - 
	 * 		| 			(punt1.getX() - getCenter().getX()) * (punt2.getY() - punt1.getY())) * 
	 * 		|		((punt2.getX() - punt1.getX()) * (punt1.getY() - getCenter().getY()) - 
	 * 		| 			(punt1.getX() - getCenter().getX()) * (punt2.getY() - punt1.getY())) / 
	 * 		|	((punt2.getX() - punt1.getX()) * (punt2.getX() - punt1.getX()) + 
	 * 		|		(punt2.getY() - punt1.getY()) * (punt2.getY() - punt1.getY()))
	 */
	
	private int distanceCenterTo2Points(Point punt1, Point punt2) {
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
