package simulator.playfield;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import simulator.Designer;
import simulator.DisplayableObjects.DisplayObject;
import simulator.DisplayableObjects.DisplayTargetPoint;

class PopupListener extends MouseAdapter // ADD: LESLEY (fixed actionlisteners and added remove option)
{
	SimulationPanel simPanel; //ADD: LESLEY
	JPopupMenu popup;
	private static DisplayObject selectedObject;
	private List<DisplayObject> dObjects;
	
	JLabel kop = new JLabel("");
	JMenuItem menuItem = new JMenuItem("  Set Target");
	JMenuItem size = new JMenuItem("  Change Size");
	JMenuItem remove = new JMenuItem("  Remove from field");
	JMenuItem addNeighbour = new JMenuItem("  Add Neighbour");	//ADD: LESLEY

	public PopupListener(SimulationPanel sim) {
		// jesper 19-3
		popup = new JPopupMenu();
		popup.add(kop);
		//popup.add(size); 			// Changing size is not important at this time
		popup.add(remove);
		popup.add(addNeighbour);	// ADD: LESLEY
		popup.add(menuItem);
		this.simPanel = sim;
		
		addNeighbour.setEnabled(false);
		
		menuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent action) 
			{
				Designer.setSouthLabel("Target mode enabled");
				SimulationPanel.setTargetingMode(true);
			}
		});
		
		remove.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent action) 
			{
				for (DisplayObject d : dObjects)
				{
					if (d.getName().equals(selectedObject.getName()))
					{
						d.setLocation(new Point2D.Double(0, 0));
						dObjects.remove(d);
						simPanel.repaintTimerOff();
						break;
					}
				}
				System.out.println("----------------\nREMOVED OBJECT: " + selectedObject.getName() + "\ndisplayObjects left in list:\n");
				for (DisplayObject d : dObjects)
				{
					System.out.println(d.getName());
				}
				System.out.println("----------------");
			}
		});
		
		addNeighbour.addActionListener(new ActionListener() // ADD:LESLEY
		{
			@Override
			public void actionPerformed(ActionEvent action) 
			{
				String givenTarget = JOptionPane.showInputDialog("Enter the name of the TargetPoint you want this to be a neighbour of:");
				DisplayTargetPoint target = (DisplayTargetPoint) selectedObject;
				boolean succes = false;
				
				for (DisplayObject d : dObjects)
				{
					if(givenTarget.equals(d.getName()))
					{
						target.setNeighbour((DisplayTargetPoint)d);
						System.out.println("Succes!");
						target.printNeighbours();
						succes = true;
						break;
					}
				}
				if (!succes)
				{
					JOptionPane.showMessageDialog(null, "The given targetPoint does not exist!");
				}
				simPanel.repaintTimerOff();
			}
		});
	}
	
	public void show(MouseEvent e, DisplayObject a, List<DisplayObject> objects) 
	{
		boolean isTargetPoint = false;
		selectedObject = a;
		if (selectedObject.getClass() == DisplayTargetPoint.class)        // ADD:LESLEY
		{                                                                 // ADD:LESLEY
			addNeighbour.setEnabled(true);                                // ADD:LESLEY
		}
		kop.setFont(new Font("Arial", Font.ITALIC, 12));
		kop.setText(selectedObject.getType() + ": " + selectedObject.getName());
		dObjects = objects;
		
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
}