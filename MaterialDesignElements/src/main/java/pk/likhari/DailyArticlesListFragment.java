package pk.likhari;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tekinarslan.material.sample.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import pk.likhari.app.AppController;
import pk.likhari.list.helper.DailyArticleListAdapter;
import pk.likhari.list.helper.SourcesAdapter;
import pk.likhari.models.ArticleJSON;
import pk.likhari.models.DailyArticleJSON;
import pk.likhari.utils.AlmaqalUtils;
import pk.likhari.utils.Constants;


@SuppressLint("NewApi")
public class DailyArticlesListFragment extends Fragment implements OnClickListener{


	public static boolean IS_TESTING = false;
	private static int SHOW_ARTICLES = 1;
	private static int SHOW_LOADING = 2;
	private static int SHOW_INTERNET_ERROR = 3;
	private static String TAG = DailyArticlesListFragment.class.getSimpleName();
	private static String TAG_BOOKSTORE_LOADING = "book_store_loading";
	public static String TAG_PURCHASED_SUBSCRIPTIONS_LOADING = "purchased_subscription_loading";
	private LinearLayout llMain,llNoInternet;


	private ProgressBar pbLoading;
	private LayoutInflater loutInflater; 
	private DailyArticleJSON dailyArticles;





	private TextView tvIssueDate;





	private FancyCoverFlow sourcesList;
	private ListView lvDailyArticle;
	private DailyArticleListAdapter dailyArticleListadapter;
	private SourcesAdapter sourcesListAdapter;
	private String jsonIssuesString  ;
	private Activity activity;

	private Button btnRetry;
	public static String CLASS_NAME = "IssuesListFragment";
	public boolean isTablet ;


	private ImageLoader imageLoader;
	private DisplayImageOptions options;





	public static String TAG_BOOK = "tag_book";
	public  int selectedMonth = -1;
	public  int selectedYear = -1;




	private View view;







	public static final String     KEY_NAME_DOWNLOAD_ID = "downloadId";
	static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
	public static final int    MB_2_BYTE             = 1024 * 1024;
	public static final int    KB_2_BYTE             = 1024;




