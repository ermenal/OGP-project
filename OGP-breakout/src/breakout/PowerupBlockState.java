package breakout;

import java.awt.Color;

public class PowerupBlockState extends BlockState {

	PowerupBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	public boolean getsDestroyedOnCollision() {
		return true;
	}
	
	public Color getColor() {
		return Color.BLUE;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj.getClass() != this.getClass()))
			return false;
		SturdyBlockState other = (SturdyBlockState) obj;
		if (!(other.getColor().equals(this.getColor())))
			return false;
		if (!(other.getTopLeft().equals(this.getTopLeft())))
			return false;
		if (!(other.getBottomRight().equals(this.getBottomRight())))
			return false;
		return true;
	}
	
	public Ball specialBlockHandler(Ball ball) {
		return new SuperchargedBall(ball.getCenter(), ball.getDiameter(), ball.getVelocity(), 0);
	}
}
