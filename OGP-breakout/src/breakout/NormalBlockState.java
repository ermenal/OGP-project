package breakout;

import java.awt.Color;

public class NormalBlockState extends BlockState {
	
	NormalBlockState(Point topLeft, Point bottomRight){
		super(topLeft, bottomRight);
	}
	
	public boolean getsDestroyedOnCollision() {
		return true;
	}
	
	public Color getColor() {
		return Color.WHITE;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlockState))
			return false;
		NormalBlockState other = (NormalBlockState) obj;
		if (!(other.getColor().equals(this.getColor())))
			return false;
		if (!(other.getTopLeft().equals(this.getTopLeft())))
			return false;
		if (!(other.getBottomRight().equals(this.getBottomRight())))
			return false;
		return true;
	}
	
	public String soortBlock() {
		return "Normal";
	}
	
}
