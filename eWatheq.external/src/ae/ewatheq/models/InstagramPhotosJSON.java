package ae.ewatheq.models;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;



public class InstagramPhotosJSON {
	
	
	
	public static String  strpagination="pagination";
	public static String  strmeta="meta";
	public static String  strdata="data";
	
	

	
	public InstagramPaginationJSON pagination;
	public InstagramMetaJSON meta;
	public List<InstagramDataJSON> data;
	
	
	public static InstagramPhotosJSON parse(JSONObject jSonObj)
	{
		InstagramPhotosJSON catWBooks = new InstagramPhotosJSON();
		try {
			catWBooks.data = InstagramDataJSON.parse(jSonObj.getJSONArray(strdata));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catWBooks;
	}
	public static List<InstagramPhotosJSON> parse(JSONArray jSonarray)
	{
		List<InstagramPhotosJSON> catWBooks = new ArrayList<InstagramPhotosJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				InstagramPhotosJSON cat = InstagramPhotosJSON.parse(jSonarray.getJSONObject(i));
				catWBooks.add(cat);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}

		return catWBooks;
	}
}
