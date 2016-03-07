package ae.ewatheq;


import java.util.List;

import ae.ewatheq.app.AppController;
import ae.ewatheq.internal.R;
import ae.ewatheq.models.RegisterResponse;
import ae.ewatheq.models.RegisterResponseData;
import ae.ewatheq.models.eWatheqFolder;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.MaskMediaUtils;
import ae.ewatheq.utils.VolleyResponseUtf8String;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sqlite.DbHelper;

import ae.ewatheq.ocr.OCRActivity;


public class SignInActivity extends Activity implements OnClickListener {
    private Activity activity;
    private TextView btnSignin, btnRegisterIdScan, btnRegisterManual, btnForgotPassword;
    private EditText etEmail, etPassword;
    private RelativeLayout rlLoading;
    public static int START_OCR_REQUEST_CODE = 100;
    public static final String TAG_SIGN = "tag_signin";
    public static final String TAG_FORGOT_PASSWORD = "tag_forgot_password";
    public static int MY_SOCKET_TIMEOUT_MS = 30000;
    private String[] permissions = {
            "android.permission.CAMERA",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_WIFI_STATE"

    };
    private static final int PERMISSIONS_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.lout_activity_signin);
        MaskMediaUtils.SetSharedBoolParameter(SignInActivity.this,
                Constants.Network_wif, true);
        btnRegisterIdScan = (TextView) findViewById(R.id.btn_register_id_scan);
        btnForgotPassword = (TextView) findViewById(R.id.btn_forgot_password);
        btnRegisterManual = (TextView) findViewById(R.id.btn_register_manual);
        btnSignin = (TextView) findViewById(R.id.btn_sign_in);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        rlLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        btnRegisterIdScan.setOnClickListener(this);
        btnRegisterManual.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String error = extras.getString(OCRActivity.STR_OCR_DATA_ERROR);
            if (error != null && !error.isEmpty())
                showOCRResult();
        }
    }

    public void signInSuccess(RegisterResponse response) {
        SignInActivity.saveUserInfo(activity, response.Data);
        saveCategories(activity, response.Data.Categories);
        Intent intent = new Intent(activity, FilesActivity.class);
        startActivity(intent);
        finish();
    }

    public static void saveUserInfo(Context context, RegisterResponseData data) {
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_AGE, data.Age);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_AVAILABLE_QOUTA, data.Quota.AvalibleQuota);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_DOB, data.DateOfBirth);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_EMAIL, data.Email);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_EMIRATES_ID, data.EmiratesID);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_NAME, data.Name);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_TOTAL_QOUTA, data.Quota.UserQuota);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_USED_QOUTA, data.Quota.UsedQuota);
        EWatheqUtils.setPref(context, Constants.SHARED_PREFERENCES_USER_ID, data.UserID);
    }

    public static void clearUserInfo(Context context) {
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_AGE);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_AVAILABLE_QOUTA);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_DOB);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_EMAIL);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_EMIRATES_ID);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_NAME);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_TOTAL_QOUTA);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_USED_QOUTA);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFERENCES_USER_ID);
        EWatheqUtils.clearPref(context, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID);
    }

    public static void saveCategories(Context context, List<eWatheqFolder> folders) {
        DbHelper db = new DbHelper(context);
        db.insertFolder(folders);
    }

    public void showMessage(String errorMessage) {
        Toast.makeText(activity, errorMessage,
                Toast.LENGTH_LONG).show();
    }

    public void watheqRegisterTesting() {
        Intent result = new Intent(activity, RegisterActivity.class);
        result.putExtra(OCRActivity.STR_OCR_DATA_NAME, "Noman");
        result.putExtra(OCRActivity.STR_OCR_DATA_EMIRATES_ID, "784197896937949");
        result.putExtra(OCRActivity.STR_OCR_DATA_COUNTRY, "ARE");
        result.putExtra(OCRActivity.STR_OCR_DATA_DATE_OF_BIRTH, "780304");
        result.putExtra(OCRActivity.STR_OCR_DATA_GENDER, "F");

        startActivity(result);
        finish();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.btn_register_id_scan) {
            Intent intent = new Intent(activity, OCRActivity.class);
            startActivityForResult(intent, START_OCR_REQUEST_CODE);
            //watheqRegisterTesting();

        } else if (id == R.id.btn_forgot_password) {
            showForgotPasswordDialog();

        } else if (id == R.id.btn_register_manual) {
            Intent intent = new Intent(activity, RegisterActivity.class);
            intent.putExtra(Constants.KEY_IS_MANUAL_REGISTER, true);
            startActivity(intent);


        } else if (id == R.id.btn_sign_in) {
            boolean isInternetAvailable = EWatheqUtils.isInternetConenctionAvailable(activity);
            if (!isInternetAvailable) {
                // Internet Error
                return;
            }
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            boolean emailVerification = EWatheqUtils.verifyEmail(email);
            if (email.length() <= 0
                    || password.length() <= 0
                    )
                Toast.makeText(activity, activity.getResources().getString(R.string.str_all_fields_are_mendatory),
                        Toast.LENGTH_LONG).show();
            else if (!emailVerification)
                Toast.makeText(activity, activity.getResources().getString(R.string.str_invalid_email),
                        Toast.LENGTH_LONG).show();
            else
                signToServer(email, password);


        }
    }

    public void showLayout(int layout) {
        if (layout == Constants.SHOW_LOADING) {
            rlLoading.setVisibility(View.VISIBLE);
            btnRegisterIdScan.setEnabled(false);
            btnRegisterManual.setEnabled(false);
            btnSignin.setEnabled(false);
            etEmail.setEnabled(false);
            etPassword.setEnabled(false);
        } else if (layout == Constants.SHOW_SCREEN) {
            rlLoading.setVisibility(View.GONE);
            btnRegisterIdScan.setEnabled(true);
            btnRegisterManual.setEnabled(true);
            btnSignin.setEnabled(true);
            etEmail.setEnabled(true);
            etPassword.setEnabled(true);
        }

    }

    public String getUrlParametersString(final String email, final String password) {
        String params = Constants.URL_PARAMETER_USERNAME + Constants.URL_PARAMETER_EQQUAL + email + Constants.URL_PARAMETER_AND;
        params = params + Constants.URL_PARAMETER_PASSWORD + Constants.URL_PARAMETER_EQQUAL + password;
        return params;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        setLanguage();
        if (requestCode == START_OCR_REQUEST_CODE) {
            if (resultCode == RESULT_OK && intent != null
                    && intent.getExtras() != null) {
                Bundle extras = intent.getExtras();

                Intent mIntent = new Intent(activity, RegisterActivity.class);
                mIntent.putExtras(extras);

                startActivity(mIntent);
                finish();
            } else if (resultCode == RESULT_CANCELED && intent != null
                    && intent.getExtras() != null) {
                Bundle extras = intent.getExtras();
                String error = extras.getString(OCRActivity.STR_OCR_DATA_ERROR);
                if (error != null && !error.isEmpty())

                    restartActivity(activity.getResources().getString(R.string.str_emirates_id_detection_error));

            }
        }
    }

    public void showOCRResult() {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(activity.getResources().getString(R.string.str_emirates_id_detection));
        alertDialog.setMessage(activity.getResources().getString(R.string.str_emirates_id_detection_error));
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getResources().getString(R.string.str_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }

    public void showForgotPasswordDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(activity.getResources().getString(R.string.str_forgot_password_title));
        alertDialog.setCancelable(false);
        final EditText input = new EditText(activity);
        input.setHint(activity.getString(R.string.str_email));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getResources().getString(R.string.str_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                String email = input.getText().toString();
                boolean emailVerification = EWatheqUtils.verifyEmail(email);
                if (emailVerification) {
                    forgotPasswordToServer(email);
                } else {
                    showMessage(activity.getResources().getString(R.string.str_invalid_email));
                }
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getResources().getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }

    public String getForgotPasswordUrlParametersString(final String email) {

        String params = Constants.URL_PARAMETER_EMAIL + Constants.URL_PARAMETER_EQQUAL + email;


        params = EWatheqUtils.RemoveSpacesFromUrl(params);
        return params;
    }

    public void forgotPasswordToServer(final String email) {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            showLayout(Constants.SHOW_LOADING);
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_FORGOT_PASSWORD + getForgotPasswordUrlParametersString(email), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new ForgotPasswordResponseTask(response).execute();
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
                    TAG_FORGOT_PASSWORD);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }
    }
    public void signToServer(final String email, final String password) {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            showLayout(Constants.SHOW_LOADING);
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_LOGIN + getUrlParametersString(email, password), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    new ParseSignInResponseTask(response).execute();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // mPostCommentResponse.requestEndedWithError(error);
                   // showMessage(error.getLocalizedMessage());
                    showMessage(activity.getString(R.string.str_error_server_general));
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(req,
                    TAG_SIGN);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }

    }
    private class ForgotPasswordResponseTask extends AsyncTask<String, Void, RegisterResponse> {
        public String responseString = "";
        public ForgotPasswordResponseTask(String responseString) {
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
            if (response != null && response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)) {
                showMessage(response.Message);
            }
            else
                showMessage(activity.getString(R.string.str_error_server_general));


            showLayout(Constants.SHOW_SCREEN);
        }
    }
    public void restartActivity(String value) {
        Intent intent = new Intent(activity, SignInActivity.class);
        intent.putExtra(OCRActivity.STR_OCR_DATA_ERROR, value);
        startActivity(intent);
        finish();
    }
    public void setLanguage() {
        String lid = EWatheqUtils.getPrefString(activity, Constants.LANGUAGES_IDS);
        if (lid != null) {
            if (Integer.parseInt(lid) == Constants.LANGUAGE_ID_AR ||
                    Integer.parseInt(lid) == Constants.LANGUAGE_ID_ENG) {
                int langID = Integer.parseInt(lid);
                Configuration configuration = getApplicationContext().getResources().getConfiguration();
                configuration.locale = MaskMediaUtils.getLocale(Constants.LANGUAGE_CODES[langID - 1]);
                getApplicationContext().getResources().updateConfiguration(
                        configuration, getApplicationContext().getResources().getDisplayMetrics());
            }
        }
    }
    private class ParseSignInResponseTask extends AsyncTask<String, Void, RegisterResponse> {
        public String responseString = "";

        public ParseSignInResponseTask(String responseString) {
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
            RegisterResponse response = gson.fromJson(clearJsonResponse(this.responseString), RegisterResponse.class);
            return response;
        }
        @Override
        protected void onPostExecute(RegisterResponse response) {
            // TODO Auto-generated method stub
            super.onPostExecute(response);
            if (response == null || response.Data == null) {
                showMessage(activity.getString(R.string.str_error_server_general));
            } else if (response != null ) {
                if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_GENERAL_ERROR))
                    showMessage(activity.getString(R.string.str_error_server_general));
                else if(response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_CREDENTIALS))
                    showMessage(activity.getString(R.string.str_error_login_credentials_wrong));
                else if (response.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS))
                    signInSuccess(response);
                else
                    showMessage(activity.getString(R.string.str_error_server_general));
            }

            showLayout(Constants.SHOW_SCREEN);
        }
    }

    public static String clearJsonResponse(String response) {
        response.trim();
        if (response != null && response.length() > 3) {
            int smallBracketIndex = -1, largeBracketIndex = -1;
            int index = -1;
            if (response.contains("{"))
                smallBracketIndex = response.indexOf("{");
            if (response.contains("["))
                largeBracketIndex = response.indexOf("[");
            if (smallBracketIndex != -1 && largeBracketIndex != -1) {
                if (smallBracketIndex < largeBracketIndex)
                    index = smallBracketIndex;
                else
                    index = largeBracketIndex;
            } else if (largeBracketIndex != -1)
                index = largeBracketIndex;
            else if (smallBracketIndex != -1)
                index = smallBracketIndex;
            if (index != -1) {
                response = response.substring(index);
            }
        }
        return response;
    }
}
