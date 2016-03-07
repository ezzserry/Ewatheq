package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class BookAuthorsJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strAuthorId="AuthorId";
	public static String  strNameArabic="NameArabic";
	public static String  strNameEnglish="NameEnglish";
	
	
	
	public String AuthorId;
	
	public String NameArabic;
	public String NameEnglish;
	
	public BookAuthorsJSON(){
		this.AuthorId = "";
		this.NameArabic = "";
		this.NameEnglish = "";
		
	}
	public static List<BookAuthorsJSON> parse(JSONArray jSonarray)
	{
		List<BookAuthorsJSON> authors = new ArrayList<BookAuthorsJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookAuthorsJSON author = BookAuthorsJSON.parse(jSonarray.getJSONObject(i));
				authors.add(author);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return authors;
	}
	public static BookAuthorsJSON parse(JSONObject jSonObj)
	{
		BookAuthorsJSON author = new BookAuthorsJSON();
		try {
			author.AuthorId= jSonObj.getString(strAuthorId);
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
