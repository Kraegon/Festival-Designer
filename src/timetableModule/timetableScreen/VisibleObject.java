package timetableModule.timetableScreen;
import java.awt.Graphics;

import javax.swing.JPanel;


public interface VisibleObject
{
	public abstract void draw(Graphics g);
	public abstract void update(JPanel s);
	public abstract boolean checkExists();
}