	public static CharSequence getAppSize(long size) {
		if (size <= 0) {
			return "0M";
		}

		if (size >= MB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / MB_2_BYTE)).append("M");
		} else if (size >= KB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / KB_2_BYTE)).append("K");
		} else {
			return size + "B";
		}
	}
	public static boolean isDownloading(int downloadManagerStatus) {
		return downloadManagerStatus == DownloadManager.STATUS_RUNNING
				|| downloadManagerStatus == DownloadManager.STATUS_PAUSED
				|| downloadManagerStatus == DownloadManager.STATUS_PENDING;
	}




	public void myOnCreate()
	{







	}





	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (!isTablet)
		{
			lvDailyArticle.getLayoutParams().height = activity.getWindowManager().getDefaultDisplay().getHeight()/2;
		}



	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate view
		activity = (Activity) getActivity();
		isTablet = activity.getResources().getBoolean(R.bool.isTablet);

		loutInflater = inflater;
		view = loutInflater.inflate(R.layout.lout_fragment_issues_list, container,false);
		Log.d(TAG_BOOK, "Starting setup.");



		lvDailyArticle 	= (ListView)view.findViewById(R.id.lvIssuesArticles);
		sourcesList 	= (FancyCoverFlow)view.findViewById(R.id.lvIssuesThumbs);

		tvIssueDate 		= (TextView)view.findViewById(R.id.tv_issue_date);








		AlmaqalUtils.applyFontSeeraMedium(activity, tvIssueDate);


		this.imageLoader=ImageLoader.getInstance();
		this.imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_loading_thumb)
		.showImageForEmptyUri(R.drawable.default_loading_thumb)
		.showImageOnFail(R.drawable.default_loading_thumb)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();



		llMain 	= (LinearLayout)view.findViewById(R.id.ll_main);
		llNoInternet 	= (LinearLayout)view.findViewById(R.id.ll_no_internet);
		btnRetry 		= (Button)view.findViewById(R.id.btn_retry);
		pbLoading 		= (ProgressBar)view.findViewById(R.id.pb_loading);

		btnRetry.setOnClickListener(this);




		sourcesList.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				/*// TODO Auto-generated method stub*/
				//	setSelectedItem(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		lvDailyArticle.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View item, int position,
					long rowId) {
				// Retrieve item based on position

				ArticleJSON article = dailyArticleListadapter.getItem(position);
				// Fire selected event for item
				ArticleDetailFragment fragmentItem = ArticleDetailFragment.newInstance(article);
				FragmentManager fm = ((AlMaqalMainActivity)activity).getSupportFragmentManager();

				fragmentItem.show(fm, "fragment_event_list");
			}

		});

		setMonths();
		myOnCreate();
		if (jsonIssuesString != null)
		{
			new ParseAndShowBookStoreTask().execute();

		}
		else	
		{

			checkInternetAndProceed();
		}


		return view;
	}
	public boolean isCurrentMonthAndYear()
	{
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH);

		if (selectedMonth == -1|| selectedYear == -1 || 
				(selectedMonth == currentMonth && selectedYear ==currentYear))
			return true;

		return false;
	}
	public String getIssuesURL()
	{
		/*		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH);
		currentMonth = currentMonth-1;
		if (selectedMonth == -1)
			selectedMonth = currentMonth;
		if (selectedYear == -1)
			selectedYear = currentYear;

		return Constants.URL_AL_MAQAL_BASE_URL+Constants.URL_AL_MAQAL_GET_ISSUES_PART+
				Constants.URL_AL_MAQAL_YEAR_PART+selectedYear+"&"+
				Constants.URL_AL_MAQAL_MONTH_PART+(selectedMonth+1);*/

		return "http://likhari.technomz.com/services/getArticles?date=2015-11-30";
	}

	public void setMonths()
	{
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR) ;
		int currentMonth= cal.get(Calendar.MONTH) ;
		String[] arabicMonths = activity.getResources().getStringArray(R.array.arabic_month);
		/*monthsList = new ArrayList<MonthData>();
		for (int i= 0 ;i<arabicMonths.length;i++)
		{
			if (!(currentYear == 2015 && i==0) && currentMonth >= i )
			{
				monthsList.add(0,new MonthData(arabicMonths[i], i));
			}	
		}*/

	}
	/*public void setSelectedItem (int position)
	{
		if (issuesList == null || issuesList.size() == 0 || position < 0)
			return;
		IssuesJSON issue = issuesList.get(position);
		//currentIssue = issue;
		Calendar issueDateCal = AlmaqalUtils.getCalendarObjFromString(issue.BookDate,Constants.DATE_FORMAT_MMDDYYYY);
		if(issueDateCal != null){
			tvIssueDate.setText(getDateText(activity, issue.BookDate));
		}



		articleListadapter = new IssueArticleListAdapter(activity, issue);
		lvIssuesArticle.setAdapter(articleListadapter);


	}*/
	public static String getDateText(Context context, int deductDays)
	{

		Calendar issueDateCal = Calendar.getInstance();
		issueDateCal.add(Calendar.DAY_OF_MONTH,-deductDays);
		String[] arabicMonths = context.getResources().getStringArray(R.array.arabic_month);
		String[] arabicDays = context.getResources().getStringArray(R.array.arabic_week_days);
		int month = issueDateCal.get(Calendar.MONTH),monthDay = issueDateCal.get(Calendar.DAY_OF_MONTH),weekDay = issueDateCal.get(Calendar.DAY_OF_WEEK);
		return arabicDays[weekDay-1]+" "+(monthDay)+" "+arabicMonths[month]+" "+issueDateCal.get(Calendar.YEAR); 
	}
	public static String getDateText(Context context)
	{

		Calendar issueDateCal = Calendar.getInstance();

		String[] arabicMonths = context.getResources().getStringArray(R.array.arabic_month);
		String[] arabicDays = context.getResources().getStringArray(R.array.arabic_week_days);
		int month = issueDateCal.get(Calendar.MONTH),monthDay = issueDateCal.get(Calendar.DAY_OF_MONTH),weekDay = issueDateCal.get(Calendar.DAY_OF_WEEK);
		return arabicDays[weekDay-1]+" "+(monthDay)+" "+arabicMonths[month]+" "+issueDateCal.get(Calendar.YEAR); 
	}
	public static String getDateText(Context context, String strDate)
	{
		Calendar issueDateCal = AlmaqalUtils.getCalendarObjFromString(strDate,Constants.DATE_FORMAT_MMDDYYYY);
		String[] arabicMonths = context.getResources().getStringArray(R.array.arabic_month);
		String[] arabicDays = context.getResources().getStringArray(R.array.arabic_week_days);
		int month = issueDateCal.get(Calendar.MONTH),monthDay = issueDateCal.get(Calendar.DAY_OF_MONTH),weekDay = issueDateCal.get(Calendar.DAY_OF_WEEK);
		return arabicDays[weekDay-1]+" "+(monthDay)+" "+arabicMonths[month]+" "+issueDateCal.get(Calendar.YEAR); 
	}
	public static String getDateText(Context context, String strStartDate, int addDays)
	{
		Calendar issueDateCal = AlmaqalUtils.getCalendarObjFromString(strStartDate,Constants.DATE_FORMAT_MMDDYYYY);
		issueDateCal.add(Calendar.DAY_OF_MONTH,addDays);
		String[] arabicMonths = context.getResources().getStringArray(R.array.arabic_month);
		String[] arabicDays = context.getResources().getStringArray(R.array.arabic_week_days);
		int month = issueDateCal.get(Calendar.MONTH),monthDay = issueDateCal.get(Calendar.DAY_OF_MONTH),weekDay = issueDateCal.get(Calendar.DAY_OF_WEEK);
		return arabicDays[weekDay-1]+" "+(monthDay)+" "+arabicMonths[month]+" "+issueDateCal.get(Calendar.YEAR); 
	}


	public void checkInternetAndProceed(){

		if (AlmaqalUtils.isInternetConenctionAvailable(activity))
		{
			makeJsonArryReqForIssues();

		}
		else 
		{
			showLayout(SHOW_INTERNET_ERROR);
		}
	}

	public void showLayout (int layout)
	{
		if (layout == SHOW_ARTICLES)
		{
			llMain.setVisibility(View.VISIBLE);
			llNoInternet.setVisibility(View.GONE);
			pbLoading.setVisibility(View.GONE);
		}
		else if (layout == SHOW_INTERNET_ERROR)
		{
			llMain.setVisibility(View.GONE);
			llNoInternet.setVisibility(View.VISIBLE);
			pbLoading.setVisibility(View.GONE);
		}
		else if (layout == SHOW_LOADING)
		{
			llMain.setVisibility(View.GONE);
			llNoInternet.setVisibility(View.GONE);
			pbLoading.setVisibility(View.VISIBLE);
		}
		else 
		{
			llMain.setVisibility(View.VISIBLE);
			llNoInternet.setVisibility(View.GONE);
			pbLoading.setVisibility(View.GONE);
		}	
	}

	private void makeJsonArryReqForIssues() {
		showLayout(SHOW_LOADING);
		String url = "";

		url = getIssuesURL();
		StringRequest req = new StringRequest(url,
				new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, response.toString());

				jsonIssuesString= response.toString();

				new ParseAndShowBookStoreTask().execute();



			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Error: " + error.getMessage());
				Toast.makeText(activity, "makeJsonArryReqForIssues Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
				showLayout(SHOW_INTERNET_ERROR);
			}
		});


		AppController.getInstance().addToRequestQueue(req,
				TAG_BOOKSTORE_LOADING);


	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// Cancelling request
		// AppController.getInstance().getRequestQueue().cancelAll(TAG_BOOKSTORE_LOADING);
	}



	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		/*lvBook.setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);*/
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();

		if (id==R.id.btn_retry)
		{
			checkInternetAndProceed();
		}






	}

	public boolean checkOnlineState() {
		ConnectivityManager CManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo NInfo = CManager.getActiveNetworkInfo();

		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}





	public String getButtonText(Button b)
	{
		if (b.getText().toString().equalsIgnoreCase(activity.getString(R.string.str_category)))

			return "";
		else 
			return b.getText().toString().toLowerCase(Locale.getDefault());
	}

	public void setLayout ()
	{
		if  (dailyArticles != null && dailyArticles.articles != null)
		{	
			dailyArticleListadapter = new DailyArticleListAdapter(activity,dailyArticles.articles);
			lvDailyArticle.setAdapter(dailyArticleListadapter);
		}
	}
	void complain(String message) {
		Log.e(TAG_BOOK, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG_BOOK, "Showing alert dialog: " + message);
		bld.create().show();
	}




	private class ParseAndShowBookStoreTask extends AsyncTask<String, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showLayout(SHOW_LOADING);

		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("M/d/yy hh:mm a");
			Gson gson = gsonBuilder.create();
			dailyArticles = gson.fromJson(jsonIssuesString, DailyArticleJSON.class);
			//Collections.reverse(issuesList);

			return true;
		}
		@Override
		protected void onPostExecute(Boolean resultt) {
			// TODO Auto-generated method stub
			super.onPostExecute(resultt);
			setLayout();
			showLayout(SHOW_ARTICLES);
		}
	}




	public void hlvMonthsItemOnClick(int position) {
		// TODO Auto-generated method stub

		//selectedMonth = monthsList.get(position).monthNUmber;
		checkInternetAndProceed();
	}

}
