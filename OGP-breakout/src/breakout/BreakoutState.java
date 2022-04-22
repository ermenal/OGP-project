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
	
	private int amountOfReplications = 0;
	
	public static final int MAX_ELAPSED_TIME = 30;
	
	
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
		
		if (elapsedTime < MAX_ELAPSED_TIME) {
			moveAllBalls(elapsedTime);
		
			raaktWallLinks();
			raaktWallBoven();
			raaktWallRechts();
			raaktOnder();
		
			raaktBlockOnder();
			raaktBlockLinks();
			raaktBlockBoven();
			raaktBlockRechts();
		
			raaktPaddleLinks(paddleDir);
			raaktPaddleBoven(paddleDir);
			raaktPaddleRechts(paddleDir);
		}
	}
	
	public void moveAllBalls(int elapsedTime) {
		for (Ball ball: balls) {
			ball.moveBall(getBottomRight(), elapsedTime);
		}
	}
	
	public void raaktWallLinks() {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(new Point(-1, 0), new Point(0, bottomRight.getY())), 4)) {
				ball.bounceWall(1);
			}
		}
	}
	
	public void raaktWallBoven() {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(new Point(0, -1), new Point(getBottomRight().getX(), 0)), 1)) {
				ball.bounceWall(2);
			}
		}
	}
	
	public void raaktWallRechts() {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(new Point(bottomRight.getX(), 0), new Point(bottomRight.getX() + 1, bottomRight.getY())), 2)) {
				ball.bounceWall(3);
			}
		}
	}
	
	public void raaktOnder() {
		balls = Arrays.stream(balls).filter(e -> !(e.raaktRechthoek(new Rect(new Point(0, bottomRight.getY()), new Point(bottomRight.getX(), bottomRight.getY()+1)), 3))).toArray(Ball[]::new);
	}
	
	public void raaktBlockOnder() {
		for (Ball ball: balls) {
			for (int i=0; i<blocks.length; i++) {
				Rect blockRechthoek = new Rect(blocks[i].getTopLeft(), blocks[i].getBottomRight());
				if (ball.raaktRechthoek(blockRechthoek, 1)) {
					ball.hitBlock(blockRechthoek, blocks[i].getsDestroyedOnCollision());
					if (blocks[i].replicator()){
						amountOfReplications = 3;
						paddle = new PaddleState(paddle.getCenter(), paddle.getSize(), true);
					}
					if (blocks[i].getsDestroyedOnCollision() == false) {
						blocks[i] = new SturdyBlockState(blocks[i].getTopLeft(), blocks[i].getBottomRight(), blocks[i].getHealth()-1);
					}else {
						blocks[i] = null;
					}
				}
			}
			blocks = Arrays.stream(blocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	public void raaktBlockLinks() {
		for (Ball ball: balls) {
			for (int i=0; i<blocks.length; i++) {
				Rect blockRechthoek = new Rect(blocks[i].getTopLeft(), blocks[i].getBottomRight());
				if (ball.raaktRechthoek(blockRechthoek, 2)) {
					ball.hitBlock(blockRechthoek, blocks[i].getsDestroyedOnCollision());
					if (blocks[i].replicator()){
						amountOfReplications = 3;
						paddle = new PaddleState(paddle.getCenter(), paddle.getSize(), true);
					}
					if (blocks[i].getsDestroyedOnCollision() == false) {
						blocks[i] = new SturdyBlockState(blocks[i].getTopLeft(), blocks[i].getBottomRight(), blocks[i].getHealth()-1);
					}else {
						blocks[i] = null;
					}
				}
			}
			blocks = Arrays.stream(blocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	public void raaktBlockBoven() {
		for (Ball ball: balls) {
			for (int i=0; i<blocks.length; i++) {
				Rect blockRechthoek = new Rect(blocks[i].getTopLeft(), blocks[i].getBottomRight());
				if (ball.raaktRechthoek(blockRechthoek, 3)) {
					ball.hitBlock(blockRechthoek, blocks[i].getsDestroyedOnCollision());
					if (blocks[i].replicator()){
						amountOfReplications = 3;
						paddle = new PaddleState(paddle.getCenter(), paddle.getSize(), true);
					}
					if (blocks[i].getsDestroyedOnCollision() == false) {
						blocks[i] = new SturdyBlockState(blocks[i].getTopLeft(), blocks[i].getBottomRight(), blocks[i].getHealth()-1);
					}else {
						blocks[i] = null;
					}
				}
			}
			blocks = Arrays.stream(blocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	public void raaktBlockRechts() {
		for (Ball ball: balls) {
			for (int i=0; i<blocks.length; i++) {
				Rect blockRechthoek = new Rect(blocks[i].getTopLeft(), blocks[i].getBottomRight());
				if (ball.raaktRechthoek(blockRechthoek, 4)) {
					ball.hitBlock(blockRechthoek, blocks[i].getsDestroyedOnCollision());
					if (blocks[i].replicator()){
						amountOfReplications = 3;
						paddle = new PaddleState(paddle.getCenter(), paddle.getSize(), true);
					}
					if (blocks[i].getsDestroyedOnCollision() == false) {
						blocks[i] = new SturdyBlockState(blocks[i].getTopLeft(), blocks[i].getBottomRight(), blocks[i].getHealth()-1);
					}else {
						blocks[i] = null;
					}
				}
			}
			blocks = Arrays.stream(blocks).filter(b -> b != null).toArray(BlockState[]::new);
		}
	}
	
	public void raaktPaddleLinks(int paddleDir) {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(paddle.getTopLeft(), paddle.getBottomRight()), 2)) {
				ball.bouncePaddle(paddleDir, 1);
				if (amountOfReplications > 0) {
					ballHitPaddleReplicator(ball);
				}
			}
		}
	}
	
	public void raaktPaddleBoven(int paddleDir) {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(paddle.getTopLeft(), paddle.getBottomRight()), 3)) {
				ball.bouncePaddle(paddleDir, 2);
				if (amountOfReplications > 0) {
					ballHitPaddleReplicator(ball);
				}
			}
		}
	}
	
	public void raaktPaddleRechts(int paddleDir) {
		for (Ball ball: balls) {
			if (ball.raaktRechthoek(new Rect(paddle.getTopLeft(), paddle.getBottomRight()), 4)) {
				ball.bouncePaddle(paddleDir, 3);
				if (amountOfReplications > 0) {
					ballHitPaddleReplicator(ball);
				}
			}
		}
	}
	
	public void ballHitPaddleReplicator(Ball ball) {
		Ball[] newBalls = new Ball[balls.length + amountOfReplications];
		
		int i = 0;
		for (;i<balls.length; i++) {
			newBalls[i] = balls[i];
		}
		
		if (amountOfReplications == 1) {
			newBalls[i] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(2, -2)));
			paddle = new PaddleState(paddle.getCenter(), paddle.getSize(), false);
		}
		
		if (amountOfReplications == 2) {
			newBalls[i] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(2, -2)));
			newBalls[i+1] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(-2, 2)));
		}
		
		if (amountOfReplications == 3) {
			newBalls[i] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(2, -2)));
			newBalls[i+1] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(-2, 2)));
			newBalls[i+2] = new NormalBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity().plus(new Vector(2, 2)));
		}
		balls = newBalls;
		amountOfReplications--;
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
