package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class InstagramMetaJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strcode="code";
	
	
	
	
	public String code;
	
	
	public InstagramMetaJSON(){
		this.code = "";
		
		
	}
	public static List<InstagramMetaJSON> parse(JSONArray jSonarray)
	{
		List<InstagramMetaJSON> captions = new ArrayList<InstagramMetaJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				InstagramMetaJSON caption = InstagramMetaJSON.parse(jSonarray.getJSONObject(i));
				captions.add(caption);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return captions;
	}
	public static InstagramMetaJSON parse(JSONObject jSonObj)
	{
		InstagramMetaJSON caption = new InstagramMetaJSON();
		try {
			caption.code = jSonObj.getString(strcode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return caption;
	}
	
	
}
