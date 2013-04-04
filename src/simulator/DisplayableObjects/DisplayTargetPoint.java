package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayTargetPoint extends DisplayObject implements Serializable
{
	private static final long serialVersionUID = 4556755193135111023L;
	
	boolean isExit = false;
	Rectangle2D bounds;
	DisplayStage stage = null;
	List<DisplayTargetPoint> neighbours = new ArrayList<DisplayTargetPoint>();  // ADD: LESLEY
	
	public DisplayTargetPoint(String name, Dimension size, boolean isSolid, Point2D location, String type)
	{
		super(name, size, isSolid, location, "TargetPoint");
	}

	@Override
	public void drawObject(Graphics2D g2)
	{
		Ellipse2D tp = new Ellipse2D.Double(location.getX() - size.getWidth()/2, location.getY() - size.getHeight()/2, 
											size.getWidth(), size.getHeight());
		// DRAW A LINE TO THE NEIGHBOURS
		g2.setColor(Color.WHITE);                                                                                                   //ADD: LESLEY
		for(DisplayTargetPoint n : neighbours)                                                                                      //ADD: LESLEY
		{                                                                                                                           //ADD: LESLEY
			g2.draw(new Line2D.Double(getLocation().getX(), getLocation().getY(), 
									  n.getLocation().getX(), n.getLocation().getY())); 											//ADD: LESLEY
		}																															//ADD: LESLEY
		g2.setColor(Color.WHITE);																									//ADD: LESLEY
		g2.fill(tp);	

		// DRAW A LINE TO THE STAGE
		g2.setColor(Color.BLACK);
		if(stage != null)                                                                                      						//ADD: LESLEY
		{                                                                                                                           //ADD: LESLEY
			g2.draw(new Line2D.Double(getLocation().getX(), getLocation().getY(), 
									  stage.getLocation().getX() + stage.getWidth()/2, stage.getLocation().getY() + stage.getHeight()/2)); 									//ADD: LESLEY
		}
		g2.setFont(new Font("Consolas", Font.PLAIN, 10));
		String exitString = "";
		if (isExit) { exitString = " : isExit"; }
		g2.drawString(getName() + exitString, (int)Math.floor(getLocation().getX()), (int)Math.floor(getLocation().getY())); 		//ADD: LESLEY
	}
	
	public void setNeighbour(DisplayTargetPoint p) // ADD: LESLEY
	{
		neighbours.add(p);
	}
	
	public List<DisplayTargetPoint> getNeighbours() // ADD: LESLEY
	{
		return neighbours;
	}
	
	public void setStage(DisplayStage d) // ADD: LESLEY
	{
		stage = d;
		stage.setLinkedTarget(this);
	}
	
	public DisplayStage getStage() // ADD: LESLEY
	{
		return stage;
	}
	
	public DisplayTargetPoint getRandomNeighbour() // ADD: LESLEY
	{
		try
		{
			for (DisplayTargetPoint n : neighbours)
			{
				if (n.getStageIsActive())
				{
					int popularity = n.getStage().getPerformancePopularity();
					if (popularity > Math.random()*100)
					{
						return n;
					}
				}
			}
			return neighbours.get((int)Math.floor(Math.random()*(neighbours.size())));
		} 
		catch (IndexOutOfBoundsException e) 
		{
			return this; // ik doe maar wat
		}
	}
	
	public void printNeighbours() // ADD: LESLEY
	{
		for (DisplayTargetPoint d : neighbours)
		{
			System.out.println(d.getName());
		}
	}
	
	public boolean hasNeighbour()
	{
		if (neighbours.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void toggleExit()
	{
		isExit = !isExit;
	}
	
	public boolean isExit()
	{
		return isExit;
	}
	
	public boolean getStageIsActive()
	{
		if (stage != null && stage.isPlaying())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
	}
}
