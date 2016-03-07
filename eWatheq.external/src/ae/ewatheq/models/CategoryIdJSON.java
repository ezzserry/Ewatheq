package ae.ewatheq.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;



public class CategoryIdJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	
	public static String  strCategoryId="CategoryId";
	

	
	public String CategoryId;
	
	public  CategoryIdJSON(){
		
		this.CategoryId = "";
		
	}
	public void removeNull(){
		if (this.CategoryId == null)
		this.CategoryId = "";
		
	}
	
	
	public static CategoryIdJSON parse(JSONObject jSonObj)
	{
		CategoryIdJSON catWBooks = new CategoryIdJSON();
		try {
			catWBooks.CategoryId = jSonObj.getString(strCategoryId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		


		return catWBooks;
	}
	public static List<CategoryIdJSON> parse(JSONArray jSonarray)
	{
		List<CategoryIdJSON> catWBooks = new ArrayList<CategoryIdJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				CategoryIdJSON cat = CategoryIdJSON.parse(jSonarray.getJSONObject(i));
				catWBooks.add(cat);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}

		return catWBooks;
	}
}
