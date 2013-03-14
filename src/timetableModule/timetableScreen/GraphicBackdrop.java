package timetableModule.timetableScreen;
import timetableModule.gui.IO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GraphicBackdrop implements VisibleObject
{
	BufferedImage m_Backdrop;
	int m_Limit = 0;
	
	public GraphicBackdrop()
	{
		m_Backdrop = null;
		try
		{
			m_Backdrop = ImageIO.read(new File("resources/metal.jpg"));
		}
		catch (IOException e)
		{
		}
	}
	
	public void setLimit(int limit)
	{
		//sets the width of the panel
		m_Limit = limit;
	}

	@Override
	public void draw(Graphics g)
	{
		Graphics2D dst = (Graphics2D) g;

		dst.setColor(Color.WHITE);
		boolean longLine = true;
		for (int i = 100, j = 0; i < m_Limit; i += 30, j++)
		{
			int len = 20;
			if (longLine)
			{
				len = 10000;
				int _time = (9 + (j/2))%24;
				String time = _time+":00";
				dst.drawChars(time.toCharArray(),0,time.length(), i + 5, 45);
			}
			dst.drawLine(i, 5, i, 5 + len);
			longLine = !longLine;
		}
		
		if (m_Backdrop != null)
		{
			//draw fucking image
		}
	}
	public boolean checkExists(){
		return true;
	}
	@Override
	public void update(JPanel s)
	{	
	}
	
}
