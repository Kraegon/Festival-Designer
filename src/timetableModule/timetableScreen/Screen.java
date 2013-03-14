package timetableModule.timetableScreen;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import timetableModule.data.*;


@SuppressWarnings("serial")
public class Screen extends JPanel
{
	static Screen INSTANCE;
	LinkedList<VisibleObject> m_List;
	LinkedList<VisibleObject> m_ReDrawList;
	
	public Screen()
	{
		super();
		m_List = new LinkedList<>();
		m_ReDrawList = new LinkedList<>();
	}
	
	public static Screen getInstance(){
		if(INSTANCE == null)
			INSTANCE = new Screen();
		return INSTANCE;
	}
	public void addDrawable(VisibleObject o)
	{
		m_List.add(o);
	}
	
	public void addReDraw(VisibleObject o)
	{
		m_ReDrawList.add(o);
	}
	
	public LinkedList<VisibleObject> getList(){
		return m_List;
	}
	public GraphicStage findStage(String stageName){
		for(VisibleObject o : m_List){
			if(o.getClass() == GraphicStage.class){
				GraphicStage stage = (GraphicStage) o;
				if(stage.getM_Name().equals(stageName)){
					return stage;
				}
			}
		}
		return null;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Iterator<VisibleObject> m_List_Iterator = m_List.iterator();
		while(m_List_Iterator.hasNext()){
			VisibleObject o = m_List_Iterator.next();
			if(!o.checkExists()){
				m_List.remove(o);
			}
		}
		
		int minWidth = 0;		
		for (int i = 0; i < m_List.size(); i ++)
		{
			VisibleObject v = m_List.get(i);
			if (v.getClass() == GraphicStage.class)
			{
				GraphicStage s = (GraphicStage) v;
				int calcLength = s.getEstimatedWidth(); 
				if (minWidth < calcLength) minWidth = calcLength;
			}
		}
		minWidth = Math.max(627, minWidth + 300);
		this.setPreferredSize(new Dimension(minWidth, 50 * m_List.size()));
		
		int vpos = 0;
		for (int i = 0; i < m_List.size(); i ++)
		{
			VisibleObject v = m_List.get(i);
			if (v.getClass() == GraphicBackdrop.class)
			{
				GraphicBackdrop s = (GraphicBackdrop) v;
				s.setLimit(Math.max(627, minWidth));
			}
			if (v.getClass() == GraphicStage.class)
			{
				GraphicStage s = (GraphicStage) v;
				s.setVerticalPosition(vpos);
				vpos ++;
			}
			v.update(this);
			if(v.getClass() != GraphicArtist.class)
				v.draw(g);
		}
		
		//double draw
		for (int i = 0; i < m_ReDrawList.size(); i ++)
		{
			VisibleObject t = m_ReDrawList.get(i);
			if (t.getClass() == GraphicPerformance.class)
			{
				GraphicPerformance gp = (GraphicPerformance) t;
				gp.setWidth(true);
			}
			t.draw(g);
		}
		m_ReDrawList.clear();
	}
}