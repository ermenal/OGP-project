package breakout;

public class PaddleState {
	// TODO: implement
	
	private Point center;
	private final Vector size;
	
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
	
	public void movePaddleRightBy() {
		center = new Point(center.getX() + 100, center.getY());
		if (center.getX() + size.getX() > 50000){
			center = new Point(50000 - size.getX(), center.getY());
		}
	}
	
	public void movePaddleLeftBy() {
		center = new Point(center.getX() - 100, center.getY());
		if (center.getX() - size.getX() < 0){
			center = new Point(size.getX(), center.getY());
		}
	}
	
	
}
