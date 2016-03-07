package ae.ewatheq;

import ae.ewatheq.list.helper.HelpFragmentAdapter;
import ae.ewatheq.utils.TouchImageView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ae.ewatheq.internal.R;
public final class HelpSubFragment extends Fragment {
	private Activity activity;
	private View view;


	private int resId;
	private TouchImageView ivThumb ;
	
	

	
	
	private String filePath;
	
	public static String KEY_RES_ID= "res_id";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle args = getArguments();
		if (args != null)
		{	
			
			resId = args.getInt(KEY_RES_ID);
		}
	}
	public static HelpSubFragment newInstance(int resID) {
		HelpSubFragment fragmentDemo = new HelpSubFragment();
		Bundle args = new Bundle();
		args.putInt(KEY_RES_ID, resID);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.lout_fragment_help_sub,
				container, false);
	
		ivThumb = (TouchImageView)view.findViewById(R.id.iv_pic);
		
		
		ivThumb.setImageResource(resId);
	
		return view;
	}
	
}
