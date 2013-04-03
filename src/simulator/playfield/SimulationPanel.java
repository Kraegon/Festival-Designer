package simulator.playfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import simulator.Designer;
import simulator.DisplayableObjects.DisplayActor;
import simulator.DisplayableObjects.DisplayEntrance;
import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayTargetPoint;
import simulator.clock.Clock;

public class SimulationPanel extends JPanel
{
	private static SimulationPanel INSTANCE;
	private double zoom = 1;
	private boolean isActive = true;
	private LinkedList<DisplayObject> displayObjects = new LinkedList<DisplayObject>();
	private LinkedList<DisplayActor> displayActor = new LinkedList<DisplayActor>();
	private static DisplayObject selectedObject;
	private Point2D lastPoint = null;
	private int focusX;
	private int focusY;
	static boolean TargetingMode = false;
	private boolean entrancePlaced = false;
	private boolean firstTargetPlaced = false;
	private boolean errorOccurred = false;
	private BufferedImage explosion;
	
	Point2D actorLocation = new Point2D.Double(0, 0);
	int index = 0;
	int xPos = 0;
	int yPos = 0;
	boolean end = false;
	
	Timer t;		
	PopupListener popup = new PopupListener(this);
	SelectionArrow arrow = new SelectionArrow(0, 0);
	DisplayTargetPoint firstTarget;
	Point2D entranceLocation = null;
	
