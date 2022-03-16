package breakout;

public class PaddleState {
	// TODO: implement
	
	private Point center;
	private Vector size;
	private Vector velocity;
	
	public PaddleState(Point center, Vector size){
		this.center = center;
		this.size = size;
	}
	
	public Point getPaddleTopLeft() {
		Point tl = center.minus(size);
		return tl;
	}
	
	public Point getPaddleBottomRight() {
		Point br = center.plus(size);
		return br;
	}
	
	public Vector getPaddleVelocity() {
		return this.velocity;
	}
	
	public void movePaddleRightBy(Vector x) {
		center.plus(x);
	}
	
	public void movePaddleLeftBy(Vector x) {
		center.minus(x);
	}
	
	
}
