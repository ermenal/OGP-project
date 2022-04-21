package breakout;

import java.awt.Color;

//No documentation required for this class
public class BreakoutFacade {
	public PaddleState createNormalPaddleState(Point center) {
		return new PaddleState(center, new Vector(1500, 250));
	}

	public Ball createNormalBall(Point center, int diameter, Vector initBallVelocity) {
		// TODO
		return new Ball(center, diameter, initBallVelocity);
	}

	public Ball createSuperchargedBall(Point center, int diameter, Vector initBallVelocity, int lifetime) {
		// TODO
		return new Ball(center, diameter, initBallVelocity);
	}

	public BreakoutState createBreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight,
			PaddleState paddle) {
		// TODO
		return new BreakoutState(balls, blocks, bottomRight, paddle);
	}

	public BlockState createNormalBlockState(Point topLeft, Point bottomRight) {
		// TODO
		return new BlockState(topLeft, bottomRight);
	}

	public BlockState createSturdyBlockState(Point topLeft, Point bottomRight, int i) {
		// TODO
		return new BlockState(topLeft, bottomRight);
	}

	public BlockState createReplicatorBlockState(Point topLeft, Point bottomRight) {
		// TODO
		return new BlockState(topLeft, bottomRight);
	}

	public BlockState createPowerupBallBlockState(Point topLeft, Point bottomRight) {
		// TODO
		return new BlockState(topLeft, bottomRight);
	}

	public Color getColor(PaddleState paddle) {
		// TODO
		return new Color(255, 255, 255);
	}

	public Color getColor(Ball ball) {
		// TODO
		return new Color(255, 255, 255);
	}

	public Rect getLocation(PaddleState paddle) {
		// TODO
		return new Rect(paddle.getTopLeft(), paddle.getBottomRight());
	}

	public Point getCenter(Ball ball) {
		return ball.getCenter();
	}

	public int getDiameter(Ball ball) {
		return ball.getDiameter();
	}

	public Ball[] getBalls(BreakoutState breakoutState) {
		return breakoutState.getBalls();
	}

	public Color getColor(BlockState block) {
		// TODO
		return new Color(255, 255, 255);
	}

	public Rect getLocation(BlockState block) {
		return new Rect(block.getTopLeft(), block.getBottomRight());
	}
}
