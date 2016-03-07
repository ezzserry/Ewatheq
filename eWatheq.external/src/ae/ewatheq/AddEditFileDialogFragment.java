package ae.ewatheq;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;

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
import ae.ewatheq.interfaces.AddEditFileCallback;
import ae.ewatheq.list.helper.FilesAdapter;
import ae.ewatheq.models.AddEditFileResponse;
import ae.ewatheq.models.DeleteFileResponse;
import ae.ewatheq.models.UploadFileResponse;
import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.models.eWatheqFolder;
import ae.ewatheq.models.eWatheqFolderFiles;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import ae.ewatheq.utils.VolleyResponseUtf8String;

import com.balysv.material.menu.MaterialMenuDrawable.IconState;
import com.balysv.material.menu.MaterialMenuView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.soundcloud.android.crop.CropUtil;
import com.sqlite.DbHelper;

import android.widget.AdapterView.OnItemSelectedListener;

import ae.ewatheq.external.R;


@SuppressLint("NewApi")
public class AddEditFileDialogFragment extends DialogFragment implements OnClickListener {

    private FragmentActivity activity;
    private View view;
    private boolean isAdd;
    private boolean isImage;
    private EditText etTitle;
    private EditText etDesc;
    private ImageView ivThumb;
    private RelativeLayout rlImage;
    private View viewShadow;
    private TextView tvDate;
    private Button btnSave;
    private Button btnShare;
    private Button btnCancel;
    private LinearLayout llUpdateCategory;
    private TextView tvUpdateCategory;
    private CheckBox cbGrantAccessToNA;
    private ImageButton ibDelete;

    private RelativeLayout rlShare, rlLoading, rlGrantAccessToNA;
    private ProgressBar pbLoading;
    private eWatheqFile file;
    private String tempFilePath;
    private String fileNameWithExtension;
    private Calendar calImageDate;
    private int imageWidth;
    public static String KEY_IS_ADD = "is_add";
    public static String KEY_IS_IMAGE = "is_IMAGE";
    public static String KEY_FILE = "file";
    public static String KEY_SELECTED_FOLDER_ID = "selected_folder_id";
    public static String KEY_FILE_PATH = "file_path";
    public static final String TAG_UPDATE_FILE_META_DATA_TO_SERVER = "tag_update_file_meta";
    private DbHelper db;
    private List<eWatheqFolder> folders;
    private eWatheqFolder selectedFolder;
    private String selectedFolderServerId;
    private String[] foldersArray;
    private AddEditFileCallback callback;

    private boolean isTablet;
    private MaterialMenuView btnBack;
    private DisplayImageOptions options;
    public static String TAG_UPDTAE_FILE = "update_file";
    public static final int TEXT_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        calImageDate = Calendar.getInstance();
        activity = getActivity();
        try {
            callback = (AddEditFileCallback) getActivity();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_loading_deafault)
                .showImageForEmptyUri(R.drawable.ic_image_loading_deafault)
                .showImageOnFail(R.drawable.ic_image_loading_deafault)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        db = new DbHelper(activity);
        folders = db.getFoldersInfo();
        if (folders != null && folders.size() > 0) {
            foldersArray = new String[folders.size()];
            for (int i = 0; i < folders.size(); i++) {
                foldersArray[i] = folders.get(i).CategoryName;
            }
        }


        Bundle args = getArguments();

