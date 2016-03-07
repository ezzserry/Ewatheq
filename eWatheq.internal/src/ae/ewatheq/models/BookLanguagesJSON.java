package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;





public class BookLanguagesJSON implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strLanguageId="LanguageId";
	public static String  strNameArabic="NameArabic";
	public static String  strNameEnglish="NameEnglish";
	
	
	public String LanguageId;
	public String NameArabic;
	public String NameEnglish;
	
	
	public BookLanguagesJSON(){
		this.LanguageId = "";
		this.NameArabic = "";
		this.NameEnglish = "";
		
	}
	public static List<BookLanguagesJSON> parse(JSONArray jSonarray)
	{
		List<BookLanguagesJSON> lang = new ArrayList<BookLanguagesJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookLanguagesJSON book = BookLanguagesJSON.parse(jSonarray.getJSONObject(i));
				lang.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return lang;
	}
	public static BookLanguagesJSON parse(JSONObject jSonObj)
	{
		BookLanguagesJSON lang = new BookLanguagesJSON();
		try {
			lang.LanguageId = jSonObj.getString(strLanguageId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			lang.NameArabic = jSonObj.getString(strNameArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			lang.NameEnglish = jSonObj.getString(strNameEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return lang;
	}
	
	
}
