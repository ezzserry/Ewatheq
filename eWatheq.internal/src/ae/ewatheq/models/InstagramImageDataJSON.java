package ae.ewatheq.models;


import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;




public class InstagramImageDataJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strheight="height";
	public static String  strurl="url";
	public static String  strwidth="width";
	
	
	
	public String height;
	
	public String url;
	public String width;
	
	public InstagramImageDataJSON(){
		this.height = "";
		this.url = "";
		this.width = "";
		
	}
	
	public static InstagramImageDataJSON parse(JSONObject jSonObj)
	{
		InstagramImageDataJSON author = new InstagramImageDataJSON();
		try {
			author.height= jSonObj.getString(strheight);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.url= jSonObj.getString(strurl);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			author.width  = jSonObj.getString(strwidth);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return author;
	}
	
	
}
