package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class BookJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strBookId="BookId";
	public static String  strBookTitle="BookTitle";
	public static String  strBookDescription="BookDescription";
	public static String  strISBN="ISBN";
	public static String  strPages="Pages";
	public static String  strPriceID_Android="PriceID_Android";
	public static String  strPriceID_IOS="PriceID_IOS";
	public static String  strFree="Free";
	public static String  strPublishedOn="PublishedOn";
	public static String  strOnStore ="OnStore";
	public static String  strFileSizeInMB ="FileSizeInMB";
	public static String  strRating ="Rating";
	public static String  strPeopleRating ="PeopleRating";
	
	public static String  strFileURL="FileURL";
	public static String  strCoverURL="CoverURL";
	
	public static String  strCoverThumbURLPhone="CoverThumbURLPhone";
	public static String  strCoverThumbURLTablet="CoverThumbURLTablet";
	public static String  strFormat="Format";

	public static String  strAuthors="Authors";
	public static String  strPublisher="Publisher";
	public static String  strLanguage="Language";
	
	
	public boolean isFileDownloaded;
	
	
	public String BookId;
	public String BookTitle;
	public String BookDescription;
	public String ISBN;
	public String Pages;
	public String Free;
	public String PublishedOn;
	public String OnStore;
	public String PriceID_Android;
	public String PriceID_IOS;
	public String FileURL;
	public String FileSizeInMB;
	public String Rating;
	public String PeopleRating;
	public ImageObjectJSON CoverURL;
	public ImageObjectJSON CoverThumbURLPhone;
	public ImageObjectJSON CoverThumbURLTablet;
	
	public BookLanguagesJSON Language;
	public BookPublishersJSON publisher;
	public BookFormatJSON Format;
	public List<BookAuthorsJSON> Authors;
	
	public BookJSON(){
		
		this.BookId = "";
		this.BookTitle = "";
		this.BookDescription = "";
		this.ISBN = "";
		this.Pages = "";
		this.PriceID_Android = "";
		this.PriceID_IOS = "";
		this.Free = "";
		this.PublishedOn = "";
		this.OnStore = "";
		this.FileURL = "";
		this.FileSizeInMB = "";
		this.Rating = "";
		this.PeopleRating = "";
		this.CoverURL = new ImageObjectJSON();
		this.CoverThumbURLPhone = new ImageObjectJSON();
		this.CoverThumbURLTablet = new ImageObjectJSON();
		
		this.Language = new BookLanguagesJSON();
		this.publisher = new BookPublishersJSON();
		this.Format = new BookFormatJSON();
		this.Authors = new ArrayList<BookAuthorsJSON>();
	}
	public float getRating()
	{
		try{
			if (Rating!=null)
				return Float.parseFloat(Rating);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return (float) 0.0;
	}
	public static List<BookJSON> parse(JSONArray jSonarray)
	{
		List<BookJSON> books = new ArrayList<BookJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookJSON book = BookJSON.parse(jSonarray.getJSONObject(i));
				books.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return books;
	}
	public static BookJSON parse(JSONObject jSonObj)
	{
		BookJSON book = new BookJSON();
		try {
			book.BookId= jSonObj.getString(strBookId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.BookTitle = jSonObj.getString(strBookTitle);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.BookDescription = jSonObj.getString(strBookDescription);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.ISBN = jSonObj.getString(strISBN);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.Pages = jSonObj.getString(strPages);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.Free = jSonObj.getString(strFree);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.PublishedOn = jSonObj.getString(strPublishedOn);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			book.OnStore  = jSonObj.getString(strOnStore);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.PriceID_Android  = jSonObj.getString(strPriceID_Android);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.PriceID_IOS = jSonObj.getString(strPriceID_IOS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.FileURL  = jSonObj.getString(strFileURL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.FileSizeInMB = jSonObj.getString(strFileSizeInMB);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.Rating = jSonObj.getString(strRating);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.PeopleRating = jSonObj.getString(strPeopleRating);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.CoverURL  = ImageObjectJSON.parse(jSonObj.getJSONObject(strCoverURL));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.CoverThumbURLPhone  = ImageObjectJSON.parse(jSonObj.getJSONObject(strCoverThumbURLPhone));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.CoverThumbURLTablet  = ImageObjectJSON.parse(jSonObj.getJSONObject(strCoverThumbURLTablet));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			book.Language  = BookLanguagesJSON.parse(jSonObj.getJSONObject(strLanguage));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.publisher  = BookPublishersJSON.parse(jSonObj.getJSONObject(strPublisher));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.Format = BookFormatJSON.parse(jSonObj.getJSONObject(strFormat));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			book.Authors = BookAuthorsJSON.parse(jSonObj.getJSONArray(strAuthors));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return book;
	}
	
	
}
