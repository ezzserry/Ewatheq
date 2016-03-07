package ae.ewatheq.models;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;



public class CategoryWiseBookJSON {
	
	public static String  strCategoryId="CategoryId";
	public static String  strNameEnglish="NameEnglish";
	public static String  strNameArabic="NameArabic";
	public static String  strLogoEnglish="LogoEnglish";
	public static String  strLogoArabic="LogoArabic";
	public static String  strBooks="Books";
	
	public String CategoryId;
	public String NameEnglish;
	public String NameArabic;
	public ImageObjectJSON LogoEnglish;
	public ImageObjectJSON LogoArabic;

	
	public List<BookJSON> Books;
	
	
	public static CategoryWiseBookJSON parse(JSONObject jSonObj)
	{
		CategoryWiseBookJSON catWBooks = new CategoryWiseBookJSON();
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
			catWBooks.LogoEnglish = ImageObjectJSON.parse(jSonObj.getJSONObject(strLogoEnglish));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.LogoArabic = ImageObjectJSON.parse(jSonObj.getJSONObject(strLogoArabic));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			catWBooks.Books = BookJSON.parse(jSonObj.getJSONArray(strBooks));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return catWBooks;
	}
	public static List<CategoryWiseBookJSON> parse(JSONArray jSonarray)
	{
		List<CategoryWiseBookJSON> catWBooks = new ArrayList<CategoryWiseBookJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				CategoryWiseBookJSON cat = CategoryWiseBookJSON.parse(jSonarray.getJSONObject(i));
				catWBooks.add(cat);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}

		return catWBooks;
	}
}
