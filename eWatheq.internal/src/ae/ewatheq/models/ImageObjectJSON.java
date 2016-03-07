package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ImageObjectJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strHeight="Height";
	public static String  strURL="URL";
	public static String  strWidth="Width";
	
	
	public String URL;
	public String Width;
	public String Height;
	
	public ImageObjectJSON(){
		this.Height = "";
		this.URL = "";
		this.Width = "";
		
	}
	public static List<ImageObjectJSON> parse(JSONArray jSonarray)
	{
		List<ImageObjectJSON> authors = new ArrayList<ImageObjectJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				ImageObjectJSON author = ImageObjectJSON.parse(jSonarray.getJSONObject(i));
				authors.add(author);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return authors;
	}
	public static ImageObjectJSON parse(JSONObject jSonObj)
	{
		ImageObjectJSON author = new ImageObjectJSON();
		try {
			author.Height= jSonObj.getString(strHeight);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.URL= jSonObj.getString(strURL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.Width  = jSonObj.getString(strWidth);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return author;
	}
	
	
}
