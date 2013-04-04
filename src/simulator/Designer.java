package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayTargetPoint;
import simulator.clock.Clock;
import simulator.playfield.SimulationPanel;
import IO.IO;

public class Designer extends JFrame {
	private static Designer INSTANCE;
	private InputFrame inputFrame;
	private static JLabel southLabel;
	private ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>();
	private JPanel westPanel = new JPanel();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> objectList = new JList<String>(model);
	SimulationPanel centerPanel = new SimulationPanel();
	private JButton startButton;
	private JPanel objectHolder = new JPanel();
	private JLabel amountOfActors;
	private JPanel neighboursPanel;
	private DefaultListModel<String> neighbourModel = new DefaultListModel<String>();
	private JList<String> neighboursList = new JList<String>(neighbourModel);

	public Designer() {
		super("2D Designer");
		INSTANCE = this;
		makeFrame();
		refresh();
		showFestivalSizePanel();
	}

	private void makeFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new CloseOperation(centerPanel, this));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setLocation(new Point(100, 100));
		setSize(800, 600);
		setMinimumSize(new Dimension(1000, 600)); //ADD: LESLEY

		westPanel.setLayout(new BorderLayout(20, 20));
		westPanel.add(objectHolder, BorderLayout.CENTER);
		objectHolder.setBackground(Color.WHITE);
		objectHolder.setBorder(BorderFactory.createTitledBorder("Objects"));
		objectList.addMouseListener(new MyMouseListener());
		// refresh();

		contentPane.add(westPanel, BorderLayout.WEST);

		// Dropdown list
		String[] DropdownStrings = { "Stage", "Entrance", "Exit", "Catering",
									 "Toilet", "Obstacle", "Target Point" }; // ADD: LESLEY
		JComboBox<String> dropList = new JComboBox<String>(DropdownStrings);
		dropList.setSelectedIndex(0);
		westPanel.add(dropList, BorderLayout.NORTH);

		dropList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				inputFrame = new InputFrame((String) cb.getSelectedItem(), getLocation());
			}
		});

		// center
		contentPane.add(centerPanel, BorderLayout.CENTER);

		// south
		contentPane.add(southLabel = new JLabel(
						"This label will be used to provide you more information about selected objects."),
						BorderLayout.SOUTH);

		makeMenuBar();

		setContentPane(contentPane);
		setLocation(new Point(100, 100));
		setVisible(true);
		
		// east
		JPanel timePanel = new JPanel(new GridLayout(6, 1, 0, 10));
		JPanel changePanel = new JPanel(new BorderLayout());
		JButton increase = new JButton("+");
		increase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (centerPanel.isRunning())
				{
					Clock.getInstance().setSpeed(Clock.getInstance().getSpeed() / 5);
				}
			}
		});
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (centerPanel.isRunning())
				{
					Clock.getInstance().setSpeed(1000);
				}
			}
		});
		JButton decrease = new JButton("-");
		decrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (centerPanel.isRunning())
				{
					Clock.getInstance().setSpeed(Clock.getInstance().getSpeed() * 5);
				}
			}
		});
		changePanel.add(decrease, BorderLayout.WEST);
		changePanel.add(reset, BorderLayout.CENTER);
		changePanel.add(increase, BorderLayout.EAST);
		
		startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(centerPanel.isRunning())      
				{       						 
					centerPanel.stopTimer();	 
				}                                
				else                             
				{  								 
					centerPanel.startTimer();	 
				}                                
			}
		});
		timePanel.add(startButton);
		
		final JButton addVisitor = new JButton("Add Visitor");
		addVisitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.addActor();
			}
		});
		
		final JButton killActor = new JButton("Kill Visitor");
		killActor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.killActor();
			}
		});
		
		amountOfActors = new JLabel("Amount of Visitors: 0");
		
		neighboursPanel = new JPanel();
		neighboursPanel.setBorder(BorderFactory.createTitledBorder("Neighbours"));
		neighboursPanel.add(neighboursList);
		
		timePanel.add(changePanel);
		changePanel.add(decrease, BorderLayout.WEST);
		changePanel.add(reset, BorderLayout.CENTER);
		changePanel.add(increase, BorderLayout.EAST);
		timePanel.add(startButton);
		timePanel.add(addVisitor);
		timePanel.add(killActor);
		timePanel.add(amountOfActors);
		timePanel.add(neighboursPanel);
		contentPane.add(timePanel, BorderLayout.EAST);
	}

	private void makeMenuBar() {
		/** JMenuBar **/
		JMenuBar menuBar = new JMenuBar();

		/** JMenu **/
		JMenuItem clear = new JMenuItem("Clear");
		JMenuItem open = new JMenuItem("Open setup");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem refresh = new JMenuItem("Refresh");
		menuBar.add(refresh);
		menuBar.add(open);
		menuBar.add(save);
		menuBar.add(clear);
		setJMenuBar(menuBar);
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationPanel.getInstance().setDisplayObjects(IO.getInstance().openSetup());
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IO.getInstance().saveSetup(SimulationPanel.getInstance().getDisplayObjects());
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
	}

	public void refresh() 
	{
		clear();
		String path = "Objecten";

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		int listSize = listOfFiles.length;

		for (int i = 0; i < listSize; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				if (listOfFiles[i].getName().endsWith(".object")) {
					DisplayObject o = IO.getInstance().openFestivalObect(listOfFiles[i].getName());
					System.out.println(o.toString());
					displayObjects.add(o);
					model.addElement(o.getName());
					objectHolder.revalidate();
				}
			}
		}
		objectHolder.add(objectList);
	}

	private void clear() {
		displayObjects.clear();
		model.removeAllElements();
		objectHolder.revalidate();
	}
	
	public void showFestivalSizePanel()
	{
		JButton small;
		JButton medium;
		JButton large;
		
		final JFrame sizeFrame = new JFrame("Size of your Festival");
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		sizeFrame.getContentPane().add(buttonPanel);
		buttonPanel.add(small = new JButton("Small Festival"));
		buttonPanel.add(medium = new JButton("Medium Festival"));
		buttonPanel.add(large = new JButton("Large Festival"));
		small.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centerPanel.setFestivalSize(new Dimension(600, 400));
				sizeFrame.dispose();
			}	
		});
		medium.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centerPanel.setFestivalSize(new Dimension(900, 600));
				sizeFrame.dispose();
			}	
		});
		large.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centerPanel.setFestivalSize(new Dimension(1200, 900));
				sizeFrame.dispose();
			}	
		});
		sizeFrame.setLocation(new Point(150, 150));
		sizeFrame.setSize(500, 100);
		sizeFrame.setVisible(true);
		sizeFrame.setResizable(false);
	}
	
	public static void setSouthLabel(String text) {
		southLabel.setText(text);
	}
	
	public void setNeighboursPanel(List<DisplayTargetPoint> targetPoints) {
		neighbourModel.removeAllElements();
		if (targetPoints.isEmpty())
		{
			neighbourModel.add(0, "No Neighbours!");
		}
		else
		{
			int index = 0;
			for (DisplayTargetPoint t : targetPoints)
			{
				neighbourModel.add(index, t.getName());
				index++;
			}
		}
	}
	
	public void setVisitors(int visitors) {
		amountOfActors.setText("Amount of Visitors: " + visitors);
	}
	
	public void setStartButton(String text) {
		startButton.setText(text);
	}
	
	public static Designer getInstance(){                                  
		if(INSTANCE == null)                                                        
			INSTANCE = new Designer();                                       
		return INSTANCE;                                                            
	}  

	public class MyMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			String value = objectList.getSelectedValue();
			for (DisplayObject d : displayObjects) {
				if (value.equals(d.getName())) {
					southLabel.setText(d.toString());
					SimulationPanel.getInstance().setSelectedObject(d);
					centerPanel.setArrow(d.getLocation().getX(), d.getLocation().getY());
					centerPanel.repaintTimerOff(); // ADD: LESLEY
					if (d.getClass() == DisplayTargetPoint.class)									//ADD: LESLEY
					{																				//ADD: LESLEY
						DisplayTargetPoint displayTarget = (DisplayTargetPoint)d;					//ADD: LESLEY
						Designer.getInstance().setNeighboursPanel(displayTarget.getNeighbours());	//ADD: LESLEY
					}																				//ADD: LESLEY
				}
			}
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
}