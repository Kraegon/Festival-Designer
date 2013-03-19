package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DisplayEntrance extends DisplayObject
{
	Rectangle2D bounds;
	
	public DisplayEntrance(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, false, location, "Entrance");
	}

	@Override
<<<<<<< HEAD
	public void drawObject(Graphics2D g) 
	{
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX() - size.getWidth()/2, 
												      location.getY() - size.getHeight()/2,
												      size.getWidth(), size.getHeight());
		bounds = obstacle;
		g.fill(obstacle);
=======
	public void drawObject(Graphics2D g) {
		Rectangle2D entrance = new Rectangle2D.Double(location.getX(), location.getY(), size.getWidth(), size.getHeight());
		g.fill(entrance);
>>>>>>> 01bded8f2c0b9519b8f9d91b74af0a95b1ef7d59
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle2D getBounds() 
	{
		return bounds;
	}
}
