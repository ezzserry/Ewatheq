package ae.ewatheq.models;

import java.util.List;

public class BookCountriesListTitlesList {
	
	
	private List<BookCountry> countryList ;
	
	
	
	private String[] bookCountriesNameEng ;
	private String[] bookCountriesNameAr ;
	
	
	
	
	
	public List<BookCountry> getBookList()
	{
		return this.countryList;
	}
	public String[] getBookCountriesNameEng()
	{
		return this.bookCountriesNameEng;
	}
	public String[] getBookCountriesNameAr()
	{
		return this.bookCountriesNameAr;
	}
	
	
	
	
	
	
	
	public void setBookList(List<BookCountry> var)
	{
		this.countryList = var;
	}
	public void setBookCountriesNameEng(String[] var)
	{
		this.bookCountriesNameEng = var;
	}
	public void setBookCountriesNameAr(String[] var)
	{
		this.bookCountriesNameAr = var;
	}
	
	
	
}
