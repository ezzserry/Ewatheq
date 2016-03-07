package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserQouta implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strUserQuota = "UserQuota";
	public static String  strUsedQuota = "UsedQuota";
	public static String  strAvalibleQuota = "AvalibleQuota";

	


	public String UserQuota;
	public String UsedQuota;
	public String AvalibleQuota;
	

	public void removeNull(){
		if (this.UserQuota == null)
			this.UserQuota = "";
		if (this.UsedQuota == null)
			this.UsedQuota = "";
		if (this.AvalibleQuota == null)
			this.AvalibleQuota = "";
	}
	
	public int getUserQuotaInt()
	{
		if (this.UserQuota != null && !UserQuota.isEmpty())
		{
			try{
			return Integer.parseInt(this.UserQuota);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return 0;
	}
	public int getUsedQuotaInt()
	{
		if (this.UsedQuota != null && !UsedQuota.isEmpty())
		{
			try{
			return Integer.parseInt(this.UsedQuota);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return 0;
	}
	public int getAvalibleQuotaInt()
	{
		if (this.AvalibleQuota != null && !AvalibleQuota.isEmpty())
		{
			try{
			return Integer.parseInt(this.AvalibleQuota);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return 0;
	}
	
	public static UserQouta parse(JSONObject jSonObj)
	{
		UserQouta qouta = new UserQouta();
		try {
			qouta.AvalibleQuota = jSonObj.getString(strAvalibleQuota);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			qouta.UsedQuota = jSonObj.getString(strUsedQuota);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			qouta.UserQuota = jSonObj.getString(strUserQuota);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qouta;
	}
	
	
}
