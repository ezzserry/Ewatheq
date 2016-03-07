package ae.ewatheq.utils;





import java.text.BreakIterator;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;










import ae.ewatheq.internal.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class MaskMediaUtils {
	
	private Context context;
	 

	
	public static boolean GetSharedBoolParameter(Context context, String key) {
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			/*preferences = context.getSharedPreferences(
					MaskMediaUtils.SHARED_PREFERENCES_NAME,
					MaskMediaUtils.SHARED_PREFERENCES_MODE);*/
			return preferences.getBoolean(key, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static void SetSharedBoolParameter(Context context, String key,
			boolean value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		/*preferences = context.getSharedPreferences(
				MaskMediaUtils.SHARED_PREFERENCES_NAME,
				MaskMediaUtils.SHARED_PREFERENCES_MODE);*/
		preferences.edit().putBoolean(key, value).commit();
	}
	public static Locale getLocale(String id)
	{
		Locale[] locales0 = BreakIterator.getAvailableLocales();
		Locale[] locales1 = Locale.getAvailableLocales();
		Locale[] locales2 = Collator.getAvailableLocales();
		Locale[] locales3 = java.text.DateFormat.getAvailableLocales();
		Locale[] locales5 = NumberFormat.getAvailableLocales();
		Locale[] locales6 = Calendar.getAvailableLocales();

		for(int i = 0; i < locales0.length; i++)
		{
			if(locales0[i].toString().contains(id))
			{
				return locales0[i];

			}
		}
		for(int i = 0; i < locales1.length; i++)
		{
			if(locales1[i].toString().contains(id))
			{
				return locales1[i];
			}
		}
		for(int i = 0; i < locales2.length; i++)
		{
			if(locales2[i].toString().contains(id))
			{
				return locales2[i];
			}
		}
		for(int i = 0; i < locales3.length; i++)
		{
			if(locales3[i].toString().contains(id))
			{
				return locales3[i];
			}
		}
		for(int i = 0; i < locales5.length; i++)
		{
			if(locales5[i].toString().contains(id))
			{
				return locales5[i];
			}
		}
		for(int i = 0; i < locales6.length; i++)
		{
			if(locales6[i].toString().contains(id))
			{
				return locales6[i];
			}
		}
		return Locale.getDefault();
	}
	public static void SetSharedParameter(Context context, String key,
			String value) {
		SharedPreferences	preferences = PreferenceManager.getDefaultSharedPreferences(context);
		/*preferences = context.getSharedPreferences(
				MaskMediaUtils.SHARED_PREFERENCES_NAME,
				MaskMediaUtils.SHARED_PREFERENCES_MODE);*/
		preferences.edit().putString(key, value).commit();

	}
	public static String GetSharedParameter(Context context, String key) {
		/*preferences = context.getSharedPreferences(
				MaskMediaUtils.SHARED_PREFERENCES_NAME,
				MaskMediaUtils.SHARED_PREFERENCES_MODE);*/
		SharedPreferences	preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, "0");

	}
	public String milliSecondsToTimer(long milliseconds){
		String finalTimerString = "";
		String secondsString = "";

		// Convert total duration into time
		int hours = (int)( milliseconds / (1000*60*60));
		int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
		int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
		// Add hours if there
		if(hours > 0){
			finalTimerString = hours + ":";
		}

		// Prepending 0 to seconds if it is one digit
		if(seconds < 10){ 
			secondsString = "0" + seconds;
		}else{
			secondsString = "" + seconds;}

		finalTimerString = finalTimerString + minutes + ":" + secondsString;

		// return timer string
		return finalTimerString;
	}

	/**
	 * Function to get Progress percentage
	 * @param currentDuration
	 * @param totalDuration
	 * */
	/*public static void  setFontSize(Context context,TextView textv){
		String strFontSize= MaskMediaUtils.GetSharedParameter(
				context,
				Constants.FONT_SIZE);
		int fontSize ;
		if (strFontSize == null)
		{
			fontSize = Constants.FONT_SIZE_DEFAULT;
			MaskMediaUtils.SetSharedParameter(context, Constants.FONT_SIZE, Integer.toString(fontSize));
		}
		else 
		{
			fontSize = Integer.parseInt(strFontSize);
		}
		if(fontSize == Constants.FONT_SIZE_2){
			
			textv.setTextSize(12);
			
		}
		else if(fontSize == Constants.FONT_SIZE_3){
			
			textv.setTextSize(14);
			
		}
		else if(fontSize == Constants.FONT_SIZE_DEFAULT){
			
			textv.setTextSize(16);
			
		}
		else if(fontSize == Constants.FONT_SIZE_5){
			
			textv.setTextSize(18);
			
		}
		else if(fontSize == Constants.FONT_SIZE_6){
			textv.setTextSize(20);

		}
		else if(fontSize == Constants.FONT_SIZE_LARGEST){
			textv.setTextSize(22);

		}
		else if(fontSize == Constants.FONT_SIZE_SMALLEST){
			
			textv.setTextSize(10);
			
		}




	}*/
	public static void  setPinkColor(Activity activity ,TextView textView){

		textView.setTextColor(activity.getResources().getColor(R.color.color_pink));


	}
	public static void  setOrangeColor(Activity activity ,TextView textView){

		textView.setTextColor(activity.getResources().getColor(R.color.color_orange));


	}
	public static void  setBlueColor(Activity activity ,TextView textView){

		textView.setTextColor(activity.getResources().getColor(R.color.color_blue));


	}
	public static void  setLightBlueColor(Activity activity ,TextView textView){

		textView.setTextColor(activity.getResources().getColor(R.color.color_light_blue));


	}
	public static void  setBlackColor(Activity activity ,TextView textView){

		textView.setTextColor(Color.BLACK);


	}





	public int getProgressPercentage(long currentDuration, long totalDuration){
		Double percentage = (double) 0;

		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);

		// calculating percentage
		percentage =(((double)currentSeconds)/totalSeconds)*100;

		// return percentage
		return percentage.intValue();
	}

	/**
	 * Function to change progress to timer
	 * @param progress - 
	 * @param totalDuration
	 * returns current duration in milliseconds
	 * */
	public int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double)progress) / 100) * totalDuration);

		// return current duration in milliseconds
		return currentDuration * 1000;
	}







}
