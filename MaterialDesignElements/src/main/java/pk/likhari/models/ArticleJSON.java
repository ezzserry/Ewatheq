package pk.likhari.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ArticleJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strauthor_id="author_id";
	public static String  strdescription_html="description_html";
	public static String  strdescription_img="description_img";
	public static String  strid="id";
	public static String  strno_of_views="no_of_views";
	public static String  strpublished_date="published_date";
	public static String  strrating_minus="rating_minus";
	public static String  strrating_plus="rating_plus";
	public static String  strsource_id="source_id";
	public static String  strtitle="title";
	
	
	
	
	
	public String author_id;
	public String description_html;
	public String description_img;
	public String id;
	public String no_of_views;
	public String published_date;
	public String rating_minus;
	public String rating_plus;
	public String source_id;
	public String title;
	
	
	
	public ArticleJSON(){
		this.author_id= "";
		this.description_html= "";
		this.description_img= "";
		this.id= "";
		this.no_of_views= "";
		this.published_date= "";
		this.rating_minus= "";
		this.rating_plus= "";
		this.source_id= "";
		this.title= "";
		
		
	}
	public static List<ArticleJSON> parse(JSONArray jSonarray)
	{
		List<ArticleJSON> articles = new ArrayList<ArticleJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				ArticleJSON article = ArticleJSON.parse(jSonarray.getJSONObject(i));
				articles.add(article);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return articles;
	}
	public static ArticleJSON parse(JSONObject jSonObj)
	{
		ArticleJSON article = new ArticleJSON();
		try {
			article.author_id= jSonObj.getString(strauthor_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.description_html= jSonObj.getString(strdescription_html);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.description_img= jSonObj.getString(strdescription_img);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.id= jSonObj.getString(strid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.no_of_views= jSonObj.getString(strno_of_views);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.published_date= jSonObj.getString(strpublished_date);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.rating_minus= jSonObj.getString(strrating_minus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			article.rating_plus= jSonObj.getString(strrating_plus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			article.source_id= jSonObj.getString(strsource_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			article.title= jSonObj.getString(strtitle);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return article;
	}
	
	
}
