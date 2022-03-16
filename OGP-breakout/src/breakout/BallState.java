package breakout;

public class BallState {
	// TODO: implement
	private Point center;
	private int diameter;
	private Vector velocity;
	
	public BallState(Point center, int diameter, Vector velocity) {
		// Maak een ball aan met gegeven center, diameter en velocity.
		this.center = center;
		this.diameter = Math.abs(diameter);
		this.velocity = velocity;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public Point getTopLeftOfSurroundingRectangle() {
		Point topLeft = new Point(center.getX() - diameter/2, center.getY() - diameter/2);
		return topLeft;
	}
	
	public Point getBottomRightOfSurroundingRectangle() {
		Point bottomRight = new Point(center.getX() + (diameter/2), center.getY() + (diameter/2));
		return bottomRight;
	}
	
	public void moveBall() {
		Point newCenter = center.plus(velocity);
		center = newCenter;
	}
	
	public boolean raaktOnder() {
		if (center.getY() + diameter/2 >= 30000) {
			return true;
		}
		return false;
	}
	
	public boolean raaktRechts(){
		if (center.getX() + diameter/2 >= 50000){
			return true;
		}
		return false;
	}
	
	public boolean raaktLinks() {
		if (center.getX() - diameter/2 <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean raaktBoven() {
		if (center.getY() - diameter/2 <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean BalHeeftPuntGeraakt(Point punt) {
		//Checken of de x_coord of y_coord van het punt zelfs in de cirkel van de bal (middelpunt center + straal) kunnen zitten, performance van de code
		int straal = diameter/2;
		if ((punt.getX() > center.getX()+straal) || (punt.getX() < center.getX()-straal) || (punt.getY() < center.getY()+straal) || (punt.getY() < center.getY()-straal)) {
			return false;
		}
		//Checken of het punt in de cirkel (bal) ligt.
		if ((Math.pow(punt.getX() - center.getX(), 2) + (Math.pow(punt.getY() - center.getY(), 2))) > Math.pow(straal, 2)) {
			return false;
		}
		return true;
	}
	
	
}
