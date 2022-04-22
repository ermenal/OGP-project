package breakout;

import java.awt.Color;

public class NormalBall extends Ball{
	
	public NormalBall(Point center, int Diameter, Vector velocity) {
		super(center, Diameter, velocity);
	}

	public Color getColor() {
		return Color.WHITE;
	}
	
	public Ball cloneBallWithChangedVelocity(Vector addedVelocity) {
		return new NormalBall(getCenter(), getDiameter(), getVelocity().plus(addedVelocity));
	}
}
