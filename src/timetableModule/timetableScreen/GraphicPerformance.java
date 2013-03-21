package timetableModule.timetableScreen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;

import javax.swing.JPanel;

import timetableModule.data.Artist;
import timetableModule.data.Performance;
import timetableModule.gui.GUI;
import IO.IO;



public class GraphicPerformance implements VisibleObject
{
	int m_X, m_Y;
	int m_Width, m_Height;
	String m_Text;
	pTime m_Time, m_Length;
	Color color = null;
	
	boolean isMouseOver;
	
	Performance performance;

	public GraphicPerformance(String text, pTime time, pTime length)
	{
		isMouseOver = false;
		m_Text = text;
		m_Time = time;
		m_Length = length;
		m_X = 100;
		m_Y = 100;
		m_Width = 100;
		m_Height = 50;
		
		color = Color.RED;
	}	
	public GraphicPerformance(Performance performance)
	{
		this.performance = performance;
		isMouseOver = false;
		m_Text = "";
		Iterator<Artist> i = performance.getArtists().iterator();
		while(i.hasNext()){
			m_Text += i.next().getName();
			if(i.hasNext())
				m_Text += " + ";
		}
		m_Time = new pTime(Integer.parseInt(performance.getStartTime().substring(0, 2)), Integer.parseInt(performance.getStartTime().substring(3, 5)));
		m_Length = new pTime(Integer.parseInt(performance.getEndTime().substring(0, 2)) - m_Time.m_Hour, Integer.parseInt(performance.getEndTime().substring(3, 5)) - m_Time.m_Minute);

		m_X = 100;
		m_Y = 100;
		m_Width = 100;
		m_Height = 50;
		
		color = Color.RED;
	}
	
	
	
	public void setForm(int x, int y)
	{
		m_X = x; m_Y = y; m_Width = m_Length.getPixels();
	}
	
	private String getFullTimeString()
	{
		return m_Time.getString() + " - " + m_Time.combine(m_Length).getString();
	}
	
	public void setWidth(boolean expand)
	{
		if (expand)
		{
			String longest = getFullTimeString();
			if (m_Text.length() > longest.length()) longest = m_Text;
			
			m_Width = Math.max(m_Width,20 + (6*longest.length()));
			color = new Color(255,100,100);
		}
	}
	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}
	@Override
	public void draw(Graphics g)
	{
		Graphics2D dst = (Graphics2D) g;
		//box
			//filled
		dst.setColor(color);
		dst.fillRect(m_X, m_Y, m_Width, m_Height);
			//border
		dst.setColor(Color.BLACK);
		dst.drawRect(m_X+1, m_Y+1, m_Width-3, m_Height-3);
		
		//reset color
		color = Color.RED;
		
		//assume a letter is 10 pixels wide
		String _text = m_Text;
		String _time = getFullTimeString();
		
		if (!isMouseOver)
		{
			int letterFit = Math.max((m_Width-15)/10,1);
			if (letterFit < m_Text.length())
				_text = m_Text.substring(0, letterFit).concat("...");
			if (letterFit < m_Time.getString().length())
				_time = m_Time.getString().substring(0, letterFit).concat("...");
		}
		//text
		int offset = Math.min(15, Math.max(0, m_Width-12));
		
		dst.setColor(Color.WHITE);
		//dst.drawChars(_text.toCharArray(), 0, _text.length(), m_X + 15, m_Y + 15);
		//dst.drawChars(_time.toCharArray(), 0, _time.length(), m_X + 15, m_Y + 25);
		dst.drawString(_text, m_X + offset, m_Y + 15);
		dst.drawString(_time, m_X + offset, m_Y + 30);
		//dst.drawChars(m_Text.toCharArray(), 0, m_Text.length(), m_X + 10, m_Y + 10);
	}
	public boolean checkExists(){
		if(IO.getInstance().getFestival().findPerformance(performance.getName()) == null)
			return false;
		return true;
	}
	@Override
	public void update(JPanel s)
	{
		m_Text = "";
		Iterator<Artist> i = performance.getArtists().iterator();
		while(i.hasNext()){
			m_Text += i.next().getName();
			if(i.hasNext())
				m_Text += " + ";
		}
		m_Time = new pTime(Integer.parseInt(performance.getStartTime().substring(0, 2)), Integer.parseInt(performance.getStartTime().substring(3, 5)));
		m_Length = new pTime(Integer.parseInt(performance.getEndTime().substring(0, 2)) - m_Time.m_Hour, Integer.parseInt(performance.getEndTime().substring(3, 5)) - m_Time.m_Minute);
		Point p = s.getMousePosition();
		isMouseOver = false;
		if (p != null) isMouseOver = (m_X <= p.x && m_X+m_Length.getPixels() > p.x && m_Y <= p.y && m_Y+m_Height > p.y);
		if (isMouseOver)
		{
			Screen screen = GUI.getScreen();
			if (screen != null) screen.addReDraw(this);
		}
	}
	
}
