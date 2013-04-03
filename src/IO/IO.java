package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import simulator.DisplayableObjects.DisplayObject;
import timetableModule.data.Artist;
import timetableModule.data.Festival;
import timetableModule.data.Performance;
import timetableModule.data.Stage;
import timetableModule.timetableScreen.GraphicArtist;
import timetableModule.timetableScreen.GraphicPerformance;
import timetableModule.timetableScreen.GraphicStage;
import timetableModule.timetableScreen.Screen;

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
	
	public void saveFestivalObject(DisplayObject o){
		ObjectOutputStream objOut;
		File objectFile = new File("Objecten\\" + o.getName() + ".object");
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(objectFile));
			if(!objectFile.exists()){
					objectFile.createNewFile();
			}
			objOut.writeObject(o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public DisplayObject openFestivalObect(String objectName){
		ObjectInputStream objIn;
		File objectFile = new File("Objecten\\" + objectName);
		try{
			objIn = new ObjectInputStream(new FileInputStream(objectFile));
			DisplayObject opened = (DisplayObject) objIn.readObject();
			objIn.close();
			return opened;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void saveSetup(LinkedList<DisplayObject> setup){
		ObjectOutputStream objOut;
		File objectFile = new File("Objecten\\lastsetup.dat");
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(objectFile));
			if(!objectFile.exists()){
					objectFile.createNewFile();
			}
			objOut.writeObject(setup);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public LinkedList<DisplayObject> openSetup(){
		ObjectInputStream objIn;
		File objectFile = new File("Objecten\\lastsetup.dat");
		try{
			objIn = new ObjectInputStream(new FileInputStream(objectFile));
			LinkedList<DisplayObject> opened = (LinkedList<DisplayObject>) objIn.readObject();
			objIn.close();
			return opened;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
