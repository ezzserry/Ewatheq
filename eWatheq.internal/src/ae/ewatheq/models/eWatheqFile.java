package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;

public class eWatheqFile implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strfileId="FileID";
	public static String  strtitle="Title";
	public static String  strdescription="Description";
	public static String  strTypeID="TypeID";
	public static String  strtype="Type";
	public static String  strfileNameWithExtension="FileNamewithextension";
	public static String  strdate="Date";
	public static String  strcategoryId="CategoryID";
	public static String  strfileLink="fileLink";
	public static String  strthumnailLink="thumnailLink";
	public static String  strIsShared="IsShared";
	public static String  strFileName="FileName";
	
	
	
	
	


	public String FileID;
	public String Title;
	public String Description;
	public String TypeID;
	
	public String FileNamewithextension;
	public String Date;
	public String CategoryID;
	public String FileLink;
	public String Thumbnaillink;
	public String IsShared;
	public String FileName; // file Size in bytes
	
	

	public void removeNull(){
		if (this.FileID == null)
			this.FileID = "";
		if (this.Title == null)
			this.Title = "";
		if (this.Description == null)
			this.Description = "";
		if (this.TypeID == null)
			this.TypeID = "";
		
		if (this.FileNamewithextension == null)
			this.FileNamewithextension = "";
		if (this.Date == null)
			this.Date = "";
		if (this.CategoryID == null)
			this.CategoryID = "";
		if (this.FileLink == null)
			this.FileLink = "";
		if (this.Thumbnaillink == null)
			this.Thumbnaillink = "";
		if (this.IsShared == null)
			this.IsShared = "";
		if (this.FileName == null)
			this.FileName = "";
		
		
	}
	public String getFormatedDate()
	{
		/*if (this.date != null && !date.isEmpty())
		{
			try{
			return EWatheqUtils.getFormattedDate(this.date);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return date;*/
			return "11/5/2015";
	}
	public String getFileTypeInt()
	{
		if (this.FileNamewithextension != null && !FileNamewithextension.isEmpty())
		{
			try{
			return EWatheqUtils.isDocument(FileNamewithextension)?Constants.FILE_TYPE_FOR_SERVER_PDF:Constants.FILE_TYPE_FOR_SERVER_PHOTO;
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return FileNamewithextension;
	}
	public String getFileExtension()
	{
		if (this.FileNamewithextension != null && !FileNamewithextension.isEmpty())
		{
			try{
			return FileNamewithextension.substring(FileNamewithextension.lastIndexOf(".")+1);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return FileNamewithextension;
	}
	public boolean isDocument()
	{
		return EWatheqUtils.isDocumentByFileType(this.TypeID)?true:false;
	}
	public static List<eWatheqFile> parse(JSONArray jSonarray)
	{
		List<eWatheqFile> files = new ArrayList<eWatheqFile>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				eWatheqFile file = eWatheqFile.parse(jSonarray.getJSONObject(i));
				files.add(file);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return files;
	}
	
	public static eWatheqFile parse(JSONObject jSonObj)
	{
		eWatheqFile file = new eWatheqFile();
		try {
			file.CategoryID= jSonObj.getString(strcategoryId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.Date = jSonObj.getString(strdate);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			file.Description = jSonObj.getString(strdescription);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.FileID = jSonObj.getString(strfileId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.FileLink= jSonObj.getString(strfileLink);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			file.FileNamewithextension = jSonObj.getString(strfileNameWithExtension);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.IsShared= jSonObj.getString(strIsShared);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.Title = jSonObj.getString(strtitle);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			file.TypeID = jSonObj.getString(strTypeID );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.FileName = jSonObj.getString(strFileName );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}


}
