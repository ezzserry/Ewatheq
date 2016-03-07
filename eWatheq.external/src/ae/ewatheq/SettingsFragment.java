package ae.ewatheq;




import ae.ewatheq.app.AppController;
import ae.ewatheq.models.UserQouta;
import ae.ewatheq.models.UserQoutaResponse;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.MaskMediaUtils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import ae.ewatheq.utils.VolleyResponseUtf8String;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sqlite.DbHelper;
import ae.ewatheq.external.R;

public class SettingsFragment extends Fragment implements OnClickListener {



	SharedPreferences prefs;
	private MainActivity activity;
	private RadioGroup rgLang;
	private RadioButton rbAr,rbEng;
	private RelativeLayout rlAr,rlEng;
	private String strLangId;
	private int langId;
	private TextView tvVersion,tvName, tvQouta ;
	private ProgressBar pbQouta;
	private Button btnLogout;
	private Button btnHelp;

	private DbHelper db;
	private LayoutInflater inflater;
	private ViewGroup container;
	private View view;

	//private boolean languageChanged = false;
	private String  name = "", userId = "",userQoutaAvailable = "",userQoutaUsed= "", userTotalQouta = "";
	private ProgressDialog ringProgressDialog;
	private UserQouta qouta;
	private int  qoutaPercentage;
	private static int  MB_CONVERSION = 1024*1024;
	public static String  TAG_GET_QOUTA= "get_qouta";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		activity = (MainActivity) getActivity();
		db = new DbHelper(activity);
		view = inflater.inflate(R.layout.lout_fragment_settings, 
				container, false);
		PackageInfo pInfo = null;
		try {
			pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_NAME);
		userId = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_ID);
		userQoutaAvailable = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_AVAILABLE_QOUTA);
		userQoutaUsed = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_USED_QOUTA);
		userTotalQouta = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_TOTAL_QOUTA);
		rgLang = (RadioGroup)view.findViewById(R.id.rg_lang);
		rbAr = (RadioButton)view.findViewById(R.id.rb_ar);
		rbEng = (RadioButton)view.findViewById(R.id.rb_eng);
		
		rlAr = (RelativeLayout)view.findViewById(R.id.rl_ar);
		rlEng = (RelativeLayout)view.findViewById(R.id.rl_eng);
		rlAr.setOnClickListener(this);
		rlEng.setOnClickListener(this);
		
		tvVersion = (TextView) view.findViewById(R.id.tv_version);
		tvName = (TextView) view.findViewById(R.id.tv_name);
		tvQouta = (TextView) view.findViewById(R.id.tv_qouta);
		tvName.setText(name);
		pbQouta = (ProgressBar) view.findViewById(R.id.pb_qouta);

		
		
		
		btnLogout = (Button) view.findViewById(R.id.btn_logout);
		btnLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DeleteFilesTask().execute();
			}
		});
		btnHelp = (Button) view.findViewById(R.id.btn_help);
		btnHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(activity, HelpActivity.class));
			}
		});
		showUserQouta();
		if (pInfo!= null)
			tvVersion.setText(pInfo.versionName);
		addListenerOnButton();
		strLangId =MaskMediaUtils.GetSharedParameter(
				activity,
				Constants.LANGUAGES_IDS);
		if (strLangId!=null)
		{
			langId = Integer.parseInt(strLangId);
		}
		else
		{
			langId =Constants.LANGUAGE_ID_ENG;
		}
	//	languageChanged = true;
		if (langId == Constants.LANGUAGE_ID_AR)
		{
			rbAr.setChecked(true);
		}
		else 
		{
			rbEng.setChecked(true);
		}
		rbAr.setClickable(false);
		rbEng.setClickable(false);
		getQoutaFromServer();
		return view;
	}
	public void showUserQouta ()
	{
		double longTotalQouta = Long.parseLong(userTotalQouta);
		double longAvailableQouta = Long.parseLong(userQoutaAvailable);
		double longUsedQouta = Long.parseLong(userQoutaUsed);
		String usedQuotaUnit = "MB";
		String TotalQuotaUnit = "MB";
		longTotalQouta = longTotalQouta/MB_CONVERSION;
		longAvailableQouta = longAvailableQouta/MB_CONVERSION;
		longUsedQouta = longUsedQouta/MB_CONVERSION;

		if (longTotalQouta > 1024){
			longTotalQouta = longTotalQouta/1024;
			TotalQuotaUnit = "GB";
		}
		if (longUsedQouta > 1024){
			longUsedQouta = longUsedQouta/1024;
			usedQuotaUnit = "GB";
		}

		tvQouta.setText(String.format( "%.1f",longUsedQouta  )+usedQuotaUnit+" / "+String.format( "%.1f", longTotalQouta )+TotalQuotaUnit);
		
		int ratio = (int) ((longUsedQouta/longTotalQouta)*100);
		
		pbQouta.setProgress(ratio);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		activity.setCurrentFragment(this);

	}
	public void setText()
	{
		TextView tvLangHeading = (TextView) view.findViewById(R.id.tv_language_heading);
		
		TextView tvCopyrights = (TextView) view.findViewById(R.id.tv_copyrights);


		if (tvCopyrights != null)
			tvCopyrights.setText(activity.getString(R.string.str_copy_rights_desc));
		
		if (tvLangHeading != null)
			tvLangHeading.setText(activity.getString(R.string.str_language));

		if (tvVersion != null)
			tvVersion.setText(activity.getString(R.string.str_version));

	}

	public void addListenerOnButton() {

		rgLang.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int position = 0;
				if (checkedId == R.id.rb_ar)
				{
					position = 0;
				}
				else {
					position = 1;

				}
				changeConfig(position);
			}
		});



	}

	public void changeConfig(int position)
	{
		Configuration configuration = activity.getApplicationContext().getResources().getConfiguration();
		configuration.locale = MaskMediaUtils.getLocale(Constants.LANGUAGE_CODES[position]);
		activity.getApplicationContext().getResources().updateConfiguration(
				configuration, activity.getApplicationContext().getResources().getDisplayMetrics());
		MaskMediaUtils.SetSharedParameter(activity, 
				Constants.LANGUAGES_IDS, Integer.toString(position+1));
		//SettingsFragment.this.setText();
		
			activity.notifyLanguageChange();
		
	}

	private class DeleteFilesTask extends AsyncTask<String, Void, Boolean> {
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showProgressDialog (getResources().getString(R.string.str_logging_out)); 
			
		}
		@Override
		protected Boolean doInBackground(String... params) {
			SignInActivity.clearUserInfo(activity);
			db.deleteAllFolders();
			db.deleteAllFiles();
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {

			if (ringProgressDialog!= null)
				ringProgressDialog.dismiss();
			startActivity(new Intent(activity, SignInActivity.class));
			activity.finish();    
		}
	}
	public void showProgressDialog(String message)
	{
		if (ringProgressDialog!=null && ringProgressDialog.isShowing())
		{
			ringProgressDialog.dismiss();
			ringProgressDialog = null;
		}
			ringProgressDialog = ProgressDialog.show(activity, message,	"", true);
			ringProgressDialog.setCancelable(false);
	}
	public void hideProgressDialog()
	{
		if (ringProgressDialog!=null && ringProgressDialog.isShowing())
		{
			ringProgressDialog.dismiss();
			ringProgressDialog = null;
		}
			
	}
	
	public void getQoutaFromServer ()
	{
		//showProgressDialog(getResources().getString(R.string.str_loading_user_qouta));
		VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST,Constants.URL_QOUTA+getQoutaUrlParameters(userId), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				UserQoutaResponse qoutaResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), UserQoutaResponse.class);
				if (qoutaResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)){
					userQoutaAvailable = qoutaResponse.Data.AvalibleQuota;
					userQoutaUsed= qoutaResponse.Data.UsedQuota;
					userTotalQouta = qoutaResponse.Data.UserQuota;
					showUserQouta();
					//showMessage(qoutaResponse.Message);
				}
				else {
					//showMessage(getResources().getString(R.string.str_update_folder_error));
				}
				hideProgressDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				//showMessage(getResources().getString(R.string.str_update_folder_error));
				hideProgressDialog();
			}
		});
		req.setRetryPolicy(new DefaultRetryPolicy(
				SignInActivity.MY_SOCKET_TIMEOUT_MS, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().addToRequestQueue(req,
				TAG_GET_QOUTA);
	}
	
	public void showMessage (String message)
	{
		
		Toast.makeText(activity, message,
				Toast.LENGTH_SHORT).show();
	}
	public String getQoutaUrlParameters(String userId)
	{

		String param = "";
		param = param + Constants.URL_PARAMETER_USER_ID+Constants.URL_PARAMETER_EQQUAL+ userId;
		
		param = EWatheqUtils.RemoveSpacesFromUrl(param);
		return param;	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.rl_ar && langId != Constants.LANGUAGE_ID_AR)
		{
			changeConfig(0);
		}
		else if (id == R.id.rl_eng&& langId != Constants.LANGUAGE_ID_ENG)
		{
			changeConfig(1);
		}
	}


}
