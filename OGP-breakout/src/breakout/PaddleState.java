package breakout;

public class PaddleState {
	// TODO: implement
	
	private final Point center;
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
	
	public PaddleState movePaddleRightBy() {
		Point newCenter = new Point(center.getX() + 10, center.getY());
		if (newCenter.getX() + size.getX() > 50000){
			newCenter = new Point(50000 - size.getX(), center.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	public PaddleState movePaddleLeftBy() {
		Point newCenter = new Point(center.getX() - 10, center.getY());
		if (newCenter.getX() - size.getX() < 0){
			newCenter = new Point(size.getX(), center.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	
}
