package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class InstagramImageJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strstandard_resolution	="standard_resolution";
	public static String  strthumbnail 				="thumbnail";
	
	
	public InstagramImageDataJSON thumbnail;
	public InstagramImageDataJSON standard_resolution;
	
	
	public InstagramImageJSON(){
		this.thumbnail 				= new InstagramImageDataJSON();
		this.standard_resolution 	= new InstagramImageDataJSON();
	}
	public static InstagramImageJSON parse(JSONObject jSonObj)
	{
		InstagramImageJSON images = new InstagramImageJSON();
		try {
			images.standard_resolution = InstagramImageDataJSON.parse(jSonObj.getJSONObject(strstandard_resolution));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			images.thumbnail = InstagramImageDataJSON.parse(jSonObj.getJSONObject(strthumbnail));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return images;
	}
	
	
}
