package ae.ewatheq.models;

import java.io.Serializable;

public class Exhibitors implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	private int exhibitors_id;
	private int exhibitors_is_fav;
	private String exhibitors_stand_no;
	
	private String exhibitors_company_eng;
	private String exhibitors_company_ar;
	private String exhibitors_sorting_key_eng;
	private String exhibitors_sorting_key_ar;
	private String exhibitors_street_eng;
	private String exhibitors_street_ar;
	
	private String exhibitors_town_eng;
	private String exhibitors_town_ar;
	
	private String exhibitors_post_code;
	private String exhibitors_po_box;
	private String exhibitors_country_eng;
	private String exhibitors_country_ar;
	
	private String exhibitors_phone;
	private String exhibitors_fax;
	private String exhibitors_email;
	private String exhibitors_website;
	private String exhibitors_profile_eng;
	
	private String exhibitors_profile_ar;
	
	
	
	
	
	
	public int getExhibitorId()
	{
		return this.exhibitors_id;
	}
	public int getExhibitorIsFav()
	{
		return this.exhibitors_is_fav;
	}
	public String getExhibitorStandNo()
	{
		if (this.exhibitors_stand_no == null)
			return "";
		return this.exhibitors_stand_no;
	}
	public String getCompanyNameEng()
	{
		if (this.exhibitors_company_eng == null)
			return "";
		return this.exhibitors_company_eng;
	}
	
	public String getCompanyNameAr()
	{
		if (this.exhibitors_company_ar == null)
			return "";
		return this.exhibitors_company_ar;
	}
	
	public String getSortingKeyEng()
	{
		if (this.exhibitors_sorting_key_eng == null)
			return "";
		return this.exhibitors_sorting_key_eng;
	}
	
	
	public String getSortingKeyAr()
	{
		if (this.exhibitors_sorting_key_ar == null)
			return "";
		return this.exhibitors_sorting_key_ar;
	}
	
	public String getStreetEng()
	{
		if (this.exhibitors_street_eng == null)
			return "";
		return this.exhibitors_street_eng;
	}
	public String getStreetAr()
	{
		if (this.exhibitors_street_ar == null)
			return "";
		return this.exhibitors_street_ar;
	}
	
	public String getTownEng()
	{
		if (this.exhibitors_town_ar == null)
			return "";
		return this.exhibitors_town_ar;
	}
	public String getTownAr()
	{
		if (this.exhibitors_town_ar == null)
			return "";
		return this.exhibitors_town_ar;
	}
	
	public String getPostCode()
	{
		if (this.exhibitors_post_code == null)
			return "";
		return this.exhibitors_post_code;
	}
	
	public String getPoBox()
	{
		if (this.exhibitors_po_box == null)
			return "";
		return this.exhibitors_po_box;
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
	public String getPhone()
	{
		if (this.exhibitors_phone == null)
			return "";
		return this.exhibitors_phone;
	}
	
	public String getFax()
	{
		if (this.exhibitors_fax == null)
			return "";
		return this.exhibitors_fax;
	}
	public String getEmail()
	{
		if (this.exhibitors_email == null)
			return "";
		return this.exhibitors_email;
	}
	public String getWebsite()
	{
		if (this.exhibitors_website == null)
			return "";
		return this.exhibitors_website;
	}
	public String getProfileEng()
	{
		if (this.exhibitors_profile_eng == null)
			return "";
		return this.exhibitors_profile_eng;
	}
	public String getProfileAr()
	{
		if (this.exhibitors_profile_ar == null)
			return "";
		return this.exhibitors_profile_ar;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setExhibitorId(int e_id)
	{
		this.exhibitors_id = e_id;
	}
	public void setExhibitorIsFav(int is_fav)
	{
		this.exhibitors_is_fav = is_fav;
	}
	public void setExhibitorStandNo(String var)
	{
		this.exhibitors_stand_no = var;
	}
	public void setCompanyNameEng(String var)
	{
		this.exhibitors_company_eng = var;
	}
	public void setCompanyNameAr(String var)
	{
		this.exhibitors_company_ar = var;
	}
	
	public void setSortingKeyEng(String var)
	{
		this.exhibitors_sorting_key_eng = var;
	}
	public void setSortingKeyAr(String var)
	{
		this.exhibitors_sorting_key_ar = var;
	}
	public void setStreetEng(String var)
	{
		this.exhibitors_street_eng = var;
	}
	
	public void setStreetAr(String var)
	{
		this.exhibitors_street_ar = var;
	}
	public void setTownEng(String var)
	{
		this.exhibitors_town_eng = var;
	}
	
	public void setTownAr(String var)
	{
		this.exhibitors_town_ar = var;
	}
	public void setPostCode(String var)
	{
		this.exhibitors_post_code = var;
	}
	
	public void setPoBox(String var)
	{
		this.exhibitors_po_box = var;
	}
	public void setCountryEng(String var)
	{
		this.exhibitors_country_eng = var;
	}
	public void setCountryAr(String var)
	{
		this.exhibitors_country_ar = var;
	}
	public void setPhone(String var)
	{
		this.exhibitors_phone = var;
	}
	
	public void setFax(String var)
	{
		this.exhibitors_fax = var;
	}
	public void setEmail(String var)
	{
		this.exhibitors_email = var;
	}
	
	public void setWebsite(String var)
	{
		this.exhibitors_website = var;
	}
	public void setProfileEng(String var)
	{
		this.exhibitors_profile_eng = var;
	}
	public void setProfileAr(String var)
	{
		this.exhibitors_profile_ar = var;
	}
	
	
}
