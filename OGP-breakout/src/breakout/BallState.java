package breakout;

/**
 * 
 * @immutable
 *
 */

public class BallState {
	// DONE: implement
	
	
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
	
	public boolean raaktOnder(Point br) {
		if (center.getY() + diameter/2 >= br.getY()) {
			return true;
		}
		return false;
	}
	
	public boolean raaktRechts(Point br){
		if (center.getX() + diameter/2 >= br.getX()){
			return true;
		}
		return false;
	}
	
	public boolean raaktLinks() {
		if (center.getX() - diameter/2 <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean raaktBoven() {
		if (center.getY() - diameter/2 <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean raaktPaddleBoven(PaddleState paddle) {
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		if (ballOnderstePunt.getY() <= paddle.getTopLeft().getY() + diameter/2 && ballOnderstePunt.getY() >= paddle.getTopLeft().getY() && paddle.getTopLeft().getX() <= ballOnderstePunt.getX() && paddle.getBottomRight().getX() >= ballOnderstePunt.getX()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, -1));
		}
		return false;
	}
	
	public boolean raaktPaddleLinks(PaddleState paddle) {
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		if (ballRechtsePunt.getX() <= paddle.getTopLeft().getX() + diameter && ballRechtsePunt.getY() <= paddle.getBottomRight().getY() && ballRechtsePunt.getY() >= paddle.getTopLeft().getY() && ballRechtsePunt.getX() >= paddle.getTopLeft().getX()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(-1, 0));
		}
		return false;
	}
	
	public boolean raaktPaddleRechts(PaddleState paddle) {
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		if (ballLinksePunt.getX() >= paddle.getBottomRight().getX() - diameter && ballLinksePunt.getY() <= paddle.getBottomRight().getY() && ballLinksePunt.getY() >= paddle.getTopLeft().getY() && ballLinksePunt.getX() <= paddle.getBottomRight().getX()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(1, 0));
		}
		return false;
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
	
	public boolean raaktBlockOnder(BlockState block) {
		Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
		if (ballBovenstePunt.getX() >= block.getTopLeft().getX() && ballBovenstePunt.getX() <= block.getBottomRight().getX() && ballBovenstePunt.getY() <= block.getBottomRight().getY() && ballBovenstePunt.getY() >= (block.getBottomRight().getY() - diameter/2)) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, 1));
		}
		return false;
	}
	
	public boolean raaktBlockLinks(BlockState block) {
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		if (ballRechtsePunt.getX() >= block.getTopLeft().getX() && ballRechtsePunt.getX() <= block.getTopLeft().getX() + diameter/2 && ballRechtsePunt.getY() <= block.getBottomRight().getY() && ballRechtsePunt.getY() >= block.getTopLeft().getY()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(-1, 0));
		}
		return false;
	}
	
	public boolean raaktBlockBoven(BlockState block) {
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		if (ballOnderstePunt.getX() >= block.getTopLeft().getX() && ballOnderstePunt.getX() <= block.getBottomRight().getX() && ballOnderstePunt.getY() >= block.getTopLeft().getY() && ballOnderstePunt.getY() <= block.getTopLeft().getY() + diameter/2) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, -1));
		}
		return false;
	}
	
	public boolean raaktBlockRechts(BlockState block) {
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		if(ballLinksePunt.getX() <= block.getBottomRight().getX() && ballLinksePunt.getX() >= block.getBottomRight().getX()-diameter/2 && ballLinksePunt.getY() >= block.getTopLeft().getY() && ballLinksePunt.getY() <= block.getBottomRight().getY()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(1, 0));
		}
		return false;
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
	
	
}
