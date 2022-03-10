package breakout;

public class BallState {
	// TODO: implement
	private Point center;
	private int diameter;
	private Vector velocity;
	
	public BallState(Point center, int diameter, Vector velocity) {
		// Maak een ball aan met gegeven center, diameter en velocity.
		this.center = center;
		this.diameter = diameter;
		this.velocity = velocity;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public boolean BalHeeftPuntGeraakt(Point punt) {
		//TODO: moeten we dit niet doen met de omtrek van de cirkel, kan ook op een hoek raken denk ik
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
