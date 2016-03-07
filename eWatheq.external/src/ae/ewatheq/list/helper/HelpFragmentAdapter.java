package ae.ewatheq.list.helper;

import ae.ewatheq.HelpSubFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;
import ae.ewatheq.external.R;

public class HelpFragmentAdapter extends FragmentPagerAdapter {

	public  static final int[] HELP_SUB_FRAGMENT_PHONE = new int[] {
		R.drawable.help01,
		R.drawable.help02,
		R.drawable.help03,
		R.drawable.help04
	};
	public  static final int[] HELP_SUB_FRAGMENT_TABLET = new int[] {
		R.drawable.help01,
		R.drawable.help02,
	};

	private int mCount ;
	private boolean isTablet ;

	public HelpFragmentAdapter(FragmentManager fm, boolean isTablet) {
		super(fm);
		this.isTablet = isTablet;
		if (isTablet)
			mCount = HELP_SUB_FRAGMENT_TABLET.length;
		else 
			mCount = HELP_SUB_FRAGMENT_PHONE.length;

	}

	@Override
	public Fragment getItem(int position) {
		if (isTablet)
			return HelpSubFragment.newInstance(HELP_SUB_FRAGMENT_TABLET[position]);
		else 
			return HelpSubFragment.newInstance(HELP_SUB_FRAGMENT_PHONE[position]);
	}

	@Override
	public int getCount() {
		return mCount;
	}




	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

	
}