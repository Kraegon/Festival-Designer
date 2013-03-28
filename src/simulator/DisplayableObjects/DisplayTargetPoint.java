package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DisplayTargetPoint extends DisplayObject implements Serializable
{
	Rectangle2D bounds;
	
	public DisplayTargetPoint(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, isSolid, location, "TargetPoint");
	}

	@Override
	public void drawObject(Graphics2D g2) 
	{
		Ellipse2D tp = new Ellipse2D.Double(location.getX() - size.getWidth()/2, location.getY() - size.getHeight()/2, 
											size.getWidth(), size.getHeight());
		g2.setColor(Color.WHITE);
		g2.fill(tp);
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
	}
}