	public SimulationPanel()
	{
		INSTANCE = this;
		t = new Timer(1000/60, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isActive) {
					repaint();
					updateObjects();
					updateActors();
				}
			}
		});

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int modifier = e.getWheelRotation();
				if(modifier < 0 && zoom < 2.5)
					zoom += 0.03;
				else if(zoom > 1)
					zoom -= 0.03;
				if (!(t.isRunning()))	// ADD: LESLEY
				{
					repaint();
				}
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent arg0) {}
			public void mouseDragged(MouseEvent e) {
				if(lastPoint == null)
					lastPoint = e.getPoint();
				if((e.getPoint().getX() < lastPoint.getX()))
					focusX -= 2;
				else if(e.getPoint().getX() > lastPoint.getX())
					focusX += 2;
				if((e.getPoint().getY() < lastPoint.getY()))
					focusY -= 2;
				else if((e.getPoint().getY() > lastPoint.getY()))
					focusY += 2;
				lastPoint = e.getPoint();
				if (!(t.isRunning()))	// ADD: LESLEY
				{
					repaint();
				}
			}
		});
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {isActive = true;}
			public void mouseClicked(MouseEvent e) 
			{
				if (TargetingMode == false) 
				{
					if (selectedObject != null) 
					{
						if (e.getButton() == MouseEvent.BUTTON1) 
						{
							// NORMAL OBJECT
							if (!(selectedObject.getClass() == DisplayEntrance.class && entrancePlaced)) //ADD: LESLEY (ALLOW ONLY ONE ENTRANCE)
							{
								selectedObject.setLocation(new Point2D.Double((e.getX() - focusX) / zoom,(e.getY() - focusY) / zoom));
								
								// AVOID DUPLICATES IN displayObjects:List 				                                                        //ADD: LESLEY
								boolean addToList = true;								                                                        //ADD: LESLEY
								for (DisplayObject d : displayObjects)					                                                        //ADD: LESLEY
								{														                                                        //ADD: LESLEY
									if (d.getName().equals(selectedObject.getName()))	                                                        //ADD: LESLEY
									{													                                                        //ADD: LESLEY
										addToList = false;								                                                        //ADD: LESLEY
									}													                                                        //ADD: LESLEY
								}														                                                        //ADD: LESLEY
								if (addToList)											                                                        //ADD: LESLEY
								{														                                                        //ADD: LESLEY
									displayObjects.add(selectedObject);					                                                        //ADD: LESLEY
								}														                                                        //ADD: LESLEY
							}															                                                        //ADD: LESLEY
							// FIRST ENTRANCE?                                                                                                  //ADD: LESLEY
							if (selectedObject.getClass() == DisplayEntrance.class && !entrancePlaced)											//ADD: LESLEY
							{                                                                                                                   //ADD: LESLEY
								System.out.println("ENTRANCE PLACED");                                                                          //ADD: LESLEY
								entranceLocation = new Point2D.Double(selectedObject.getLocation().getX(), selectedObject.getLocation().getY()); //ADD: LESLEY
								entrancePlaced = true;                                                                                          //ADD: LESLEY
							}                                                                                                                   //ADD: LESLEY
							// SET TARGETPOINTS                                                                                                 //ADD: LESLEY
							if (selectedObject.getClass() == DisplayTargetPoint.class)                                       					//ADD: LESLEY
							{     																												//ADD: LESLEY
								System.out.println("TARGETPOINT PLACED"); 																		//ADD: LESLEY
								if (selectedObject.getName().equals("FIRST_TARGET"))															//ADD: LESLEY
								{																												//ADD: LESLEY
									firstTarget = (DisplayTargetPoint)selectedObject;    														//ADD: LESLEY
									firstTargetPlaced = true;																					//ADD: LESLEY
								}																												//ADD: LESLEY
							}                                                                                                                   //ADD: LESLEY
							// UPDATE OBJECTS ONLY WHEN TIMER IS NOT RUNNING                                                                    //ADD: LESLEY
							if (!(t.isRunning()))	// ADD: LESLEY                                                                              //ADD: LESLEY
							{                                                                                                                   //ADD: LESLEY
								repaint();                                                                                                      //ADD: LESLEY
								updateObjects();                                                                                                //ADD: LESLEY
							}                                                                                                                   //ADD: LESLEY
						}                                                                                                                       //ADD: LESLEY
						else if (e.getButton() == MouseEvent.BUTTON3) 
						{
							popup.show(e, selectedObject, displayObjects);
						}
					}
				} else {

					if (e.getButton() == MouseEvent.BUTTON1) {
						// loop de array door of er een toe te voegen object is.
						for (DisplayObject a : displayObjects) {

							Point2D mouse = e.getPoint();
							// System.out.println("mouse: " + mouse);
							Point2D object = a.getLocation();
							// System.out.println("object: " + object);

							int mousex = (int) mouse.getX();
							int mousey = (int) mouse.getY();

							Point2D objectLocation = a.getLocation();
							Dimension objectSize = a.getSize();

							int x = (int) objectLocation.getX();
							int y = (int) objectLocation.getY();

							int x2 = (int) (x + objectSize.getWidth());
							int y2 = (int) (y + objectSize.getHeight());

							if (mousex >= x && mousey >= y && mousex <= x2
									&& mousey <= y2) {

								selectedObject.addTarget(a.getName());
								TargetingMode = false;
								System.out.println("---------------------------------------");

								System.out.println("added target: "
										+ a.getName());
							}
						}
					}
				}
			}
		});
		readExplosionFile();
	}                                                                            
	
	public void addActor()                                         
	{                                                                                                                                                       
		if (entranceLocation == null || !firstTargetPlaced)
		{
			JOptionPane.showMessageDialog(null, "Add an entrance and targetLocation named 'FIRST_TARGET' before you place actors on the stage");
		}
		else
		{
			double direction = Math.random() * 2 * Math.PI;
			displayActor.add(new DisplayActor(entranceLocation, direction, firstTarget));
			Designer.getInstance().setVisitors(displayActor.size());	//ADD: LESLEY
		}
	}   

	public void killActor()                                          
	{                                                               
		if (!displayActor.isEmpty())                                
		{                                                            
			errorOccurred = true;
			actorLocation = displayActor.get(displayActor.size()-1).getLocation();
			displayActor.remove(displayActor.size()-1);                  
			Designer.getInstance().setVisitors(displayActor.size());     
		}                                                                
	}
	
	public synchronized void killActor(String name)
	{
		for (DisplayActor d : displayActor)
		{
			if (d.getName().equals(name))
			{
				displayActor.remove(d);
				Designer.getInstance().setVisitors(displayActor.size()); 
			}
		}
	}
	                                                                                
	public static SimulationPanel getInstance(){                                  
		if(INSTANCE == null)                                                        
			INSTANCE = new SimulationPanel();                                       
		return INSTANCE;                                                            
	}                                                                               
	public boolean isActive() {                                                     
		return isActive;                                                            
	}                                                                               
	public void toggleActive() {                                                    
		isActive = !isActive;
	}
	
	public void paintComponent(Graphics gTemp)
	{
		Graphics2D g = (Graphics2D) gTemp;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //ADD: LESLEY
		try {
			g.setPaint(new TexturePaint(ImageIO.read(new File("Data\\grass.png")), new Rectangle2D.Double(focusX, focusY, 256 * zoom, 256 * zoom)));
			g.fill(new Rectangle2D.Double(0,0,getWidth(),getHeight()));
		} catch (IOException e) {}//Ain't gonna happen unless you don't have grass.png
		g.setPaint(Color.BLACK);
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString(Clock.getInstance().toString(), getWidth() - 125, 25);
		g.translate(focusX, focusY);
		g.scale(zoom, zoom);
		g.setPaint(Color.BLACK);
		
		g.drawRect(0, 0, getWidth(), getHeight()); 
		
		//DRAW OBJECTS
		for(DisplayObject a : displayObjects)
		{
			a.drawObject(g);
		}
		//DRAW ACTORS 						
		for(DisplayActor a : displayActor)
		{
			a.drawObject(g);
		}
		arrow.drawObject(g);
		if (errorOccurred)
		{
			drawExplosion(g, actorLocation);
		}
	}
	
	public void drawExplosion(Graphics g, Point2D location)
	{		
		if (index >= 25) 
		{ 
			end = true;
			errorOccurred = false;
			index = 0;
			xPos = 0;
			yPos = 0;
		}
		else
		{
			xPos = (index % 5)*320/5;
			yPos = (index / 5)*320/5;
			index++;
			int size = 128;
			
			BufferedImage subImg = explosion.getSubimage(xPos, yPos, 320/5, 320/5);
			g.drawImage(subImg, (int)Math.floor(location.getX())-size/2, (int)Math.floor(location.getY())-size/2, size, size, null);
		}
	}
	
	public void readExplosionFile()
	{
		try 
		{
			explosion = ImageIO.read(new File("explosions.png"));
		} 
		catch (IOException e) 
		{
			System.out.println("NO TEXTURE");
		}
	}
	
	public void setArrow(double x, double y)
	{
		arrow.setX(x);
		arrow.setY(y);
	}
	
	public void repaintTimerOff() 	 // ADD: LESLEY
	{    
		if (!(t.isRunning()))
		{
			repaint();
			updateObjects();
		}               
	} 
	
	public void updateObjects() 	 // ADD: LESLEY
	{    
		for(DisplayObject a : displayObjects)
			a.update();                
	}      
	
	public void updateActors() 		 // ADD: LESLEY
	{    
		for(DisplayActor a : displayActor)	
			a.update(displayActor);                
	}   
	
	public void stopTimer() {      // ADD: LESLEY
		t.stop(); 
		Designer.getInstance().setStartButton("START");
	}                             
	public void startTimer() {     // ADD: LESLEY
		if (entranceLocation == null || !firstTargetPlaced)
		{
			JOptionPane.showMessageDialog(null, "Add an entrance and targetLocation named 'FIRST_TARGET' before you place actors on the stage");
		}
		else
		{
			t.start(); 
			Designer.getInstance().setStartButton("STOP");
		}
	}                        
	public DisplayObject getSelectedObject() {
		return SimulationPanel.selectedObject;
	}
	public void setSelectedObject(DisplayObject selectedObject) {
		SimulationPanel.selectedObject = selectedObject;
	}
	public static boolean isTargetingMode() {
		return TargetingMode;
	}
	public static void setTargetingMode(boolean targetingMode) {
		TargetingMode = targetingMode;
	}
}