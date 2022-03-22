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
	
	public PaddleState movePaddleRightBy(Point br) {
		int moveBy = 100;
		Point newCenter = new Point(center.getX() + moveBy, center.getY());
		if (newCenter.getX() + size.getX() > br.getX()){
			newCenter = new Point(br.getX() - size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	public PaddleState movePaddleLeftBy() {
		int moveBy = 100;
		Point newCenter = new Point(center.getX() - moveBy, center.getY());
		if (newCenter.getX() - size.getX() < 0){
			newCenter = new Point(size.getX(), newCenter.getY());
		}
		return new PaddleState(newCenter, size);
	}
	
	
}
