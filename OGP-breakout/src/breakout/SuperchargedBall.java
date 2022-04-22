package breakout;

import java.awt.Color;

public class SuperchargedBall extends Ball {
	private int time;
	
	SuperchargedBall(Point center, int diameter, Vector velocity){
		super(center, diameter, velocity);
		time = 0;
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
	
}
