package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DisplayCatering extends DisplayObject implements Serializable
{
	Rectangle2D bounds;
	
	public DisplayCatering(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, isSolid, location, "Catering");
	}

	/**
	 * Vervang methode -- JULIAN
	 */
	public void drawObject(Graphics2D g) {
		g.setPaint(Color.RED);	
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX(), location.getY(),
				  									  size.getWidth(), size.getHeight());
		bounds = obstacle;
		g.fill(obstacle);
		g.setPaint(Color.BLACK);
		g.draw(obstacle);
		g.setFont(new Font("Consolas", Font.PLAIN, 15));
		g.drawString(name, (int) location.getX() + 5, (int) location.getY() + 15);
	}

	public void update() {}
}
