package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DisplayEntrance extends DisplayObject implements Serializable
{
	Rectangle2D bounds;
	
	public DisplayEntrance(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, false, location, "Entrance");
	}

	@Override
	public void drawObject(Graphics2D g) 
	{
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX(), location.getY(),
			      									  size.getWidth(), size.getHeight());
		bounds = obstacle;
		g.fill(obstacle);
	}

	@Override
	public void update() {
		//Stand there and look pretty.
	}
}
