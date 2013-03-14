package timetableModule.data;


import java.io.Serializable;
import java.util.*;

/**
 * Data class. Holds a performance's artist(s), stage, times and expected popularity level. 
 * Data here should be used to draw within the table and borrows artist(s) and stage from it's Festival parent.
 * @author Julian G. West
 *
 */
public class Performance implements Serializable, FestivalObject{

	private static final long serialVersionUID = -7222551310907198818L;
	private String name;
	private LinkedList<Artist> artists;
	private Stage stage;
	private String startTime;
	private String endTime;
	private int popularity;
	
	public Performance(String name, String startTime, String endTime, int popularity, Stage stage){
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.popularity = popularity;
		this.stage = stage;
		artists = new LinkedList<Artist>();
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public LinkedList<Artist> getArtists() {
		return artists;
	}
	public void addArtists(Artist artist) {
		artists.add(artist);
	}
	public void clearArtists(){
		artists.clear();
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public String toString(){
		String str = "Performance name:" + name + "\nIncluding:";
		for(Artist a : artists){
			str += "\n\t" + a.getName();
		}
		str += "\nAt " + startTime + " till " + endTime + "\nOn stage " + stage.getName() + " with expected popularity " + popularity + "\n -------";
		return str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
