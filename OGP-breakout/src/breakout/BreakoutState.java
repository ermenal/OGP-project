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
			
			for (BlockState block: blocks) {
				if (ball.raaktBlockOnder(block)) {
					ball.bounceBlock(block, 1);
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockLeft(block)) {
					ball.bounceBlock(block, 2);
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockBoven(block)) {
					ball.bounceBlock(block, 3);
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockRight(block)) {
					ball.bounceBlock(block, 4);
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
			}
			
			if (ball.raaktPaddleLinks(paddle)) {
				ball.bouncePaddle(paddle, paddleDir, 1);
			}
			if (ball.raaktPaddleBoven(paddle)) {
				ball.bouncePaddle(paddle, paddleDir, 2);
			}
			if (ball.raaktPaddleRechts(paddle)) {
				ball.bouncePaddle(paddle, paddleDir, 3);
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
		if (blocks.length == 0) {
		return true;
		}
		return false;
	}

	public boolean isDead() {
		if (balls.length == 0) {
			return true;
		}
		return false;
	}
}
