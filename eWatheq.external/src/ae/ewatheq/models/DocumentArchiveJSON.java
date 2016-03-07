package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class DocumentArchiveJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strDocumentId="DocumentId";
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
	public static String  strDocumentFileUrl="DocumentFileUrl";
	public static String  strTranslationUrl="TranslationUrl";
	public static String  strTranscriptUrl="TranscriptUrl";
	public static String  strCategory="Category";



	public String DocumentId;
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
	public String DocumentFileUrl;
	public String TranslationUrl;
	public String TranscriptUrl;


	public List<CategoryIdJSON> Category;


	public void removeNull(){
		if (this.DocumentId == null)
			this.DocumentId = "";
		if (this.KeywordsEnglish == null)
			this.KeywordsEnglish = "";
		if (this.KeywordsArabic == null)
			this.KeywordsArabic = "";
		if (this.CaptionEnglish == null)
			this.CaptionEnglish = "";
		if (this.CaptionArabic == null)
			this.CaptionArabic = "";
		if (this.FileNo == null)
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
		if (this.DocumentFileUrl == null)
			this.DocumentFileUrl = "";
		if (this.TranslationUrl == null)
			this.TranslationUrl = "";
		if (this.TranscriptUrl == null)
			this.TranscriptUrl = "";
		if (this.Category == null)
			this.Category = new ArrayList<CategoryIdJSON>();
		else 
		{
			for (int i=0;i<Category.size();i++)
				Category.get(i).removeNull();
		}
		
	}
	public static List<DocumentArchiveJSON> parse(JSONArray jSonarray)
	{
		List<DocumentArchiveJSON> books = new ArrayList<DocumentArchiveJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				DocumentArchiveJSON document = DocumentArchiveJSON.parse(jSonarray.getJSONObject(i));
				books.add(document);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return books;
	}
	
	public static DocumentArchiveJSON parse(JSONObject jSonObj)
	{
		DocumentArchiveJSON document = new DocumentArchiveJSON();
		try {
			document.DocumentId= jSonObj.getString(strDocumentId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.KeywordsEnglish = jSonObj.getString(strKeywordsEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			document.KeywordsArabic = jSonObj.getString(strKeywordsArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.CaptionEnglish = jSonObj.getString(strCaptionEnglish);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.CaptionArabic = jSonObj.getString(strCaptionArabic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.FileNo = jSonObj.getString(strFileNo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.From_Day = jSonObj.getString(strFrom_Day);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.From_Month = jSonObj.getString(strFrom_Month);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.From_Year = jSonObj.getString(strFrom_Year);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.To_Day = jSonObj.getString(strTo_Day);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.To_Month = jSonObj.getString(strTo_Month);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.To_Year = jSonObj.getString(strTo_Year);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.DocumentFileUrl  = jSonObj.getString(strDocumentFileUrl);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.TranslationUrl  = jSonObj.getString(strTranslationUrl);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.TranscriptUrl  = jSonObj.getString(strTranscriptUrl);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.Category  = CategoryIdJSON.parse(jSonObj.getJSONArray(strCategory));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return document;
	}


}
