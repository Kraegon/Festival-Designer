package simulator;
// InputFrame class for 2D-Designer

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import simulator.DisplayableObjects.DisplayCatering;
import simulator.DisplayableObjects.DisplayEntrance;
import simulator.DisplayableObjects.DisplayExit;
import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayObstacle;
import simulator.DisplayableObjects.DisplayStage;
import simulator.DisplayableObjects.DisplayToilet;
import IO.IO;

public class InputFrame extends JFrame
{
	private CheckInputs checkInp;
	private String source;
	// dit zullen later de in de agenda opgeslagen stage objecten worden
	private String[] stages = {"MainStage", "BigStage", "SmallStage", "Indie", "Metal", "Rock", "Jazz" };
	JComboBox<String> cb;
	JCheckBox b;
	
	public InputFrame(String source, Point sourcePoint)
	{
	   	setDefaultCloseOperation(HIDE_ON_CLOSE); 
	   	JPanel contentPane = new JPanel(new BorderLayout(10, 10));
	   	contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
	   	setLocation(new Point(100, 100));
	   	setSize(300, 200);
	   	setResizable(false);
	   	setContentPane(contentPane);
	   	setLocation(new Point(100, 100));
	   	setVisible(true);
	
	   	checkInp = new CheckInputs();
		this.source = source;
		
		switch(source)
		{
			case "Stage":
				makeStage();
				break;
			case "Obstacle":
				makeObstacle();
				break;
			case "Entrance":
				makeEntrance();
				break;
			case "Exit":
				makeExit();
				break;
			case "Catering":
				makeCatering();
				break;
			case "Toilet":
				makeToilet();
				break;	
		}		
	}

	public void makeObstacle()
	{
		setTitle("Create Obstacle");
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Name: "), new JLabel ("Hoogte: "), new JLabel ("Breedte: "),
			new JTextField(""),	new JTextField("10"), new JTextField("10"), b = new JCheckBox("Collidable", true)
		};
		JPanel leftPane = new JPanel(new GridLayout(4,1));
		JPanel rightPane = new JPanel(new GridLayout(4,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JTextField name = (JTextField) comps[3];
				JTextField breedte = (JTextField) comps[4];
				JTextField hoogte = (JTextField) comps[5];
					
				if (checkInp.checkString(name.getText()) && checkInp.checkNumber(breedte.getText()) && checkInp.checkNumber(hoogte.getText()))
				{
				 	DisplayObstacle o = new DisplayObstacle(name.getText(), new Dimension(Integer.parseInt(breedte.getText()),Integer.parseInt(hoogte.getText())), true, new Point2D.Double(0,0), "obstacle");
				 	IO.getInstance().saveFestivalObject(o);
				 	dispose();	
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void makeStage()
	{
		setTitle("Create Stage");
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Stage: "), new JLabel ("Hoogte: "), new JLabel ("Breedte: "),
			cb = new JComboBox<String>(stages),	new JTextField("10"), new JTextField("10")
		};
		JPanel leftPane = new JPanel(new GridLayout(4,1));
		JPanel rightPane = new JPanel(new GridLayout(4,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

				JTextField name = (JTextField) comps[3];
				JTextField breedte = (JTextField) comps[4];
				JTextField hoogte = (JTextField) comps[5];
					
				if (checkInp.checkString(name.getText()) && checkInp.checkNumber(breedte.getText()) && checkInp.checkNumber(hoogte.getText()))
				{
					DisplayObject o = new DisplayStage(name.getText(), new Dimension(Integer.parseInt(breedte.getText()),Integer.parseInt(hoogte.getText())), false, new Point2D.Double(0,0), "Stage");			
					IO.getInstance().saveFestivalObject(o);
					dispose();
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void makeEntrance()
	{
		setTitle("Create Entrance");
		setSize(300, 130);
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Name: "),
			new JTextField(""),
		};
		JPanel leftPane = new JPanel(new GridLayout(1,1));
		JPanel rightPane = new JPanel(new GridLayout(1,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JTextField name = (JTextField) comps[1];
				if (checkInp.checkString(name.getText()))
				{
					DisplayObject o = new DisplayEntrance(name.getText(), new Dimension(30,30), false, new Point2D.Double(0,0), "Entrance");			
					IO.getInstance().saveFestivalObject(o);
					dispose();	
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void makeExit()
	{
		setTitle("Create Exit");
		setSize(300, 130);
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Name: "),
			new JTextField(""),
		};
		JPanel leftPane = new JPanel(new GridLayout(1,1));
		JPanel rightPane = new JPanel(new GridLayout(1,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JTextField name = (JTextField) comps[1];
					
				if (checkInp.checkString(name.getText()))
				{
					DisplayObject o = new DisplayExit(name.getText(), new Dimension(30,30), false, new Point2D.Double(0,0), "Exit");			
					IO.getInstance().saveFestivalObject(o);
					dispose();
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void makeCatering()
	{
		setTitle("Create Catering");
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Name: "), new JLabel ("Hoogte: "), new JLabel ("Breedte: "),
			new JTextField(""), new JTextField("10"), new JTextField("10")
		};
		JPanel leftPane = new JPanel(new GridLayout(3,1));
		JPanel rightPane = new JPanel(new GridLayout(3,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JTextField name = (JTextField) comps[3];
				JTextField breedte = (JTextField) comps[4];
				JTextField hoogte = (JTextField) comps[5];
				
				if (checkInp.checkString(name.getText()) && checkInp.checkNumber(breedte.getText()) && checkInp.checkNumber(hoogte.getText()))
				{
					DisplayObject o = new DisplayCatering(name.getText(), new Dimension(Integer.parseInt(breedte.getText()),Integer.parseInt(hoogte.getText())), false, new Point2D.Double(0,0), "Catering");			
					IO.getInstance().saveFestivalObject(o);
					dispose();
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void makeToilet()
	{
		setTitle("Create Toilet");
		setSize(300, 130);
		final JComponent[] comps = new JComponent[]
		{
			new JLabel("Name: "),
			new JTextField(""),
		};
		JPanel leftPane = new JPanel(new GridLayout(1,1));
		JPanel rightPane = new JPanel(new GridLayout(1,1));
		
		for(JComponent component : comps)
		{
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}
		JPanel okPane = new JPanel();
		JButton ok = new JButton("OK");
		okPane.add(ok);
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JTextField name = (JTextField) comps[1];
					
				if (checkInp.checkString(name.getText()))
				{
					DisplayObject o = new DisplayToilet(name.getText(), new Dimension(30,30), false, new Point2D.Double(0,0), "Toilet");			
					IO.getInstance().saveFestivalObject(o);
					dispose();
				}
				else
				{
					showInputErrorPane();
				}
			}
		});		
		add(leftPane, BorderLayout.WEST);
		add(rightPane, BorderLayout.CENTER);
		add(okPane, BorderLayout.SOUTH);
	}
	
	public void showInputErrorPane()
	{
		JOptionPane.showMessageDialog(null, "Invalid input detected!", "Error!", JOptionPane.ERROR_MESSAGE);
	}
}