package timetableModule.data;

import java.io.Serializable;
import java.util.*;
/**
 * Data class. Holds all artists and stages and performances with a date. 
 * When a new performance is made, please select it's artist(s) and stage from here.
 * @author Julian G. West
 *
 */
public class Festival implements Serializable{

	private static final long serialVersionUID = 3988410474252007794L;
	private LinkedList<Stage> stages;
	private LinkedList<Artist> artists;
	private LinkedList<Performance> performances;
	private String name;
	private String date;	
	private String startTime; // Gregorian calendar + simpledateformat?
	private String endTime;

	public Festival(){
		stages = new LinkedList<Stage>();
		artists = new LinkedList<Artist>();
		performances = new LinkedList<Performance>();
	}
	
	public Festival(String date){
		stages = new LinkedList<Stage>();
		artists = new LinkedList<Artist>();
		performances = new LinkedList<Performance>();
		this.date = date;
	}
	
	public void addStage(Stage stage){
		stages.add(stage);
	}
	public void addArtist(Artist artist){
		artists.add(artist);
	}
	public void addPerformance(Performance performance) throws IllegalArgumentException{
		if(stages.isEmpty())
			throw new IllegalArgumentException("No stages created");
		if(artists.isEmpty())
			throw new IllegalArgumentException("No artists created");
		performances.add(performance);
	}
	public Artist findArtist(String targetArtist){
		for(Artist artist : artists){
			if(artist.getName().equals(targetArtist)){
				return artist;
			}
		}
		return null;
	}
	public Stage findStage(String targetStage){
		for(Stage stage : stages){
			if(stage.getName().equals(targetStage)){
				return stage;
			}
		}
		return null;
	}
	public Performance findPerformance(String targetPerformance){
		for(Performance performance : performances){
			if(performance.getName().equals(targetPerformance)){
				return performance;
			}
		}
		return null;
	}
	public LinkedList<Stage> getStages() {
		return stages;
	}
	public LinkedList<Artist> getArtists() {
		return artists;
	}
	public LinkedList<Performance> getPerformances() {
		return performances;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString(){
		String str = "\n -------- \n";
		str += "Festival name: " + name + "\n";
		str += "Date of festival " + date + "\n";
		str += "Runs from " + startTime + " to " + endTime + "\n";
		if(stages.isEmpty())
			str += "No stages made. \n";
		else
			for(Stage s : stages){
				str += s.toString();
			}
		if(artists.isEmpty())
			str += "No artists made. \n";
		else
			for(Artist a : artists){
				str += a.toString();
			}
		if(performances.isEmpty())
			str += "No performances made. \n";
		else
			for(Performance p : performances){
				str += p.toString();
			}
		
		return str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
