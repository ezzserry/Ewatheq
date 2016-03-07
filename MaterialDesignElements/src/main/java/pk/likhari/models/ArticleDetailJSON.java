package pk.likhari.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class ArticleDetailJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strArticleId="ArticleId";
	public static String  strDescription="Description";
	
	
	
	
	
	
	public String ArticleId;
	public String Description;
	
	public ArticleDetailJSON(){
		
		this.ArticleId = "";
		this.Description = "";
		
	}
	
	public static List<ArticleDetailJSON> parse(JSONArray jSonarray)
	{
		List<ArticleDetailJSON> books = new ArrayList<ArticleDetailJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				ArticleDetailJSON book = ArticleDetailJSON.parse(jSonarray.getJSONObject(i));
				books.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return books;
	}
	public static ArticleDetailJSON parse(JSONObject jSonObj)
	{
		ArticleDetailJSON issue = new ArticleDetailJSON();
		try {
			issue.ArticleId= jSonObj.getString(strArticleId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			issue.Description = jSonObj.getString(strDescription);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return issue;
	}
	
	
}
