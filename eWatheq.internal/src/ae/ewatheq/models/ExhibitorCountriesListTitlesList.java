package ae.ewatheq.models;

import java.util.List;

public class ExhibitorCountriesListTitlesList {
	
	
	private List<ExhibitorCountry> exhList ;
	
	
	
	private String[] ExhibitorCountriesNameEng ;
	private String[] ExhibitorCountriesNameAr ;
	
	
	
	
	
	public List<ExhibitorCountry> getExhibitorList()
	{
		return this.exhList;
	}
	public String[] getExhibitorCountriesNameEng()
	{
		return this.ExhibitorCountriesNameEng;
	}
	public String[] getExhibitorCountriesNameAr()
	{
		return this.ExhibitorCountriesNameAr;
	}
	
	
	
	
	
	
	
	public void setExhibitorList(List<ExhibitorCountry> var)
	{
		this.exhList = var;
	}
	public void setExhibitorCountriesNameEng(String[] var)
	{
		this.ExhibitorCountriesNameEng = var;
	}
	public void setExhibitorCountriesNameAr(String[] var)
	{
		this.ExhibitorCountriesNameAr = var;
	}
	
	
	
}
