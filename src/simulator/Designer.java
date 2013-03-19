package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import simulator.DisplayableObjects.DisplayCatering;
import simulator.DisplayableObjects.DisplayEntrance;
import simulator.DisplayableObjects.DisplayExit;
import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayObstacle;
import simulator.DisplayableObjects.DisplayStage;
import simulator.DisplayableObjects.DisplayToilet;
import simulator.playfield.SimulationPanel;

public class Designer extends JFrame 
{
	private InputFrame inputFrame;
	private JLabel southLabel;
	private ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>();
	private JPanel westPanel = new JPanel();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> objectList = new JList<String>(model);
	SimulationPanel centerPanel = new SimulationPanel();
	private JPanel objectHolder = new JPanel();
	
	public Designer()
	{
		super("2D Designer");
		makeFrame();
		refresh();
    }
	
	private void makeFrame()
	{
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new CloseOperation(centerPanel, this));
		
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setLocation(new Point(100, 100));
        setSize(800, 600);
        
        westPanel.setLayout(new BorderLayout(20, 20));
        westPanel.add(objectHolder, BorderLayout.CENTER);
        objectHolder.setBackground(Color.WHITE);
        objectHolder.setBorder(BorderFactory.createTitledBorder("Objects"));
        objectList.addMouseListener(new MyMouseListener());
        //refresh();
        
        contentPane.add(westPanel, BorderLayout.WEST);
        
     	//Dropdown list
        String[] DropdownStrings = { "Stage", "Entrance", "Exit", "Catering", "Toilet", "Obstacle" };
	    JComboBox<String> dropList = new JComboBox<String>(DropdownStrings);
	    dropList.setSelectedIndex(0);
	    westPanel.add(dropList, BorderLayout.NORTH);
      
	    dropList.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{ 
	    		JComboBox cb = (JComboBox)e.getSource();
	    		inputFrame = new InputFrame((String)cb.getSelectedItem(), getLocation());
	    	}
	    });
      
      	//center
	    contentPane.add(centerPanel, BorderLayout.CENTER);
	    
	    //south
	    contentPane.add(southLabel = new JLabel("This label will be used to provide you more information about selected objects."), BorderLayout.SOUTH);
	  
	    makeMenuBar();
	        
	    setContentPane(contentPane);
	    setLocation(new Point(100, 100));
	    setVisible(true);
	}
   
	private void makeMenuBar()
   	{
        /** JMenuBar **/
        JMenuBar menuBar = new JMenuBar();
        
        /** JMenu **/
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem open = new JMenuItem("Open agenda");
        JMenuItem save = new JMenuItem("Opslaan");
        JMenuItem refresh = new JMenuItem("Refresh");
        menuBar.add(refresh);
        menuBar.add(open);
        menuBar.add(save);
        menuBar.add(clear);
        setJMenuBar(menuBar);
        
        clear.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 clear();
			}
		});
        
        refresh.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 refresh();
			}
		});
    }
   
   public void refresh()
   {
	   clear();
	   // Directory path here
	   
	   String name;
	   String type;
	   boolean isSolid = true;
	   int width = 0;
	   int height = 0;
	   int x = 0;
	   int y = 0;
		
	   String path = "Objecten"; 
	   String files;
	   
	   File folder = new File(path);
	   File[] listOfFiles = folder.listFiles(); 
	   int listSize = listOfFiles.length;
	   
	   for (int i = 0; i < listSize; i++) 
	   {
		   if (listOfFiles[i].isFile()) 
		   {
			   files = listOfFiles[i].getName();
		
			   if (files.endsWith(".object"))
			   {
				   try
				   {
					   //open
					   FileInputStream stream = new FileInputStream("Objecten/"+listOfFiles[i].getName());
			
					   //zet stream op
					   DataInputStream in = new DataInputStream(stream);
			
					   //buffer									//lees uit bestand
					   BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			
					   //save buffer
					   name = buffer.readLine();
					   height = Integer.parseInt(buffer.readLine());
					   width = Integer.parseInt(buffer.readLine());
					   isSolid = Boolean.parseBoolean(buffer.readLine());
					   x = Integer.parseInt(buffer.readLine());
					   y = Integer.parseInt(buffer.readLine());
					   type = buffer.readLine();
					   
					   Dimension size = new Dimension(width, height);
					   Point location = new Point(x, y);
					   
					   switch(type)
					   {
					   		case "Obstacle":
					   			DisplayObstacle o;
					   			displayObjects.add(o = new DisplayObstacle(name, size, isSolid, location, type));
					   			model.addElement(o.getName());
					   			break;
					   		case "Stage":
					   			DisplayStage s;
					   			displayObjects.add(s = new DisplayStage(name, size, isSolid, location, type));
					   			model.addElement(s.getName());
					   			break;
					   		case "Entrance":
					   			DisplayEntrance e;
					   			displayObjects.add(e = new DisplayEntrance(name, size, isSolid, location, type));
					   			model.addElement(e.getName());
					   			break;
					   		case "Exit":
					   			DisplayExit ex;
					   			displayObjects.add(ex = new DisplayExit(name, size, isSolid, location, type));
					   			model.addElement(ex.getName());
					   			break;
					   		case "Catering":
					   			DisplayCatering c;
					   			displayObjects.add(c = new DisplayCatering(name, size, isSolid, location, type));
					   			model.addElement(c.getName());
					   			break;
					   		case "Toilet":
					   			DisplayToilet t;
					   			displayObjects.add(t = new DisplayToilet(name, size, isSolid, location, type));
					   			model.addElement(t.getName());
					   			break;
					   		default:
					   			DisplayObstacle z;
					   			displayObjects.add(z = new DisplayObstacle(name, size, isSolid, location, type));
					   			model.addElement(z.getName());
					   			break;
					   }
					   objectHolder.revalidate();
					   
					   in.close();
				   }
				   catch(IOException e)
				   {
					   JOptionPane.showConfirmDialog(null, "Er is een fout opgetreden bij het lezen van de .object-bestanden!");
				   }
			   }
		   }
	   }
	   objectHolder.add(objectList);
   }
   
   private void clear()
   {
	   displayObjects.clear();
	   model.removeAllElements();
	   objectHolder.revalidate();
   }
   
   public class MyMouseListener implements MouseListener
   {
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			String value = objectList.getSelectedValue();
			for (DisplayObject d : displayObjects)
			{
				if (value.equals(d.getName()))
				{
					southLabel.setText(d.toString());
					SimulationPanel.getInstance().setSelectedObject(d);
					
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}   
   	}
}
class CloseOperation implements WindowListener{
	SimulationPanel panel;
	JFrame source;
	
	public CloseOperation(SimulationPanel panel, JFrame source){
		this.panel = panel;
		this.source = source;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		panel.toggleActive();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		panel.toggleActive();
		source.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		panel.toggleActive();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		panel.toggleActive();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		panel.toggleActive();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		panel.toggleActive();
	}
}