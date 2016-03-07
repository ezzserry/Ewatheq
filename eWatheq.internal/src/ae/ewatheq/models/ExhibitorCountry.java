package ae.ewatheq.models;

public class ExhibitorCountry {
	private int exhibitors_country_id;
	private String exhibitors_country_eng;
	private String exhibitors_country_ar;
	
	
	
	public ExhibitorCountry() {
		// TODO Auto-generated constructor stub
		exhibitors_country_id = 0;
		exhibitors_country_eng = "";
		exhibitors_country_ar = "";
	}
	
	
	
	
	
	
	
	public int getExhibitorCountryId()
	{
		return exhibitors_country_id;
	}
	public String getCountryEng()
	{
		if (this.exhibitors_country_eng == null)
			return "";
		return this.exhibitors_country_eng;
	}
	
	public String getCountryAr()
	{
		if (this.exhibitors_country_ar == null)
			return "";
		return this.exhibitors_country_ar;
	}
	
	public void setExhibitorsCountryId(int var)
	{
		exhibitors_country_id = var;
	}
	public void setCountryEng(String var)
	{
		this.exhibitors_country_eng = var;
	}
	public void setCountryAr(String var)
	{
		this.exhibitors_country_ar = var;
	}
}
