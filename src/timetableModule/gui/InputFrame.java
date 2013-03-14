package timetableModule.gui;






import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class InputFrame{
	
	JFrame frame;
	String source;
	
	public InputFrame(String source, Point sourcePoint){
		this.frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		frame.setLocation(new Point(sourcePoint.x + 150, sourcePoint.y + 150));
		this.source = source;
		switch(source){
		case "artist":
			ArtistPanel aPanel = new ArtistPanel();
			frame.setContentPane(aPanel.makeArtistPane(frame));
			frame.setTitle("Add artist");
			frame.setSize(new Dimension(300,200));
			break;
		case "stage":
			StagePanel sPanel = new StagePanel();
			frame.setContentPane(sPanel.makeStagePane(frame));
			frame.setTitle("Add stage");
			frame.setSize(new Dimension(275, 150));
			break;
		case "performance":
			PerformancePanel pPanel = new PerformancePanel();
			frame.setContentPane(pPanel.makePerformancePane(frame));
			frame.setTitle("Add performance");
			frame.setSize(new Dimension(400,307));
			break;
		case "editArtist":
			frame.setContentPane(makeSelector());
			frame.setTitle("Edit artist");
			break;
		case "editStage":
			frame.setContentPane(makeSelector());
			frame.setTitle("Edit stage");
			break;
		case "editPerformance":
			frame.setContentPane(makeSelector());
			frame.setTitle("Edit performance");
			break;
		default:
			frame.setContentPane(makeErrorPane());
			frame.setTitle("Error");
			break;
		}
		frame.setVisible(true);
	}
	
	public JPanel makeErrorPane(){
		JPanel errorPane = new JPanel(new BorderLayout());
		errorPane.add(new JLabel("Something has gone horribly wrong."), BorderLayout.CENTER);
		return errorPane;
	}
	public JPanel makeSelector(){
		JPanel selectorPane = new JPanel(new BorderLayout());
		selectorPane.add(new JLabel(""));
		String[] names;
		switch(source){
		case "editArtist":
			names = new String[IO.getInstance().getFestival().getArtists().size()];
			for(int i = 0; i < names.length; i++){
				names[i] = IO.getInstance().getFestival().getArtists().get(i).getName();
			}
			break;
		case "editStage":
			names = new String[IO.getInstance().getFestival().getStages().size()];
			for(int i = 0; i < names.length; i++){
				names[i] = IO.getInstance().getFestival().getStages().get(i).getName();
			}
			break;
		case "editPerformance":
			names = new String[IO.getInstance().getFestival().getPerformances().size()];
			for(int i = 0; i < names.length; i++){
				names[i] = IO.getInstance().getFestival().getPerformances().get(i).getName();
			}
			break;
		default: names = new String[0];
		}
		final JList<String> list = new JList<String>(names);
		list.setSelectedIndex(0);
		JPanel okPane = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch(source){
					case "editArtist":
						ArtistPanel aPanel = new ArtistPanel();
						frame.setContentPane(aPanel.editArtistPane(frame, list.getSelectedValue()));
						frame.setSize(new Dimension(300,200));
					break;
					case "editStage":
						StagePanel sPanel = new StagePanel();
						frame.setContentPane(sPanel.editStagePane(frame, list.getSelectedValue()));
						frame.setSize(new Dimension(275, 150));
					break;
					case "editPerformance":
						PerformancePanel pPanel = new PerformancePanel();
						frame.setContentPane(pPanel.editPerformancePane(frame, list.getSelectedValue()));
						frame.setSize(new Dimension(400,307));
					break;
				}
			}
		});
		okPane.add(ok);
		selectorPane.add(okPane, BorderLayout.SOUTH);
		selectorPane.add(new JScrollPane(list), BorderLayout.CENTER);
		frame.setSize(new Dimension(200,250));
		frame.setResizable(true);
		return selectorPane;
	}
}
