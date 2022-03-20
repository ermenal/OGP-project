package breakout;

import java.util.ArrayList;

// TODO: implement, document
public class BreakoutState {
	private BallState[] balls;
	private BlockState[] blocks;
	private final Point bottomRight;
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
		ArrayList<BallState> newBalls = new ArrayList<BallState>();
		for (BallState ball: balls) {
			newBalls.add(ball.moveBall());
		}
		balls = newBalls.toArray(new BallState[] {});
	}
	
	public void raakMethode(int paddleDir) {
		for (BallState ball: balls) {
			if (ball.raaktLinks()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(1));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktRechts()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(3));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktBoven()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(okBal.bounceWall(2));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktOnder()) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			
			for (BlockState block: blocks) {
				if (ball.raaktBlockOnder(block)) {
					
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 1));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockLeft(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 2));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockBoven(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 3));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
					ArrayList<BlockState> newBlocks = new ArrayList<BlockState>();
					for (BlockState okBlock: blocks) {
						if (okBlock != block){
							newBlocks.add(okBlock);
						}
					}
					blocks = newBlocks.toArray(new BlockState[] {});
				}
				if (ball.raaktBlockRight(block)) {
					ArrayList<BallState> newBalls = new ArrayList<BallState>();
					for (BallState okBal: balls) {
						if (okBal != ball){
							newBalls.add(okBal);
						}else {
							newBalls.add(okBal.bounceBlock(block, 4));
						}
					}
					balls = newBalls.toArray(new BallState[] {});
					
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
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 1));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktPaddleBoven(paddle)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 2));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			if (ball.raaktPaddleRechts(paddle)) {
				ArrayList<BallState> newBalls = new ArrayList<BallState>();
				for (BallState okBal: balls) {
					if (okBal != ball){
						newBalls.add(okBal);
					}else {
						newBalls.add(ball.bouncePaddle(paddle, paddleDir, 3));
					}
				}
				balls = newBalls.toArray(new BallState[] {});
			}
			
		}
	}
	
	
	
	

	public void movePaddleRight() {
		paddle = paddle.movePaddleRightBy(getBottomRight());
	}

	public void movePaddleLeft() {
		paddle = paddle.movePaddleLeftBy();
	}
	
	public boolean isWon() {
		if (blocks.length == 0 && balls.length > 0) {
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
