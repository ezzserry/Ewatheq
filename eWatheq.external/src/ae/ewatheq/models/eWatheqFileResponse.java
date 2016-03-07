package ae.ewatheq.models;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

public class eWatheqFileResponse {

	
	public static String  strStatus="Status";
	public static String  strMessage="Message";
	public static String  strData="Data";
	
	public  String Status ;
	public String Message ;
	
	public List<eWatheqFile> Data;
	public eWatheqFileResponse() {
		// TODO Auto-generated constructor stub
		Status = "";
		Message = "";
		Data = new ArrayList<eWatheqFile>();
	}



	

	public static eWatheqFileResponse parse(JSONObject jSonObj)
	{
		eWatheqFileResponse folder = new eWatheqFileResponse();
		try {
			folder.Status = jSonObj.getString(strStatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			folder.Message = jSonObj.getString(strMessage);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			folder.Data = eWatheqFile.parse(jSonObj.getJSONArray(strData));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return folder;
	}
	
}
