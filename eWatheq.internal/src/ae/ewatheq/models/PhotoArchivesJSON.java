package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class PhotoArchivesJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strPhotoId="PhotoId";
	public static String  strKeywordsEnglish="KeywordsEnglish";
	public static String  strKeywordsArabic="KeywordsArabic";
	public static String  strCaptionEnglish="CaptionEnglish";
	public static String  strCaptionArabic="CaptionArabic";
	public static String  strFileNo="FileNo";
	public static String  strFrom_Day="From_Day";
	public static String  strFrom_Month="From_Month";
	public static String  strFrom_Year="From_Year";
	public static String  strTo_Day="To_Day";
	public static String  strTo_Month="To_Month";
	public static String  strTo_Year="To_Year";
	public static String  strPhotoUrl ="PhotoUrl";
	public static String  strThumb="Thumb";
	public static String  strCategory="Category";




	public String PhotoId;
	public String KeywordsEnglish;
	public String KeywordsArabic;
	public String CaptionEnglish;
	public String CaptionArabic;
	public String FileNo;
	public String From_Day;
	public String From_Month;
	public String From_Year;
	public String To_Day;
	public String To_Month;
	public String To_Year;
	public ImageObjectJSON PhotoUrl;
	public ImageObjectJSON Thumb;


	public List<CategoryIdJSON> Category;

	public void removeNull(){
		if (this.PhotoId == null)
			this.PhotoId = "";
		if (this.KeywordsEnglish == null)
			this.KeywordsEnglish = "";
		if (this.KeywordsArabic == null)
			this.KeywordsArabic = "";
		if (this.CaptionEnglish == null)
			this.CaptionEnglish = "";
		if (this.CaptionArabic == null)
			this.CaptionArabic = "";
		if (this.FileNo == null || FileNo.equalsIgnoreCase("null"))
			this.FileNo = "";
		if (this.From_Day == null)
			this.From_Day = "";
		if (this.From_Month == null)
			this.From_Month = "";
		if (this.From_Year == null)
			this.From_Year = "";
		if (this.To_Day == null)
			this.To_Day = "";
		if (this.To_Month == null)
			this.To_Month = "";
		if (this.To_Year == null)
			this.To_Year = "";
		if (this.PhotoUrl == null)
			this.PhotoUrl = new ImageObjectJSON();
		if (this.Thumb == null)
			this.Thumb = new ImageObjectJSON();

		if (this.Category == null)
			this.Category = new ArrayList<CategoryIdJSON>();
		else 
		{
			for (int i=0;i<Category.size();i++)
				Category.get(i).removeNull();
		}
	}

	public PhotoArchivesJSON(){
		this.PhotoId = "";
		this.KeywordsEnglish = "";
		this.KeywordsArabic = "";
		this.CaptionEnglish = "";
		this.CaptionArabic = "";
		this.FileNo = "";
		this.From_Day = "";
		this.From_Month= "";
		this.From_Year= "";
		this.To_Day = "";
		this.To_Month = "";
		this.To_Year = "";
		this.PhotoUrl = new ImageObjectJSON();
		this.Thumb = new ImageObjectJSON();
		this.Category = new ArrayList<CategoryIdJSON>();
	}

	public static List<PhotoArchivesJSON> parse(JSONArray jSonarray)
	{
		List<PhotoArchivesJSON> books = new ArrayList<PhotoArchivesJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				PhotoArchivesJSON book = PhotoArchivesJSON.parse(jSonarray.getJSONObject(i));
				books.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return books;
	}
	public static PhotoArchivesJSON parse(JSONObject jSonObj)
	{
		PhotoArchivesJSON photo = new PhotoArchivesJSON();
		try {
			photo.PhotoId= jSonObj.getString(strPhotoId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.KeywordsEnglish = jSonObj.getString(strKeywordsEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			photo.KeywordsArabic = jSonObj.getString(strKeywordsArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.CaptionEnglish = jSonObj.getString(strCaptionEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.CaptionArabic = jSonObj.getString(strCaptionArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.FileNo = jSonObj.getString(strFileNo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.From_Day = jSonObj.getString(strFrom_Day);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.From_Month = jSonObj.getString(strFrom_Month);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.From_Year = jSonObj.getString(strFrom_Year);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.To_Day = jSonObj.getString(strTo_Day);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.To_Month = jSonObj.getString(strTo_Month);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.To_Year = jSonObj.getString(strTo_Year);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.PhotoUrl  = ImageObjectJSON.parse(jSonObj.getJSONObject(strPhotoUrl));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.Thumb  = ImageObjectJSON.parse(jSonObj.getJSONObject(strThumb));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			photo.Category  = CategoryIdJSON.parse(jSonObj.getJSONArray(strCategory));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return photo;
	}


}
