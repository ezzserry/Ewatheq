package ae.ewatheq;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.balysv.material.menu.MaterialMenuDrawable.IconState;
import com.balysv.material.menu.MaterialMenuView;
import ae.ewatheq.external.R;




@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class AboutUsSubFragmentDialog extends DialogFragment implements OnClickListener {

	private Activity activity;
	private View view;

	public static String KEY_HTML_FILE_PATH = "file";

	private WebView wbShowFile;


	private String filePath;
	private MaterialMenuView btnBack;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle args = getArguments();
		if (args != null)
		{	
			filePath =  args.getString(KEY_HTML_FILE_PATH);


		}
	}
	public static AboutUsSubFragmentDialog newInstance(String  filePath) {
		AboutUsSubFragmentDialog fragmentDemo = new AboutUsSubFragmentDialog();
		Bundle args = new Bundle();
		args.putString(KEY_HTML_FILE_PATH, filePath);
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
		wbShowFile = (WebView)view.findViewById(R.id.wv_show_pdf);
		if (btnBack != null)
		{
			btnBack.setOnClickListener(this);
			btnBack.setState(IconState.ARROW);
		}
		wbShowFile.setVisibility(View.VISIBLE);
		wbShowFile.getSettings().setJavaScriptEnabled(true); 
		wbShowFile.getSettings().setLoadWithOverviewMode(true);
		wbShowFile.getSettings().setUseWideViewPort(true);
		wbShowFile.getSettings().setDisplayZoomControls(false);
		wbShowFile.getSettings().setBuiltInZoomControls(false);
		wbShowFile.loadUrl("file:///android_asset/"+filePath);
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

}
