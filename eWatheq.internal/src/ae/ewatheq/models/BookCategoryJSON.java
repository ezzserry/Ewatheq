package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








public class BookCategoryJSON implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strBooks="Books";
	public static String  strCategoryId="CategoryId";
	public static String  strName="Name";
	public static String  strNameAr="NameAr";
	public static String  strIsActive="IsActive";
	public static String  strIsDeleted="IsDeleted";
	public static String  strCategoryTypeId="CategoryTypeId";
	public static String  strTypeName="TypeName";
	public static String  strAddedByUserLoginId="AddedByUserLoginId";
	public static String  strAddedOn="AddedOn";
	public static String  strUpdatedByUserLoginid="UpdatedByUserLoginid";
	public static String  strUpdatedOn="UpdatedOn";
	public static String  strFileId="FileId";
	public static String  strFileIdAr="FileIdAr";
	
	
	
	
	public String Books;
	public String CategoryId;
	public String Name;
	public String NameAr;
	public String IsActive;
	public String IsDeleted;
	public String CategoryTypeId;
	public String TypeName;
	public String AddedByUserLoginId;
	public String AddedOn;
	public String UpdatedByUserLoginid;
	public String UpdatedOn;
	public String FileId;
	public String FileIdAr;
	
	
	public BookCategoryJSON(){
		this.Books = "";
		this.CategoryId = "";
		this.Name = "";
		this.NameAr = "";
		this.IsActive = "";
		this.IsDeleted = "";
		this.CategoryTypeId = "";
		this.TypeName = "";
		this.AddedByUserLoginId = "";
		this.AddedOn = "";
		this.UpdatedByUserLoginid = "";
		this.UpdatedOn = "";
		this.FileId = "";
		this.FileIdAr = "";
		
	}
	
	public static List<BookCategoryJSON> parse(JSONArray jSonarray)
	{
		List<BookCategoryJSON> bookcategories = new ArrayList<BookCategoryJSON>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				BookCategoryJSON book = BookCategoryJSON.parse(jSonarray.getJSONObject(i));
				bookcategories.add(book);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		return bookcategories;
	}
	public static BookCategoryJSON parse(JSONObject jSonObj)
	{
		BookCategoryJSON book = new BookCategoryJSON();
		try {
			book.Books= jSonObj.getString(strBooks);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.CategoryId = jSonObj.getString(strCategoryId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.Name = jSonObj.getString(strName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.NameAr = jSonObj.getString(strNameAr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.IsActive = jSonObj.getString(strIsActive);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.IsDeleted = jSonObj.getString(strIsDeleted);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.CategoryTypeId = jSonObj.getString(strCategoryTypeId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.TypeName  = jSonObj.getString(strTypeName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.AddedByUserLoginId  = jSonObj.getString(strAddedByUserLoginId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.AddedOn  = jSonObj.getString(strAddedOn);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.UpdatedByUserLoginid  = jSonObj.getString(strUpdatedByUserLoginid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.UpdatedOn  = jSonObj.getString(strUpdatedOn);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.FileId  = jSonObj.getString(strFileId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			book.FileIdAr  = jSonObj.getString(strFileIdAr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return book;
	}
	
	
}
