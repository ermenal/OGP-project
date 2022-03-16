package breakout;

public class BallState {
	// TODO: implement
	private Point center;
	private int diameter;
	private Vector velocity;
	
	public BallState(Point center, int diameter, Vector velocity) {
		// Maak een ball aan met gegeven center, diameter en velocity.
		this.center = center;
		this.diameter = Math.abs(diameter);
		this.velocity = velocity;
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
	
	public void moveBall() {
		Point newCenter = center.plus(velocity);
		center = newCenter;
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
		// Probleem: Soms bug, rechts en links zitten er ook niet in.
		
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		
		if (ballOnderstePunt.getY() <= paddle.getPaddleTopLeft().getY() + diameter/2 && ballOnderstePunt.getY() >= paddle.getPaddleTopLeft().getY() && paddle.getPaddleTopLeft().getX() <= ballOnderstePunt.getX() && paddle.getPaddleBottomRight().getX() >= ballOnderstePunt.getX()) {
			return true;
		}
		return false;
	}
	
	public boolean raaktPaddleLinks(PaddleState paddle) {
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		if (ballRechtsePunt.getX() <= paddle.getPaddleTopLeft().getX() + diameter/2 && ballRechtsePunt.getY() <= paddle.getPaddleBottomRight().getY() && ballRechtsePunt.getY() >= paddle.getPaddleTopLeft().getY() && ballRechtsePunt.getX() >= paddle.getPaddleTopLeft().getX()) {
			return true;
		}
		return false;
	}
	
	public boolean raaktPaddleRechts(PaddleState paddle) {
		Point ballLinksePunt = new Point(center.getX() - diameter/2, center.getY());
		if (ballLinksePunt.getX() >= paddle.getPaddleBottomRight().getX() - diameter/2 && ballLinksePunt.getY() <= paddle.getPaddleBottomRight().getY() && ballLinksePunt.getY() >= paddle.getPaddleTopLeft().getY() && ballLinksePunt.getX() <= paddle.getPaddleBottomRight().getX()) {
			return true;
		}
		return false;
	}
	
	public void bounceWall(int wallNumber) {
		if (wallNumber == 1) {
			// leftWall
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0));
			velocity = newVelocity;
		}
		if (wallNumber == 2) {
			// topWall
			Vector newVelocity = velocity.mirrorOver(new Vector(0, 1));
			velocity = newVelocity;
		}
		if (wallNumber == 3) {
			// righttWall
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0));
			velocity = newVelocity;
		}
	}
	
	public void bouncePaddle(PaddleState paddle, int paddleDir, int paddleSideNumber) {
		int addedVelocity = paddleDir * 2;
		if (paddleSideNumber == 1) {
			// leftSide
			Vector newVelocity = velocity.mirrorOver(new Vector(-1, 0));
			velocity = new Vector(newVelocity.getX() + addedVelocity, newVelocity.getY());
		}
		if (paddleSideNumber == 2) {
			// topSide
			Vector newVelocity = velocity.mirrorOver(new Vector(0, -1));
			velocity = new Vector(newVelocity.getX() + addedVelocity, newVelocity.getY());
		}
		if (paddleSideNumber == 3) {
			// rightSide
			Vector newVelocity = velocity.mirrorOver(new Vector(1, 0));
			velocity = new Vector(newVelocity.getX() + addedVelocity, newVelocity.getY());
		}
		
	}
	
	public boolean raaktBlockOnder(BlockState block) {
		Point ballBovenstePunt = new Point(center.getX(), center.getY() - diameter/2);
		if (ballBovenstePunt.getX() >= block.getTopLeft().getX() && ballBovenstePunt.getX() <= block.getBottomRight().getX() && ballBovenstePunt.getY() <= block.getBottomRight().getY() && ballBovenstePunt.getY() >= (block.getBottomRight().getY() - diameter/2)) {
			return true;
		}
		return false;
	}
	
	public boolean raaktBlockLeft(BlockState block) {
		Point ballRechtsePunt = new Point(center.getX() + diameter/2, center.getY());
		if (ballRechtsePunt.getX() >= block.getTopLeft().getX() && ballRechtsePunt.getX() <= block.getTopLeft().getX() + diameter/2 && ballRechtsePunt.getY() <= block.getBottomRight().getY() && ballRechtsePunt.getY() >= block.getTopLeft().getY()) {
			return true;
		}
		return false;
	}
	
	public boolean raaktBlockBoven(BlockState block) {
		return false;
	}
	
	public void bounceBlock(BlockState block, int blockSideNumber) {
		if (blockSideNumber == 1)
			//bottomSide
			velocity = velocity.mirrorOver(new Vector(0, 1));
	}
	
	
}
