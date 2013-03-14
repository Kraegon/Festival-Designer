package timetableModule.data;

import java.io.Serializable;

/**
 * Data class. Has a stage's name maximum amount of visitors and if it's the main stage or not.
 * @author Julian G. West
 *
 */
public class Stage implements Serializable, FestivalObject{
	
	private static final long serialVersionUID = 1058035801560913516L;
	private String name;
	private int visitorsMax;
	private boolean isMainStage;
	
	public Stage(String name, int visitorsMax, boolean isMainStage) {
		this.name = name;
		this.visitorsMax = visitorsMax;
		this.isMainStage = isMainStage;
	}
	
	public Stage(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVisitorsMax() {
		return visitorsMax;
	}
	public void setVisitorsMax(int visitorsMax) {
		this.visitorsMax = visitorsMax;
	}

	public boolean isMainStage() {
		return isMainStage;
	}

	public void setIsMainStage(boolean isMainStage) {
		this.isMainStage = isMainStage;
	}
	
	public String toString(){
		return "\nStage: " + name + "\nVisitors maximum: " + visitorsMax + "\nIs this a main stage? " + isMainStage  + "\n ------- \n";		
	}
	
}
