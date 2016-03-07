package ae.ewatheq;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.balysv.material.menu.MaterialMenuView;
import com.balysv.material.menu.MaterialMenuDrawable.IconState;

import ae.ewatheq.list.helper.HelpFragmentAdapter;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.external.R;
@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class HelpActivity extends FragmentActivity {
	HelpFragmentAdapter mAdapter;
	ViewPager mPager;
	//PageIndicator mIndicator;
	MaterialMenuView btnBack;
	Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lout_activity_help);
		activity = this;
		EWatheqUtils.setPref(activity, Constants.SHARED_PREFERENCES_IS_HOME_SCREEN_OPENED_SECOND_TIME, true);

		mAdapter = new HelpFragmentAdapter(getSupportFragmentManager(),getResources().getBoolean(R.bool.isTablet));

		btnBack = (MaterialMenuView)findViewById(R.id.btn_back);
		if (btnBack != null)
		{
			btnBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			btnBack.setState(IconState.ARROW);
		}
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		/*mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);*/
	}
}