package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class DisplayToilet extends DisplayObject
{
	public DisplayToilet(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, isSolid, location, "Toilet");
	}

	@Override
	public void drawObject(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}