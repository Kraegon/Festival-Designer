package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import simulator.clock.Clock;
import simulator.playfield.SimulationPanel;
import IO.IO;

public class Designer extends JFrame {
	private InputFrame inputFrame;
	private static JLabel southLabel;
	private ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>();
	private JPanel westPanel = new JPanel();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> objectList = new JList<String>(model);
	SimulationPanel centerPanel = new SimulationPanel();
	private JPanel objectHolder = new JPanel();

	public Designer() {
		super("2D Designer");
		makeFrame();
		refresh();
	}

	private void makeFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new CloseOperation(centerPanel, this));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setLocation(new Point(100, 100));
		setSize(800, 600);
		setMinimumSize(new Dimension(800, 600)); //ADD: LESLEY

		westPanel.setLayout(new BorderLayout(20, 20));
		westPanel.add(objectHolder, BorderLayout.CENTER);
		objectHolder.setBackground(Color.WHITE);
		objectHolder.setBorder(BorderFactory.createTitledBorder("Objects"));
		objectList.addMouseListener(new MyMouseListener());

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
				inputFrame = new InputFrame((String) cb.getSelectedItem(),
						getLocation());
			}
		});

		// center
		contentPane.add(centerPanel, BorderLayout.CENTER);

		// south
		contentPane
				.add(southLabel = new JLabel(
						"This label will be used to provide you more information about selected objects."),
						BorderLayout.SOUTH);

		makeMenuBar();

		setContentPane(contentPane);
		setLocation(new Point(100, 100));
		setVisible(true);
		
		// east
		JPanel timePanel = new JPanel(new GridLayout(3, 1, 0, 200));
		final JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clock.getInstance().toggle();
				if(startButton.getText().equals("STOP"))      //ADD: LESLEY
				{       									  //ADD: LESLEY
					centerPanel.stopTimer();				  //ADD: LESLEY
					startButton.setText("START");             //ADD: LESLEY
				}                                             //ADD: LESLEY
				else                                          //ADD: LESLEY
				{  											  //ADD: LESLEY
					centerPanel.startTimer();			      //ADD: LESLEY
					startButton.setText("STOP");              //ADD: LESLEY
				}                                             //ADD: LESLEY
			}
		});
		JPanel changePanel = new JPanel(new BorderLayout());
		JButton increase = new JButton("+");
		increase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clock.getInstance().setSpeed(Clock.getInstance().getSpeed() / 5);
			}
		});
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clock.getInstance().setSpeed(1000);
			}
		});
		JButton decrease = new JButton("-");
		decrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clock.getInstance().setSpeed(Clock.getInstance().getSpeed() * 5);
			}
		});
		changePanel.add(decrease, BorderLayout.WEST);
		changePanel.add(reset, BorderLayout.CENTER);
		changePanel.add(increase, BorderLayout.EAST);
		
		
		final JButton addVisitor = new JButton("Add Visitor");
		addVisitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.addActor();
			}
		});
		timePanel.add(startButton);
		timePanel.add(changePanel);
		timePanel.add(addVisitor);
		contentPane.add(timePanel, BorderLayout.EAST);
	}

	private void makeMenuBar() {
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
	
	public static void setSouthLabel(String text) {
		southLabel.setText(text);
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