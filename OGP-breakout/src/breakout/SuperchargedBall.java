package breakout;

import java.awt.Color;

public class SuperchargedBall extends Ball {
	private int time;
	
	SuperchargedBall(Point center, int diameter, Vector velocity, int time){
		super(center, diameter, velocity);
		this.time = time;
	}

	public void moveBall(Point br, int timeElapsed) {
		super.moveBall(br, timeElapsed);
		time += timeElapsed;
	}
	
	public Color getColor() {
		return Color.GREEN;
	}
	
	public void hitBlock(Rect rect, boolean destroyed) {
		if (destroyed == false) {
			super.hitBlock(rect, destroyed);
		}else {
			return;
		}
	}
	
	public int getTime() {
		return time;
	}
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new SuperchargedBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity), time);
	}

}
