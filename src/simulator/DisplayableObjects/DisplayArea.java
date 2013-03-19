package simulator.DisplayableObjects;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class DisplayArea extends DisplayObject{
	private ArrayList<Point2D> targetLocations;
	
	public DisplayArea(String name, Dimension size, Point2D location, String type){
		super(name, size, true, location, type);
	}
	public ArrayList<Point2D> getTargetLocations(){
		return targetLocations;
	}
	public void addPoint(Point2D point){
		targetLocations.add(point);
	}
	public void removePoint(int index){
		targetLocations.remove(index);
	}
}