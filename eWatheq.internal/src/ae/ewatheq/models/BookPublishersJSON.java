package ae.ewatheq.models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class BookPublishersJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;

	
	public static String  strPublisherId="PublisherId";
	public static String  strNameArabic="NameArabic";
	public static String  strNameEnglish="NameEnglish";


	public String PublisherId;
	public String NameArabic;
	public String NameEnglish;

	
	
	public BookPublishersJSON(){
		this.PublisherId = "";
		this.NameArabic = "";
		this.NameEnglish = "";
	
	}
	
	
	public static List<BookPublishersJSON> parse(JSONArray jSonarray)
	{
		List<BookPublishersJSON> publishers = new ArrayList<BookPublishersJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookPublishersJSON book = BookPublishersJSON.parse(jSonarray.getJSONObject(i));
				publishers.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return publishers;
	}
	public static BookPublishersJSON parse(JSONObject jSonObj)
	{
		BookPublishersJSON pub = new BookPublishersJSON();
		try {
			pub.PublisherId = jSonObj.getString(strPublisherId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub.NameArabic = jSonObj.getString(strNameArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub.NameEnglish = jSonObj.getString(strNameEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return pub;
	}


}
