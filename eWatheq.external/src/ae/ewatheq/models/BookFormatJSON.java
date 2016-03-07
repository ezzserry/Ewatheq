package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class BookFormatJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strFormatId="FormatId";
	public static String  strNameArabic="NameArabic";
	public static String  strNameEnglish="NameEnglish";
	
	
	
	public String FormatId;
	
	public String NameArabic;
	public String NameEnglish;
	
	public BookFormatJSON(){
		this.FormatId = "";
		this.NameArabic = "";
		this.NameEnglish = "";
		
	}
	public static List<BookFormatJSON> parse(JSONArray jSonarray)
	{
		List<BookFormatJSON> authors = new ArrayList<BookFormatJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookFormatJSON author = BookFormatJSON.parse(jSonarray.getJSONObject(i));
				authors.add(author);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return authors;
	}
	public static BookFormatJSON parse(JSONObject jSonObj)
	{
		BookFormatJSON author = new BookFormatJSON();
		try {
			author.FormatId= jSonObj.getString(strFormatId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.NameArabic= jSonObj.getString(strNameArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.NameEnglish  = jSonObj.getString(strNameEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return author;
	}
	
	
}
