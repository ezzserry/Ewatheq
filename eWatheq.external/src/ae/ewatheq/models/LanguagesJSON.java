package ae.ewatheq.models;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;





public class LanguagesJSON implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strLanguageID="LanguageID";
	public static String  strLanguageName="LanguageName";
	
	
	public String LanguageId;
	
	public String LanguageName;
	
	public LanguagesJSON(){
		this.LanguageId = "";
		this.LanguageName = "";
		
	}
	
	
	public static LanguagesJSON parse(JSONObject jSonObj)
	{
		LanguagesJSON lang = new LanguagesJSON();
		try {
			lang.LanguageId = jSonObj.getString(strLanguageID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			lang.LanguageName = jSonObj.getString(strLanguageName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return lang;
	}
	
	
}
