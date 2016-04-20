package ae.ewatheq;



import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.TouchImageView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.material.menu.MaterialMenuDrawable.IconState;
import com.balysv.material.menu.MaterialMenuView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import ae.ewatheq.internal.R;



@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class OpenFileDialogFragment extends DialogFragment implements OnClickListener {

	private Activity activity;
	private View view;


	private boolean isImage;
	private TouchImageView ivThumb ;
	private WebView wbShowFile;
	private ProgressBar pbLoading;
	private TextView tvTitle;

	private eWatheqFile file;
	private DisplayImageOptions options;
	private String filePath;
	private MaterialMenuView btnBack;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle args = getArguments();
		if (args != null)
		{	
			file = (eWatheqFile) args.getSerializable(AddEditFileDialogFragment.KEY_FILE);
			
			isImage = !EWatheqUtils.isDocumentByFileType(file.TypeID);
			if (isImage)
			{
				filePath = Constants.URL_WEBSITE_BASE+file.Thumbnaillink;
			}
			else
			{
				filePath =  Constants.URL_GOOGLE_DOCS+Constants.URL_WEBSITE_BASE+file.FileLink;
			}
		}
	}
	public static OpenFileDialogFragment newInstance(eWatheqFile file) {
		OpenFileDialogFragment fragmentDemo = new OpenFileDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable(AddEditFileDialogFragment.KEY_FILE, file);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		view = inflater.inflate(R.layout.lout_fragment_open_file,
				container, false);
		btnBack = (MaterialMenuView)view.findViewById(R.id.btn_back);

		ivThumb = (TouchImageView)view.findViewById(R.id.iv_pic);
		tvTitle = (TextView)view.findViewById(R.id.tv_top_header_title);
		
		wbShowFile = (WebView)view.findViewById(R.id.wv_show_pdf);
		tvTitle.setText(file.Title);
		pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
		if (btnBack != null)
		{
			btnBack.setOnClickListener(this);
			btnBack.setState(IconState.ARROW);
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

		if(isImage)
		{
			ivThumb.setVisibility(View.VISIBLE);
			wbShowFile.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(filePath, ivThumb, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {

					pbLoading.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					pbLoading.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					pbLoading.setVisibility(View.GONE);
				}
				
			});
		}
		else {
			ivThumb.setVisibility(View.GONE);
			wbShowFile.setVisibility(View.VISIBLE);
			wbShowFile.setWebChromeClient(new WebChromeClient() {
	            public void onProgressChanged(WebView view, int progress)   
	            {
	                //Make the bar disappear after URL is loaded, and changes string to Loading...
	                

	                // Return the app name after finish loading
	                if(progress > 95)
	                	pbLoading.setVisibility(View.GONE);
	                else 
	                	pbLoading.setVisibility(View.VISIBLE);
	                }
	            
	            });
			wbShowFile.setWebViewClient(new MyWebViewClient());
			
			wbShowFile.getSettings().setJavaScriptEnabled(true);
			wbShowFile.getSettings().setLoadWithOverviewMode(true);
			wbShowFile.getSettings().setUseWideViewPort(true);
			wbShowFile.getSettings().setDisplayZoomControls(false);
			this.wbShowFile.getSettings().setBuiltInZoomControls(true);
		    
			wbShowFile.loadUrl(filePath); 
		}

		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.getLayoutParams().width = activity.getWindowManager().getDefaultDisplay().getWidth();
		view.getLayoutParams().height = activity.getWindowManager().getDefaultDisplay().getHeight();

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub\
		//base.onClickListener(v);
		int id = v.getId();
		if (id ==  R.id.btn_back )
		{

			this.dismiss();
		}
	}
	private class MyWebViewClient extends WebViewClient {	
		 @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		    }

		 @Override
		public void onPageFinished(WebView view, String url) {
			 pbLoading.setVisibility(View.GONE);
				
			super.onPageFinished(view, url);
		}

		 @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			 pbLoading.setVisibility(View.VISIBLE);
			
			super.onPageStarted(view, url, favicon);
		}
	}
	

}
