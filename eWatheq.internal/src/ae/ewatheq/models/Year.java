package ae.ewatheq.models;

public class Year {
	private int year_id;
	private String year_name;
	
	
	
	public Year() {
		// TODO Auto-generated constructor stub
		year_id = 0;
		year_name = "";
	}
	
	
	
	public Year(int y_id, String y_name) {
		// TODO Auto-generated constructor stub
		year_id = y_id;
		year_name = y_name;
	}
	
	
	
	public int getYearId()
	{
		return year_id;
	}
	public String getYearName()
	{
		if (this.year_name == null)
			return "";
		return year_name;
	}
	public void setYearId(int a_id)
	{
		year_id = a_id;
	}
	public void setYearName(String a_name)
	{
		year_name = a_name;
	}
}
