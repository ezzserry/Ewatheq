package ae.ewatheq.utils;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import ae.ewatheq.models.ExhibitorsRIdsOnMap;
import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.external.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

@SuppressLint("SimpleDateFormat")
public class EWatheqUtils {


	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getFormattedDate(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		//return dateFormat.format(date);
		return strDate;
	}
	public static String getFormattedDate(String strDate, String strCurrentFormat, String strNeededFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat currentdf= new SimpleDateFormat(strCurrentFormat, Locale.ENGLISH);
		
		try {
			cal.setTime(currentdf.parse(strDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done
	 
		
		
		return getFormattedDate(cal,strNeededFormat);
		
		
	}
	public static String getFormattedDate(Calendar cal, String strNeededFormat) {
		SimpleDateFormat neededdf= new SimpleDateFormat(strNeededFormat, Locale.ENGLISH);
		Date date = cal.getTime();
		
		return neededdf.format(date);
	}
	public static String getFileType(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		if (isDocument(extension))
			return Constants.FILE_TYPE_FOR_SERVER_PDF;
		else
			return Constants.FILE_TYPE_FOR_SERVER_PHOTO;
	}
	
	public static boolean isDocument(String fileExtension)
	{
		if (fileExtension.equalsIgnoreCase("pdf"))
			return true;
		else 
			return false;
	}
	public static  int  getDeviceDensity(Context context)
	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density * 160f);
		return densityDpi;
	}
	public static  void getcellHomeScreenWidth(Context context)
	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density * 160f);
		
		
	}
	public static  void getcellHomeScreenHeight(Context context)
	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density * 160f);
	}
	public static boolean isDocumentByFileType(String fileType)
	{
		
		if (fileType!= null && fileType.equalsIgnoreCase(Constants.FILE_TYPE_FOR_SERVER_PDF))
			return true;
		 
			return false;
	}
	public static File copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		return dst;
	}
	/*public static String getFormattedDate(Calendar cal)
	{
		try{
			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_ON_SCREEN);
			return format.format(cal.getTime());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}*/
	public static File copy(File srcFile) throws IOException {
		String tempFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File watheqDir = new File(tempFolderPath+File.separator+Constants.LOCAL_DIR);
		String fileType = srcFile.getAbsolutePath();
		if (fileType.contains("."))
			fileType = fileType.substring(fileType.lastIndexOf("."));
		boolean isWatheqDirExist = false;
		if (!watheqDir.exists())
		{
			if (watheqDir.mkdir())
			{
				isWatheqDirExist = true;
			}
		}
		else 
			isWatheqDirExist = true;
		if (isWatheqDirExist)
		{
			try {
				File dstFile = new File(watheqDir, "temp"+fileType);
				return copy(srcFile, dstFile);
			} catch (Exception e) {
				// TODO: handle exception
			}	
		}
		return null;
	}
	public static String getRealPathFromURI(Context context, Uri contentUri) {
		/*Cursor cursor = null;
		try { 
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}*/
		
		System.out.println("URI to open is: " + contentUri);
		if (contentUri.toString().startsWith("content://media/external/file")) {
			Cursor cursor = context.getContentResolver().query(contentUri,
					new String[] { "_data" }, null, null, null);
			if (cursor.moveToFirst()) {
				contentUri = Uri.parse(cursor.getString(0));
			}
		}

		String mFilePath = Uri.decode(contentUri.getEncodedPath());
		return mFilePath;
	}
	public static int getFileSize(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 

			cursor = context.getContentResolver().query(contentUri,
					null, null, null, null);
			 cursor.moveToFirst();
			long size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
			cursor.close();
			return (int) size;
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return 0;
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
	public static boolean verifyEmail(String email)
	{
		Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();	
	}
	public static boolean verifyPassword(String password)
	{
		if (password.length() < 8)
			return false;
		else 
			return true;
	}
	public static String  getPrimaryAccount(Context context)
	{

		try {
			Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
			for (Account account : accounts) {
				return account.name;
			}
		} catch (Exception e) {
			Log.i("Exception", "Exception:" + e);
		}
		return "";

	}
	public static String RemoveSpacesFromUrl(String strUrl) {
		if (strUrl.contains(" "))
			strUrl = strUrl.replace(" ", "%20");
		return strUrl ; 

	}
	public static void SlideUP(View view,Context context)
	{
		view.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.anim_slide_up));
	}

	public static void SlideDown(View view,Context context)
	{
		view.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.anim_slide_down));
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
	public static String getFileExtension (String name)
	{
		//http://ecssrbooks.com/NACms/Content/uploads/ZayedTorath.pdf
		if (name.contains("."))
		{
			return name.substring(name.lastIndexOf(".")+1);
		}
		return name;
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
	public static String getPrefString(Activity context, String key)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getString(key,"");
	}
	public static Boolean getPrefBoolean(Activity context, String key)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getBoolean(key, false);
	}
	public static int getPrefInt(Activity context, String key)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getInt(key, 0);
	}
	public static void setPref( Context context, String key, Object value ) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);   
		SharedPreferences.Editor editor = prefs.edit();

		if (value == null) {
			editor.remove(key);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else {
			throw new IllegalArgumentException("Unsupported type: "
					+ value.getClass().getSimpleName());
		}

		editor.commit();
	}
	public static void clearPref( Context context, String key) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);   
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(key);
		editor.commit();
	}
	public static File getThumbDirectory()
	{
		String path = Environment.getExternalStorageDirectory()+File.separator
				+ Constants.LOCAL_DIR;
		
		 File dir = new File(path);
		 if(!dir.exists())
		     dir.mkdirs();
		 String thumbPath = Environment.getExternalStorageDirectory()+File.separator
					+ Constants.LOCAL_DIR+File.separator + Constants.LOCAL_DIR_THUMB;
		 File thumbDir = new File(thumbPath);
		 if(!thumbDir.exists())
			 thumbDir.mkdirs();
		 
		 return thumbDir;
	}
	public static File getTempDirectory()
	{
		String path = Environment.getExternalStorageDirectory()+File.separator
				+ Constants.LOCAL_DIR;
		
		 File dir = new File(path);
		 if(!dir.exists())
		     dir.mkdirs();
		 String thumbPath = Environment.getExternalStorageDirectory()+File.separator
					+ Constants.LOCAL_DIR+File.separator + Constants.LOCAL_DIR_TEMP;
		 File thumbDir = new File(thumbPath);
		 if(!thumbDir.exists())
			 thumbDir.mkdirs();
		 
		 return thumbDir;
	}
	public static File getTempFile(eWatheqFile file)
	{
		
		
		
		 File thumbDir = getTempDirectory();
		
		
		 File tempFile = new File(thumbDir, file.FileID+"."+file.getFileExtension());
		 if(!tempFile.exists())
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if (tempFile.exists())
			 return tempFile;
		 else return null;
	}
	public static void getDeleteAllTempFiles()
	{
		
		
		
		 File thumbDir = getTempDirectory();
		 if (thumbDir!=null && thumbDir.exists() && thumbDir.isDirectory()) 
		 {
		     String[] children = thumbDir.list();
		     for (int i = 0; i < children.length; i++)
		     {
		        new File(thumbDir, children[i]).delete();
		     }
		 }
		 
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
