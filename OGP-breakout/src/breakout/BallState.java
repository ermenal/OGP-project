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
	
	public boolean raaktPaddle(PaddleState paddle) {
		// Probleem: Soms bug, rechts en links zitten er ook niet in.
		
		Point ballOnderstePunt = new Point(center.getX(), center.getY() + diameter/2);
		if (ballOnderstePunt.getY() >= paddle.getPaddleTopLeft().getY() && paddle.getPaddleTopLeft().getX() <= ballOnderstePunt.getX() && paddle.getPaddleBottomRight().getX() >= ballOnderstePunt.getX()) {
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
	
	public void bouncePaddle(PaddleState paddle, int paddleDir) {
		Vector newVelocity = velocity.mirrorOver(new Vector(0, -1));
		int addedVelocity = paddleDir * 2;
		velocity = new Vector(newVelocity.getX() + addedVelocity, newVelocity.getY() + addedVelocity);
	}
	
	
}
