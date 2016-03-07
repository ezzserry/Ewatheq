package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class InstagramCaptionJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strtext="text";
	
	
	
	
	public String text;
	
	
	public InstagramCaptionJSON(){
		this.text = "";
		
		
	}
	public static List<InstagramCaptionJSON> parse(JSONArray jSonarray)
	{
		List<InstagramCaptionJSON> captions = new ArrayList<InstagramCaptionJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				InstagramCaptionJSON caption = InstagramCaptionJSON.parse(jSonarray.getJSONObject(i));
				captions.add(caption);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return captions;
	}
	public static InstagramCaptionJSON parse(JSONObject jSonObj)
	{
		InstagramCaptionJSON caption = new InstagramCaptionJSON();
		try {
			caption.text = jSonObj.getString(strtext);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return caption;
	}
	
	
}
