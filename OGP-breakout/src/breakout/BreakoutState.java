package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BreakoutState {
	
	private Ball[] balls;
	/** @representationObject */
	private BlockState[] blocks;
	private final Point bottomRight;
	private PaddleState paddle;
	private final int MaxSuperchargedTime = 10000;
	
	public final static int MAX_ELAPSED_TIME = 40;
	
	
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		
		if (balls == null || blocks == null || bottomRight == null || paddle == null) {
			throw new IllegalArgumentException("BreakoutState argument can't be null");
		}
		if (Arrays.stream(balls).anyMatch(b -> b == null) || Arrays.stream(blocks).anyMatch(b -> b == null)) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are null");
		}
		
		if (Arrays.stream(balls).anyMatch(e -> e.getCenter().getX() - e.getDiameter()/2 < 0 || e.getCenter().getX() + e.getDiameter()/2 > bottomRight.getX()) || Arrays.stream(balls).anyMatch(e -> e.getCenter().getY() + e.getDiameter()/2 > bottomRight.getY() || e.getCenter().getY() - e.getDiameter()/2 < 0) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getX() < 0 || e.getBottomRight().getX() > bottomRight.getX()) || Arrays.stream(blocks).anyMatch(e -> e.getTopLeft().getY() < 0 || e.getBottomRight().getY() > bottomRight.getY())) {
			throw new IllegalArgumentException("balls and blocks may not have elements that are outside of the field");
		}
		
		if (paddle.getBottomRight().getX() > bottomRight.getX() || paddle.getBottomRight().getY() > bottomRight.getY() || paddle.getTopLeft().getX() < 0 || paddle.getTopLeft().getY() < 0) {
			throw new IllegalArgumentException("paddle should be inside of the field");
		}
		
		this.balls = balls.clone();
		this.blocks = blocks.clone();
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	public Ball[] getBalls() {
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

	
	public void tick(int paddleDir, int elapsedTime) {
		
		if (elapsedTime > MAX_ELAPSED_TIME) {
			elapsedTime = MAX_ELAPSED_TIME;
		}
			moveAllBalls(elapsedTime);
		
			wallCollisionHandler();
			raaktOnder();
		
			blockCollisionHandler();
		
			paddleCollisionHandler(paddleDir);
	}
	
	public void moveAllBalls(int elapsedTime) {
		for (int i=0; i<balls.length; i++) {
			balls[i].moveBall(getBottomRight(), elapsedTime);
			balls[i] = balls[i].superchargedTimeHandler(MaxSuperchargedTime);
		}
	}
		public void wallCollisionHandler() {
		for (Ball ball: balls) {
			if (raaktWallLinks(ball)) {
				continue;
			}
			if (raaktWallBoven(ball)) {
				continue;
			}
			if (raaktWallRechts(ball)) {
				continue;
			}
		}
	}
	
	public boolean raaktWallLinks(Ball ball) {
		if (ball.raaktRechthoek(new Rect(new Point(-1, 0), new Point(0, bottomRight.getY())), 4)) {
			ball.bounceWall(1);
			return true;
		}
		return false;
		
	}
	
	public boolean raaktWallBoven(Ball ball) {
		if (ball.raaktRechthoek(new Rect(new Point(0, -1), new Point(getBottomRight().getX(), 0)), 1)) {
			ball.bounceWall(2);
			return true;
		}
		return false;
	}
	
	public boolean raaktWallRechts(Ball ball) {
		if (ball.raaktRechthoek(new Rect(new Point(bottomRight.getX(), 0), new Point(bottomRight.getX() + 1, bottomRight.getY())), 2)) {
			ball.bounceWall(3);
			return true;
		}
		return false;
		
	}
	
	public void raaktOnder() {
		balls = Arrays.stream(balls).filter(e -> !(e.raaktRechthoek(new Rect(new Point(0, bottomRight.getY()), new Point(bottomRight.getX(), bottomRight.getY()+1)), 3))).toArray(Ball[]::new);
	}
	
	public void blockCollisionHandler() {
		for (int j=0; j < balls.length;j++) {
			BlockState[] tempBlocks = getBlocks();
			for (int i=0; i<tempBlocks.length; i++) {
				boolean geraakt = false;
				Rect blockRechthoek = new Rect(tempBlocks[i].getTopLeft(), tempBlocks[i].getBottomRight());
				if (balls[j].raaktRechthoek(blockRechthoek, 1)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 2)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 3)) {
					geraakt = true;
				}
				if (balls[j].raaktRechthoek(blockRechthoek, 4)) {
					geraakt = true;
				}
				if (geraakt) {
				balls[j].hitBlock(blockRechthoek, tempBlocks[i].getsDestroyedOnCollision());
				
				paddle = tempBlocks[i].specialBlockHandler(paddle);
				balls[j] = tempBlocks[i].specialBlockHandler(balls[j]);
				tempBlocks[i] = tempBlocks[i].specialBlockHandler();
				}
			}
			blocks = Arrays.stream(tempBlocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	public void paddleCollisionHandler(int paddleDir) {
		Rect paddleRect = new Rect(paddle.getTopLeft(), paddle.getBottomRight());
		for (Ball ball: getBalls()) {
			boolean geraakt = false;
			if (ball.raaktRechthoek(paddleRect, 3)) {
				ball.bouncePaddle(paddleDir, 2);
				geraakt = true;
			}
			if (ball.raaktRechthoek(paddleRect, 2)) {
				ball.bouncePaddle(paddleDir, 1);
				geraakt = true;
			}
			if (ball.raaktRechthoek(paddleRect, 4)) {
				ball.bouncePaddle(paddleDir, 3);
				geraakt = true;
			}
			if (geraakt) {
				balls = Arrays.stream(paddle.hitPaddle(getBalls(), ball)).filter(b -> b != null).toArray(Ball[]::new);
				paddle = new PaddleState(paddle.getCenter(), Math.max(0, paddle.getAmountOfReplications()-1));
			}
		}
	} 
	
	public void ballHitPaddleReplicator(Ball ball) {
		
	}
	
	public void movePaddleRight(int elapsedTime) {
		paddle = paddle.movePaddleRight(getBottomRight(), elapsedTime);
	}
	
	public void movePaddleLeft(int elapsedTime) {
		paddle = paddle.movePaddleLeft(elapsedTime);
	}
	
	public boolean isWon() {
		return blocks.length == 0 && balls.length > 0;
	}

	public boolean isDead() {
		return balls.length == 0;
	}
}
