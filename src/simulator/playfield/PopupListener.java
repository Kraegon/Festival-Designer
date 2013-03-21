package simulator.playfield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import simulator.Designer;
import simulator.DisplayableObjects.DisplayObject;

class PopupListener extends MouseAdapter 
{
	JPopupMenu popup;
	private static DisplayObject selectedObject;
	JLabel kop = new JLabel("");
	JMenuItem menuItem = new JMenuItem("Set Target");
	JMenuItem Rotation = new JMenuItem("Rotation");

	public PopupListener() {
		// jesper 19-3
		popup = new JPopupMenu();
		popup.add(kop);
		popup.add(Rotation);
		popup.add(menuItem);

	}
	
	public void show(MouseEvent e, DisplayObject a) {
		selectedObject = a;
		kop.setText(selectedObject.getName());
		
		menuItem.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent action) 
			{
				//Designer.setSouthLabel("Target mode enabled");
				//SimulationPanel.setTargetingMode(true);
			}
		
		});
		// show
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
}