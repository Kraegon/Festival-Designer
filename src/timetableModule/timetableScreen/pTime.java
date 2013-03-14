package timetableModule.timetableScreen;

public class pTime
{
	public int m_Hour;
	public int m_Minute;
	
	public pTime(int hour, int minute)
	{
		//m_Hour = hour%24;
		m_Hour = hour;
		add(minute);
	}
	
	public pTime(pTime t)
	{
		m_Hour = t.m_Hour;
		m_Minute = t.m_Minute;
	}
	
	public pTime clone()
	{
		return new pTime(m_Hour,m_Minute);
	}
	
	public int getPixels()
	{
		return 60*m_Hour + m_Minute;
	}
	
	public String getString()
	{
		Integer _hour = m_Hour%24;
		String hour = (_hour < 10)?("0"+_hour):(_hour.toString());
		String minute = (m_Minute < 10)?(":0"+m_Minute):(":"+m_Minute);
		return hour + minute;
	}
	
	/*
	 * adds given minutes to pTime instance and returns it
	 */
	public pTime add(int min)
	{
		m_Minute += min;
		while(m_Minute >= 60)
		{
			m_Hour ++;
			m_Minute -= 60;
		}
		return this;
	}
	
	/*
	 * adds values of parameter instance to current instance
	 */
	public pTime add(pTime time)
	{
		return add(time.getPixels());
	}
	
	/*
	 * returns a new pTime instance
	 * the source instances remain unaffected
	 */
	public pTime combine(pTime time)
	{
		return clone().add(time.getPixels());
	}
}
