package timetableModule.timetableScreen;
import timetableModule.gui.IO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import timetableModule.data.*;



public class GraphicArtist implements VisibleObject
{
	LinkedList<GraphicPerformance> m_List;
	String m_Name;
	int m_Vpos; //vertical position
	
	Artist artist;

	public GraphicArtist(String name)
	{
		m_Name = name;
		m_List = new LinkedList<>();
	}
	public GraphicArtist(Artist artist){
		this.artist = artist;
		m_Name = artist.getName();
		m_List = new LinkedList<>();
	}
	
	public int getEstimatedWidth()
	{
		return (m_List.size()+1)*100;
	}
	
	public void setVerticalPosition(int i)
	{
		m_Vpos = i;
	}
	
	public void addPerformance(GraphicPerformance p)
	{
		m_List.add(p);
	}
	
	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	@Override
	public void draw(Graphics g)
	{
		System.out.print("Drawing Artist...");
		int m_Y = 50 + 50 * m_Vpos;
		Graphics2D dst = (Graphics2D) g;
		//box
		dst.setColor(Color.BLUE);
		dst.fillRect(0, m_Y, 96, 50);
		dst.setColor(Color.BLACK);
		dst.drawRect(0, m_Y, 96, 50);
		//text
		dst.setColor(Color.WHITE);
		dst.drawString(m_Name, 10, m_Y + 20);
		
		
		for (int i = 0; i < m_List.size(); i ++)
		{
			GraphicPerformance p = m_List.get(i);
			p.setForm(100*(i+1),m_Y);
			p.draw(g);
		}		
	}
	public boolean checkExists(){
		if(IO.getInstance().getFestival().findArtist(artist.getName()) == null)
			return false;
		return true;
	}
	@Override
	public void update(JPanel s)
	{
		m_Name = artist.getName();
		for (int i = 0; i < m_List.size(); i ++)
		{
			GraphicPerformance p = m_List.get(i);
			p.update(s);
		}	
	}
	
}
