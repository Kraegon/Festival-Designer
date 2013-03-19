package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public abstract class DisplayObject 
{
	protected String name;
	protected String type;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected boolean isSolid = true;
	protected Dimension size = new Dimension(width, height);
	protected Point2D location = new Point(x, y);
	
	public DisplayObject(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		this.name = name;
		this.size = size;
		this.isSolid = isSolid;
		this.location = location;
		this.type = type;
	}
	
	public abstract void drawObject(Graphics2D g);
	
	public abstract void update();
	
	public abstract Rectangle2D getBounds();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}
	
	public String toString() {
		return type + ": Name = " + name + ", Dimension = " + size.getWidth() + " x " + size.getHeight() +
			   ", isSolid = " + isSolid + ", Location = (" + location.getX() + ", " + location.getY() + ")";
	}
}
