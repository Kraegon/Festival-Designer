package simulator.DisplayableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import simulator.clock.Clock;
import timetableModule.data.Performance;
import timetableModule.data.Stage;
import IO.IO;

/**
 * Vervang hele klasse.
 * @author Julian G. West
 */
public class DisplayStage extends DisplayObject implements Serializable
{
	Rectangle2D bounds;
	Stage attributedStage;
	Performance attributedPerformance;
	
	public DisplayStage(String name, Dimension size, boolean isSolid, Point2D location, String type, Stage attributedStage)
	{
		super(name, size, true, location, "Stage");
		this.attributedStage = attributedStage;
	}

	public void drawObject(Graphics2D g) {
		g.setPaint(Color.CYAN);	
		Rectangle2D obstacle = new Rectangle2D.Double(location.getX(), location.getY(),
				  									  size.getWidth(), size.getHeight());
		bounds = obstacle;
		g.fill(obstacle);
		g.setPaint(Color.BLACK);
		g.draw(obstacle);
		g.setFont(new Font("Consolas", Font.PLAIN, 15));
		g.drawString(attributedStage.getName(), (int) location.getX() + 5, (int) location.getY() + 15);
		if(attributedPerformance != null){
			g.drawString("Now playing: " + attributedPerformance.getName(), (int) location.getX() + 5, (int) location.getY() + 35);
		}
		
	}
	public void update() {
		for(Performance p : IO.getInstance().getFestival().getPerformances()){
			if(p.getStage().getName().equals(attributedStage.getName())){
				int parsedTime;
				String startTime = p.getStartTime();
				if(startTime.length() == 4)
					parsedTime = Integer.parseInt(startTime.substring(0, 1)) * 3600 +  Integer.parseInt(startTime.substring(2, 4)) * 60;
				else
					parsedTime = Integer.parseInt(startTime.substring(0, 2)) * 3600 + Integer.parseInt(startTime.substring(3, 5)) * 60;
				if(Clock.getInstance().getCurrentTime() > parsedTime){
					attributedPerformance = p;
				}
			}
		}
		if(attributedPerformance != null){
			String endTime = attributedPerformance.getEndTime();
			int parsedTime;
			if(endTime.length() == 4)
				parsedTime = Integer.parseInt(endTime.substring(0, 1)) * 3600 +  Integer.parseInt(endTime.substring(2, 4)) * 60;
			else
				parsedTime = Integer.parseInt(endTime.substring(0, 2)) * 3600 + Integer.parseInt(endTime.substring(3, 5)) * 60;
			if(Clock.getInstance().getCurrentTime() > parsedTime){
				attributedPerformance = null;
			}
		}
	}
	
	public void setStage(Stage attributedStage){
		this.attributedStage = attributedStage;
	}
	public Stage getStage(){
		return attributedStage;
	}
}
