package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DisplayExit extends DisplayObject
{
	Rectangle2D bounds;
	
	public DisplayExit(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, isSolid, location, "Exit");
	}

	@Override
	public void drawObject(Graphics2D g) {
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX() - size.getWidth()/2, 
			      location.getY() - size.getHeight()/2,
			      size.getWidth(), size.getHeight());
		
		bounds = obstacle;
		g.fill(obstacle);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
