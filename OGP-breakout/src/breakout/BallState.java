package breakout;

/**
 * This class represents a ball on a 2D-grid
 * 
 * @immutable
 *
 */

public class BallState {
	
	private final Point center;
	private final Vector velocity;
	private final int diameter;
	
	public BallState(Point center, int diameter, Vector velocity) {
		// Maak een ball aan met gegeven center, diameter en velocity.
		this.center = center;
		this.diameter = Math.abs(diameter);
		this.velocity = velocity;
	}
	
	public int getDiameter() {
		return diameter;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public Point getTopLeftOfSurroundingRectangle() {
		Point topLeft = new Point(center.getX() - diameter/2, center.getY() - diameter/2);
		return topLeft;
	}
	
	public Point getBottomRightOfSurroundingRectangle() {
		Point bottomRight = new Point(center.getX() + (diameter/2), center.getY() + (diameter/2));
		return bottomRight;
	}
	
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
	
	public BallState bounceWall(int wallNumber) {
		
		if (wallNumber == 1) {
			// leftWall
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0));
			return new BallState(new Point(center.getX(), center.getY()), diameter, newVelocity);
		}
		if (wallNumber == 2) {
			// topWall
			Vector newVelocity = velocity.mirrorOver(new Vector(0, 1));
			return new BallState(new Point(center.getX(), center.getY()), diameter, newVelocity);
		}
		if (wallNumber == 3) {
			// righttWall
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0));
			return new BallState(new Point(center.getX(), center.getY()), diameter, newVelocity);
		}
		return null;
	}
	
	public BallState bouncePaddle(PaddleState paddle, int paddleDir, int paddleSideNumber) {
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
		// bottomSide
		if (sideNumber == 1) {
			Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
			if (ballBovenstePunt.getX() >= rechthoek.getTopLeft().getX() && ballBovenstePunt.getX() <= rechthoek.getBottomRight().getX() && ballBovenstePunt.getY() <= rechthoek.getBottomRight().getY() && ballBovenstePunt.getY() >= rechthoek.getBottomRight().getY() - diameter/2) {
				return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, 1));
			}
		}
		// leftSide
		if (sideNumber == 2) {
			Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
			if (ballRechtsePunt.getX() >= rechthoek.getTopLeft().getX() && ballRechtsePunt.getX() <= rechthoek.getTopLeft().getX() + diameter/2 && ballRechtsePunt.getY() <= rechthoek.getBottomRight().getY() && ballRechtsePunt.getY() >= rechthoek.getTopLeft().getY()) {
				return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(-1, 0));
			}
		}
		//topSide
		if (sideNumber == 3) {
			Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
			if (ballOnderstePunt.getX() >= rechthoek.getTopLeft().getX() && ballOnderstePunt.getX() <= rechthoek.getBottomRight().getX() && ballOnderstePunt.getY() >= rechthoek.getTopLeft().getY() && ballOnderstePunt.getY() <= rechthoek.getTopLeft().getY() + diameter/2) {
				return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, -1));
			}
		}
		//rightSide
		if (sideNumber == 4) {
			Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
			if(ballLinksePunt.getX() <= rechthoek.getBottomRight().getX() && ballLinksePunt.getX() >= rechthoek.getBottomRight().getX()-diameter/2 && ballLinksePunt.getY() >= rechthoek.getTopLeft().getY() && ballLinksePunt.getY() <= rechthoek.getBottomRight().getY()) {
				return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(1, 0));
			}
		}
		return false;
	}
	
}
