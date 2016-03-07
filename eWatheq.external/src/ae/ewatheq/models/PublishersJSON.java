package ae.ewatheq.models;



import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class PublishersJSON implements Serializable {
	private static final long serialVersionUID = -6099312954099962806L;

	public static String  strPublisherID="PublisherID";
	public static String  strPublisherNameEng="PublisherNameEng";
	public static String  strPublisherNameAr="PublisherNameAr";

	public String PublisherId;

	public String PublisherNameEng;
	public String PublisherNameAr;

	
	public PublishersJSON(){
		this.PublisherId = "";
		this.PublisherNameEng = "";
		this.PublisherNameAr = "";
		
	}
	public static PublishersJSON parse(JSONObject jSonObj)
	{
		PublishersJSON pub = new PublishersJSON();
		try {
			pub.PublisherId = jSonObj.getString(strPublisherID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub.PublisherNameEng = jSonObj.getString(strPublisherNameEng);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub.PublisherNameEng  = jSonObj.getString(strPublisherNameAr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return pub;
	}


}
