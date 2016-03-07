package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class AuthorsJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strAuthorID="AuthorID";
	public static String  strNameEng="NameEng";
	public static String  strNameAr="NameAr";
	
	
	
	public String AuthorId;
	
	public String NameEng;
	public String NameAr;
	
	public AuthorsJSON(){
		this.AuthorId = "";
		this.NameEng = "";
		this.NameAr = "";
		
	}
	public static List<AuthorsJSON> parse(JSONArray jSonarray)
	{
		List<AuthorsJSON> authors = new ArrayList<AuthorsJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				AuthorsJSON author = AuthorsJSON.parse(jSonarray.getJSONObject(i));
				authors.add(author);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return authors;
	}
	public static AuthorsJSON parse(JSONObject jSonObj)
	{
		AuthorsJSON author = new AuthorsJSON();
		try {
			author.AuthorId= jSonObj.getString(strAuthorID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.NameEng= jSonObj.getString(strNameEng);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.NameAr  = jSonObj.getString(strNameAr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return author;
	}
	
	
}
