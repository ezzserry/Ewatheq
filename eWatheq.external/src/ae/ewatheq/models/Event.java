package ae.ewatheq.models;

import java.io.Serializable;

public class Event implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	private int event_id;
	private String event_desc_ar;
	private String event_desc_eng;
	
	
	private String event_venue_ar;
	private String event_venue_eng;
	
	
	
	
	private String event_date_eng;
	
	
	
	
	
	private String event_start_time;
	
	private String event_end_time;
	
	
	
	
	
	
	
	public int getEventId()
	{
		return this.event_id;
	}
	
	public String getEventNameAr()
	{
		if (this.event_desc_ar == null)
			return "";
		return this.event_desc_ar;
	}
	
	public String getEventNameEng()
	{
		if (this.event_desc_eng == null)
			return "";
		return this.event_desc_eng;
	}
	
	public String getEventVenueAr()
	{
		if (this.event_venue_ar == null)
			return "";
		return this.event_venue_ar;
	}
	public String getEventVenueEng()
	{
		if (this.event_venue_eng == null)
			return "";
		return this.event_venue_eng;
	}
	
	/*public String getEventDateAr()
	{
		if (this.event_date_ar == null)
			return "";
		return this.event_date_ar;
	}*/
	public String getEventDateEng()
	{
		if (this.event_date_eng == null)
			return "";
		return this.event_date_eng;
	}
	
	
	public String getEventStartTime()
	{
		if (this.event_start_time == null)
			return "";
		return this.event_start_time;
	}
	
	public String getEventEndTime()
	{
		if (this.event_end_time == null)
			return "";
		return this.event_end_time;
	}
	
	
	
	
	
	
	public void setEventId(int e_id)
	{
		this.event_id = e_id;
	}
	public void setEventNameAr(String desc)
	{
		this.event_desc_ar = desc;
	}
	public void setEventNameEng(String desc)
	{
		this.event_desc_eng = desc;
	}
	public void setEventVenueAr(String venue)
	{
		this.event_venue_ar = venue;
	}
	public void setEventVenueEng(String venue)
	{
		this.event_venue_eng = venue;
	}
	/*public void setEventDateAr(String date)
	{
		this.event_date_ar = date;
	}*/
	public void setEventDateEng(String date)
	{
		this.event_date_eng = date;
	}
	public void setEventStartTime(String startTime)
	{
		this.event_start_time = startTime;
	}
	public void setEventEndTime(String endTime)
	{
		this.event_end_time = endTime;
	}
	
}