        if (args != null) {
            isAdd = args.getBoolean(KEY_IS_ADD);
            selectedFolderServerId = args.getString(KEY_SELECTED_FOLDER_ID, "");

            if (!isAdd) {
                file = (eWatheqFile) args.getSerializable(KEY_FILE);
                isImage = !file.isDocument();
            } else {
                isImage = args.getBoolean(KEY_IS_IMAGE);
                tempFilePath = args.getString(KEY_FILE_PATH);
                if (tempFilePath != null && !tempFilePath.isEmpty()) {
                    fileNameWithExtension = tempFilePath.substring(tempFilePath.lastIndexOf("/") + 1);
                }
            }

        }
    }

    public void setSelectedFolder() {
        int position = 0;
        if (selectedFolderServerId != null && !selectedFolderServerId.isEmpty()) {
            for (int i = 0; i < folders.size(); i++) {
                eWatheqFolder folder = folders.get(i);
                if (selectedFolderServerId.equalsIgnoreCase(folder.CategoryID)) {
                    selectedFolder = folder;
                    position = i;
                }
            }
        } else {
            selectedFolder = folders.get(position);
        }
        //  tvUpdateCategory.setSelection(position);
        tvUpdateCategory.setText(selectedFolder.CategoryName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
        view = inflater.inflate(R.layout.lout_fragment_add_edit_file,
                container, false);

        btnBack = (MaterialMenuView) view.findViewById(R.id.btn_back);
        ivThumb = (ImageView) view.findViewById(R.id.iv_image);
        etDesc = (EditText) view.findViewById(R.id.et_description);
        etTitle = (EditText) view.findViewById(R.id.et_title);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        rlImage = (RelativeLayout) view.findViewById(R.id.rl_image);
        viewShadow = view.findViewById(R.id.view_shadow);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnShare = (Button) view.findViewById(R.id.btn_share);

        tvUpdateCategory = (TextView) view.findViewById(R.id.tv_update_category);
        tvUpdateCategory.setOnClickListener(this);

        llUpdateCategory = (LinearLayout) view.findViewById(R.id.ll_update_category);

        cbGrantAccessToNA = (CheckBox) view.findViewById(R.id.cb_grant_access_to_na);
        ibDelete = (ImageButton) view.findViewById(R.id.ib_delete);
        rlShare = (RelativeLayout) view.findViewById(R.id.rl_share);
        rlLoading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        rlGrantAccessToNA = (RelativeLayout) view.findViewById(R.id.rl_grant_access_to_na);
        pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);

        isTablet = activity.getResources().getBoolean(R.bool.isTablet);

        tvDate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        ibDelete.setOnClickListener(this);
        ivThumb.setOnClickListener(this);


        if (btnBack != null) {
            btnBack.setOnClickListener(this);
            btnBack.setState(IconState.ARROW);
        }
        rlGrantAccessToNA.setVisibility(View.GONE);
        if (isAdd) {
            ibDelete.setVisibility(View.GONE);
            rlShare.setVisibility(View.GONE);
            llUpdateCategory.setVisibility(View.GONE);

        } else {
            if (file != null) {
                etTitle.setText(file.Title);
                etDesc.setText(file.Description);
                tvDate.setText(EWatheqUtils.getFormattedDate(file.Date, Constants.DATE_FORMAT_FOR_SERVER, Constants.DATE_FORMAT_ON_SCREEN));
                grantAccess(file.IsShared);
            }
            tvUpdateCategory.setVisibility(View.VISIBLE);
        }
        if (isAdd) {
            tvDate.setText(EWatheqUtils.getFormattedDate(calImageDate, Constants.DATE_FORMAT_ON_SCREEN));
        }
        setSelectedFolder();
        setTreeViewObserver();
        return view;
    }

    public void setTreeViewObserver() {
        if (rlImage != null && viewShadow != null) {
            ViewTreeObserver vto = rlImage.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    rlImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int height = rlImage.getMeasuredHeight();
                    viewShadow.getLayoutParams().height = height;
                }
            });
        }
    }

    public void setImageViewNewFile() {
        Bitmap src = null;




        if (tempFilePath != null && !tempFilePath.isEmpty()) {

            if (isImage) {
                src = BitmapFactory.decodeFile(tempFilePath);
                int rotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(activity, activity.getContentResolver(), Uri.fromFile(new File(tempFilePath))));
                if (rotation != 0) {
                    src = rotateImage(src,rotation);
                }
            } else
                src = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_pdf_deafault);
            if (src != null) {
                showImage(src);
            } else {
                src = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_image_loading_deafault);
                showImage(src);
            }
        } else if (!isImage) {
            src = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_pdf_deafault);
            if (src != null) {
                showImage(src);
            }
        }

    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
    public void showImage(Bitmap bmp) {
        double bitMapWidth = bmp.getWidth();
        double bitMapHeight = bmp.getHeight();

        double ratio = bitMapWidth / bitMapHeight;
        if (bitMapWidth > imageWidth)
            bitMapWidth = imageWidth;
        bitMapHeight = (int) (bitMapWidth / ratio);
        if (bitMapHeight > imageWidth) {
            bitMapHeight = imageWidth;
            bitMapWidth = (int) (bitMapHeight * ratio);
        }
        ivThumb.getLayoutParams().height = imageWidth;
        ivThumb.getLayoutParams().width = imageWidth;


        ivThumb.setImageBitmap(Bitmap.createScaledBitmap(bmp, (int) bitMapWidth, (int) bitMapHeight, true));
        if (isTablet)
            tvDate.getLayoutParams().width = imageWidth;
    }

    public void setImageView() {

        Bitmap bmp = null;
        File thumbFile = FilesAdapter.isFileExistInLocal(file);

        if (thumbFile != null && thumbFile.exists()) {
            try {
                bmp = BitmapFactory.decodeFile(thumbFile.getAbsolutePath());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (bmp == null) {

            ImageLoader.getInstance().displayImage(Constants.URL_WEBSITE_BASE + file.Thumbnaillink, ivThumb, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    FilesAdapter.saveThumbnail(loadedImage, imageUri);
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {

                }
            });
        } else {
            showImage(bmp);

        }
    }


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

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
    public void onResume() {
        // TODO Auto-generated method stub

        super.onResume();


        imageWidth = activity.getWindowManager()
                .getDefaultDisplay().getWidth() - 200;

        int width = activity.getWindowManager()
                .getDefaultDisplay().getWidth();
        int height = activity.getWindowManager()
                .getDefaultDisplay().getHeight();
        if (isTablet) {
            view.getLayoutParams().width = width - 200;

            imageWidth = (int) ((width - 200) * 0.35);
        } else {
            view.getLayoutParams().width = width;
            view.getLayoutParams().height = height;
            imageWidth = (int) (width - 40);
        }
        if (isAdd || !isImage)
            setImageViewNewFile();
        else {

            setImageView();

        }
        this.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        new DeleteAllTempFiles().execute();
    }

    // ItemDetailFragment.newInstance(item)
    public static AddEditFileDialogFragment newInstance(eWatheqFile file, boolean isAdd, boolean isImage, String tempFilePath, String serverId) {
        AddEditFileDialogFragment fragmentDemo = new AddEditFileDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(AddEditFileDialogFragment.KEY_IS_ADD, isAdd);
        args.putString(AddEditFileDialogFragment.KEY_SELECTED_FOLDER_ID, serverId);
        if (isAdd) {

            args.putBoolean(AddEditFileDialogFragment.KEY_IS_IMAGE, isImage);
            args.putString(AddEditFileDialogFragment.KEY_FILE_PATH, tempFilePath);
        } else {
            args.putSerializable(AddEditFileDialogFragment.KEY_FILE, file);
        }
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    public void showMessage(String errorMessage) {
        Toast.makeText(activity, errorMessage,
                Toast.LENGTH_LONG).show();
    }

    public void uploadFileSuccess(String response) {
        Toast.makeText(activity, response,
                Toast.LENGTH_LONG).show();
    }

    public void showLayout(int layout) {
        if (layout == Constants.SHOW_LOADING) {
            rlLoading.setVisibility(View.VISIBLE);

        } else if (layout == Constants.SHOW_SCREEN) {
            rlLoading.setVisibility(View.GONE);

        }

    }

    public void updateFileOnServer(eWatheqFile file) {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_EDIT_FILE + getFileUpdateUrlParameters(file), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //mPostCommentResponse.requestCompleted();
                    //showLayout(SHOW_BOOK_STORE);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    AddEditFileResponse fileResponse;

                    Gson gson = gsonBuilder.create();
                    fileResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), AddEditFileResponse.class);
                    if (fileResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)) {
                        UpdateSuccessFull(fileResponse.Data);
                        dismiss();
                    } else if (fileResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_CREDENTIALS)) {

                        showMessage(activity.getString(R.string.str_error_file_edit));
                        showLayout(Constants.SHOW_SCREEN);
                    } else {
                        showMessage(activity.getString(R.string.str_error_server_general));
                        showLayout(Constants.SHOW_SCREEN);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // mPostCommentResponse.requestEndedWithError(error);

                    showMessage(activity.getString(R.string.str_error_server_general));
                    showLayout(Constants.SHOW_SCREEN);
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    SignInActivity.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(req,
                    TAG_UPDTAE_FILE);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }
    }

    public void deleteFileFromServer(final eWatheqFile file) {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            showLayout(Constants.SHOW_LOADING);
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_DELETE_FILE + getDeleteFileUrlParameters(file), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //mPostCommentResponse.requestCompleted();
                    //showLayout(SHOW_BOOK_STORE);
                    GsonBuilder gsonBuilder = new GsonBuilder();


                    Gson gson = gsonBuilder.create();
                    DeleteFileResponse deleteResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), DeleteFileResponse.class);
                    if (deleteResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)) {
                        showMessage(activity.getString(R.string.str_response_file_delete));
                        deleteFileToLocal(file);
                        dismiss();
                    } else if (deleteResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_CREDENTIALS)) {
                        showMessage(activity.getString(R.string.str_error_file_delete));
                        showLayout(Constants.SHOW_SCREEN);
                    } else {
                        showMessage(activity.getString(R.string.str_error_server_general));
                        showLayout(Constants.SHOW_SCREEN);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // mPostCommentResponse.requestEndedWithError(error);

                    showMessage(activity.getString(R.string.str_error_server_general));
                    showLayout(Constants.SHOW_SCREEN);
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    SignInActivity.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(req,
                    TAG_UPDTAE_FILE);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }
    }

    /*public void grantAccessToNaOnServer (final eWatheqFile file, final String message )
    {
        showLayout(Constants.SHOW_LOADING);
        VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST,Constants.URL_SHARE_WITH_NA+getShareWithNAFileUrlParameters(file, message), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
                //showLayout(SHOW_BOOK_STORE);
                GsonBuilder gsonBuilder = new GsonBuilder();


                Gson gson = gsonBuilder.create();
                DeleteFileResponse deleteResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), DeleteFileResponse.class);
                if (deleteResponse.Status.equalsIgnoreCase(Constants.RESPONSE_SUCCESS)){
                    shareWithNAResponseSucessful(file, true);

                }
                else if (deleteResponse.Status.equalsIgnoreCase(Constants.RESPONSE_ERROR)){
                    uploadFileError(deleteResponse.Message);
                    grantAccess(false);

                }
                showLayout(Constants.SHOW_SCREEN);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mPostCommentResponse.requestEndedWithError(error);

                uploadFileError(error.getMessage());
                showLayout(Constants.SHOW_SCREEN);
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                SignInActivity.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(req,
                TAG_UPDTAE_FILE);
    }*/
    public String getFileUpdateUrlParameters(eWatheqFile file) {
        String param = "";
        param = param + Constants.URL_PARAMETER_FILE_ID + Constants.URL_PARAMETER_EQQUAL + file.FileID + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_TITLE + Constants.URL_PARAMETER_EQQUAL + file.Title + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_DESCRIPTION + Constants.URL_PARAMETER_EQQUAL + file.Description + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_FILE_TYPE + Constants.URL_PARAMETER_EQQUAL + file.TypeID + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_CATEGORY_ID + Constants.URL_PARAMETER_EQQUAL + selectedFolderServerId + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_ACTUAL_DATE + Constants.URL_PARAMETER_EQQUAL + file.Date;
        param = EWatheqUtils.RemoveSpacesFromUrl(param);
        return param;

    }

    public String getDeleteFileUrlParameters(eWatheqFile file) {
        String param = "";
        param = param + Constants.URL_PARAMETER_FILE_ID + Constants.URL_PARAMETER_EQQUAL + file.FileID;
        param = EWatheqUtils.RemoveSpacesFromUrl(param);
        return param;


    }

    public String getShareWithNAFileUrlParameters(eWatheqFile file, String message) {

        String param = "";
        param = param + Constants.URL_PARAMETER_FILE_ID + Constants.URL_PARAMETER_EQQUAL + file.FileID + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_MESSAGE + Constants.URL_PARAMETER_EQQUAL + message;
        param = EWatheqUtils.RemoveSpacesFromUrl(param);
        return param;
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, UploadFileResponse> {

        eWatheqFile file;
        private long totalSize = 0;

        UploadFileToServer(eWatheqFile file) {
            this.file = file;
        }

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            showLayout(Constants.SHOW_LOADING);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            showLayout(Constants.SHOW_LOADING);

            // updating progress bar value
            pbLoading.setProgress(progress[0]);

            // updating percentage value

        }

        @Override
        protected UploadFileResponse doInBackground(Void... params) {
            return uploadFileInputStreamEntity();

        }

        public String getFileUploadUrlParameters(long fileSize) {
            String param = "";
            param = param + Constants.URL_PARAMETER_TITLE + Constants.URL_PARAMETER_EQQUAL + file.Title + Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_DESCRIPTION + Constants.URL_PARAMETER_EQQUAL + file.Description + Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_FILE_TYPE + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getFileType(file.FileNamewithextension) + Constants.URL_PARAMETER_AND;
            //param = param + Constants.URL_PARAMETER_FILE_TYPE+Constants.URL_PARAMETER_EQQUAL+ "2"+Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_FILE_EXTENSION + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getFileExtension(file.FileNamewithextension) + Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_ACTUAL_DATE + Constants.URL_PARAMETER_EQQUAL + file.Date + Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_CATEGORY_ID + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID) + Constants.URL_PARAMETER_AND;
            param = param + Constants.URL_PARAMETER_FILE_SIZE + Constants.URL_PARAMETER_EQQUAL + fileSize;
            param = EWatheqUtils.RemoveSpacesFromUrl(param);
            return param;
            //title=&description=&filetype=1&fileextension=png&AcualDate=12/10/2015&categoryID=37&filesize=365877
            //title=&description=&filetype=1&fileextension=jpg&AcualDate=12/10/2015&categoryID=37&filesize=53241
        }

        @SuppressWarnings("deprecation")
        private UploadFileResponse uploadFileInputStreamEntity() {
            UploadFileResponse serverResponse = new UploadFileResponse();
            String responseString = null;


            try {
                File sourceFile = new File(tempFilePath);
                long fileSize = sourceFile.length();


                HttpPost httppost = new HttpPost(Constants.URL_ADD_FILE + getFileUploadUrlParameters(fileSize));


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

            return serverResponse;

        }

        @Override
        protected void onPostExecute(UploadFileResponse result) {
            super.onPostExecute(result);
            Log.e(activity.getLocalClassName(), "Response from server: " + result);
            // showing the server response in an alert dialog

            if (result.getUploadSuccess()) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                AddEditFileResponse fileResponse;

                Gson gson = gsonBuilder.create();
                fileResponse = gson.fromJson(SignInActivity.clearJsonResponse(result.getResponse()), AddEditFileResponse.class);
                if (fileResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)) {
                    UpdateSuccessFull(fileResponse.Data);
                    dismiss();
                } else if (fileResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_FAIL_WRONG_CREDENTIALS)) {
                    showMessage(activity.getString(R.string.str_error_file_add));
                    showLayout(Constants.SHOW_SCREEN);
                }
            } else {
                showMessage(activity.getString(R.string.str_error_server_general));
                showLayout(Constants.SHOW_SCREEN);
            }


        }
    }

    public void UpdateSuccessFull(eWatheqFile file) {
        if (isAdd) {
            showMessage(activity, activity.getString(R.string.str_response_file_add));
            addFileToLocal(file);
            if (callback != null)
                callback.updateFiles(selectedFolderServerId, true);

        } else {
            showMessage(activity, activity.getString(R.string.str_response_file_edit));
            editFileToLocal(file);
            if (callback != null)
                callback.updateFiles(selectedFolderServerId, true);
        }
        dismiss();
    }

    public void DeleteSuccessFull(eWatheqFile file) {

        showMessage(activity, "Update File Successfullly");
        deleteFileToLocal(file);
        if (callback != null)
            callback.updateFiles(selectedFolderServerId, true);


    }

    public void addFileToLocal(eWatheqFile file) {
        db.insertFile(file);
    }

    public void editFileToLocal(eWatheqFile file) {
        db.updateFile(file);
    }

    public void deleteFileToLocal(eWatheqFile file) {
        db.deleteFile(file);
        if (callback != null)
            callback.updateFiles(selectedFolderServerId, true);

    }

    public void shareWithNAResponseSucessful(eWatheqFile file, boolean grantAccess) {
        shareWithNASetVisibility(true);
        grantAccess(true);
        showMessage(activity, "Grant Access to NA Successfullly");
        editFileToLocal(file);
        if (callback != null)
            callback.updateFiles(selectedFolderServerId, true);

    }

    public void grantAccess(boolean grantAccess) {

        if (!isAdd) {
            cbGrantAccessToNA.setVisibility(View.VISIBLE);
            cbGrantAccessToNA.setChecked(grantAccess);
            if (grantAccess)
                cbGrantAccessToNA.setClickable(false);
            else {
                cbGrantAccessToNA.setClickable(true);
                cbGrantAccessToNA.setOnClickListener(this);
            }

        }


    }

    public void grantAccess(String grantAccess) {
        try {
            grantAccess(Boolean.parseBoolean(grantAccess));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void shareWithNASetVisibility(boolean isVisible) {
        if (isVisible) {
            rlGrantAccessToNA.setVisibility(View.VISIBLE);
        } else
            rlGrantAccessToNA.setVisibility(View.GONE);

    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public eWatheqFolderFiles uploadFile() {
        eWatheqFolderFiles file = new eWatheqFolderFiles();
        return file;
    }

    /*public void showGrantAccessToNaDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.str_grant_access_to_na));
        builder.setMessage(getString(R.string.str_grant_access_to_na_detail_message));

        // Use an EditText view to get user input.
        final EditText input = new EditText(activity);
        input.setHint(getString(R.string.str_grant_access_to_na_message_hint));
        input.setId(TEXT_ID);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.str_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                grantAccessToNaOnServer(file,value);
                FilesActivity.hideSoftKeyPad(activity, input);
                dialog.dismiss();
                return;
            }
        });

        builder.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FilesActivity.hideSoftKeyPad(activity, input);
                return;
            }
        });

        builder.create().show();

    }*/
    public void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.str_delete_file));
        builder.setMessage(getString(R.string.str_are_you_sure_to_delete_file));

        // Use an EditText view to get user input.


        builder.setPositiveButton(getString(R.string.str_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteFileFromServer(file);
                dialog.dismiss();
                return;
            }
        });

        builder.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                return;
            }
        });

        builder.create().show();

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            calImageDate.set(Calendar.YEAR, selectedYear);
            calImageDate.set(Calendar.MONTH, selectedMonth);
            calImageDate.set(Calendar.DAY_OF_MONTH, selectedDay);
            tvDate.setText(EWatheqUtils.getFormattedDate(calImageDate, Constants.DATE_FORMAT_ON_SCREEN));
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub\
        //base.onClickListener(v);

        int id = v.getId();
        if (id == R.id.btn_cancel || id == R.id.btn_back) {
            FilesActivity.hideSoftKeyPad(activity, etTitle);
            this.dismiss();
        } else if (id == R.id.tv_update_category) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getString(R.string.str_update_category));
            builder.setItems(foldersArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    selectedFolderServerId = folders.get(item).CategoryID;
                    setSelectedFolder();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (id == R.id.tv_date) {
            int year = calImageDate.get(Calendar.YEAR);
            int month = calImageDate.get(Calendar.MONTH);
            int day = calImageDate.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(activity, datePickerListener, year, month, day).show();
        } else if (id == R.id.btn_save) {
            FilesActivity.hideSoftKeyPad(activity, etTitle);
            if (isAdd) {
                eWatheqFile tempFile = new eWatheqFile();
                tempFile.CategoryID = selectedFolder.CategoryID;
                //tempFile.CategoryID = file.CategoryID;
                tempFile.Date = EWatheqUtils.getFormattedDate(tvDate.getText().toString(), Constants.DATE_FORMAT_ON_SCREEN, Constants.DATE_FORMAT_FOR_SERVER);
                tempFile.Description = etDesc.getText().toString();
                tempFile.FileID = "";
                tempFile.FileLink = tempFilePath;
                tempFile.FileNamewithextension = fileNameWithExtension;
                tempFile.IsShared = String.valueOf(cbGrantAccessToNA);
                tempFile.Title = etTitle.getText().toString();
                tempFile.TypeID = EWatheqUtils.getFileType(fileNameWithExtension);

                new UploadFileToServer(tempFile).execute();
            } else {
                eWatheqFile tempFile = new eWatheqFile();
                tempFile.CategoryID = file.CategoryID;
                tempFile.Date = EWatheqUtils.getFormattedDate(tvDate.getText().toString(), Constants.DATE_FORMAT_ON_SCREEN, Constants.DATE_FORMAT_FOR_SERVER);
                tempFile.Description = etDesc.getText().toString();
                tempFile.FileID = file.FileID;
                tempFile.FileNamewithextension = fileNameWithExtension;
                tempFile.IsShared = String.valueOf(cbGrantAccessToNA);
                tempFile.Title = etTitle.getText().toString();
                tempFile.TypeID = file.TypeID;

                updateFileOnServer(tempFile);
            }
        } else if (id == R.id.ib_delete) {
            showDeleteConfirmationDialog();
        } else if (id == R.id.cb_grant_access_to_na) {
            grantAccess(false);
            //	showGrantAccessToNaDialog();
        } else if (id == R.id.iv_image) {
            if (!isAdd) {
                OpenFileDialogFragment fragment = OpenFileDialogFragment.newInstance(file);
                FragmentManager fm = activity.getSupportFragmentManager();
                fragment.show(fm, "show_add_edit_dialog");
            }
        } else if (id == R.id.btn_share) {
            new DownloadFileFromURL().execute();
        }
    }

    public void shareFile(File outFile) {
        if (file != null) {


            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {""};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            // the attachment
            if (outFile.exists())
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outFile));
            // the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.str_email_subject) + etTitle.getText().toString());
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        ProgressDialog pDialog;
        File outFile;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = createDialog();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;

            // Downloading Other Data

            try {
                outFile = EWatheqUtils.getTempFile(file);
                if (outFile != null) {
                    URL url = new URL(Constants.URL_WEBSITE_BASE + file.FileLink);
                    URLConnection conection = url.openConnection();
                    conection.setConnectTimeout(30000);
                    conection.connect();

                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);


                    //Open the empty db as the output stream
                    OutputStream output = new FileOutputStream(outFile);
                    // Output stream to write file


                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                }


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }


            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
                pDialog = null;
            }
            shareFile(outFile);

        }

    }

    private ProgressDialog createDialog() {

        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage(activity.getString(R.string.str_downloading));
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;

    }

    class DeleteAllTempFiles extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... f_url) {
            try {
                EWatheqUtils.getDeleteAllTempFiles();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return "";
        }
    }


}
