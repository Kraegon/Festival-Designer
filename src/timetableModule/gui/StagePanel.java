package timetableModule.gui;






import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import timetableModule.data.*;






public class StagePanel{
	
	public  JPanel makeStagePane(JFrame frame) {
		JPanel stagePane = new JPanel(new BorderLayout());
		JComponent[] comps = new JComponent[]{
			new JLabel("Name: "),
			new JLabel("Max visitors: "),
			new JTextField("Name"),
			new JTextField("Maximum amount of visitors"),
			new JCheckBox("Main stage?", false)
		};
		JButton stageButton = new JButton("OK");
		JPanel leftPanel = new JPanel(new GridLayout(2, 1));
		JPanel rightPanel = new JPanel(new GridLayout(2, 1));
		JPanel southPanel = new JPanel(new GridLayout(1, 2));
		
		stageButton.addActionListener(new InputListener(frame, comps, "stage"));

		leftPanel.add(comps[0]);
		leftPanel.add(comps[1]);

		rightPanel.add(comps[2]);
		rightPanel.add(comps[3]);

		southPanel.add(comps[4]);
		southPanel.add(stageButton);

		stagePane.add(leftPanel, BorderLayout.WEST);
		stagePane.add(rightPanel, BorderLayout.CENTER);
		stagePane.add(southPanel, BorderLayout.SOUTH);

		return stagePane;
	}
	public  JPanel editStagePane(final JFrame frame, String targetStage) {
		JPanel stagePane = new JPanel(new BorderLayout());
		final Stage stage = IO.getInstance().getFestival().findStage(targetStage);
		JComponent[] comps = new JComponent[]{
			new JLabel("Name: "),
			new JLabel("Max visitors: "),
			new JTextField(stage.getName()),
			new JTextField("" + stage.getVisitorsMax()),
			new JCheckBox("Main stage?", stage.isMainStage())
		};
		JButton stageButton = new JButton("OK");
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IO.getInstance().getFestival().getStages().remove(stage);
				frame.dispose();
			}
		});
		JPanel leftPanel = new JPanel(new GridLayout(2, 1));
		JPanel rightPanel = new JPanel(new GridLayout(2, 1));
		JPanel southPanel = new JPanel(new GridLayout(1, 2));

		stageButton.addActionListener(new EditListener(frame, comps, stage));

		leftPanel.add(comps[0]);
		leftPanel.add(comps[1]);

		rightPanel.add(comps[2]);
		rightPanel.add(comps[3]);

		southPanel.add(comps[4]);
		southPanel.add(stageButton);
		southPanel.add(delete);

		stagePane.add(leftPanel, BorderLayout.WEST);
		stagePane.add(rightPanel, BorderLayout.CENTER);
		stagePane.add(southPanel, BorderLayout.SOUTH);

		return stagePane;
	}
}
