package breakout;

import java.awt.Color;

public class ReplicatorBlockState extends BlockState {

	ReplicatorBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	public boolean getsDestroyedOnCollision() {
		return true;
	}
	
	public Color getColor() {
		return Color.GREEN;
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
	
	public boolean replicator() {
		return true;
	}
	
}
