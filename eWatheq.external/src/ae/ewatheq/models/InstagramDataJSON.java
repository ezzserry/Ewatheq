package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class InstagramDataJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strimages="images";
	public static String  strcaption="caption";
	
	public InstagramImageJSON images;
	public InstagramCaptionJSON caption;
	
	public InstagramDataJSON(){	
		this.images = new InstagramImageJSON();
		this.caption = new InstagramCaptionJSON();
	}
	public void removeNull(){
		if (this.images == null)
			this.images = new InstagramImageJSON();
		if (this.caption == null)
			this.caption = new InstagramCaptionJSON();
		
		
		
		
	}
	
	public static List<InstagramDataJSON> parse(JSONArray jSonarray)
	{
		List<InstagramDataJSON> books = new ArrayList<InstagramDataJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				InstagramDataJSON book = InstagramDataJSON.parse(jSonarray.getJSONObject(i));
				books.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return books;
	}
	public static InstagramDataJSON parse(JSONObject jSonObj)
	{
		InstagramDataJSON book = new InstagramDataJSON();
		try {
			book.images  = InstagramImageJSON.parse(jSonObj.getJSONObject(strimages));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.caption  = InstagramCaptionJSON.parse(jSonObj.getJSONObject(strcaption));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}
	
	
}
