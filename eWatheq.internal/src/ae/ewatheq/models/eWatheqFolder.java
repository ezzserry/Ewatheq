package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class eWatheqFolder implements Serializable{
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strcategoryId="CategoryID";
	public static String  strcategoryName="CategoryName";
	public static String  strnofFiles="NoOfFiles";
	public static String  strupdateCounter="UpdateCounter";
	


	public String folderId;
	public String CategoryID;
	public String CategoryName;
	public String NoOfFiles;
	public String UpdateCounter;
	

	public void removeNull(){
		if (this.CategoryID == null)
			this.CategoryID = "";
		if (this.CategoryName == null)
			this.CategoryName = "";
		if (this.NoOfFiles == null)
			this.NoOfFiles = "";
		if (this.UpdateCounter == null)
			this.UpdateCounter = "";
		
		
	}
	public int getUpdateCounterInt()
	{
		if (this.UpdateCounter != null && !UpdateCounter.isEmpty())
		{
			try{
			return Integer.parseInt(this.UpdateCounter);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return 0;
	}
	public static List<eWatheqFolder> parse(JSONArray jSonarray)
	{
		List<eWatheqFolder> books = new ArrayList<eWatheqFolder>();
		for(int i=0;i<jSonarray.length();i++)
		{
			try {
				eWatheqFolder document = eWatheqFolder.parse(jSonarray.getJSONObject(i));
				books.add(document);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return books;
	}
	
	public static eWatheqFolder parse(JSONObject jSonObj)
	{
		eWatheqFolder folder = new eWatheqFolder();
		try {
			folder.CategoryID = jSonObj.getString(strcategoryId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			folder.CategoryName = jSonObj.getString(strcategoryName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			folder.NoOfFiles = jSonObj.getString(strnofFiles);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			folder.UpdateCounter = jSonObj.getString(strupdateCounter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return folder;
	}


}
