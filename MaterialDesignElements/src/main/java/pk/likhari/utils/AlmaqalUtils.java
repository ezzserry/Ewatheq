package pk.likhari.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pk.likhari.R;
import pk.likhari.R.anim;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class AlmaqalUtils {


	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static void fadeInFadeOutAnim(Context context ,View view)
	{
		 if(view.getVisibility() == View.VISIBLE) {
	            Animation out =AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
	            view.startAnimation(out);
	            view.setVisibility(View.INVISIBLE);
	        } else {
	            Animation in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
	            view.startAnimation(in);
	            view.setVisibility(View.VISIBLE);
	        }
	}
	public static void SlideInAnimation(Context context ,final View view)
	{
		 if(view.getVisibility() == View.GONE) {
	            Animation out =AnimationUtils.loadAnimation(context, anim.anim_slide_up);
	            out.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						view.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						
					}
	            });
	            view.startAnimation(out);
	            
	        }
	}
	public static void SlideInOutAnimation(Context context ,final View view)
	{
		 if(view.getVisibility() == View.VISIBLE) {
	            Animation out =AnimationUtils.loadAnimation(context, anim.anim_slide_down);
	            out.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						view.setVisibility(View.GONE);
					}
	            });
	            view.startAnimation(out);
	            
	        }
	}
	public static void applyFontSeeraMedium(Context context, TextView text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_medium.ttf");
		text.setTypeface(tf);	
	}
	public static void applyFontSeeraRegular(Context context, TextView text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_regular.ttf");
		text.setTypeface(tf);	
	}
	public static void applyFontAdenBold(Context context, TextView text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sf_aden_bold.ttf");
		text.setTypeface(tf);	
	}
	public static void applyFontSeeraMedium(Context context, Button text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_medium.ttf");
		text.setTypeface(tf);	
	}
	public static void applyFontSeeraRegular(Context context, Button text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_regular.ttf");
		text.setTypeface(tf);	
	}
	public static void applyFontAdenBold(Context context, Button text) {
		Typeface tf;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/sf_aden_bold.ttf");
		text.setTypeface(tf);	
	}

	/*public static void applyFont(Context context, Button btnText,int fontType) {
		Typeface tf;
		if (fontType == Constants.FONT_TYPE_SF_ADEN_BOLD)
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/sf_aden_bold.ttf");
		else if (fontType == Constants.FONT_TYPE_SF_ADEN_BOLD)
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_medium.ttf");
		else 
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/sultan_seera_regular.ttf");
		btnText.setTypeface(tf);
	}*/
	public static String getGetPurchasedSubscriptionURL(String email)
	{


		return Constants.URL_AL_MAQAL_BASE_URL+Constants.URL_AL_MAQAL_GET_PURCHSED_SUBSCRIPTIONS+
				Constants.URL_AL_MAQAL_EMAIL_PART+email;
	}
	public static String getDateInDayFormat(String dateString) {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy");
		Date date;
		try {
			date = fmt.parse(dateString);
			SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE dd MMMM yyyy",Locale.ENGLISH);
			return fmtOut.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static String getDate(long milliSeconds, String dateFormat)
	{
		// Create a DateFormatter object for displaying date in specified format.
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	public static boolean ValidateEmailAddress(final String hex) {

		Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();

	}
	
	
	public static String getPrimaryAccount(Context context) {

		return MaskMediaUtils.GetSharedParameter(context, Constants.USER_ACCOUNT);
	}
	public static String getDeviceName() {
		return  Build.MODEL ;
	}
	public static String getUserName() {
		return  Build.USER ;
	}
	public static String getEMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();

	}
	public static String RemoveSpacesFromUrl(String strUrl) {
		if (strUrl.contains(" "))
			strUrl = strUrl.replace(" ", "%20");
		return strUrl ; 

	}
	public static String getOsVersion() {
		return  Build.VERSION.CODENAME ;
	}

	
	public static Calendar getCalendarObjFromString(String dateString, String format) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

		try {
			cal.setTime(sdf.parse(dateString));// all done
			return cal;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static boolean compareSameDayOrBetween(Calendar calFirst,Calendar calSecond, Calendar calIssue) {

		int dayFirst,monthFirst,yearFirst,daySecond,monthSecond,yearSecond,dayIssue,monthIssue,yearIssue;


		dayFirst = calFirst.get(Calendar.DAY_OF_MONTH);
		daySecond = calSecond.get(Calendar.DAY_OF_MONTH);
		dayIssue= calIssue.get(Calendar.DAY_OF_MONTH);
		monthFirst = calFirst.get(Calendar.MONTH);
		monthSecond = calSecond.get(Calendar.MONTH);
		monthIssue = calIssue.get(Calendar.MONTH);
		yearFirst = calFirst.get(Calendar.YEAR);
		yearSecond = calSecond.get(Calendar.YEAR);
		yearIssue= calIssue.get(Calendar.YEAR);



		if (dayFirst == dayIssue && monthFirst == monthIssue && yearFirst == yearIssue)
			return true;
		else if (daySecond == dayIssue && monthSecond == monthIssue && yearSecond == yearIssue)
			return true;
		else if (calFirst.getTimeInMillis() < calIssue.getTimeInMillis() &&
				calSecond.getTimeInMillis() > calIssue.getTimeInMillis() )
			return true;

		return false;
	}

	public static void setLanguage(Context context,int langId)
	{



		if(langId == Constants.LANGUAGE_ID_AR||
				langId == Constants.LANGUAGE_ID_ENG){


			Configuration configuration = context.getApplicationContext().getResources().getConfiguration();
			configuration.locale = MaskMediaUtils.getLocale(Constants.LANGUAGE_CODES[langId-1]);

			context.getApplicationContext().getResources().updateConfiguration(
					configuration, context.getApplicationContext().getResources().getDisplayMetrics());
		}
	}
	public static int getNumberOfDaysFromPurchaseDiscription(String descr)
	{

		int numberOfDays = 0;
		try{
			String [] descLines = descr.split("\n");
			if (descLines.length>1){
				String [] daysLine = descLines[1].split(" ");
				numberOfDays = Integer.parseInt(daysLine[0]);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return numberOfDays;

	}
	public static long getCurrentTiem(){
		return System.currentTimeMillis();
	}
	public static boolean isConnectingToInternet(Context _context){
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i < info.length; i++) 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}
	public static String getYearFromDateString (String dateString) {
		String strYear = "";
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy",Locale.ENGLISH);
		Date date;
		try {
			date = fmt.parse(dateString);
			strYear = fmtOut.format(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strYear;
	}
	public static int getIntYearFromDateString (String dateString) {
		String strYear = getYearFromDateString(dateString);
		int intYear = 0;
		try {
			intYear = Integer.parseInt(strYear);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return intYear;
	}
	public static boolean isInternetConenctionAvailable(Context context){
		ConnectivityManager check = (ConnectivityManager) context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isConnected = false;
		if (check != null) 
		{
			NetworkInfo[] info = check.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i <info.length; i++) 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						isConnected = true;
						break;
					}

		}
		return isConnected;
	}
	public static Boolean convertStringToBoolean(String str)
	{
		str = str.trim();
		if (str.equalsIgnoreCase("true"))
			return true;
		else 
			return false;
	}



	public static String GetYoutubeVideoThumb(String link)
	{
		String id = "";
		String thumbUrl = "";
		String youtubeThumbStartUrl = "http://img.youtube.com/vi/";
		String youtubeThumbEndUrl = "/1.jpg";
		int start = link.indexOf("?v=")+3;
		int end = link.indexOf("&");
		id = link.substring(start,end);
		thumbUrl = youtubeThumbStartUrl+id+youtubeThumbEndUrl;
		return thumbUrl;
	}
	public static String GetYoutubeVideoId(String link)
	{
		String id = "";
		int start = link.indexOf("?v=")+3;
		int end = link.indexOf("&");
		id = link.substring(start,end);
		return id;
	}
	public static String getDateInDayFormatAr(String dateString) {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy");
		Date date;
		try {
			date = fmt.parse(dateString);
			Locale []myLocale = Locale.getAvailableLocales();
			SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE dd MMMM yyyy",Locale.ENGLISH);
			return fmtOut.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";


	}
	public static String updateUrl (String url)
	{
		//http://ecssrbooks.com/NACms/Content/uploads/ZayedTorath.pdf
		String first = url.substring(0,url.lastIndexOf("/"));
		String second = url.substring(url.lastIndexOf("/"));
		if (!url.contains("Content"))
		{
			url = first + "/Content/uploads"+second;
		}
		return url;
	}



	public static boolean isExhExistInFavArray(String exhId, ArrayList<Integer> favExhIds)
	{
		for (int i =0;i<favExhIds.size();i++)
		{

			if (isExhEquals(exhId,favExhIds.get(i)))
				return true;
		}
		return false;
	}
	public static boolean deleteFileFromLocal(String path)
	{
		if (path!= null && path.length()>0)
		{
			String lastp =path
					.substring(path
							.lastIndexOf('/') + 1);

			File file = new File(Environment.getExternalStorageDirectory()+"/"
					+ Constants.LOCAL_DIR+"/"+lastp);
			if (file.exists())
				return file.delete();
		}
		return false;

	}
	public static boolean isExhEquals(String strexhId, int intExhIds)
	{

		String favId = Integer.toString(intExhIds);
		if (favId.equals(strexhId))
			return true;

		return false;
	}
	public static ArrayList<Integer> arrayToArrayLis(int [] favIds)
	{
		ArrayList<Integer> favIdsArrayList = new ArrayList<Integer>();
		if (favIds != null && favIds.length>0)
		{
			for (int i = 0 ; i < favIds.length; i++)
				favIdsArrayList.add(favIds[i]);
		}
		return favIdsArrayList;
	}
	public static int getExhibitorRId( String id)
	{

		String rid = id.replace("@+id/", "");
		int _id=0;

		try
		{
			Class<R.id> res = R.id.class;
			Field field = res.getField(rid);
			_id = field.getInt(null);
		}
		catch (Exception e)
		{
			_id = 0;
		}
		return _id;
	}
}
