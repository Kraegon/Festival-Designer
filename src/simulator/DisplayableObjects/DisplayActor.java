package simulator.DisplayableObjects;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class DisplayActor extends DisplayObject
{
	private BufferedImage image;
	private Image imageActor;
	private Point2D location;
	private Point2D targetLocation;
	private boolean isSolid = true;
	private double direction;
	private int speed;
	
	private static int id = 0;
	private final static int WIDTH = 16;
	private final static int HEIGHT = 16;
	Rectangle2D bounds;
	
	public DisplayActor(Point2D location, double direction){
		super("Actor " + id, new Dimension(16,16), true, location, "actor");
		id++;
		this.location = location;
		this.direction = direction;
		this.speed = 10;
		this.imageActor = new ImageIcon("Data\\Actor.png").getImage();
		targetLocation = location;
		bounds = new Rectangle2D.Double(location.getX(), location.getY(), 16, 16);
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}
	
	public double getDirection(){
		return direction;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public boolean getIsSolid(){
		return isSolid;
	}
	
	public void setTargetLocation(Point point){
		targetLocation = new Point2D.Double(point.getX(),point.getY());
	}
	
	public void drawObject(Graphics2D g)
	{
		g.scale(0.25, 0.25);
		g.drawImage(imageActor,getTransformation(),null);
		g.scale(4,4);
	}
	
	public void update(){
		Random random = new Random();
		if(location.equals(targetLocation))
			targetLocation = new Point2D.Double(random.nextInt(500), random.nextInt(500));


		Point2D lastLocation = location;
		//location = new Point2D.Double(/*getEntranceX(),getEntranceY()*/);
		
		if (location.getX()<0 || location.getX()>800/*maxX panel*/ ||location.getY()<0||location.getY()>600/*maxY panel*/){
			direction = direction + Math.PI;
		}
		
		// Goes to the TargetArea.
		double x = targetLocation.getX() - location.getX();
		double y = targetLocation.getY() - location.getY();
		double changeAngle = Math.atan2(y, x);
		direction = changeAngle;
		
		boolean collisionActor = false;
		/*for(DisplayObject displayObject : otherActors){
			if(displayObject.getClass() == DisplayActor.class){
				DisplayActor otherActor = (DisplayActor) displayObject;
				if (displayObject == this)
					continue;
				if(collision(otherActor))
					collisionActor = true;
			}
		}*/
		if(collisionActor == true)
			location = lastLocation;
		else
			location = new Point2D.Double(10 * Math.sin(direction), 10 * Math.cos(direction));
	}
	
	private boolean collision(DisplayActor displayActor){
		double x = displayActor.location.getX() - location.getX();
		double y = displayActor.location.getY() - location.getY();
		Rectangle2D.Double rectActor = new Rectangle2D.Double(x,y,WIDTH,HEIGHT);
		if(rectActor.intersects(rectActor))
			return false;
		else
			return true;
		
	}
	
//a method to transform the direction for an image.	
	private AffineTransform getTransformation(){
		AffineTransform tx = new AffineTransform();
		tx.translate(location.getX(), location.getY());
		// not sure if this works if it doesn't instead of location.getX()/.getY() try 16,32
		tx.rotate(direction, 16, 16);
		return tx;
	}	
}