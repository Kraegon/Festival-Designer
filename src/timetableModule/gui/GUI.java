package timetableModule.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import timetableModule.timetableScreen.GraphicBackdrop;
import timetableModule.timetableScreen.Screen;
import IO.IO;

	public class GUI extends JFrame
	{
		// Attributen
		private InputFrame inputFrame;
		private static Screen agendaPanel = Screen.getInstance();
		
		private static final long serialVersionUID = 1L;
	    JLabel statusLabel;
    	
	    private String status = "This panel will show you useful messages";
	    JButton[] buttons;
	    IO io;
		CheckInputs checkInp;
	    
	    public GUI()
	    {
	        super("FestivalPlanner"); 
	        makeFrame();
	        io = IO.getInstance();
	        checkInp = new CheckInputs();
	        Timer timer = new Timer(1000/60, new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Button refresh
					enableButtons(buttons);
					
					//Table refresh
					repaint();
				}
			});
	        timer.start();
	        
	        
	    }
	    
	    private void makeFrame()
	    {
	        /** Main Frame **/
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
	        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLocation(new Point(100, 100));

	        /** Maak menuBar **/
	        makeMenuBar();
	        
	        /** Componenten **/
	         
	        /** westPanel **/
	        JPanel westPanel = new JPanel();
	        westPanel.setLayout(new GridLayout(10, 1, 10, 10));
	        contentPane.add(westPanel, BorderLayout.WEST);
	        
	        /** WEST **/
	        JButton addArtist = new JButton("Add Artist");
	        westPanel.add(addArtist);
	        addArtist.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else {              	
	                		addArtist();
	                	}     
	                }
	            });        
	        JButton addStage = new JButton("Add Stage");
	        westPanel.add(addStage);
	        addStage.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else {              	
	                		addStage();
	                	}
	                }
	            });
	        
	        JButton addPerformance = new JButton("Add Performance");
	        westPanel.add(addPerformance);
	        addPerformance.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else if(io.getFestival().getArtists().isEmpty() || io.getFestival().getStages().isEmpty()){              	
	                		statusLabel.setText("No stages or artists made.");
	                	} else {
	                		addPerformance();
	                	}
	                }
	            });
	        westPanel.add(new JSeparator());
	        JButton editArtist = new JButton("Edit Artist");
	        westPanel.add(editArtist);
	        editArtist.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else {              	
	                		editArtist();
	                	}                    
	                }
	            });
	        JButton editStage = new JButton("Edit Stage");
	        westPanel.add(editStage);
	        editStage.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else {              	
	                		editStage();
	                	}                    
	                }
	            });
	        JButton editPerformance = new JButton("Edit Performance");
	        westPanel.add(editPerformance);
	        editPerformance.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	if(io.getFestival() == null){
	                		statusLabel.setText("No festival set.");
	                	} else {              	
	                		editPerformance();
	                	}                    
	                }
	            });
	          
	        /** CENTER **/
	       /* center = new JButton("CENTER");
	        contentPane.add(center, BorderLayout.CENTER);
	        center.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                { 
                	if(io.getFestival() == null){
                		statusLabel.setText("No festival set.");
                	} else {              	
	                	io.printFestival();              	
	                    statusLabel.setText("This is where the agenda will be.");
                	}
                }
            });*/ //DEBUG
	        
	        /*for (int i = 0; i < 15; i ++)
			{
				GraphicStage g = new GraphicStage("podium "+(i+1));
				g.setVerticalPosition(i);
				agendaPanel.addDrawable(g);
				Random r = new Random();
				int f = 2 + Math.abs((r.nextInt()%10));
				
				pTime endTime = new pTime(9,0);
				for (int j = 0; j < f;  j ++)
				{				
					endTime.add(Math.abs(r.nextInt())%5);
					pTime length = new pTime(0,30 + (Math.abs(r.nextInt())%90));
					
					GraphicPerformance gp = new GraphicPerformance("Sabaton",endTime.clone(),length.clone());
					
					g.addPerformance(gp);
					
					endTime.add(length);
				}
				if (f > 0) System.out.println();
			}*/ //DEBUG
			
		
	        JPanel center = new JPanel(new GridLayout(1,1));
	        contentPane.add(center, BorderLayout.CENTER);
	        agendaPanel.setVisible(true);
			agendaPanel.setBackground(Color.BLACK);
			agendaPanel.setDoubleBuffered(true);
			agendaPanel.setPreferredSize(new Dimension(1000,1000));
			
			//add backdrop first !!!
			agendaPanel.addDrawable(new GraphicBackdrop());
			JScrollPane scrollPane = new JScrollPane(agendaPanel);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			center.add(scrollPane);
			
	        /** SOUTH **/
	        statusLabel = new JLabel(getStatus());
	        contentPane.add(statusLabel, BorderLayout.SOUTH);
	        
	        /** Componenten samenvoegen **/
	        setContentPane(contentPane);
	        setSize(1000, 600);
	        setVisible(true);
	        
	        /** Maak een public verzameling van alle buttons die in deze methode lokaal staan **/
	        buttons = new JButton[]
	        {
	        	addArtist, addPerformance, addStage, 
	        	editArtist, editPerformance, editStage
	        };
	        setResizable(false);
	    }
	    
	    private void makeMenuBar()
	    {
	        /** JMenuBar **/
	        JMenuBar menuBar = new JMenuBar();
	        
	        /** JMenu **/
	        JMenu fileMenu = new JMenu("File");
	        JMenu simulatorMenu = new JMenu("Simulator");
	        JMenu infoMenu = new JMenu("Info");
	        menuBar.add(fileMenu);
	        menuBar.add(simulatorMenu);
	        menuBar.add(infoMenu);
	        
	        /** JMenuItem **/
	        JMenuItem newMenu = new JMenuItem("New");
	        newMenu.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                {
	                    setTitle("FestivalPlanner - " + io.getFilePath());
	                    newFestivalDialog();
	                }
	            });
	        fileMenu.add(newMenu);
	        
	        fileMenu.addSeparator();
	        
	        JMenuItem saveMenu = new JMenuItem("Save");
	        saveMenu.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	try{
	                		io.saveFestival();
	                		statusLabel.setText("Save success");
	                	} catch (IOException exc){
	                		statusLabel.setText("Saving failed");
	                	} catch (NullPointerException exc) {
	                		statusLabel.setText("No festival set");
	                	}
	                }
	            });
	        fileMenu.add(saveMenu);
	        
	        JMenuItem saveAsMenu = new JMenuItem("Save as...");
	        saveAsMenu.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                	JFileChooser choose = new JFileChooser();	                	
	                	choose.setFileFilter(new FileNameExtensionFilter("Festival data files | .fest", "fest"));
	                	choose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	                	int choice = choose.showSaveDialog(getContentPane());
	                	if(choice == JFileChooser.APPROVE_OPTION){
	                		io.setFilePath(choose.getSelectedFile() + ".fest");
	                		try{
		                		io.saveFestival();
		                		statusLabel.setText("Save successful");
		                		setTitle("FestivalPlanner - " + io.getFestiFile().getName());
		                	} catch (IOException exc){
		                		statusLabel.setText("Saving failed");
		                	} catch (NullPointerException exc) {
		                		statusLabel.setText("No festival set");
		                	}
	                	}
	                }
	            });
	        fileMenu.add(saveAsMenu);
	        
	        fileMenu.addSeparator();
	        
	        JMenuItem openMenu = new JMenuItem("Open");
	        openMenu.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                {
	                	JFileChooser choose = new JFileChooser();
	                	choose.setFileFilter(new FileNameExtensionFilter("Festival data files | .fest", "fest"));
	                	choose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	                	int choice = choose.showOpenDialog(getContentPane());
	                	if(choice == JFileChooser.APPROVE_OPTION) {
	                		io.setFilePath(choose.getSelectedFile().getPath());
	                		io.setFestiFile(choose.getSelectedFile());
	                		try{
	                			io.openFestival();
	                			statusLabel.setText("Opening successful");
	                			setTitle("FestivalPlanner - " + io.getFestiFile().getName());
	                		} catch(IOException exc){
	                			statusLabel.setText("Opening failed");
	                			exc.printStackTrace();
	                		}
	                	}
			    		enableButtons(buttons);
	                }
	            });
	        fileMenu.add(openMenu);	           
	        
	        JMenuItem simulatorItem = new JMenuItem("Execute");
	        simulatorItem.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                    executeSimulator();
	                }
	            });
	        simulatorMenu.add(simulatorItem);
	        
	        JMenuItem aboutItem = new JMenuItem("Show info");
	        aboutItem.addActionListener(new ActionListener() 
	            {
	                public void actionPerformed(ActionEvent e) 
	                { 
	                    showInfo();  
	                }
	            });
	        infoMenu.add(aboutItem);
	        
	        /** menuBar op frame zetten **/
	        setJMenuBar(menuBar);
	        
	    }
	    
	    private void newFestivalDialog()
	    {
	    	JTextField fesName = new JTextField();
	    	JTextField day = new JTextField();
	    	JTextField month = new JTextField();
	    	JTextField year = new JTextField();
	    	JTextField startHour = new JTextField();
	    	JTextField startMinute = new JTextField();
	    	JTextField endHour = new JTextField();
	    	JTextField endMinute = new JTextField();
	    	JLabel errorLabel = new JLabel();
	    	
	    	final JComponent[] inputs = new JComponent[] 
	    		{
	    			new JLabel("Name Festival:"),
	    			fesName,
	    			new JLabel("Set date (DD-MM-YYYY):"),
	    			day, month, year,
	    			new JLabel("Set starting time (Hours-Minutes):"),
	    			startHour, startMinute,
	    			new JLabel("Set ending time (Hours-Minutes):"),
	    			endHour, endMinute,
	    			errorLabel
	    		};
	    	// Alle inputvelden waar alleen nummers in mogen
	    	final JTextField[] numberInputs = new JTextField[]
    			{
	    			day, month, year, startHour, startMinute, endHour, endMinute
    			};
	    	
	    	boolean exitAllowed = false;
	    	boolean correctData = true;
	    	
		    while(!exitAllowed)
		    {
		    	// -1 = defaultClose; 2 = Cancel; 0 = OK;
		    	if (JOptionPane.showConfirmDialog(null, inputs, "New Festival", JOptionPane.OK_CANCEL_OPTION) == 0)
		    	{
		    		//correctData aan begin van checkloop op true zetten
			    	correctData = true;
			    	
			    	// empty fields?
			    	for (JComponent i : inputs)
			    	{
			    		if (i.getClass() == JTextField.class)
			    		{
			    			JTextField compTemp = (JTextField) i;
			    			if (compTemp.getText().equals(""))
			    			{
			    				errorLabel.setText("Please fill in every field!");
			    				correctData = false;
				    			break;
			    			}
			    		}
			    	}
			    	
			    	for (JTextField j : numberInputs)
			    	{
				    	if (!checkInp.onlyNumbers(j.getText()))
				    	{
				    		errorLabel.setText("Please use numbers when entering dates or moments.");
				    		correctData = false;
				    		break;
				    	}
				    }
			    	
			    	// Check leading zero's
			    	for (JTextField j : numberInputs)
			    	{
				    	if (checkInp.isFirstCharZero(j.getText()))
				    	{
				    		errorLabel.setText("Please refrain from using leading zero's.");
				    		correctData = false;
				    		break;
				    	}
				    }
			    	
			    	// Check timevalues
			    	if (correctData)
			    	{
			    		if (!checkInp.isMinuteSecondValue(startMinute.getText()) ||
			    			!checkInp.isMinuteSecondValue(endMinute.getText()) ||
			    			!checkInp.isHourValue(startHour.getText()) ||
			    			!checkInp.isHourValue(endHour.getText()) ||
			    			!checkInp.isDay(day.getText()) ||
			    			!checkInp.isMonth(month.getText()) ||
			    			!checkInp.isYear(year.getText()))
			    		{
			    			errorLabel.setText("Please use valid values when entering dates or moments");
			    			correctData = false;
			    			System.out.println("WRONG TIMEVALUE: THE WINDOW IS NOT SUPPOSED TO CLOSE ITSELF!!");
				    		//break;
			    		}
			    	}
			    	
			    	//Check if startHour/Minute take place before endHour/endMinute
			    	GregorianCalendar startDate = new GregorianCalendar(2000, 12, 12, 
			    			Integer.parseInt(startHour.getText()),
			    			Integer.parseInt(startMinute.getText()));
			    	
			    	GregorianCalendar endDate = new GregorianCalendar(2000, 12, 12, 
							Integer.parseInt(endHour.getText()),
							Integer.parseInt(endMinute.getText()));
			    	
			    	if(startDate.getTimeInMillis() > endDate.getTimeInMillis())
			    	{
			    		errorLabel.setText("The ending time can't be before the starting time of your festival.");
		    			correctData = false;
			    		//break;
			    	}
			    	
			    	// all data was entered correctly
			    	if (correctData)
			    	{	
			    		io.setNewFestival("default.fest");
			    		enableButtons(buttons);
			    		io.getFestival().setName(fesName.getText());
	    				io.getFestival().setDate(day.getText() + " - " + month.getText() + " - " + year.getText());
	    				io.getFestival().setStartTime(startHour.getText() + ":" + startMinute.getText());
	    				io.getFestival().setEndTime(endHour.getText() + ":" + endMinute.getText());
				    	statusLabel.setText("Success");
				    	exitAllowed = true;
			    	}	
			    }
			    else 
			    {
			    	// close window
			    	exitAllowed = true;
			    	System.out.println("CANCELED");
			    }
		    }
	    }
	    public static Screen getScreen()
		{
			return agendaPanel;
		}
	    
	    private void addArtist()
	    {
	    	inputFrame = new InputFrame("artist", getLocation());
	        statusLabel.setText("Opened add artist screen.");
	    }
	    
	    private void addPerformance() 
	    {
	    	inputFrame = new InputFrame("performance", getLocation());
	    	statusLabel.setText("Opened add performance screen.");
		}
	    
	    private void addStage()
	    {
	    	inputFrame = new InputFrame("stage", getLocation());
	    	statusLabel.setText("Opened add stage screen.");
	    }
	    private void editArtist(){
	    	inputFrame = new InputFrame("editArtist", getLocation());
	    	statusLabel.setText("Opened edit screen.");
	    }
	    private void editStage(){
	    	inputFrame = new InputFrame("editStage", getLocation());
	    	statusLabel.setText("Opened edit screen.");
	    }
	    private void editPerformance(){
	    	inputFrame = new InputFrame("editPerformance", getLocation());
	    	statusLabel.setText("Opened edit screen.");
	    }
	    private void showInfo()
	    {
	        JOptionPane.showMessageDialog(null, "This program was created by projectgroup TIA6 from Avans Hogeschool in Breda.");
	    }
	    
	    private String getStatus()
	    {
	        return status;
	    }
	    
	    private void executeSimulator()
	    {
	    	statusLabel.setText("Starting simulator");
	    	simulator.SimulatorMain.getInstance();
	    	if(simulator.SimulatorMain.getInstance().getDesigner() != null)
	    		simulator.SimulatorMain.getInstance().getDesigner().setVisible(true);
	    }
	
	    private void enableButtons(JButton[] buttons)
	    {
	    	for (JButton b : buttons)
	    	{	
	    		if(io.getFestival() == null){
	    			b.setEnabled(false);
	    		} else if((b.getText().contains("Performance")) && (io.getFestival().getArtists().isEmpty() || io.getFestival().getStages().isEmpty())){ 
	    			b.setEnabled(false);
            	} else if(b.getText().contains("Edit Artist") && io.getFestival().getArtists().isEmpty()){
            		b.setEnabled(false);
            	} else if(b.getText().contains("Edit Stage") && io.getFestival().getStages().isEmpty()) {
            		b.setEnabled(false);
            	} else if(b.getText().contains("Edit Performance") && io.getFestival().getPerformances().isEmpty()) {   
            		b.setEnabled(false);
            	} else {
            		b.setEnabled(true);
            	}
            }
	    }
	}
