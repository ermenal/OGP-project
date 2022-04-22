package breakout;

import java.awt.Color;

public class SturdyBlockState extends BlockState {
	public final int health;
	
	SturdyBlockState(Point topLeft, Point bottomRight, int hp){
		super(topLeft, bottomRight);
		health = hp;
	}
	
	public boolean getsDestroyedOnCollision() {
		return health <= 1;
	}
	
	public Color getColor() {
		switch(health) {
		case 1: 
			return Color.YELLOW;
		case 2:
			return Color.ORANGE;
		default:
			return Color.RED;
		}
	}
	
	public int getHealth() {
		return health;
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
}
