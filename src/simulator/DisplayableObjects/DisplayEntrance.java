package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DisplayEntrance extends DisplayObject
{
	public DisplayEntrance(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, false, location, "Entrance");
	}

	@Override
	public void drawObject(Graphics2D g) {
		Rectangle2D entrance = new Rectangle2D.Double(location.getX(), location.getY(), size.getWidth(), size.getHeight());
		g.setPaint(new Color(20,20,20,140));
		g.fill(entrance);
	}

	@Override
	public void update() {
		//Stand there and look pretty.
	}
}
