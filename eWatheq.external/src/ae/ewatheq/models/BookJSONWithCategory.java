package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class BookJSONWithCategory implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	
	
	
	public BookJSON book;
	public String CategoryId;
	public String NameArabic;
	public String NameEnglish;
	public BookJSONWithCategory(){
		this.book = new BookJSON();
		this.CategoryId = "";
		this.NameArabic = "";
		this.NameEnglish = "";
		
	}
	
}
