package ae.ewatheq;


import com.google.android.youtube.player.internal.ac;

import ae.ewatheq.list.helper.AboutUsListAdapter;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import ae.ewatheq.internal.R;


public class AboutUsFragment extends Fragment {


	private MainActivity activity;


//	private ListView lvAboutUs;
	private WebView wvAboutUs;
	private LinearLayout llAboutInterface;
	//private AboutUsListAdapter aboutUsListAdapter;
	/*private String[] mAboutNAListTitles;
	private String[] AboutNAFilesList;*/
	private ImageView ivBanner;
	// Drawer List
	private View view;
	private static int GUIDE_POSITION = 1;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();

		view = inflater.inflate(R.layout.lout_fragment_about_us, 
				container, false);
		llAboutInterface = (LinearLayout)view.findViewById(R.id.ll_about_interface);
		
		//lvAboutUs = (ListView)view.findViewById(R.id.lv_about_us_lists);
		wvAboutUs = (WebView)view.findViewById(R.id.wv_about_watheq);
		wvAboutUs.loadUrl("file:///android_asset/"+activity.getResources().getString(R.string.str_about_ewatheq_html_file));
		ivBanner = (ImageView)view.findViewById(R.id.iv_banner);
		/*mAboutNAListTitles = getResources().getStringArray(R.array.about_na_list_titles);
		AboutNAFilesList = getResources().getStringArray(R.array.about_na_files);*/
		/*aboutUsListAdapter = new AboutUsListAdapter(activity, mAboutNAListTitles);
		lvAboutUs.setAdapter(aboutUsListAdapter);

		lvAboutUs.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == GUIDE_POSITION)
				{
					startActivity(new Intent(activity, HelpActivity.class));

				}
				else {
					AboutUsSubFragmentDialog fragment =  AboutUsSubFragmentDialog.newInstance(AboutNAFilesList[position]);

					FragmentManager fm = activity.getSupportFragmentManager();
					fragment.show(fm, "show_add_edit_dialog");
				}
			}
		});*/
		llAboutInterface.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutUsSubFragmentDialog fragment =  AboutUsSubFragmentDialog.newInstance(activity.getString(R.string.str_about_interface_html_file));

				FragmentManager fm = activity.getSupportFragmentManager();
				fragment.show(fm, "show_add_edit_dialog");
			}
		});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		activity.setCurrentFragment(this);
		int deviceWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		BitmapDrawable bd=(BitmapDrawable) this.getResources().getDrawable(R.drawable.about_us_banner);
		double height=bd.getBitmap().getHeight();
		double width=bd.getBitmap().getWidth();
		double ratio = height/width;
		ivBanner.getLayoutParams().height = (int) (deviceWidth*ratio);
	}
}
