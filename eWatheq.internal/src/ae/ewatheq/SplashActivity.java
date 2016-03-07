package ae.ewatheq;

import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.FilePathUtil;
import ae.ewatheq.utils.MaskMediaUtils;
import ae.ewatheq.internal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {


	private Handler mHandler;
	private ServiceWaitThread mThread;
	private String appVersion;
	private Activity activity;
	public static boolean IS_OFFLINE = true;
	public static boolean SIGN_IN_TESTING = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		setSigninTesting(false);
		setOfflineTesting(true);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lout_activity_splash);
		EWatheqUtils.setPref(activity, 
				Constants.Network_wif, true);

		try {
			PackageInfo p  = getPackageManager().getPackageInfo(Constants.PACKAGE_NAME, 0);
			appVersion = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_APP_VERSION);
			if (appVersion.equalsIgnoreCase("0") || !appVersion.equalsIgnoreCase(p.versionName))
			{
				appVersion = p.versionName;
				EWatheqUtils.setPref(activity, Constants.SHARED_PREFERENCES_NEED_TO_UPDATE_DB, true);
				EWatheqUtils.setPref(activity, Constants.SHARED_PREFERENCES_APP_VERSION, appVersion);
				appVersion = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_APP_VERSION);
			}
			else 
				EWatheqUtils.setPref(activity, Constants.SHARED_PREFERENCES_NEED_TO_UPDATE_DB, false);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHandler = new Handler();
		mThread = new ServiceWaitThread();
		mThread.start();

	}
	public static void setSigninTesting(boolean isSigninTesting)
	{
		SIGN_IN_TESTING = isSigninTesting;
	}
	public static void setOfflineTesting(boolean isofflineTesting)
	{
		IS_OFFLINE = isofflineTesting;
	}
	private class ServiceWaitThread extends Thread {
		@Override
		public void run() {
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				throw new RuntimeException("waiting thread sleep() has been interrupted");
			}
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					onServiceReady();
				}
			});
			mThread = null;
		}
	}
	protected void onServiceReady() {
		String lid=EWatheqUtils.getPrefString(activity,Constants.LANGUAGES_IDS);

		if(lid!=null && !lid.isEmpty()){
			if(Integer.parseInt(lid)==Constants.LANGUAGE_ID_AR||
					Integer.parseInt(lid)==Constants.LANGUAGE_ID_ENG){
				int langID=Integer.parseInt(lid);

				Configuration configuration = getApplicationContext().getResources().getConfiguration();
				configuration.locale = MaskMediaUtils.getLocale(Constants.LANGUAGE_CODES[langID-1]);
				getApplicationContext().getResources().updateConfiguration(
						configuration, getApplicationContext().getResources().getDisplayMetrics());
			}
		}

		selectLanguage();
	}
	public void selectLanguage()
	{
		String strLangId;
		strLangId =EWatheqUtils.getPrefString(
				activity,
				Constants.LANGUAGES_IDS);
		if (strLangId!=null && !strLangId.isEmpty())
		{
			startNextActivity();
		}
		else
		{
			final CharSequence[] items = { getString(R.string.str_ar),getString(R.string.str_eng)};
			AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
			builder.setTitle(getString(R.string.str_sel_lang));
			builder.setCancelable(false);
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					// Do something with the selection

					Configuration configuration = SplashActivity.this.getApplicationContext().getResources().getConfiguration();
					configuration.locale = MaskMediaUtils.getLocale(Constants.LANGUAGE_CODES[item]);
					SplashActivity.this.getApplicationContext().getResources().updateConfiguration(
							configuration, SplashActivity.this.getApplicationContext().getResources().getDisplayMetrics());
					EWatheqUtils.setPref(activity, Constants.LANGUAGES_IDS, Integer.toString(item+1));
					startNextActivity();
					dialog.dismiss();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();

		}
	}
	public void startNextActivity()
	{
		String userId= EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_ID);
		EWatheqUtils.setPref(activity, Constants.KEY_FILE_PATH, getFilePath());
		Intent intent;
		if (userId != null && !userId.isEmpty() && ! SIGN_IN_TESTING)
		{
			intent= new Intent(activity, FilesActivity.class);
			
		}
		else 
		{
			//000818es61e6002
			intent= new Intent(activity, SignInActivity.class);
			
		}
		startActivity(intent);
		finish();
	}
	private String getFilePath() {
		final Intent intent = getIntent();
		String selectedImagePath = "";
		if (intent != null){
			Uri uri = intent.getData();   
			if (uri != null)
				selectedImagePath = FilePathUtil.getPath(getApplicationContext(), uri);
		}
		return selectedImagePath;
	}
}
