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
	
	public BallState moveBall() {
		Point newCenter = center.plus(velocity);
		return new BallState(newCenter, diameter, velocity);
	}
	
	public boolean raaktOnder() {
		if (center.getY() + diameter/2 >= 30000) {
			return true;
		}
		return false;
	}
	
	public boolean raaktRechts(){
		if (center.getX() + diameter/2 >= 50000){
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
		if (ballOnderstePunt.getY() <= paddle.getPaddleTopLeft().getY() + diameter/2 && ballOnderstePunt.getY() >= paddle.getPaddleTopLeft().getY() && paddle.getPaddleTopLeft().getX() <= ballOnderstePunt.getX() && paddle.getPaddleBottomRight().getX() >= ballOnderstePunt.getX()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(0, -1));
		}
		return false;
	}
	
	public boolean raaktPaddleLinks(PaddleState paddle) {
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		if (ballRechtsePunt.getX() <= paddle.getPaddleTopLeft().getX() + diameter && ballRechtsePunt.getY() <= paddle.getPaddleBottomRight().getY() && ballRechtsePunt.getY() >= paddle.getPaddleTopLeft().getY() && ballRechtsePunt.getX() >= paddle.getPaddleTopLeft().getX()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(-1, 0));
		}
		return false;
	}
	
	public boolean raaktPaddleRechts(PaddleState paddle) {
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		if (ballLinksePunt.getX() >= paddle.getPaddleBottomRight().getX() - diameter && ballLinksePunt.getY() <= paddle.getPaddleBottomRight().getY() && ballLinksePunt.getY() >= paddle.getPaddleTopLeft().getY() && ballLinksePunt.getX() <= paddle.getPaddleBottomRight().getX()) {
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
		// 
		//oplossing: Laatste if weglaten
		//
		return new BallState(new Point(center.getX(), center.getY()), diameter, new Vector(velocity.getX(), velocity.getY()));
		//
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
		//
		//
		//oplossing: Laatste if weglaten
		return new BallState(center, diameter, velocity);
		//
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
	
	public boolean raaktBlockLeft(BlockState block) {
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
	
	public boolean raaktBlockRight(BlockState block) {
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		if(ballLinksePunt.getX() <= block.getBottomRight().getX() && ballLinksePunt.getX() >= block.getBottomRight().getX()-diameter/2 && ballLinksePunt.getY() >= block.getTopLeft().getY() && ballLinksePunt.getY() <= block.getBottomRight().getY()) {
			return raakDottedProduct(new Vector(velocity.getX(), velocity.getY()), new Vector(1, 0));
		}
		return false;
	}
	
	public BallState bounceBlock(BlockState block, int blockSideNumber) {
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
		//
		//
		//oplossing: Laatste if weglaten
		return new BallState(center, diameter, velocity);
		//
	}
	
	
}
