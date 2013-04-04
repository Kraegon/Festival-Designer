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

import simulator.playfield.SimulationPanel;

@SuppressWarnings("serial")
public class DisplayActor extends DisplayObject implements Serializable {

	private Image imageActor;
	private Point2D location;
	private Point2D targetLocation;
	private Point2D lastTargetLocation;
	private Point2D lastLocation;
	
	private boolean isSolid = true;
	private double direction;
	private double speed;
	private double boundary = 5;

	private static int id = 0;
	private boolean visible = true;
	private final static int WIDTH = 30;
	private final static int HEIGHT = 30;
	List<DisplayTargetPoint> actions = new LinkedList<DisplayTargetPoint>();

	public DisplayActor(Point2D location, double direction, DisplayTargetPoint firstTarget) 
	{
		super("Actor " + id, new Dimension(16, 16), true, location, "actor");
		id++;
		this.location = location;
		this.speed = 1 + Math.random() * 1;
		this.imageActor = new ImageIcon("Data\\Actor.png").getImage();
		
		addTarget(firstTarget);			
		setTargetLocation(firstTarget.getLocation());
		lastLocation = location;
		lastTargetLocation = firstTarget.getLocation();
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

	public void drawObject(Graphics2D g) 
	{
		if (visible)
		{
			g.drawImage(imageActor, getTransformation(), null);
		}
	}
	
	public void setTargetLocation(Point2D point) 		
	{ 																	
		targetLocation = new Point2D.Double(point.getX(), point.getY());	
	}	
	
	public DisplayTargetPoint getDisplayTargetPoint() {
		return actions.get(0);
	}

	public void addTarget(DisplayTargetPoint d)
	{									
		actions.add(d);					
	}	

	public Point2D getRandomPoint2D()
	{			
		return (new Point2D.Double(lastTargetLocation.getX() - 75 + Math.random()*150,	
				 			       lastTargetLocation.getY() - 75 + Math.random()*150)); 
	}
	
	public void update(List<DisplayObject> otherObjects) 
	{	
		if (visible)
		{
			location = new Point2D.Double(location.getX() + speed * Math.cos(direction), 
					  					  location.getY() + speed * Math.sin(direction));
	
			double xD = targetLocation.getX() - location.getX();
			double yD = targetLocation.getY() - location.getY();
			double changeAngle = Math.atan2(yD, xD);
			direction = changeAngle;
			
			boolean crash = false;   
			for (DisplayObject displayObjects : otherObjects) 
			{
				DisplayObject otherObject = displayObjects;
				if (collisionObject(this, otherObject))
				{
					crash = true;
				}
				if (crash)
				{
					location = new Point2D.Double(location.getX() - speed * Math.cos(90), location.getY() - speed * Math.sin(180));
				}
			}
				
			// IF TARGETLOCATION REACHED
			if (location.getX() > targetLocation.getX() - boundary && location.getX() < targetLocation.getX() + boundary &&
				location.getY() > targetLocation.getY() - boundary && location.getY() < targetLocation.getY() + boundary) 
			{	
				if (actions.get(0).isExit())
				{
					SimulationPanel.getInstance().killActor(getName());
					return;
				}
				
				if (actions.get(0).hasNeighbour())
				{
					if (actions.get(0).getStageIsActive())
					{
						// ADD RANDOM TARGETLOCATION TO LIST
						setTargetLocation(getRandomPoint2D());
					}
					else
					{
						addTarget(actions.get(0).getRandomNeighbour()); 	// add a neighbour to list
						actions.remove(0); 									// remove location from list
						setTargetLocation(actions.get(0).getLocation()); 	// set neighbor as new targetlocation
						lastTargetLocation = actions.get(actions.size()-1).getLocation();
					}
				}
				else
				{
					// ADD RANDOM TARGETLOCATION TO LIST
					setTargetLocation(getRandomPoint2D());
				} 
			}
		} 
	}
	
	private boolean collisionObject(DisplayActor otherActor, DisplayObject otherObject) 
	{
		if (!otherObject.isSolid())
		{
		  return false;
		}
		
		double xa = otherActor.location.getX() - location.getX();
		double ya = otherActor.location.getY() - location.getY();
		double xo = otherObject.location.getX() - location.getX();
		double yo = otherObject.location.getY() - location.getY();
		
		Rectangle2D.Double rectActor = new Rectangle2D.Double(xa, ya, (double)otherActor.getSize().getHeight(), 
			  														(double)otherActor.getSize().getWidth());
		Rectangle2D.Double rectObject = new Rectangle2D.Double(xo, yo, (double) otherObject.getSize().getHeight(),
			  														 (double) otherObject.getSize().getWidth());
		if (rectActor.intersects(rectObject)) 
		{
		  return true;
		} 
		else
		{
		  return false;
		}
	}
	
	// a method to transform the direction for an image.
	private AffineTransform getTransformation() 
	{
		AffineTransform tx = new AffineTransform();
		tx.translate(location.getX(), location.getY());
		tx.rotate(direction + Math.toRadians(90), WIDTH/2, HEIGHT/2+5);
		return tx;
	}
	
	public void setVisible(boolean v)
	{
		visible = v;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}