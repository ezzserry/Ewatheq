package ae.ewatheq.models;



import org.json.JSONException;
import org.json.JSONObject;

public class eWatheqSingleFolderResponse {

	
	public static String  strStatus="Status";
	public static String  strMessage="Message";
	public static String  strData="Data";
	
	public  String Status ;
	public String Message ;
	
	public eWatheqFolder Data;
	public eWatheqSingleFolderResponse() {
		// TODO Auto-generated constructor stub
		Status = "";
		Message = "";
		Data = new eWatheqFolder();
	}



	

	public static eWatheqSingleFolderResponse parse(JSONObject jSonObj)
	{
		eWatheqSingleFolderResponse folder = new eWatheqSingleFolderResponse();
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
			folder.Data = eWatheqFolder.parse(jSonObj.getJSONObject(strData));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return folder;
	}
	
}
