package simulator.playfield;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class SelectionArrow
{
	private double x;
	private double y;
	
	public SelectionArrow(double sx, double sy)
	{
		this.x = sx;
		this.y = sy;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void drawObject(Graphics2D g2)
	{
		g2.setColor(Color.RED);;
		Rectangle2D r = new Rectangle2D.Double(x-20/2, y-20/2, 20, 20);
		Line2D lx = new Line2D.Double(-1000, y, 1000, y);
		Line2D ly = new Line2D.Double(x, -1000, x, 1000);
		g2.draw(r);
		g2.draw(ly);
		g2.draw(lx);
	}
}
