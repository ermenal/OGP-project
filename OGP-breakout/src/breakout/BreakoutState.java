package breakout;

// TODO: implement, document
public class BreakoutState {
	private BallState[] balls;
	private BlockState[] blocks;
	private Point bottomRight;
	private PaddleState paddle;
	
	public BreakoutState(BallState[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		this.balls = balls;
		this.blocks = blocks;
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	public BallState[] getBalls() {
		return balls.clone();
	}

	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	public PaddleState getPaddle() {
		return paddle;
	}

	public Point getBottomRight() {
		return this.bottomRight;
	}

	public void tick(int paddleDir) {
	}

	public void movePaddleRight() {
		Vector x = new Vector(10, 0);
		paddle.movePaddleRightBy(x);
	}

	public void movePaddleLeft() {
		Vector x = new Vector(10, 0);
		paddle.movePaddleLeftBy(x);
	}
	
	public boolean isWon() {
		return false;
	}

	public boolean isDead() {
		return false;
	}
}
