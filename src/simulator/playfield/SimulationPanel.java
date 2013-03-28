package simulator.playfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import simulator.DisplayableObjects.DisplayActor;
import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayTargetPoint;

public class SimulationPanel extends JPanel
{
	private static SimulationPanel INSTANCE;
	private double zoom = 1;
	private boolean isActive = true;
	private Rectangle2D field;
	private LinkedList<DisplayObject> displayObjects = new LinkedList<DisplayObject>();
	private LinkedList<DisplayActor> displayActor = new LinkedList<DisplayActor>();
	private static DisplayObject selectedObject;
	private Point2D lastPoint = null;
	private int focusX;
	private int focusY;
	static boolean TargetingMode = false;
	PopupListener popup = new PopupListener();
	SelectionArrow arrow = new SelectionArrow(0, 0);
	
	public SimulationPanel()
	{
		double direction = Math.random() * 2 * Math.PI;
		for (int i = 0; i < 100; i++){
			displayActor.add(new DisplayActor((new Point2D.Double(50, 50)), direction));
		}

		displayObjects.addAll(displayActor);
		
		Timer t = new Timer(1000/60, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isActive){				
					repaint();	
					for(DisplayObject a : displayObjects)
						a.update();
				}
			}
		});
		t.start();
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int modifier = e.getWheelRotation();
				if(modifier < 0 && zoom < 2.5)
					zoom += 0.03;
				else if(zoom > 1)
					zoom -= 0.03;
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
			}
		});
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {isActive = true;}
			public void mouseClicked(MouseEvent e) {
				if (TargetingMode == false) {
					if (selectedObject != null) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							selectedObject.setLocation(new Point2D.Double((e
									.getX() - focusX) / zoom,
									(e.getY() - focusY) / zoom));
							displayObjects.add(selectedObject);
						} else if (e.getButton() == MouseEvent.BUTTON3) {

							popup.show(e, selectedObject);
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
								System.out
										.println("---------------------------------------");

								System.out.println("added target: "
										+ a.getName());
							}
						}
					}
				}
			}

		});
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
	
	public void paintComponent(Graphics gTemp){
		Graphics2D g = (Graphics2D) gTemp;
		try {
			g.setPaint(new TexturePaint(ImageIO.read(new File("Data\\grass.png")), new Rectangle2D.Double(focusX, focusY, 256 * zoom, 256 * zoom)));
			g.fill(new Rectangle2D.Double(0,0,getWidth(),getHeight()));
		} catch (IOException e) {}//Ain't gonna happen unless you don't have grass.png
		g.translate(focusX, focusY);
		g.scale(zoom, zoom);
		g.setPaint(Color.BLACK);
		for(DisplayObject a : displayObjects)
		{
			a.drawObject(g);
			if (a.getClass() == DisplayTargetPoint.class)		// ADD: LESLEY
			{													// test for giving actors a targetpoint
				for (DisplayActor actor : displayActor)			//
				{												//
					actor.setTargetLocation(a.getLocation());	//
				}												//
			}
		}
		arrow.drawObject(g);
	}
	
	public void setArrow(double x, double y)
	{
		arrow.setX(x);
		arrow.setY(y);
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