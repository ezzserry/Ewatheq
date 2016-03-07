package ae.ewatheq.models;

import java.util.List;

public class EventAndTitles {
	
	
	private List<Event> eventList ;
	
	
	
	private String[] eventTitles ;
	
	
	
	
	
	public List<Event> getEventList()
	{
		return this.eventList;
	}
	public String[] getEventTitles()
	{
		return this.eventTitles;
	}
	
	
	
	
	
	
	
	public void setEventList(List<Event> eventList)
	{
		this.eventList = eventList;
	}
	public void setEventTitles(String[] eventTitles)
	{
		this.eventTitles = eventTitles;
	}
	
	
	
}
