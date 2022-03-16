package breakout;

import java.util.ArrayList;

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
		return bottomRight;
	}

	public void tick(int paddleDir) {
		moveAllBalls();
		raakMethode(paddleDir);
	}
	
	public void moveAllBalls() {
		for (BallState ball: balls) {
			ball.moveBall();
		}
	}
	
	public void raakMethode(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktOnder()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktLinks()) {
				ball.bounceWall(1);
			}
			if (ball.raaktBoven()) {
				ball.bounceWall(2);
			}
			if (ball.raaktRechts()) {
				ball.bounceWall(3);
			}
			if (ball.raaktPaddle(paddle)) {
				ball.bouncePaddle(paddle, paddleDir);
			}
		}
	}
	
	
	
	

	public void movePaddleRight() {
		paddle.movePaddleRightBy();
	}

	public void movePaddleLeft() {
		paddle.movePaddleLeftBy();
	}
	
	public boolean isWon() {
		return false;
	}

	public boolean isDead() {
		return false;
	}
}
