package breakout;

/**
 * 
 * @immutable
 *
 */

public class PaddleState {
	
	private final Point center;
	private final Vector size;
	
	public PaddleState(Point center, Vector size){
		this.center = center;
		this.size = size;
	}
	
	public Point getTopLeft() {
		Point tl = center.minus(size);
		return tl;
	}
	
	public Point getBottomRight() {
		Point br = center.plus(size);
		return br;
	}
	
	public Point getCenter() {
		return new Point(center.getX(), center.getY());
	}
	
	public Vector getSize() {
		return new Vector(size.getX(), size.getY());
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
