package timetableModule.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import timetableModule.data.*;





public class PerformancePanel{
	
	public JPanel makePerformancePane(JFrame frame){
		final JPanel performancePane = new JPanel(new BorderLayout());
		LinkedList<Stage> stages = IO.getInstance().getFestival().getStages();
		String[] stageNames = new String[stages.size()];
		for(int i = 0; i < stages.size(); i++){
			stageNames[i] = stages.get(i).getName();
		}
		LinkedList<Artist> artists = IO.getInstance().getFestival().getArtists();
		final DefaultListModel<String> artistNames = new DefaultListModel<String>();
		for(int i = 0; i < artists.size(); i++){
			artistNames.addElement(artists.get(i).getName());
		}
		final DefaultListModel<String> queueList = new DefaultListModel<String>();
		final JList<String> artistsList = new JList<String>(artistNames);
		final JList<String> artistQueue = new JList<String>(queueList);
		JButton artistAdd = new JButton("»»");
		artistAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String element = artistsList.getSelectedValue();
				queueList.addElement(element);
				artistNames.removeElement(element);
			}
		});
		JButton artistRemove = new JButton("««");
		artistRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String element = artistQueue.getSelectedValue();
				queueList.removeElement(element);
				artistNames.addElement(element);
			}
		});
		JComponent[] comps = new JComponent[]{
				new JLabel("Stages:"),//0
				new JLabel("Artists:"),//1
				new JLabel("Adding:"),//2
				new JLabel("Start time: "),//3
				new JLabel("End time: "),//4
				new JLabel("Estimated popularity: "),//5
				new JLabel("Name:"), //6
				new JTextField("name"), //7
				new JTextField("start time"),//8
				new JTextField("end time"),//9
				new JTextField("popularity"),//10
				new JList<String>(stageNames),//11 (stages)
				artistQueue
		};
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new InputListener(frame, comps, "performance"));
		
		JPanel northPanel = new JPanel(new GridLayout(1,4));
		JPanel leftPanel = new JPanel(new GridLayout(1,1));//Stage list
		JPanel centerPanel = new JPanel(new BorderLayout());//Container for center panels
		JPanel southPanel = new JPanel(new GridLayout(1, 2));//OK button
		JPanel centerTopPanel = new JPanel(new GridLayout(1,3));//Artist list,buttons panel and Adding List
		JPanel centerTopMiddlePanel = new JPanel(new GridLayout(2,1));
		JPanel centerBottomPanel = new JPanel(new GridLayout(4,2));//Start time, end time and estimated popularity.
		northPanel.add(comps[0]);
		northPanel.add(comps[1]);
		northPanel.add(new JLabel(""));
		northPanel.add(comps[2]);
		leftPanel.add(new JScrollPane(comps[11]));
		leftPanel.setSize(new Dimension(100, 300));
		southPanel.add(okButton);
		centerTopPanel.add(new JScrollPane(artistsList));
		centerTopMiddlePanel.add(artistAdd);
		centerTopMiddlePanel.add(artistRemove);
		centerTopPanel.add(centerTopMiddlePanel);
		centerTopPanel.add(new JScrollPane(artistQueue));
		centerBottomPanel.add(comps[6]);
		centerBottomPanel.add(comps[7]);
		centerBottomPanel.add(comps[3]);
		centerBottomPanel.add(comps[8]);
		centerBottomPanel.add(comps[4]);
		centerBottomPanel.add(comps[9]);
		centerBottomPanel.add(comps[5]);
		centerBottomPanel.add(comps[10]);
		
		southPanel.add(okButton);
		centerPanel.add(centerTopPanel, BorderLayout.NORTH);
		centerPanel.add(centerBottomPanel, BorderLayout.SOUTH);
		performancePane.add(northPanel, BorderLayout.NORTH);
		performancePane.add(leftPanel, BorderLayout.WEST);
		performancePane.add(centerPanel, BorderLayout.CENTER);
		performancePane.add(southPanel, BorderLayout.SOUTH);
		
		return performancePane;
	}	
	public JPanel editPerformancePane(final JFrame frame, String targetPerformance){
		JPanel performancePane = new JPanel(new BorderLayout());
		final Performance performance = IO.getInstance().getFestival().findPerformance(targetPerformance);
		LinkedList<Stage> stages = IO.getInstance().getFestival().getStages();
		String[] stageNames = new String[stages.size()];
		for(int i = 0; i < stages.size(); i++){
			stageNames[i] = stages.get(i).getName();
		}
		JList<String> stageList = new JList<String>(stageNames);
		LinkedList<Artist> artists = IO.getInstance().getFestival().getArtists();
		final DefaultListModel<String> artistNames = new DefaultListModel<String>();
		for(int i = 0; i < artists.size(); i++){
			artistNames.addElement(artists.get(i).getName());
		}
		final DefaultListModel<String> queueList = new DefaultListModel<String>();
		for(int i = 0; i < performance.getArtists().size(); i++){
			String element = performance.getArtists().get(i).getName();
			queueList.addElement(element);
			artistNames.removeElement(element);
		}
		final JList<String> artistsList = new JList<String>(artistNames);
		final JList<String> artistQueue = new JList<String>(queueList);
		JButton artistAdd = new JButton("»");
		artistAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String element = artistsList.getSelectedValue();
				queueList.addElement(element);
				artistNames.removeElement(element);
			}
		});
		JButton artistRemove = new JButton("«");
		artistRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String element = artistQueue.getSelectedValue();
				queueList.removeElement(element);
				artistNames.addElement(element);
			}
		});
		String startTime = "" + performance.getStartTime();
		String endTime = "" + performance.getEndTime();
		String popularity = "" + performance.getPopularity();
		JComponent[] comps = new JComponent[]{
				new JLabel("Stages"),//0
				new JLabel("Artists"),//1
				new JLabel("Adding"),//2
				new JLabel("Start time: "),//3
				new JLabel("End time: "),//4
				new JLabel("Estimated popularity: "),//5
				new JLabel("Name:"), //6
				new JTextField(performance.getName()), //7
				new JTextField(startTime),//8
				new JTextField(endTime),//9
				new JTextField(popularity),//10
				stageList,//11 (stages)
				artistQueue
		};
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new EditListener(frame, comps, performance));
		
		stageList.setSelectedValue(performance.getStage(), true);
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IO.getInstance().getFestival().getPerformances().remove(performance);
				frame.dispose();
			}
		});
		JPanel northPanel = new JPanel(new GridLayout(1,4));
		JPanel leftPanel = new JPanel(new GridLayout(1,1));//Stage list
		JPanel centerPanel = new JPanel(new BorderLayout());//Container for center panels
		JPanel southPanel = new JPanel(new GridLayout(1, 2));//OK button
		JPanel centerTopPanel = new JPanel(new GridLayout(1,3));//Artist list,buttons panel and Adding List
		JPanel centerTopMiddlePanel = new JPanel(new GridLayout(2,1));
		JPanel centerBottomPanel = new JPanel(new GridLayout(4,2));//Start time, end time and estimated popularity.
		leftPanel.add(new JScrollPane(comps[11]));
		southPanel.add(okButton);	
		centerTopPanel.add(new JScrollPane(artistsList));
		centerTopMiddlePanel.add(artistAdd);
		centerTopMiddlePanel.add(artistRemove);
		centerTopPanel.add(centerTopMiddlePanel);
		centerTopPanel.add(new JScrollPane(artistQueue));
		northPanel.add(comps[0]);
		northPanel.add(comps[1]);
		northPanel.add(new JLabel(""));
		northPanel.add(comps[2]);
		centerBottomPanel.add(comps[6]);
		centerBottomPanel.add(comps[7]);
		centerBottomPanel.add(comps[3]);
		centerBottomPanel.add(comps[8]);
		centerBottomPanel.add(comps[4]);
		centerBottomPanel.add(comps[9]);
		centerBottomPanel.add(comps[5]);
		centerBottomPanel.add(comps[10]);
		southPanel.add(delete);
		southPanel.add(okButton);
		centerPanel.add(centerTopPanel, BorderLayout.NORTH);
		centerPanel.add(centerBottomPanel, BorderLayout.SOUTH);
		performancePane.add(northPanel, BorderLayout.NORTH);
		performancePane.add(leftPanel, BorderLayout.WEST);
		performancePane.add(centerPanel, BorderLayout.CENTER);
		performancePane.add(southPanel, BorderLayout.SOUTH);
		
		return performancePane;
	}	
}