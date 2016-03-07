package pk.likhari.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class DailyArticleJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strarticles="articles";
	public static String  strauthor="author";
	public static String  strsource="source";
	public List<ArticleJSON> articles;
	public List<AuthorJSON> author;
	public List<SourceJSON> source;
	public DailyArticleJSON(){
		
		this.articles = new ArrayList<ArticleJSON>();
		this.author = new ArrayList<AuthorJSON>();
		this.source = new ArrayList<SourceJSON>();
	}
	public static DailyArticleJSON parse(JSONObject jSonObj)
	{
		DailyArticleJSON dailyArticles = new DailyArticleJSON();
		
		try {
			dailyArticles.articles= ArticleJSON.parse(jSonObj.getJSONArray(strarticles));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dailyArticles.author= AuthorJSON.parse(jSonObj.getJSONArray(strauthor));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dailyArticles.source= SourceJSON.parse(jSonObj.getJSONArray(strsource));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dailyArticles;
	}
	
	
}
