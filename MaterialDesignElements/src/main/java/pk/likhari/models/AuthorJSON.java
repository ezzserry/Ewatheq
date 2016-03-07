package pk.likhari.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class AuthorJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strdescription="description";
	public static String  strdescription_urdu="description_urdu";
	public static String  strid="id";
	public static String  strlogo_large="logo_large";
	public static String  strlogo_small="logo_small";
	public static String  strname="name";
	public static String  strname_ur="name_ur";
	public static String  strno_of_followers="no_of_followers";
	public static String  strno_of_views="no_of_views";

	
	
	
	
	
	public String description;
	public String description_urdu;
	public String id;
	public String logo_large;
	public String logo_small;
	public String name;
	public String name_ur;
	public String no_of_followers;
	public String no_of_views;

	
	
	
	public AuthorJSON(){
		this.description= "";
		this.description_urdu= "";
		this.id= "";
		this.logo_large= "";
		this.logo_small= "";
		this.name= "";
		this.name_ur= "";
		this.no_of_followers= "";
		this.no_of_views= "";
		
		
		
	}
	public static List<AuthorJSON> parse(JSONArray jSonarray)
	{
		List<AuthorJSON> authors = new ArrayList<AuthorJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				AuthorJSON author = AuthorJSON.parse(jSonarray.getJSONObject(i));
				authors.add(author);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return authors;
	}
	public static AuthorJSON parse(JSONObject jSonObj)
	{
		AuthorJSON author = new AuthorJSON();
		try {
			author.description= jSonObj.getString(strdescription);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.description_urdu= jSonObj.getString(strdescription_urdu);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.id= jSonObj.getString(strid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.logo_large= jSonObj.getString(strlogo_large);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.logo_small= jSonObj.getString(strlogo_small);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.name= jSonObj.getString(strname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.name_ur= jSonObj.getString(strname_ur);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.no_of_followers= jSonObj.getString(strno_of_followers);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			author.no_of_views= jSonObj.getString(strno_of_views);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		


		return author;
	}
	
	
}
