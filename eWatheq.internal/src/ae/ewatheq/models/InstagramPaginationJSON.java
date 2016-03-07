package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class InstagramPaginationJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strnext_max_id="next_max_id";
	public static String  strnext_url="next_url";
	
	
	
	
	public String next_max_id;
	public String next_url;
	
	
	public InstagramPaginationJSON(){
		this.next_max_id = "";
		this.next_url = "";
		
		
	}
	public static List<InstagramPaginationJSON> parse(JSONArray jSonarray)
	{
		List<InstagramPaginationJSON> captions = new ArrayList<InstagramPaginationJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				InstagramPaginationJSON caption = InstagramPaginationJSON.parse(jSonarray.getJSONObject(i));
				captions.add(caption);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return captions;
	}
	public static InstagramPaginationJSON parse(JSONObject jSonObj)
	{
		InstagramPaginationJSON caption = new InstagramPaginationJSON();
		try {
			caption.next_max_id = jSonObj.getString(strnext_max_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			caption.next_url = jSonObj.getString(strnext_url);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return caption;
	}
	
	
}
