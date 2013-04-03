package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import timetableModule.data.Stage;

/**
 * Vervang hele klasse.
 * @author Julian G. West
 */
public class DisplayStage extends DisplayObject implements Serializable
{
	Rectangle2D bounds;
	Stage attributedStage;
	
	public DisplayStage(String name, Dimension size, boolean isSolid, Point2D location, String type, Stage attributedStage)
	{
		super(name, size, isSolid, location, "Stage");
		this.attributedStage = attributedStage;
	}

	public void drawObject(Graphics2D g) {
		g.setPaint(Color.CYAN);	
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX(), location.getY(),
				  									  size.getWidth(), size.getHeight());
		System.out.println(location);
		bounds = obstacle;
		g.fill(obstacle);
		g.setPaint(Color.BLACK);
		g.draw(obstacle);
		g.setFont(new Font("Consolas", Font.PLAIN, 15));
		g.drawString(attributedStage.getName(), (int) location.getX() + 5, (int) location.getY() + 15);
	}
	public void update() {
		
	}
	
	public void setStage(Stage attributedStage){
		this.attributedStage = attributedStage;
	}
	public Stage getStage(){
		return attributedStage;
	}
}
