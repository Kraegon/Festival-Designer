package timetableModule.gui;






import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

import timetableModule.data.*;

import timetableModule.timetableScreen.*;





public class InputListener implements ActionListener{
	private JFrame frame;
	private JComponent[] comps;
	private String target;
	/**
	 * Constructor for the InputListerener to 
	 * @param frame : To be closed at the end of execution.
	 * @param comps : Components to read out.
	 */
	public InputListener(JFrame frame, JComponent[] comps, String target){
		this.frame = frame;
		this.comps = comps;
		this.target = target;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object[] inputDetails = new Object[16];
		int counter = 0;
		for(int i = 0; i < comps.length; i++){
			if(comps[i].getClass() == JTextField.class){
				JTextField compTemp = (JTextField) comps[i];
				inputDetails[counter] = compTemp.getText();
				counter++;
			} 
			if(comps[i].getClass() == JCheckBox.class){
				JCheckBox compTemp = (JCheckBox) comps[i];
				inputDetails[counter] = compTemp.isSelected();
				counter++;
			}
			if(comps[i].getClass() == JList.class){
				JList<String> compTemp = (JList<String>) comps[i];
				inputDetails[counter] = compTemp.getSelectedValue();
				break;
			}
		}
		switch(target){
			case "artist": 
				String nameA = (String) inputDetails[0];
				String genreA = (String) inputDetails[1];
				String miscA = (String) inputDetails[2];
				Artist artist = new Artist(nameA,genreA,miscA);
				IO.getInstance().getFestival().addArtist(artist);
				Screen.getInstance().addDrawable(new GraphicArtist(artist));
				break;
			case "stage": 
				String nameS = (String) inputDetails[0];
				int maxVisitorsS = Integer.parseInt((String) inputDetails[1]);
				boolean isMainStageS = (boolean) inputDetails[2];
				Stage stage = new Stage(nameS, maxVisitorsS, isMainStageS);
				IO.getInstance().getFestival().addStage(stage);
				Screen.getInstance().addDrawable(new GraphicStage(stage));
				break;
			case "performance": 
				String nameP = (String) inputDetails[0];
				String startTimeP = (String) inputDetails[1];
				String endTimeP = (String) inputDetails[2];
				int popularityP = Integer.parseInt((String) inputDetails[3]);
				Stage stageP = IO.getInstance().getFestival().findStage((String) inputDetails[4]);
				Performance p = new Performance(nameP, startTimeP,endTimeP,popularityP,stageP);
				
				IO.getInstance().getFestival().addPerformance(p);
				JList<String> artistsList = (JList<String>) comps[12];
				DefaultListModel<String> artistsModel = (DefaultListModel<String>) artistsList.getModel();
				Object[] artistsArray = artistsModel.toArray();
				for(Object s : artistsArray){
					p.addArtists(IO.getInstance().getFestival().findArtist((String) s));
				}
				Screen.getInstance().findStage(p.getStage().getName()).addPerformance(new GraphicPerformance(p));
				break;
		}
		
		frame.dispose();
	}
}