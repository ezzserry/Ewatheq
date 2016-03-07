package ae.ewatheq.models;

public class BookCountry {
	private int country_id;
	private String country_name_eng;

	private String country_name_ar;
	
	
	public BookCountry() {
		// TODO Auto-generated constructor stub
		country_id = 1;
		country_name_ar = "";
		country_name_eng = "";
	}
	
	
	
	public BookCountry(int cou_id, String cou_name_ar,String cou_name_eng) {
		// TODO Auto-generated constructor stub
		country_id = cou_id;
		country_name_ar = cou_name_ar;
		country_name_eng = cou_name_eng;
	}
	
	
	
	public int getCountryId()
	{
		return country_id;
	}
	public String getCountryNameEng()
	{
		if (this.country_name_eng == null)
			return "";
		return country_name_eng;
	}
	public String getCountryNameAr()
	{
		if (this.country_name_ar == null)
			return "";
		return country_name_ar;
	}
	public void setCountryId(int a_id)
	{
		country_id = a_id;
	}
	public void setCountryNameAr(String a_name)
	{
		country_name_ar = a_name;
	}
	public void setCountryNameEng(String a_name)
	{
		country_name_eng = a_name;
	}
}
