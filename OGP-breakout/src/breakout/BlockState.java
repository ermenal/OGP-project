package breakout;

public class BlockState {
	// TODO: implement
	private final Point topLeft;
	private final Point bottomRight;

	public BlockState(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Point getTopLeft() {
		return this.topLeft;
	}
	
	public Point getBottomRight() {
		return this.bottomRight;
	}
}
