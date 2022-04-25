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

public class Ball {
	
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
	
	public Ball(Point center, int diameter, Vector velocity) {
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
	 * Returns the ball's color. Either white if it is a normal ball, or green if it is a supercharged ball.
	 * 
	 * @post 
	 * 		| result == Color.GREEN || result == Color.WHITE
	 */
	public abstract Color getColor();
	
	/**
	 * Returns the amount of time the ball has been supercharged for. If the ball is not supercharged, the result will be {@code -1}
	 * 
	 * @post
	 * 		| result == -1 || result >= 0
	 */
	
	public int getTime() {
		return -1;
	}
	
	/**
	 * Returns either a normal ball or a supercharged ball, that has an altered velocity in accordance with {@code addedVelocity}
	 * 
	 * @pre {@code addedVelocity} is not {@code null}
	 * 		| addedVelocity != null
	 */
	
	public abstract Ball cloneBallWithChangedVelocity(Vector addedVelocity);
	
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
	
	public void bounceWall(int wallNumber) {
		
		if (wallNumber == 1) {
			// leftWall
			velocity = velocity.mirrorOver(new Vector(1, 0));
		}
		if (wallNumber == 2) {
			// topWall
			velocity = velocity.mirrorOver(new Vector(0, 1));
		}
		if (wallNumber == 3) {
			// righttWall
			velocity = velocity.mirrorOver(new Vector(-1, 0));
		}
	}
	
	public void bouncePaddle(int paddleDir, int paddleSideNumber) {
		int addedVelocity = paddleDir * 2;
		if (paddleSideNumber == 1) {
			// leftSide
			velocity = velocity.mirrorOver(new Vector(-1, 0)).plus(new Vector(addedVelocity, 0));
		}
		if (paddleSideNumber == 2) {
			// topSide
			velocity = velocity.mirrorOver(new Vector(0, -1)).plus(new Vector(addedVelocity, 0));
		}
		if (paddleSideNumber == 3) {
			// rightSide
			velocity = velocity.mirrorOver(new Vector(1, 0)).plus(new Vector(addedVelocity, 0));
		}
	}
	
	/**
	 * Returns the dot product of the  and the vector {@code n} and vector constructed by scaling {@code velocity} with -1
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
	
	public void hitBlock(Rect rect, boolean destroyed) {
			if (raaktRechthoek(rect, 1))
				velocity = velocity.mirrorOver(new Vector(0, 1));
			if (raaktRechthoek(rect, 2))
				velocity = velocity.mirrorOver(new Vector(-1, 0));
			if (raaktRechthoek(rect, 3))
				velocity = velocity.mirrorOver(new Vector(0, -1));
			if (raaktRechthoek(rect, 4))
				velocity = velocity.mirrorOver(new Vector(1, 0));
		
	}
	
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
	 * Returns the distance of the center of the ball to a line constructed by 2 points to the power of 2
	 * 
	 * @pre {@code punt1} is not {@code null}
	 * 		| punt1 != null
	 * @pre {@code punt2} is not {@code null}
	 * 		| punt2 != null
	 * @pre {@code punt1} must have either a different x-coordinate to {@code punt2}, a different y-coordinate or both
	 * 		| punt1.getX() != punt2.getX() || 
	 * 		|	punt1.getY() != punt2.getY()
	 * 
	 * @post The result is the distance between the center of the ball and the line constructed by the 2 given points
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
