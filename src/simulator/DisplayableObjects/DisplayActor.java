package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class DisplayActor extends DisplayObject implements Serializable {

	private Image imageActor;
	private Point2D location;
	private Point2D targetLocation;
	private boolean isSolid = true;
	private double direction;
	private int speed;

	private static int id = 0;
	private final static int WIDTH = 16;
	private final static int HEIGHT = 16;
	LinkedList<DisplayObject> otherActors;
	List<DisplayObject> actions = new LinkedList<DisplayObject>(); // ADD: LESLEY

	public DisplayActor(Point2D location, double direction) 
	{
		super("Actor " + id, new Dimension(16, 16), true, location, "actor");
		id++;
		this.location = location;
		this.speed = 10;
		this.imageActor = new ImageIcon("Data\\Actor.png").getImage();
		targetLocation = location;
	}

	public Point2D getLocation() {
		return location;
	}

	public double getDirection() {
		return direction;
	}

	public double getSpeed() {
		return speed;
	}

	public boolean getIsSolid() {
		return isSolid;
	}

	public void setTargetLocation(Point2D point) { 								// ADD&FIX: LESLEY; Point is now Point2D 
		targetLocation = new Point2D.Double(point.getX()*4, point.getY()*4);	// HAVE TO MULTIPLY POINTS BY 4			
	}																			//

	public void drawObject(Graphics2D g) 
	{
		g.scale(0.25, 0.25);
		g.drawImage(imageActor, getTransformation(), null);
		g.scale(4, 4);
	}
	
	public void addTarget(DisplayObject d)	// ADD: LESLEY
	{										//
		actions.add(d);						//	
	}										//

	public void update() {
		// Point2D lastLocation = location;
		// location = new Point2D.Double(/*getEntranceX(),getEntranceY()*/);

		double xD = targetLocation.getX() - location.getX();
		double yD = targetLocation.getY() - location.getY();
		double changeAngle = Math.atan2(yD, xD);
		direction = changeAngle;

		/*
		 * boolean collisionActor = false; for (DisplayObject displayObject :
		 * otherActors) { if (displayObject.getClass() == DisplayActor.class) {
		 * DisplayActor otherActor = (DisplayActor) displayObject; if
		 * (displayObject == this) continue; if (collision(otherActor))
		 * collisionActor = true; } } if (collisionActor == true) location =
		 * lastLocation; else { location = new Point2D.Double(10 *
		 * Math.sin(direction), 10 * Math.cos(direction)); }
		 */
		
		// IF TARGETLOCATION REACHED
		if (location.getX() > targetLocation.getX() - 10 && location.getX() < targetLocation.getX() + 10 &&
			location.getY() > targetLocation.getY() - 10 && location.getY() < targetLocation.getY() + 10) 
		{
			try 
			{	
				setTargetLocation(actions.get(actions.size()-1).getLocation()); // ADD: LESLEY
			} 
			catch (IndexOutOfBoundsException e)
			{
				targetLocation =  location;
			}
			
			//targetLocation = new Point2D.Double((int)Math.floor(Math.random()*3000), (int)Math.floor(Math.random()*3000)); //NEW RANDOM TARGETLOCATION
		} 
		else 
		{
			System.out.println("TARGET: " + targetLocation.toString() + " LOCATION: " + location.toString());
			double xT = targetLocation.getX();
			double yT = targetLocation.getY();
			double xL = location.getX();
			double yL = location.getY();
			if (xL < xT)
				location.setLocation(x += 10, y);
			else
				location.setLocation(x -= 10, y);
			if (yL < yT)
				location.setLocation(x, y += 10);
			else
				location.setLocation(x, y -= 10);
		}
	}

	private boolean collision(DisplayActor displayActor) {
		double x = displayActor.location.getX() - location.getX();
		double y = displayActor.location.getY() - location.getY();
		Rectangle2D.Double rectActor = new Rectangle2D.Double(x, y, WIDTH,
				HEIGHT);
		if (rectActor.intersects(rectActor))
			return false;
		else
			return true;

	}

	// a method to transform the direction for an image.
	private AffineTransform getTransformation() {
		AffineTransform tx = new AffineTransform();
		tx.translate(location.getX(), location.getY());
		tx.rotate(direction + Math.toRadians(90), WIDTH / 2, HEIGHT / 2);
		return tx;
	}
}