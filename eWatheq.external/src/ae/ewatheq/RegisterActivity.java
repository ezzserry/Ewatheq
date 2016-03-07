package ae.ewatheq;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import ae.ewatheq.app.AppController;
import ae.ewatheq.external.R;
import ae.ewatheq.models.RegisterResponse;
import ae.ewatheq.models.UploadFileResponse;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.MaskMediaUtils;
import ae.ewatheq.ocr.OCRActivity;

import ae.ewatheq.utils.VolleyResponseUtf8String;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.balysv.material.menu.MaterialMenuDrawable;
import com.balysv.material.menu.MaterialMenuView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.balysv.materialripple.MaterialRippleLayout;
import com.soundcloud.android.crop.CropUtil;


public class RegisterActivity extends Activity implements OnClickListener{


	private Activity activity;

	private static int SHOW_SCREEN = 1;
	private static int SHOW_LOADING = 2;
	private TextView btnRegister;
	private ImageView ivEidThumb,ivWatheqLogo;
	private MaterialRippleLayout rvEidThumb;
	private EditText etEmail, etFullname, etEmiratesId, etPassword, etConfirmPassword;
	private CheckBox cbAgreedOn;
	//private ProgressBar pbLoading;
	public static int START_OCR_REQUEST_CODE = 100;
	public static final String TAG_REGISTER= "tag_register";
	//	private String name ;
	private String gender ;
	private String country ;
	private String scannedEmiratesId ;
	private String dob ;
	private boolean isManualRegister ;
	private Bundle extras;
	private RelativeLayout rlLoading;

	private Uri captureFileImageUri;
	private String eIdfilePath;

	private MaterialMenuView btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Intent intent = getIntent();
		if(intent != null && intent.getExtras()!= null)
		{
			extras = intent.getExtras();
			isManualRegister = intent.getBooleanExtra(Constants.KEY_IS_MANUAL_REGISTER, false);

			if (!isManualRegister){

				//	name = extras.getString(OCRActivity.STR_OCR_DATA_NAME);
				gender = extras.getString(OCRActivity.STR_OCR_DATA_GENDER);
				country = extras.getString(OCRActivity.STR_OCR_DATA_COUNTRY);
				scannedEmiratesId = extras.getString(OCRActivity.STR_OCR_DATA_EMIRATES_ID);
				dob = extras.getString(OCRActivity.STR_OCR_DATA_DATE_OF_BIRTH);
				if (dob.length() == 6)
				{
					String dob1 = dob.substring(0, 2);
					String dob2 = dob.substring(2, 4);
					String dob3 = dob.substring(4, 6);
					dob = dob2+"/"+dob3+"/19"+dob1;
				}
				else
				{
					dob = "00/00/0000";
				}
			}
		}
		setContentView(R.layout.lout_activity_register_manual);
		MaskMediaUtils.SetSharedBoolParameter(activity,
				Constants.Network_wif, true);

		btnBack = (MaterialMenuView)findViewById(R.id.btn_back);
		if (btnBack != null)
		{
			btnBack.setOnClickListener(this);
			btnBack.setState(MaterialMenuDrawable.IconState.ARROW);
		}
		btnRegister = (TextView)findViewById(R.id.btn_register);

		ivWatheqLogo = (ImageView)findViewById(R.id.iv_watheq_logo);
		rvEidThumb = (MaterialRippleLayout)findViewById(R.id.rv_eid_thumb);
		ivEidThumb = (ImageView)findViewById(R.id.iv_eid_thumb);
		ivEidThumb.setOnClickListener(this);
		etEmiratesId = (EditText)findViewById(R.id.et_emirates_id);
		if (isManualRegister)
		{

			setImageHeight();
		}
		else
		{
			if (scannedEmiratesId != null)
			{
				etEmiratesId.setVisibility(View.GONE);
			}
			ivWatheqLogo.setVisibility(View.VISIBLE);
			rvEidThumb.setVisibility(View.GONE);
		}

