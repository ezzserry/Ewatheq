package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;



public class ImageCategoryJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	
	public static String  strCategoryId="CategoryId";
	public static String  strNameEnglish="NameEnglish";
	public static String  strNameArabic="NameArabic";
	public static String  strLogoEnglish="LogoEnglish";
	public static String  strLogoArabic="LogoArabic";

	
	public String CategoryId;
	public String NameEnglish;
	public String NameArabic;
	public String LogoEnglish;
	public String LogoArabic;

	public  ImageCategoryJSON(){
		
		this.CategoryId = "";
		this.NameEnglish = "";
		this.NameArabic = "";
		this.LogoEnglish = "";
		this.LogoArabic = "";
		
	}
	public void removeNull(){
		if (this.CategoryId == null)
		this.CategoryId = "";
		if (this.NameEnglish == null)
		this.NameEnglish = "";
		if (this.NameArabic == null)
		this.NameArabic = "";
		if (this.LogoEnglish == null)
		this.LogoEnglish = "";
		if (this.LogoArabic == null)
		this.LogoArabic = "";
		
	}
	
	
	public static ImageCategoryJSON parse(JSONObject jSonObj)
	{
		ImageCategoryJSON catWBooks = new ImageCategoryJSON();
		try {
			catWBooks.CategoryId = jSonObj.getString(strCategoryId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.NameEnglish = jSonObj.getString(strNameEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.NameArabic = jSonObj.getString(strNameArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.LogoEnglish = jSonObj.getString(strLogoEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.LogoArabic = jSonObj.getString(strLogoArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return catWBooks;
	}
	public static List<ImageCategoryJSON> parse(JSONArray jSonarray)
	{
		List<ImageCategoryJSON> catWBooks = new ArrayList<ImageCategoryJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				ImageCategoryJSON cat = ImageCategoryJSON.parse(jSonarray.getJSONObject(i));
				catWBooks.add(cat);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}

		return catWBooks;
	}
}