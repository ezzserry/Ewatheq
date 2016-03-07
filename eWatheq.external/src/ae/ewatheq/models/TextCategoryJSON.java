package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;



public class TextCategoryJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	
	public static String  strCategoryId="CategoryId";
	public static String  strNameEnglish="NameEnglish";
	public static String  strNameArabic="NameArabic";
	public static String  strLogoEnglish="LogoEnglish";
	public static String  strLogoArabic="LogoArabic";

	
	public String CategoryId;
	public String NameEnglish;
	public String NameArabic;
	

	public  TextCategoryJSON(){
		
		this.CategoryId = "";
		this.NameEnglish = "";
		this.NameArabic = "";
		
		
	}
	public void removeNull(){
		if (this.CategoryId == null)
		this.CategoryId = "";
		if (this.NameEnglish == null)
		this.NameEnglish = "";
		if (this.NameArabic == null)
		this.NameArabic = "";
		
		
	}
	
	
	public static TextCategoryJSON parse(JSONObject jSonObj)
	{
		TextCategoryJSON catWBooks = new TextCategoryJSON();
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
		
		


		return catWBooks;
	}
	public static List<TextCategoryJSON> parse(JSONArray jSonarray)
	{
		List<TextCategoryJSON> catWBooks = new ArrayList<TextCategoryJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				TextCategoryJSON cat = TextCategoryJSON.parse(jSonarray.getJSONObject(i));
				catWBooks.add(cat);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}

		return catWBooks;
	}
}
