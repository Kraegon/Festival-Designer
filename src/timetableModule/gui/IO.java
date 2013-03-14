package timetableModule.gui;

import java.io.*;

import timetableModule.data.*;

import timetableModule.timetableScreen.*;

/**
 * Handles file IO for festivals. Will pass festival objects to GUI.
 * @author Julian G. West
 *
 */
public class IO {
	private Festival festival;	
	private String filePath;	
	private static IO INSTANCE;
	File festiFile;	
	
	public IO(){
		INSTANCE = this;
	}
	public static IO getInstance(){
		if(INSTANCE == null)
			new IO("default.fest");
		return INSTANCE;
	}
	
	public IO(String filePath){
		this.filePath = filePath;
		festiFile = new File(filePath);
		try {
			festiFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		INSTANCE = this;
	}
	
	public Festival getFestival() {
		return festival;
	}
	public void setNewFestival(String filePath){
		this.filePath = filePath;
		festival = new Festival();
		festiFile = new File(filePath);
	}
	public void setFestival(Festival festival) {
		this.festival = festival;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public File getFestiFile() {
		return festiFile;
	}
	public void setFestiFile(File festiFile) {
		this.festiFile = festiFile;
	}
	
	public void openFestival() throws IOException{
		ObjectInputStream objIn;
		try{
			objIn = new ObjectInputStream(new FileInputStream(filePath));
			setFestival((Festival) objIn.readObject());
			objIn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		}
		for(Artist a : festival.getArtists()){
			Screen.getInstance().addDrawable(new GraphicArtist(a));
		} 
		for(Stage s : festival.getStages()){
			Screen.getInstance().addDrawable(new GraphicStage(s));
		}
		for(Performance p : festival.getPerformances()){
			Screen.getInstance().findStage(p.getStage().getName()).addPerformance(new GraphicPerformance(p));
		}
	}
	
	public void saveFestival() throws IOException, NullPointerException{
		ObjectOutputStream objOut;
				if(festiFile.exists()){
					objOut = new ObjectOutputStream(new FileOutputStream(filePath));
					objOut.writeObject(festival);
					objOut.close();
				} else {
					festiFile.createNewFile();
					objOut = new ObjectOutputStream(new FileOutputStream(filePath));
					objOut.writeObject(festival);
					objOut.close();
				}
	}
	
	public void printFestival(){
		System.out.println("Festival: " + filePath);
		if(festival != null)
			System.out.print(festival.toString());
		else
			System.out.println("No current festival");
	}
}
