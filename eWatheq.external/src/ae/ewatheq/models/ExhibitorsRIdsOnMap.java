package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ExhibitorsRIdsOnMap {
	
	
	private int showOnMapexhibitorsId;
	private ArrayList<Integer> favMapexhibitorsId;
	
	
	
	public ExhibitorsRIdsOnMap() {
		// TODO Auto-generated constructor stub
		showOnMapexhibitorsId = 0;
		favMapexhibitorsId = new ArrayList<Integer>();
	}
	
	
	public int getShowOnMapexhibitorsId()
	{
		return this.showOnMapexhibitorsId;
	}
	public ArrayList<Integer> getExhibitorIsFav()
	{
		return this.favMapexhibitorsId;
	}
	
	
	
	
	public void setExhibitorId(int var)
	{
		this.showOnMapexhibitorsId = var;
	}
	public void setExhibitorIsFav(ArrayList<Integer> var)
	{
		this.favMapexhibitorsId = var;
	}
	
	
}