		etEmail = (EditText)findViewById(R.id.et_email);
		etPassword = (EditText)findViewById(R.id.et_password);
		etConfirmPassword = (EditText)findViewById(R.id.et_confirm_password);
		etFullname = (EditText)findViewById(R.id.et_name);
		cbAgreedOn = (CheckBox)findViewById(R.id.cb_agreed_on_info);
		rlLoading = (RelativeLayout)findViewById(R.id.rl_loading);
		btnRegister.setOnClickListener(this);


	}
	public void setImageHeight()
	{
		if (isManualRegister)
		{
			if (ivEidThumb != null ){
				ViewTreeObserver vto = ivEidThumb.getViewTreeObserver();
				vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						ivEidThumb.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						int width= ivEidThumb.getMeasuredWidth();
						ivEidThumb.getLayoutParams().height = (int) (width * Constants.EMIRATES_ID_CARD_RATIO);
					}
				});
			}
		}
	}
	public void registerIdScanSuccess(RegisterResponse response)
	{
		SignInActivity.saveUserInfo(activity, response.Data);
		SignInActivity.saveCategories(activity, response.Data.Categories);
		Intent intent = new Intent(activity, FilesActivity.class);
		startActivity(intent);
		finish();
	}
	public void registerManualSuccess(RegisterResponse response)
	{
		Toast.makeText(activity, activity.getResources().getString(R.string.str_response_manually_sccuess),
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(activity, SignInActivity.class);
		startActivity(intent);
		finish();
	}
	public void showMessage(String errorMessage)
	{
		Toast.makeText(activity, errorMessage,
				Toast.LENGTH_LONG).show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.btn_register)
		{
			boolean isInternetAvailable = EWatheqUtils.isInternetConenctionAvailable(activity);
			if(!isInternetAvailable)
			{
				return;
			}
			String email = etEmail.getText().toString();
			String password = etPassword.getText().toString();
			String confirmPassword = etConfirmPassword.getText().toString();
			String fullName = etFullname.getText().toString();
			String eId = etEmiratesId.getText().toString();
			boolean emailVerification = EWatheqUtils.verifyEmail(email);
			if (email.length() <= 0
					|| password.length() <= 0
					|| confirmPassword.length() <= 0
					|| fullName.length() <= 0)
				Toast.makeText(activity, activity.getResources().getString(R.string.str_all_fields_are_mendatory),
						Toast.LENGTH_LONG).show();
			else if(!emailVerification)
				Toast.makeText(activity, activity.getResources().getString(R.string.str_invalid_email),
						Toast.LENGTH_LONG).show();
			else if(password.length() != confirmPassword.length())
				Toast.makeText(activity, activity.getResources().getString(R.string.str_password_not_matching),
						Toast.LENGTH_LONG).show();

			else if(!EWatheqUtils.verifyPassword(password))
				Toast.makeText(activity, activity.getResources().getString(R.string.str_password_must_be_minimum_8_characters_long),
						Toast.LENGTH_LONG).show();

			else if(!cbAgreedOn.isChecked())
				Toast.makeText(activity, activity.getResources().getString(R.string.str_emirates_id_detection_error),
						Toast.LENGTH_LONG).show();
			else if (isManualRegister && !OCRActivity.isId(eId) )
			{
				Toast.makeText(activity, activity.getResources().getString(R.string.str_error_wrong_emirates_id),
						Toast.LENGTH_LONG).show();
			}

			else  {
				if(!isManualRegister)
					registerIdScanToServer(fullName, email, password, scannedEmiratesId);
				else {
					if(checkImageSelected ())
						new RegisterManualToServerTask(fullName, email, password,eId).execute();
					else {
						Toast.makeText(activity, activity.getResources().getString(R.string.str_please_select_your_emirates_id_card_image_its_mendatory),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
		else if (id == R.id.iv_eid_thumb)
		{
			if (captureFileImageUri == null )
				new CaptureImageTask().execute();
			else
			{
				// Open Image
			}
		}
		else if (id ==  R.id.btn_back )
		{
			startActivity(new Intent(activity, SignInActivity.class));
			finish();
		}
	}
	public boolean checkImageSelected ()
	{
		if (captureFileImageUri != null)
		{
			File file = new File(captureFileImageUri.getPath());
			if (file != null && file.exists())
			{
				return true;
			}
		}
		return false;
	}

	public void captureImage() {

		if (captureFileImageUri != null)
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, captureFileImageUri);
			startActivityForResult(intent, FilesActivity.REQUEST_IMAGE_CAPTURE);
		}
	}
	public void showLayout (int layout)
	{
		if (layout == SHOW_LOADING)
		{
			rlLoading.setVisibility(View.VISIBLE);
			btnRegister.setEnabled(false);

			etEmail.setEnabled(false);
			etEmiratesId.setEnabled(false);
			etPassword.setEnabled(false);
			etConfirmPassword.setEnabled(false);
			etFullname.setEnabled(false);
		}
		else if (layout == SHOW_SCREEN)
		{
			rlLoading.setVisibility(View.GONE);
			btnRegister.setEnabled(true);

			etEmail.setEnabled(true);
			etPassword.setEnabled(true);
			etConfirmPassword.setEnabled(true);
			etFullname.setEnabled(true);
			etEmiratesId.setEnabled(true);
		}

	}
	public String getIdScanRegisterUrlParametersString(final String name,final String email,final String password,final String emiratesId)
	{

		String params = Constants.URL_PARAMETER_NAME+Constants.URL_PARAMETER_EQQUAL+name+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_PASSWORD+Constants.URL_PARAMETER_EQQUAL+password+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_EMAIL+Constants.URL_PARAMETER_EQQUAL+email+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_EMIRATES_ID+Constants.URL_PARAMETER_EQQUAL+emiratesId+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_GENDER+Constants.URL_PARAMETER_EQQUAL+getUrlGender(gender)+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_DATE_OF_BIRTH+Constants.URL_PARAMETER_EQQUAL+dob+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_AGE+Constants.URL_PARAMETER_EQQUAL+getUrlAge(dob)+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_NATINALITY_ID+Constants.URL_PARAMETER_EQQUAL+getUrlCountry(country);


		params = EWatheqUtils.RemoveSpacesFromUrl(params);
		return params;
	}
	public String getManualRegisterUrlParametersString(final String name,final String email,final String password,final String eId)
	{
		String params = Constants.URL_PARAMETER_FULL_NAME+Constants.URL_PARAMETER_EQQUAL+name+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_PASSWORD+Constants.URL_PARAMETER_EQQUAL+password+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_EMIRATES_ID+Constants.URL_PARAMETER_EQQUAL+eId+Constants.URL_PARAMETER_AND;
		params = params+Constants.URL_PARAMETER_EMAIL+Constants.URL_PARAMETER_EQQUAL+email;
		params = EWatheqUtils.RemoveSpacesFromUrl(params);
		return params;
	}

	public void registerIdScanToServer(final String name,final String email,final String password,final String emiratesId){
		if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
			showLayout(Constants.SHOW_LOADING);
			VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_ID_SCAN_REGISTER + getIdScanRegisterUrlParametersString(name, email, password, emiratesId), new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {

					new ParseIdScanRegisterResponseTask(response).execute();


				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// mPostCommentResponse.requestEndedWithError(error);
					showMessage(error.getMessage());
					showLayout(Constants.SHOW_SCREEN);
				}
			});
			req.setRetryPolicy(new DefaultRetryPolicy(
					SignInActivity.MY_SOCKET_TIMEOUT_MS,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			AppController.getInstance().addToRequestQueue(req,
					TAG_REGISTER);
		}else {
				showMessage(activity.getString(R.string.str_error_internet));
			}
	}


	public static String getUrlGender(String gender)
	{
		String retGender = "true";
		if (gender!=null && !gender.isEmpty() && gender.length()==1)
		{
			if (gender.equalsIgnoreCase("F"))
				retGender = "false";
		}
		return retGender;
	}
	public static int getCurrentYear()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	public static String getUrlAge(String dob)
	{
		String retAge = "";
		if (dob!=null && !dob.isEmpty())
		{

			try {
				int yr = Integer.parseInt(dob.substring(dob.lastIndexOf("/")+1));
				int currentYear = getCurrentYear();
				retAge = ""+(currentYear-yr);

			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return retAge;
	}

	public static String getUrlCountry(String country)
	{
		String retDOB = "";
		if (country!=null && !country.isEmpty()&& country.length()==3)
		{
			retDOB = country;
		}
		return retDOB;
	}
	private class ParseIdScanRegisterResponseTask extends AsyncTask<String, Void, RegisterResponse>{


		public String responseString = "";
		public ParseIdScanRegisterResponseTask(String responseString) {
			// TODO Auto-generated constructor stub
			this.responseString = responseString;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showLayout(Constants.SHOW_LOADING);
		}
		@Override
		protected RegisterResponse doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("M/d/yy hh:mm a");
			Gson gson = gsonBuilder.create();
			RegisterResponse response = gson.fromJson(SignInActivity.clearJsonResponse(this.responseString), RegisterResponse.class);
			return response;
		}
		@Override
		protected void onPostExecute(RegisterResponse response) {
			// TODO Auto-generated method stub
			super.onPostExecute(response);
			if (response == null || response.Data == null) {
				showMessage(activity.getString(R.string.str_error_server_general));
			} else if (response != null && response.Status != null ) {
				if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS))
					registerIdScanSuccess(response);
				else if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_EMIRATES_ID_ALREADY_EXIST))
					showMessage(activity.getString(R.string.str_error_emirates_id_already_exist));
				else if(response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_EMIRATES_ID))
					showMessage(activity.getString(R.string.str_error_wrong_emirates_id));
				else if(response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_EMAIL_ALREADY_EXIST))
					showMessage(activity.getString(R.string.str_error_email_already_exist));
				else
					showMessage(activity.getString(R.string.str_error_server_general));
			}
			else
				showMessage(activity.getString(R.string.str_error_server_general));



			showLayout(Constants.SHOW_SCREEN);
		}
	}

	private class ParseManualRegisterResponseTask extends AsyncTask<String, Void, RegisterResponse>{


		public String responseString = "";
		public ParseManualRegisterResponseTask(String responseString) {
			// TODO Auto-generated constructor stub
			this.responseString = responseString;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showLayout(Constants.SHOW_LOADING);
		}
		@Override
		protected RegisterResponse doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("M/d/yy hh:mm a");
			Gson gson = gsonBuilder.create();
			RegisterResponse response = gson.fromJson(SignInActivity.clearJsonResponse(this.responseString), RegisterResponse.class);
			return response;
		}
		@Override
		protected void onPostExecute(RegisterResponse response) {
			// TODO Auto-generated method stub
			super.onPostExecute(response);
			super.onPostExecute(response);
			if (response == null ) {
				showMessage(activity.getString(R.string.str_error_server_general));
			} else if (response != null && response.Status != null ) {
				if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS))
					registerManualSuccess(response);
				else if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_EMIRATES_ID_ALREADY_EXIST))
					showMessage(activity.getString(R.string.str_error_emirates_id_already_exist));
				else if(response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_EMIRATES_ID))
					showMessage(activity.getString(R.string.str_error_wrong_emirates_id));
				else if(response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_EMAIL_ALREADY_EXIST))
					showMessage(activity.getString(R.string.str_error_email_already_exist));
				else
					showMessage(activity.getString(R.string.str_error_server_general));
			}
			else
				showMessage(activity.getString(R.string.str_error_server_general));
			showLayout(Constants.SHOW_SCREEN);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			if (requestCode == FilesActivity.REQUEST_IMAGE_CAPTURE)
			{
				try {
					File file = new File(captureFileImageUri.getPath());
					if (file!= null && file.exists()){
						handleCroppedImage(captureFileImageUri);
					}
					else
						captureFileImageUri = null;
				} catch (NullPointerException e) {
					e.printStackTrace();
					captureFileImageUri = null;
				}
			}
		}
		else if (resultCode == RESULT_CANCELED)
		{
			captureFileImageUri = null;
		}

	}
	private void handleCroppedImage(Uri uri)
	{
		eIdfilePath = uri.getPath();
		File file = new File(eIdfilePath);
		double size =  file.length();
		if (file!= null && file.exists()){
			if (!FilesActivity.isFileSizeOk((int) size))	{
				Toast.makeText(this, activity.getString(R.string.str_max_file_seize), Toast.LENGTH_SHORT).show();
				return;
			}
			Bitmap bmp = getScaledBitmap(uri);
			if (bmp != null)
			{
				ivEidThumb.setImageBitmap(bmp);
			}
		}
	}
	public Bitmap getScaledBitmap(Uri uri )
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = true;
		Bitmap selectImg=null;
		InputStream inStr;
		try {
			inStr = getContentResolver().openInputStream(uri);
			selectImg = BitmapFactory.decodeStream(inStr, null, options);
			int width,height;

			float bmpWidth = selectImg.getWidth();
			float bmpHeight = selectImg.getHeight();
			float bmpRatio = (float) (bmpHeight/bmpWidth);

			int ivThumbWidth = ivEidThumb.getMeasuredWidth();
			int ivThumbHeight = ivEidThumb.getMeasuredHeight();
			if (bmpWidth>bmpHeight)
			{
				height = ivThumbHeight;
				width = (int) (height/bmpRatio);
			}
			else
			{
				width = ivThumbWidth;
				height = (int) (bmpRatio*width);
			}
			selectImg = Bitmap.createScaledBitmap(selectImg, width,height,true);
			int rotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(activity, activity.getContentResolver(), uri));
			if (rotation != 0) {
				selectImg = AddEditFileDialogFragment.rotateImage(selectImg, rotation);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return selectImg;
	}
	public int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	private class RegisterManualToServerTask extends AsyncTask<Void, Integer, UploadFileResponse> {

		String name, email, password, eId;

		RegisterManualToServerTask(String name, String email, String password, String eId)
		{
			this.eId = eId;
			this.name = name;
			this.email = email;
			this.password = password;
		}
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			showLayout(Constants.SHOW_LOADING);
			super.onPreExecute();
		}


		@Override
		protected UploadFileResponse doInBackground(Void... params) {
			return uploadFileInputStreamEntity();

		}

		@SuppressWarnings("deprecation")
		private UploadFileResponse uploadFileInputStreamEntity(){
			UploadFileResponse serverResponse = new UploadFileResponse();
			String responseString = null;

			if (EWatheqUtils.isInternetConenctionAvailable(activity)) {

				try {
					File sourceFile = new File(eIdfilePath);


					if (sourceFile != null && sourceFile.exists()) {
						HttpPost httppost = new HttpPost(Constants.URL_MANUAL_REGISTER + getManualRegisterUrlParametersString(name, email, password, eId));


						HttpParams httpParameters = new BasicHttpParams();
						// Set the timeout in milliseconds until a connection is established.
						int timeoutConnection = 30000;
						HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
						// Set the default socket timeout (SO_TIMEOUT)
						// in milliseconds which is the timeout for waiting for data.
						int timeoutSocket = 30000;
						HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

						HttpClient httpclient = new DefaultHttpClient(httpParameters);

						// Adding file data to http body
						InputStreamEntity reqEntity = new InputStreamEntity(
								new FileInputStream(sourceFile), sourceFile.length());
						reqEntity.setContentType("application/octet-stream");
						reqEntity.setChunked(true); // Send in multiple parts if needed
						httppost.setEntity(reqEntity);


						// Making server call
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity r_entity = response.getEntity();

						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == 200) {
							// Server response
							serverResponse.setresponse(EntityUtils.toString(r_entity));
							serverResponse.setuploadSuccess(true);

						} else {
							responseString = "Error occurred! Http Status Code: "
									+ statusCode;
							serverResponse.setresponse(activity.getString(R.string.str_error_server_general));
							serverResponse.setuploadSuccess(false);
						}
					} else {
						responseString = "File not exist";
						serverResponse.setresponse(activity.getString(R.string.str_error_server_general));
						serverResponse.setuploadSuccess(false);
					}

				} catch (ClientProtocolException e) {
					responseString = e.toString();
					serverResponse.setresponse(activity.getString(R.string.str_error_server_general));
					serverResponse.setuploadSuccess(false);
				} catch (IOException e) {
					responseString = e.toString();
					serverResponse.setresponse(activity.getString(R.string.str_error_server_general));
					serverResponse.setuploadSuccess(false);
				} catch (Exception e) {
					responseString = e.toString();
					serverResponse.setresponse(activity.getString(R.string.str_error_server_general));
					serverResponse.setuploadSuccess(false);
				}
			}
			else {
			//	showMessage(activity.getString(R.string.str_error_internet));
				serverResponse.setresponse(activity.getString(R.string.str_error_internet));
				serverResponse.setuploadSuccess(false);
			}

			return serverResponse;

		}

		@Override
		protected void onPostExecute(UploadFileResponse result) {
			super.onPostExecute(result);
			Log.e(activity.getLocalClassName(), "Response from server: " + result);
			// showing the server response in an alert dialog

			if (result.getUploadSuccess())
			{
				new ParseManualRegisterResponseTask(result.getResponse()).execute();
			}
			else
			{
				showMessage(result.getResponse());
			}
		}
	}
	private class CaptureImageTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			captureFileImageUri = FilesActivity.getOutputMediaFileUri(FilesActivity.REQUEST_IMAGE_CAPTURE,0);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (captureFileImageUri != null)
				captureImage();
		}
	}
}
